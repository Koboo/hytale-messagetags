package eu.koboo.myplugin;

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
    protected void shutdown() {
        getLogger().atInfo().log("MyPlugin was successfully shutdown!");
        super.shutdown();
    }
}
