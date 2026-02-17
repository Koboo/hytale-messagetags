package eu.koboo.messagetags.api.tests;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.hypixel.hytale.server.core.Message;
import eu.koboo.messagetags.api.MessageTags;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;

import java.awt.*;

@Execution(ExecutionMode.CONCURRENT)
@TestInstance(TestInstance.Lifecycle.PER_METHOD)
public abstract class AbstractTest {

    private static final Gson GSON = new GsonBuilder()
        .setPrettyPrinting()
        .serializeNulls()
        .create();

    boolean beautifyOutput = false;

    public AbstractTest() {
        if(this.getClass().isAnnotationPresent(BeautifyJson.class)) {
            this.beautifyOutput = true;
        }
    }

    protected void assertMessage(String expected, Message message) {
        String json = MessageTags.toJson(message);
        String beautifiedMessage = beautify(json);
        print(beautifiedMessage);
        if (expected != null && !expected.isEmpty()) {
            String beautifiedExpected = beautify(expected);
            Assertions.assertEquals(beautifiedExpected, beautifiedMessage);
        }
    }

    protected void assertStripped(String expected, String stripped) {
        print(stripped);
        if (expected != null && !expected.isEmpty()) {
            Assertions.assertEquals(expected, stripped);
        }
    }

    private String beautify(String json) {
        if(!beautifyOutput) {
            return json;
        }
        if(json == null || json.isEmpty()) {
            return json;
        }
        JsonObject object = JsonParser.parseString(json).getAsJsonObject();
        return GSON.toJson(object);
    }

    protected void print(String json) {
        System.out.println("============ START ============");
        System.out.println(json);
        System.out.println("============= END =============");
    }
}
