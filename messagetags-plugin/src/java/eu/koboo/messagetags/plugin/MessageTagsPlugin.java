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
        //getCommandRegistry().registerCommand(new CommandTagsTest());
    }
}
