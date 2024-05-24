package org.wallentines.mcore.amqp;

import com.rabbitmq.client.*;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.wallentines.mcore.messenger.Message;
import org.wallentines.mcore.messenger.Messenger;
import org.wallentines.mcore.messenger.MessengerModule;
import org.wallentines.mcore.messenger.MessengerType;
import org.wallentines.mcore.util.PacketBufferUtil;
import org.wallentines.midnightlib.event.EventHandler;
import org.wallentines.midnightlib.event.HandlerList;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.TimeoutException;


public class AMQPMessenger extends Messenger {

    private static final Logger LOGGER = LoggerFactory.getLogger("AMQPMessenger");

    private final Connection connection;
    private final Channel channel;
    private final Set<String> queues = new HashSet<>();
    private final Map<String, HandlerList<Message>> handlers = new HashMap<>();

    private final boolean durable;
    private final int queueTTL;

    private final DeliverCallback callback;

    public AMQPMessenger(MessengerModule module, String url, @Nullable String user, @Nullable String pass, @Nullable String vhost, boolean durable, int queueTTL) throws IOException, TimeoutException {
        super(module);

        this.durable = durable;
        this.queueTTL = queueTTL;

        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(url);
        if(user != null) factory.setUsername(user);
        if(pass != null) factory.setPassword(pass);
        if(vhost != null) factory.setVirtualHost(vhost);


        connection = factory.newConnection();
        channel = connection.createChannel();

        this.callback = (tag, delivery) -> {
            String queue = delivery.getEnvelope().getRoutingKey();
            HandlerList<Message> subscribers = handlers.get(queue);
            if(subscribers == null) return;

            ByteBuf read = Unpooled.wrappedBuffer(delivery.getBody());
            Message msg = new Message(this, queue, read);
            subscribers.invoke(msg);
        };
    }


    @Override
    public void subscribe(Object listener, String queue, EventHandler<Message> handler) {

        try {
            addQueue(queue);
        } catch (IOException ex) {
            LOGGER.warn("An error occurred while subscribing to a message queue!", ex);
        }
        handlers.compute(queue, (k,v) -> {
            if(v == null) v = new HandlerList<>();
            v.register(listener, handler);
            return v;
        });
    }

    @Override
    public void unsubscribe(Object listener, String queue) {

        HandlerList<Message> subscribed = handlers.get(queue);
        if(subscribed == null) return;

        subscribed.unregisterAll(listener);

    }

    @Override
    public void publish(String queue, ByteBuf message) {
        try {

            addQueue(queue);
            channel.basicPublish("", queue, null, PacketBufferUtil.getBytes(message));

        } catch (IOException ex) {
            LOGGER.warn("An error occurred while publishing a message!", ex);
        }
    }

    @Override
    public void publish(String queue, int ttl, ByteBuf message) {
        try {

            addQueue(queue);
            AMQP.BasicProperties.Builder builder = new AMQP.BasicProperties.Builder().expiration(Objects.toString(ttl));
            channel.basicPublish("", queue, builder.build(), PacketBufferUtil.getBytes(message));

        } catch (IOException ex) {
            LOGGER.warn("An error occurred while publishing a message!", ex);
        }
    }

    private void addQueue(String queue) throws IOException {

        if (queues.add(queue)) {
            Map<String, Object> args = new HashMap<>();
            if(queueTTL > 0) args.put("x-expires", queueTTL);

            channel.queueDeclare(queue, durable, false, false, args);
            channel.basicConsume(queue, true, callback, tag -> { });
        }
    }

    @Override
    public void onShutdown() {
        if(connection != null) {
            try {
                connection.close();
            } catch (IOException ex) {
                LOGGER.warn("An error occurred while closing an AMQP messenger!", ex);
            }
        }
    }

    public static final MessengerType TYPE = (module, params) -> {

        String host = params.getString("url");
        String username = params.getOrDefault("username", (String) null);
        String password = params.getOrDefault("password", (String) null);
        String vhost = params.getOrDefault("virtual_host", (String) null);
        boolean durable = params.getOrDefault("durable", false);
        int channelTTL = params.getOrDefault("channel_expiry", 60000).intValue();

        try {
            return new AMQPMessenger(module, host, username, password, vhost, durable, channelTTL);
        } catch (IOException | TimeoutException ex) {
            LOGGER.warn("An exception occurred while loading the AMQP messenger!", ex);
            return null;
        }
    };

    public static void register() {

        MessengerType.REGISTRY.register("amqp", TYPE);
    }
}
