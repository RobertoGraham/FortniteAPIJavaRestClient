package io.github.robertograham.fortniteapirestclient.domain;

public class StatsGroup {

    private Stats solo;
    private Stats duo;
    private Stats squad;

    public Stats getSolo() {
        return solo;
    }

    public void setSolo(Stats solo) {
        this.solo = solo;
    }

    public Stats getDuo() {
        return duo;
    }

    public void setDuo(Stats duo) {
        this.duo = duo;
    }

    public Stats getSquad() {
        return squad;
    }

    public void setSquad(Stats squad) {
        this.squad = squad;
    }

    @Override
    public String toString() {
        return "StatsGroup{" +
                "solo=" + solo +
                ", duo=" + duo +
                ", squad=" + squad +
                '}';
    }
}
