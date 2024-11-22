package org.wallentines.mcore.amqp;

import org.bukkit.plugin.java.JavaPlugin;
import org.wallentines.mcore.messenger.MessengerModule;
import org.wallentines.smi.AmqpMessenger;


public class Plugin extends JavaPlugin {

    @Override
    public void onLoad() {
        AmqpMessenger.register(MessengerModule.REGISTRY);
    }
}
