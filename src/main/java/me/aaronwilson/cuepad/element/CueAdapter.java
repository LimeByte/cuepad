package me.aaronwilson.cuepad.element;

import java.lang.reflect.Type;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

public class CueAdapter implements JsonSerializer<Cue>, JsonDeserializer<Cue> {

    @Override
    public JsonElement serialize(Cue cue, Type type, JsonSerializationContext context) {
        JsonObject object = new JsonObject();
        object.addProperty("name", cue.getName());
        object.addProperty("path", cue.getSource());
        object.addProperty("singleton", cue.isSingleton());
        return object;
    }


    @Override
    public Cue deserialize(JsonElement element, Type type, JsonDeserializationContext context) throws JsonParseException {
        JsonObject object = element.getAsJsonObject();
        String name = object.get("name").getAsString();
        String filePath = object.get("path").getAsString();

        Cue cue = new Cue(name, filePath);

        if (object.has("singleton")) {
            cue.setSingleton(object.get("singleton").getAsBoolean());
        }

        return cue;
    }

}
