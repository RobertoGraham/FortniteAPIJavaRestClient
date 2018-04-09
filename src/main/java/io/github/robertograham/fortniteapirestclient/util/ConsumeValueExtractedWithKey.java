package io.github.robertograham.fortniteapirestclient.util;

import java.util.function.BiFunction;
import java.util.function.Consumer;

public interface ConsumeValueExtractedWithKey<VALUE_HOLDER> {

    <VALUE_TYPE> void consumeValueExtractedWithKey(Consumer<VALUE_TYPE> valueConsumer, BiFunction<VALUE_HOLDER, String, VALUE_TYPE> extractValueFromHolderWithKeyFunction, VALUE_HOLDER valueHolder, String key);
}
