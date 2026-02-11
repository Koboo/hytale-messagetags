package eu.koboo.messagetags.api.tests;

import com.hypixel.hytale.server.core.Message;
import eu.koboo.messagetags.api.MessageTags;
import org.junit.jupiter.api.Assertions;

public class TestUtils {

    public static void assertMessage(String expected, Message message) {
        String json = MessageTags.toJson(message);
        print(json);
        if(expected != null && !expected.isEmpty()) {
            Assertions.assertEquals(expected, json);
        }
    }

    public static void assertStripped(String expected, String stripped) {
        print(stripped);
        if(expected != null && !expected.isEmpty()) {
            Assertions.assertEquals(expected, stripped);
        }
    }

    public static void print(String json) {
        System.out.println("============ START ============");
        System.out.println(json);
        System.out.println("============= END =============");
    }

}
