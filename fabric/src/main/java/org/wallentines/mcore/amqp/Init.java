package org.wallentines.mcore.amqp;

import net.fabricmc.api.ModInitializer;

public class Init implements ModInitializer {

    @Override
    public void onInitialize() {
        AMQPMessenger.register();
    }
}
