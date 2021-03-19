package com.scorecard.utils.parser;

import com.scorecard.event.model.Event;
import com.scorecard.utils.builder.EventBuilder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class EventParserUtils {

    public static List<Event> parseEvents(ArrayList eventsObject){
        return (List<Event>) eventsObject.stream()
                           .map(eventObj -> (HashMap) eventObj)
                           .map(eventMap -> {
                               String id = ((HashMap<?, ?>) eventMap).get("id").toString();
                               String gameId = ((HashMap<?, ?>) eventMap).get("gameId").toString();
                               String information = ((HashMap<?, ?>) eventMap).get("information").toString();
                               return EventBuilder.getInstance().id(Long.parseLong(id))
                                                                .gameId(Long.parseLong(gameId))
                                                                .information(information)
                                                                .build();
                           }).collect(Collectors.toList());
    }
}
