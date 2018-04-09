package io.github.robertograham.fortniteapirestclient.service.account.mapper;

import io.github.robertograham.fortniteapirestclient.service.account.model.Account;
import io.github.robertograham.fortniteapirestclient.util.JSONObjectHelper;
import io.github.robertograham.fortniteapirestclient.util.mapper.Mapper;
import org.json.JSONObject;

public class AccountJsonAccountMapper implements Mapper<String, Account> {

    private JSONObjectHelper jsonObjectHelper;

    public AccountJsonAccountMapper() {
        jsonObjectHelper = new JSONObjectHelper();
    }

    @Override
    public Account mapFrom(String userJson) {
        JSONObject accountJsonObject = new JSONObject(userJson);

        Account account = new Account();

        jsonObjectHelper.consumeValueExtractedWithKey(account::setId, JSONObject::getString, accountJsonObject, "id");
        jsonObjectHelper.consumeValueExtractedWithKey(account::setDisplayName, JSONObject::getString, accountJsonObject, "displayName");

        return account;
    }
}
