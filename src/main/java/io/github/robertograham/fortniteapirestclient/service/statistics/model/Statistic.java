package io.github.robertograham.fortniteapirestclient.service.statistics.model;

public class Statistic {

    private String name;
    private Integer value;
    private String window;
    private Integer ownerType;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    public String getWindow() {
        return window;
    }

    public void setWindow(String window) {
        this.window = window;
    }

    public Integer getOwnerType() {
        return ownerType;
    }

    public void setOwnerType(Integer ownerType) {
        this.ownerType = ownerType;
    }

    @Override
    public String toString() {
        return "Statistic{" +
                "name='" + name + '\'' +
                ", value=" + value +
                ", window='" + window + '\'' +
                ", ownerType=" + ownerType +
                '}';
    }
}
