package io.github.robertograham.fortniteapirestclient.util;

import org.json.JSONObject;

import java.util.function.BiFunction;
import java.util.function.Consumer;

public class JSONObjectHelper implements ConsumeValueExtractedWithKey<JSONObject> {

    @Override
    public <VALUE_TYPE> void consumeValueExtractedWithKey(Consumer<VALUE_TYPE> valueConsumer,
                                                          BiFunction<JSONObject, String, VALUE_TYPE> extractFunction,
                                                          JSONObject jsonObject,
                                                          String key) {
        if (jsonObject.has(key))
            valueConsumer.accept(extractFunction.apply(jsonObject, key));
    }
}
