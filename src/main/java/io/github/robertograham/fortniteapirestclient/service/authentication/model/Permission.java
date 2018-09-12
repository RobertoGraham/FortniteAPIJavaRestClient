package io.github.robertograham.fortniteapirestclient.service.authentication.model;

import com.google.api.client.util.Key;

public class Permission {

    @Key
    private String resource;
    @Key
    private Integer action;

    public String getResource() {
        return resource;
    }

    public void setResource(String resource) {
        this.resource = resource;
    }

    public Integer getAction() {
        return action;
    }

    public void setAction(Integer action) {
        this.action = action;
    }

    @Override
    public String toString() {
        return "Permission{" +
                "resource='" + resource + '\'' +
                ", action=" + action +
                '}';
    }
}
