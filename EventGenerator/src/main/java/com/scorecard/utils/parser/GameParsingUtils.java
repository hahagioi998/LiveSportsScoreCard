package com.scorecard.utils.parser;

import com.scorecard.event.model.Event;
import com.scorecard.event.model.Game;
import com.scorecard.utils.builder.GameBuilder;
import com.scorecard.utils.enums.Sports;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class GameParsingUtils {

    public static Sports getSportEnum(String sport) {
        return Arrays.stream(Sports.values()).filter(sportEnum -> sportEnum.getName().toUpperCase().equals(sport)).findFirst().get();
    }

    public static Game parseGames(HashMap gameMap){
        String teams[] = ((List<String>)gameMap.get("teams")).toArray(String[]::new);
        Long id = Long.parseLong(gameMap.get("id").toString());
        String information = gameMap.get("information").toString();
        Sports sport = GameParsingUtils.getSportEnum(gameMap.get("sport").toString());
        ArrayList eventList = (ArrayList) gameMap.get("events");
        List<Event> events = EventParserUtils.parseEvents(eventList);
        return GameBuilder.getInstance()
            .id(id)
            .sport(sport)
            .teams(teams)
            .events(events)
            .information(information)
            .build();
    }

}
