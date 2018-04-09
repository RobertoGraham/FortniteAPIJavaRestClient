package io.github.robertograham.fortniteapirestclient.service.statistics.mapper;

import io.github.robertograham.fortniteapirestclient.service.statistics.model.Statistic;
import io.github.robertograham.fortniteapirestclient.util.JSONObjectHelper;
import io.github.robertograham.fortniteapirestclient.util.mapper.Mapper;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class StatisticsJsonStatisticsListMapper implements Mapper<String, List<Statistic>> {

    private JSONObjectHelper jsonObjectHelper;

    public StatisticsJsonStatisticsListMapper() {
        jsonObjectHelper = new JSONObjectHelper();
    }

    @Override
    public List<Statistic> mapFrom(String statisticsJson) {
        JSONArray statisticsJsonArray = new JSONArray(statisticsJson);

        return StreamSupport.stream(statisticsJsonArray.spliterator(), false)
                .map(JSONObject.class::cast)
                .map(statisticJsonObject -> {
                    Statistic statistic = new Statistic();

                    jsonObjectHelper.consumeValueExtractedWithKey(statistic::setName, JSONObject::getString, statisticJsonObject, "name");
                    jsonObjectHelper.consumeValueExtractedWithKey(statistic::setValue, JSONObject::getInt, statisticJsonObject, "value");
                    jsonObjectHelper.consumeValueExtractedWithKey(statistic::setWindow, JSONObject::getString, statisticJsonObject, "window");
                    jsonObjectHelper.consumeValueExtractedWithKey(statistic::setOwnerType, JSONObject::getInt, statisticJsonObject, "ownerType");

                    return statistic;
                })
                .collect(Collectors.toList());
    }
}
