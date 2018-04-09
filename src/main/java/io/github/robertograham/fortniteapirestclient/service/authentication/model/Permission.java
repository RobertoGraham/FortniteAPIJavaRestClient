package io.github.robertograham.fortniteapirestclient.service.authentication.model;

public class Permission {

    private String resource;
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
