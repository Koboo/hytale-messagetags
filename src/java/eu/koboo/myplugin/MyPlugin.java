package eu.koboo.myplugin;

import com.hypixel.hytale.common.plugin.PluginIdentifier;
import com.hypixel.hytale.server.core.HytaleServer;
import com.hypixel.hytale.server.core.plugin.JavaPlugin;
import com.hypixel.hytale.server.core.plugin.JavaPluginInit;
import eu.koboo.myplugin.commands.GetPosCommand;
import eu.koboo.myplugin.commands.GetTargetBlockCommand;

import javax.annotation.Nonnull;

public class MyPlugin extends JavaPlugin{

    public MyPlugin(@Nonnull JavaPluginInit init) {
        super(init);
    }

    @Override
    protected void setup() {
        getCommandRegistry().registerCommand(new GetPosCommand());
        getCommandRegistry().registerCommand(new GetTargetBlockCommand());
        getLogger().atInfo().log("MyPlugin was successfully setup!");
        super.setup();
    }

    @Override
    protected void start() {
        // You can disable core plugins here
        // Uncomment to disable Hytale's Teleport plugin
        //PluginIdentifier identifier = PluginIdentifier.fromString("Hytale:Teleport");
        //HytaleServer.get().getPluginManager().unload(identifier);
        // You can also override previously registered command here,
        // by registering in "start()" instead of "setup()"
        super.start();
    }

    @Override
    protected void shutdown() {
        getLogger().atInfo().log("MyPlugin was successfully shutdown!");
        super.shutdown();
    }
}
