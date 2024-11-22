package org.wallentines.mcore.amqp;

import com.velocitypowered.api.plugin.Plugin;
import com.velocitypowered.api.proxy.ProxyServer;
import org.wallentines.mcore.messenger.MessengerModule;
import org.wallentines.smi.AmqpMessenger;

import javax.inject.Inject;

@Plugin(id="amqp-messenger")
public class ProxyPlugin {

    @Inject
    public ProxyPlugin(ProxyServer server) {
        AmqpMessenger.register(MessengerModule.REGISTRY);
    }

}
