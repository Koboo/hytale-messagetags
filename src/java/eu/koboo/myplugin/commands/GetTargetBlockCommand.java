package eu.koboo.myplugin.commands;

import com.hypixel.hytale.component.Ref;
import com.hypixel.hytale.component.Store;
import com.hypixel.hytale.server.core.Message;
import com.hypixel.hytale.server.core.command.system.CommandContext;
import com.hypixel.hytale.server.core.command.system.basecommands.AbstractPlayerCommand;
import com.hypixel.hytale.server.core.universe.PlayerRef;
import com.hypixel.hytale.server.core.universe.Universe;
import com.hypixel.hytale.server.core.universe.world.World;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;

import javax.annotation.Nonnull;
import java.io.IOException;
import java.nio.file.Path;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;

public class GetTargetBlockCommand extends AbstractPlayerCommand {

    public GetTargetBlockCommand() {
        super("gettargetblock", "Prints your targeted block");
    }

    @Override
    protected void execute(@Nonnull CommandContext ctx,
                           @Nonnull Store<EntityStore> store,
                           @Nonnull Ref<EntityStore> ref,
                           @Nonnull PlayerRef playerRef,
                           @Nonnull World world) {

        String upsideDownName = "upsidedown";
        World upsideDownWorld;
        if (Universe.get().isWorldLoadable(upsideDownName)) {
            try {
                CompletableFuture<World> future = Universe.get().loadWorld(upsideDownName);
                upsideDownWorld = future.join();
            } catch (CompletionException e) {
                ctx.sendMessage(Message.raw("Erm, no loading: " + e.getMessage()));
                return;
            }
        } else {
            upsideDownWorld = Universe.get().getWorld(upsideDownName);
        }
        if (upsideDownWorld == null) {
            Path path = Universe.get().getPath();
            Path upsidedownPath = path.resolve("worlds/" + upsideDownName);
            try {
                copyDirectory(
                        path.resolve("worlds/default"),
                        upsidedownPath
                );
            } catch (IOException exp) {
                ctx.sendMessage(Message.raw("Erm, no: " + exp.getMessage()));
                return;
            }
            try {
                CompletableFuture<World> worldFuture = Universe.get().makeWorld(
                        upsideDownName,
                        upsidedownPath,
                        world.getWorldConfig(),
                        false
                );
                upsideDownWorld = worldFuture.join();
            } catch (CompletionException e) {
                ctx.sendMessage(Message.raw("Erm, no making: " + e.getMessage()));
                return;
            }
            if (!upsideDownWorld.isStarted()) {
                try {
                    CompletableFuture<Void> future = upsideDownWorld.start();
                    future.join();
                } catch (CompletionException e) {
                    ctx.sendMessage(Message.raw("Erm, no starting: " + e.getMessage()));
                    return;
                }
            }
        }
        // We probably got the world now
        ctx.sendMessage(Message.raw("Trying to teleport now..."));
    }

    private void copyDirectory(Path source, Path target) throws IOException {

    }
}
