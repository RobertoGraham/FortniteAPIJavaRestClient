package io.github.robertograham.fortniteapirestclient.service.account.model;

import java.time.LocalDateTime;

public class Eula {

    private String key;
    private int version;
    private int revision;
    private String title;
    private String body;
    private String locale;
    private LocalDateTime createdTimestamp;
    private LocalDateTime lastModifiedTimestamp;
    private String agentUserName;
    private String status;
    private boolean custom;
    private boolean wasDeclined;
    private boolean hasResponse;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public int getRevision() {
        return revision;
    }

    public void setRevision(int revision) {
        this.revision = revision;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getLocale() {
        return locale;
    }

    public void setLocale(String locale) {
        this.locale = locale;
    }

    public LocalDateTime getCreatedTimestamp() {
        return createdTimestamp;
    }

    public void setCreatedTimestamp(LocalDateTime createdTimestamp) {
        this.createdTimestamp = createdTimestamp;
    }

    public LocalDateTime getLastModifiedTimestamp() {
        return lastModifiedTimestamp;
    }

    public void setLastModifiedTimestamp(LocalDateTime lastModifiedTimestamp) {
        this.lastModifiedTimestamp = lastModifiedTimestamp;
    }

    public String getAgentUserName() {
        return agentUserName;
    }

    public void setAgentUserName(String agentUserName) {
        this.agentUserName = agentUserName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public boolean isCustom() {
        return custom;
    }

    public void setCustom(boolean custom) {
        this.custom = custom;
    }

    public boolean isWasDeclined() {
        return wasDeclined;
    }

    public void setWasDeclined(boolean wasDeclined) {
        this.wasDeclined = wasDeclined;
    }

    public boolean isHasResponse() {
        return hasResponse;
    }

    public void setHasResponse(boolean hasResponse) {
        this.hasResponse = hasResponse;
    }

    @Override
    public String toString() {
        return "Eula{" +
                "key='" + key + '\'' +
                ", version=" + version +
                ", revision=" + revision +
                ", title='" + title + '\'' +
                ", body='" + body + '\'' +
                ", locale='" + locale + '\'' +
                ", createdTimestamp=" + createdTimestamp +
                ", lastModifiedTimestamp=" + lastModifiedTimestamp +
                ", agentUserName='" + agentUserName + '\'' +
                ", status='" + status + '\'' +
                ", custom=" + custom +
                ", wasDeclined=" + wasDeclined +
                ", hasResponse=" + hasResponse +
                '}';
    }
}
