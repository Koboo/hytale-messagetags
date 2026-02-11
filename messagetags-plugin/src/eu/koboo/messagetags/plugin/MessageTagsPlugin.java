package eu.koboo.messagetags.plugin;

import com.hypixel.hytale.server.core.plugin.JavaPlugin;
import com.hypixel.hytale.server.core.plugin.JavaPluginInit;

import javax.annotation.Nonnull;

public class MessageTagsPlugin extends JavaPlugin {

    public MessageTagsPlugin(@Nonnull JavaPluginInit init) {
        super(init);
    }

    @Override
    protected void setup() {
        super.setup();
        // Only register test and debug commands for explicit purposes
        if ("true".equalsIgnoreCase(System.getenv("TEST"))) {
            getCommandRegistry().registerCommand(new CommandTagsTest());
        }
    }
}
