package io.github.robertograham.fortniteapirestclient.service.account.model;

import com.google.api.client.util.Key;

public class AuthId {

    @Key
    private String id;
    @Key
    private String type;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "AuthId{" +
                "id='" + id + '\'' +
                ", type='" + type + '\'' +
                '}';
    }
}
