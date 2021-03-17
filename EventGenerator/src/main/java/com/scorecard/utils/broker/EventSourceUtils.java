package com.scorecard.utils.broker;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.scorecard.config.rabbitmq.channel.EventSource;
import com.scorecard.utils.enums.Sports;
import org.springframework.integration.support.MessageBuilder;
import org.json.JSONObject;

public class EventSourceUtils {
    private EventSource eventSource;
    public EventSourceUtils(EventSource eventSource){
        this.eventSource = eventSource;
    }

    public String serializeForMessage(Object t, boolean withEvents) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();

        if(!withEvents) {
            String temporalStr = objectMapper.writeValueAsString(t);
            JSONObject temporalJson = new JSONObject(temporalStr);
            temporalJson.remove("events");
            return temporalJson.toString();
        }

        return objectMapper.writeValueAsString(t);
    }

    public void sendEvent(String eventData, Sports sport){
        if(sport == Sports.SOCCER) {
            this.eventSource.soccer().send(MessageBuilder.withPayload(eventData).build());
        } else if(sport == Sports.BASKETBALL){
            this.eventSource.basketball().send(MessageBuilder.withPayload(eventData).build());
        } else {
            this.eventSource.hockey().send(MessageBuilder.withPayload(eventData).build());
        }
    }
}
