package com.scorecard.utils;

import com.scorecard.persistence.models.Event;
import com.scorecard.persistence.models.Game;
import com.scorecard.utils.builders.EventBuilder;
import com.scorecard.utils.builders.GameBuilder;
import com.scorecard.utils.enums.Sports;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.Date;
import java.util.Objects;

public class WebSocketUtils {
    public static boolean isInitialEvent(String queueMessage) {
        JSONObject jsonObject = new JSONObject(queueMessage);
        return !jsonObject.has("gameId");
    }

    public static Game parseGamePayload(String gamePayload){
        JSONObject jsonObject = new JSONObject(gamePayload);
        Long gameId = jsonObject.getLong("id");
        Sports sport = Arrays.stream(Sports.values())
                .filter(sportEnum -> sportEnum.name().toUpperCase().equals(jsonObject.getString("sport")))
                .findFirst()
                .get();
        String teams[] = jsonObject.getJSONArray("teams").toList().stream().map(team -> team.toString()).toArray(String[]::new);
        String information = jsonObject.getString("information");
        Long startDateMillis = jsonObject.isNull("startDate") ? null : jsonObject.getLong("startDate");
        Date startDate = null;
        if(Objects.nonNull(startDateMillis)){
            startDate = new Date(startDateMillis);
        }
        return GameBuilder.getInstance()
                .gameId(gameId)
                .sport(sport)
                .teams(teams)
                .information(information)
                .startDate(startDate)
                .build();
    }

    public static Event parseEventPayload(String eventPayload){
        JSONObject jsonObject = new JSONObject(eventPayload);
        Long eventId = jsonObject.getLong("id");
        Long gameId = jsonObject.getLong("gameId");
        Date eventDate = new Date(jsonObject.getLong("eventDate"));
        String information = jsonObject.getString("information");
        return EventBuilder.getInstance()
                .eventId(eventId)
                .gameId(gameId)
                .eventDate(eventDate)
                .information(information)
                .build();
    }
}
