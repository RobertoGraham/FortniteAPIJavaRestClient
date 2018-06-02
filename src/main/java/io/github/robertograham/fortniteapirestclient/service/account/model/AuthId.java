package io.github.robertograham.fortniteapirestclient.service.account.model;

public class AuthId {

    private String id;
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
