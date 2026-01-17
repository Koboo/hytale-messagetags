package eu.koboo.myplugin.commands;

import com.hypixel.hytale.component.Ref;
import com.hypixel.hytale.component.Store;
import com.hypixel.hytale.math.vector.Vector3d;
import com.hypixel.hytale.math.vector.Vector3f;
import com.hypixel.hytale.math.vector.Vector3i;
import com.hypixel.hytale.server.core.Message;
import com.hypixel.hytale.server.core.asset.type.blocktype.config.BlockType;
import com.hypixel.hytale.server.core.command.system.CommandContext;
import com.hypixel.hytale.server.core.command.system.basecommands.AbstractPlayerCommand;
import com.hypixel.hytale.server.core.entity.InteractionManager;
import com.hypixel.hytale.server.core.entity.entities.Player;
import com.hypixel.hytale.server.core.modules.entity.component.HeadRotation;
import com.hypixel.hytale.server.core.modules.entity.component.TransformComponent;
import com.hypixel.hytale.server.core.modules.interaction.InteractionModule;
import com.hypixel.hytale.server.core.universe.PlayerRef;
import com.hypixel.hytale.server.core.universe.world.World;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
import com.hypixel.hytale.server.core.util.TargetUtil;
import org.checkerframework.checker.nullness.compatqual.NonNullDecl;

public class GetTargetBlockCommand extends AbstractPlayerCommand {

    public GetTargetBlockCommand() {
        super("gettargetblock", "Prints your targeted block");
    }

    @Override
    protected void execute(@NonNullDecl CommandContext context,
                           @NonNullDecl Store<EntityStore> store,
                           @NonNullDecl Ref<EntityStore> ref,
                           @NonNullDecl PlayerRef playerRef,
                           @NonNullDecl World world) {
        world.execute(() -> {
            int maxDistance = 16;
            Vector3i blockPosition = TargetUtil.getTargetBlock(
                    ref,
                    maxDistance,
                    store);
            if(blockPosition == null) {
                context.sendMessage(Message.raw("No block found!"));
                return;
            }
            BlockType blockType = world.getBlockType(blockPosition);
            if (blockType == null) {
                context.sendMessage(Message.raw("No block found!"));
                return;
            }
            TransformComponent transformComponent = store.getComponent(ref, TransformComponent.getComponentType());
            assert transformComponent != null;
            Vector3d position = transformComponent.getPosition();
            double distance = position.distanceTo(blockPosition);
            context.sendMessage(Message.raw("Looking at block: " + blockType.getId() + " (" + distance + " blocks away)"));
        });
    }
}
