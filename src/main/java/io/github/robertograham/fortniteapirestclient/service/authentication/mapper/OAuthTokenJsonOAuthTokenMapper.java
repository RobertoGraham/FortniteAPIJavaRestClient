package io.github.robertograham.fortniteapirestclient.service.authentication.mapper;

import io.github.robertograham.fortniteapirestclient.service.authentication.model.OAuthToken;
import io.github.robertograham.fortniteapirestclient.service.authentication.model.Permission;
import io.github.robertograham.fortniteapirestclient.util.JSONObjectHelper;
import io.github.robertograham.fortniteapirestclient.util.mapper.Mapper;
import io.github.robertograham.fortniteapirestclient.util.mapper.impl.StringLocalDateTimeMapper;
import org.json.JSONObject;

import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class OAuthTokenJsonOAuthTokenMapper implements Mapper<String, OAuthToken> {

    private StringLocalDateTimeMapper stringLocalDateTimeMapper;
    private JSONObjectHelper jsonObjectHelper;

    public OAuthTokenJsonOAuthTokenMapper() {
        stringLocalDateTimeMapper = new StringLocalDateTimeMapper();
        jsonObjectHelper = new JSONObjectHelper();
    }

    @Override
    public OAuthToken mapFrom(String oAuthTokenJson) {
        JSONObject oAuthTokenJsonObject = new JSONObject(oAuthTokenJson);

        OAuthToken oAuthToken = new OAuthToken();

        jsonObjectHelper.consumeValueExtractedWithKey(oAuthToken::setAccessToken, JSONObject::getString, oAuthTokenJsonObject, "access_token");
        jsonObjectHelper.consumeValueExtractedWithKey(oAuthToken::setExpiresIn, JSONObject::getLong, oAuthTokenJsonObject, "expires_in");
        jsonObjectHelper.consumeValueExtractedWithKey(oAuthToken::setTokenType, JSONObject::getString, oAuthTokenJsonObject, "token_type");
        jsonObjectHelper.consumeValueExtractedWithKey(oAuthToken::setRefreshToken, JSONObject::getString, oAuthTokenJsonObject, "refresh_token");
        jsonObjectHelper.consumeValueExtractedWithKey(oAuthToken::setRefreshExpires, JSONObject::getLong, oAuthTokenJsonObject, "refresh_expires");
        jsonObjectHelper.consumeValueExtractedWithKey(oAuthToken::setAccountId, JSONObject::getString, oAuthTokenJsonObject, "account_id");
        jsonObjectHelper.consumeValueExtractedWithKey(oAuthToken::setClientId, JSONObject::getString, oAuthTokenJsonObject, "client_id");
        jsonObjectHelper.consumeValueExtractedWithKey(oAuthToken::setInternalClient, JSONObject::getBoolean, oAuthTokenJsonObject, "internal_client");
        jsonObjectHelper.consumeValueExtractedWithKey(oAuthToken::setClientService, JSONObject::getString, oAuthTokenJsonObject, "client_service");
        jsonObjectHelper.consumeValueExtractedWithKey(oAuthToken::setApp, JSONObject::getString, oAuthTokenJsonObject, "app");
        jsonObjectHelper.consumeValueExtractedWithKey(oAuthToken::setInAppId, JSONObject::getString, oAuthTokenJsonObject, "in_app_id");
        jsonObjectHelper.consumeValueExtractedWithKey(oAuthToken::setExpiresAt, (jsonObject, key) -> stringLocalDateTimeMapper.mapFrom(jsonObject.getString(key)), oAuthTokenJsonObject, "expires_at");
        jsonObjectHelper.consumeValueExtractedWithKey(oAuthToken::setRefreshExpiresAt, (jsonObject, key) -> stringLocalDateTimeMapper.mapFrom(jsonObject.getString(key)), oAuthTokenJsonObject, "refresh_expires_at");
        jsonObjectHelper.consumeValueExtractedWithKey(oAuthToken::setLastPasswordValidation, (jsonObject, key) -> stringLocalDateTimeMapper.mapFrom(jsonObject.getString(key)), oAuthTokenJsonObject, "lastPasswordValidation");
        jsonObjectHelper.consumeValueExtractedWithKey(oAuthToken::setPerms, (jsonObject, key) -> StreamSupport.stream(jsonObject.getJSONArray(key).spliterator(), false)
                .map(JSONObject.class::cast)
                .map(permissionJsonObject -> {
                    Permission permission = new Permission();

                    jsonObjectHelper.consumeValueExtractedWithKey(permission::setAction, JSONObject::getInt, permissionJsonObject, "action");
                    jsonObjectHelper.consumeValueExtractedWithKey(permission::setResource, JSONObject::getString, permissionJsonObject, "resource");

                    return permission;
                }).collect(Collectors.toList()), oAuthTokenJsonObject, "perms");

        return oAuthToken;
    }
}
