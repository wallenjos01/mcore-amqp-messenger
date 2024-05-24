package org.wallentines.mcore.amqp;

import org.bukkit.plugin.java.JavaPlugin;


public class Plugin extends JavaPlugin {

    @Override
    public void onLoad() {
        AMQPMessenger.register();
    }
}
