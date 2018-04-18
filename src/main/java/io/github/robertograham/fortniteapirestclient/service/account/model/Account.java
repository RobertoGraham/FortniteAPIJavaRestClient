package io.github.robertograham.fortniteapirestclient.service.account.model;

import java.util.Map;

public class Account {

    private String id;
    private String displayName;
    private Map<String, Object> links;
    private Map<String, ExternalAuth> externalAuths;

    public Map<String, Object> getLinks() {
        return links;
    }

    public void setLinks(Map<String, Object> links) {
        this.links = links;
    }

    public Map<String, ExternalAuth> getExternalAuths() {
        return externalAuths;
    }

    public void setExternalAuths(Map<String, ExternalAuth> externalAuths) {
        this.externalAuths = externalAuths;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    @Override
    public String toString() {
        return "Account{" +
                "id='" + id + '\'' +
                ", displayName='" + displayName + '\'' +
                ", links=" + links +
                ", externalAuths=" + externalAuths +
                '}';
    }
}
