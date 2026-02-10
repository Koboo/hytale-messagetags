package eu.koboo.messagetags.plugin;

import com.hypixel.hytale.component.Ref;
import com.hypixel.hytale.component.Store;
import com.hypixel.hytale.server.core.Message;
import com.hypixel.hytale.server.core.command.system.CommandContext;
import com.hypixel.hytale.server.core.command.system.basecommands.AbstractCommandCollection;
import com.hypixel.hytale.server.core.command.system.basecommands.AbstractPlayerCommand;
import com.hypixel.hytale.server.core.console.ConsoleSender;
import com.hypixel.hytale.server.core.entity.entities.Player;
import com.hypixel.hytale.server.core.universe.PlayerRef;
import com.hypixel.hytale.server.core.universe.world.World;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
import eu.koboo.messagetags.api.MessageTags;

import javax.annotation.Nonnull;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class CommandTagsTest extends AbstractCommandCollection {

    public CommandTagsTest() {
        super("tags", "Prints out test messages and their json representation");
        addSubCommand(new Test1("1"));
        addSubCommand(new Test2("2"));
        addSubCommand(new Test3("3"));
        addSubCommand(new Test4("4"));
        addSubCommand(new Test5("5"));
        addSubCommand(new Test6("6"));
        addSubCommand(new Test7("7"));
        addSubCommand(new Test8("8"));
        addSubCommand(new Test9("9"));
        addSubCommand(new Test10("10"));
    }

    public static class Test1 extends AbstractPlayerCommand {

        public Test1(String name) {
            super(name, "");
        }

        @Override
        protected void execute(@Nonnull CommandContext context,
                               @Nonnull Store<EntityStore> store,
                               @Nonnull Ref<EntityStore> ref,
                               @Nonnull PlayerRef playerRef,
                               @Nonnull World world) {
            Player player = store.ensureAndGetComponent(ref, Player.getComponentType());
            Message parse = MessageTags.parse("<bold>Aw<ul>e<&6>so</ul>me <color:#fcba03>test <color:&4>New co<r>lo<em>r");
            ConsoleSender.INSTANCE.sendMessage(Message.raw(MessageTags.toJson(parse)));
            player.sendMessage(parse);
        }
    }

    public static class Test2 extends AbstractPlayerCommand {

        public Test2(String name) {
            super(name, "");
        }

        @Override
        protected void execute(@Nonnull CommandContext context,
                               @Nonnull Store<EntityStore> store,
                               @Nonnull Ref<EntityStore> ref,
                               @Nonnull PlayerRef playerRef,
                               @Nonnull World world) {
            Player player = store.ensureAndGetComponent(ref, Player.getComponentType());
            player.sendMessage(MessageTags.parse("<bold><color:&4>Test<newline><italic>New line with tag"));
            player.sendMessage(MessageTags.parse("<bold><color:&4>Test\n<italic>New line with linebreak"));
        }
    }

    public static class Test3 extends AbstractPlayerCommand {

        public Test3(String name) {
            super(name, "");
        }

        @Override
        protected void execute(@Nonnull CommandContext context,
                               @Nonnull Store<EntityStore> store,
                               @Nonnull Ref<EntityStore> ref,
                               @Nonnull PlayerRef playerRef,
                               @Nonnull World world) {
            Player player = store.ensureAndGetComponent(ref, Player.getComponentType());
            Message parse = MessageTags.parse("<bold><color:&4><lang:server.commands.help.desc><newline><italic>Testing translations");
            ConsoleSender.INSTANCE.sendMessage(Message.raw(MessageTags.toJson(parse)));
            player.sendMessage(parse);
        }
    }

    public static class Test4 extends AbstractPlayerCommand {

        public Test4(String name) {
            super(name, "");
        }

        @Override
        protected void execute(@Nonnull CommandContext context,
                               @Nonnull Store<EntityStore> store,
                               @Nonnull Ref<EntityStore> ref,
                               @Nonnull PlayerRef playerRef,
                               @Nonnull World world) {
            Player player = store.ensureAndGetComponent(ref, Player.getComponentType());
            Message parse = MessageTags.parse("<&b><link:https://github.com/Koboo>CLICK ME</link>");
            ConsoleSender.INSTANCE.sendMessage(Message.raw(MessageTags.toJson(parse)));
            player.sendMessage(parse);
        }
    }

    public static class Test5 extends AbstractPlayerCommand {

        public Test5(String name) {
            super(name, "");
        }

        @Override
        protected void execute(@Nonnull CommandContext context,
                               @Nonnull Store<EntityStore> store,
                               @Nonnull Ref<EntityStore> ref,
                               @Nonnull PlayerRef playerRef,
                               @Nonnull World world) {
            Player player = store.ensureAndGetComponent(ref, Player.getComponentType());
            Message parse = MessageTags.parse("<&b><link:https://github.com/Koboo><lang:server.commands.help.desc></link>Test<&a>NewMessage<newline>");
            ConsoleSender.INSTANCE.sendMessage(Message.raw(MessageTags.toJson(parse)));
            player.sendMessage(parse);
        }
    }

    public static class Test6 extends AbstractPlayerCommand {

        public Test6(String name) {
            super(name, "");
        }

        @Override
        protected void execute(@Nonnull CommandContext context,
                               @Nonnull Store<EntityStore> store,
                               @Nonnull Ref<EntityStore> ref,
                               @Nonnull PlayerRef playerRef,
                               @Nonnull World world) {
            Player player = store.ensureAndGetComponent(ref, Player.getComponentType());
            Message parse = MessageTags.parse("<color:TT>Test invalid tag");
            ConsoleSender.INSTANCE.sendMessage(Message.raw(MessageTags.toJson(parse)));
            player.sendMessage(parse);
        }
    }

    public static class Test7 extends AbstractPlayerCommand {

        public Test7(String name) {
            super(name, "");
        }

        @Override
        protected void execute(@Nonnull CommandContext context,
                               @Nonnull Store<EntityStore> store,
                               @Nonnull Ref<EntityStore> ref,
                               @Nonnull PlayerRef playerRef,
                               @Nonnull World world) {
            Player player = store.ensureAndGetComponent(ref, Player.getComponentType());
            Message parse = MessageTags.strip("<bold>Aw<ul>e<&6>so</ul>me <color:#fcba03>test <color:&4>But every<r>thing <em>stripped");
            ConsoleSender.INSTANCE.sendMessage(Message.raw(MessageTags.toJson(parse)));
            player.sendMessage(parse);
        }
    }

    public static class Test8 extends AbstractPlayerCommand {

        public Test8(String name) {
            super(name, "");
        }

        @Override
        protected void execute(@Nonnull CommandContext context,
                               @Nonnull Store<EntityStore> store,
                               @Nonnull Ref<EntityStore> ref,
                               @Nonnull PlayerRef playerRef,
                               @Nonnull World world) {
            Player player = store.ensureAndGetComponent(ref, Player.getComponentType());
            Message parse = MessageTags.parse("<gradient:blue:red:yellow>Let's test gradients right now! Wuuuhuuu!");
            ConsoleSender.INSTANCE.sendMessage(Message.raw(MessageTags.toJson(parse)));
            player.sendMessage(parse);
        }
    }

    public static class Test9 extends AbstractPlayerCommand {

        public Test9(String name) {
            super(name, "");
        }

        @Override
        protected void execute(@Nonnull CommandContext context,
                               @Nonnull Store<EntityStore> store,
                               @Nonnull Ref<EntityStore> ref,
                               @Nonnull PlayerRef playerRef,
                               @Nonnull World world) {
            Player player = store.ensureAndGetComponent(ref, Player.getComponentType());
            String message = "Hello world (in transitions)!";
            player.sendMessage(MessageTags.parse(transitionTag(0f) + message));
            player.sendMessage(MessageTags.parse(transitionTag(0.1f) + message));
            player.sendMessage(MessageTags.parse(transitionTag(0.2f) + message));
            player.sendMessage(MessageTags.parse(transitionTag(0.3f) + message));
            player.sendMessage(MessageTags.parse(transitionTag(0.4f) + message));
            player.sendMessage(MessageTags.parse(transitionTag(0.5f) + message));
            player.sendMessage(MessageTags.parse(transitionTag(0.6f) + message));
            player.sendMessage(MessageTags.parse(transitionTag(0.7f) + message));
            player.sendMessage(MessageTags.parse(transitionTag(0.8f) + message));
            player.sendMessage(MessageTags.parse(transitionTag(0.9f) + message));
            player.sendMessage(MessageTags.parse(transitionTag(1f) + message));
        }

        private String transitionTag(float progress) {
            return "<transition:blue:red:yellow:" + progress + ">";
        }
    }

    public static class Test10 extends AbstractPlayerCommand {

        public Test10(String name) {
            super(name, "");
            setAllowsExtraArguments(true);
        }

        @Override
        protected void execute(@Nonnull CommandContext context,
                               @Nonnull Store<EntityStore> store,
                               @Nonnull Ref<EntityStore> ref,
                               @Nonnull PlayerRef playerRef,
                               @Nonnull World world) {
            String inputString = context.getInputString();
            String[] split = inputString.split(" ");
            split[0] = null; // label
            split[1] = null; // argument
            inputString = String.join(" ", Arrays.stream(split).filter(Objects::nonNull).toArray(String[]::new));
            Player player = store.ensureAndGetComponent(ref, Player.getComponentType());
            player.sendMessage(Message.raw(inputString));
            player.sendMessage(MessageTags.parse(inputString));
        }
    }
}
