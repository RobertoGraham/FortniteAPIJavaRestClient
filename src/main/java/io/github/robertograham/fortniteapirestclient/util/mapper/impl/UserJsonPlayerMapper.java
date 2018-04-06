package io.github.robertograham.fortniteapirestclient.util.mapper.impl;

import io.github.robertograham.fortniteapirestclient.domain.Player;
import io.github.robertograham.fortniteapirestclient.util.mapper.Mapper;
import org.json.JSONObject;

public class UserJsonPlayerMapper implements Mapper<String, Player> {

    @Override
    public Player map(String userJson) {
        JSONObject userJsonObject = new JSONObject(userJson);

        Player player = new Player();

        player.setId(userJsonObject.getString("id"));
        player.setUsername(userJsonObject.getString("displayName"));

        return player;
    }
}
