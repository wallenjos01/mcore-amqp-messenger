package org.wallentines.mcore.amqp;

import net.fabricmc.api.ModInitializer;
import org.wallentines.mcore.messenger.MessengerModule;
import org.wallentines.smi.AmqpMessenger;

public class Init implements ModInitializer {

    @Override
    public void onInitialize() {
        AmqpMessenger.register(MessengerModule.REGISTRY);
    }
}
