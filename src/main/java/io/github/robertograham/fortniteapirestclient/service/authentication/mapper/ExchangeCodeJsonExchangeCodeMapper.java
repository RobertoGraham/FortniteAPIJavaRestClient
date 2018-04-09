package io.github.robertograham.fortniteapirestclient.service.authentication.mapper;

import io.github.robertograham.fortniteapirestclient.service.authentication.model.ExchangeCode;
import io.github.robertograham.fortniteapirestclient.util.JSONObjectHelper;
import io.github.robertograham.fortniteapirestclient.util.mapper.Mapper;
import org.json.JSONObject;

public class ExchangeCodeJsonExchangeCodeMapper implements Mapper<String, ExchangeCode> {

    private JSONObjectHelper jsonObjectHelper;

    public ExchangeCodeJsonExchangeCodeMapper() {
        jsonObjectHelper = new JSONObjectHelper();
    }

    @Override
    public ExchangeCode mapFrom(String exchangeCodeJson) {
        JSONObject exchangeCodeJsonObject = new JSONObject(exchangeCodeJson);

        ExchangeCode exchangeCode = new ExchangeCode();

        jsonObjectHelper.consumeValueExtractedWithKey(exchangeCode::setExpiresInSeconds, JSONObject::getInt, exchangeCodeJsonObject, "expiresInSeconds");
        jsonObjectHelper.consumeValueExtractedWithKey(exchangeCode::setCode, JSONObject::getString, exchangeCodeJsonObject, "code");
        jsonObjectHelper.consumeValueExtractedWithKey(exchangeCode::setCreatingClientId, JSONObject::getString, exchangeCodeJsonObject, "creatingClientId");

        return exchangeCode;
    }
}
