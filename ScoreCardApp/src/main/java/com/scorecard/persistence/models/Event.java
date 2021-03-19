package com.scorecard.persistence.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.Date;

@Document(collection = "events")
public class Event {

    @Id
    private String id;
    @Field(name = "event_id")
    private Long eventId;
    @Field(name = "game_id")
    private Long gameId;
    @Field(name = "event_date")
    private Date eventDate;
    @Field(name = "information")
    private String information;

    public Event(){}

    public Event(Long eventId, Long gameId, Date eventDate, String information) {
        this.eventId = eventId;
        this.gameId = gameId;
        this.eventDate = eventDate;
        this.information = information;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Long getEventId() {
        return eventId;
    }

    public void setEventId(Long eventId) {
        this.eventId = eventId;
    }

    public Long getGameId() {
        return gameId;
    }

    public void setGameId(Long gameId) {
        this.gameId = gameId;
    }

    public Date getEventDate() {
        return eventDate;
    }

    public void setEventDate(Date eventDate) {
        this.eventDate = eventDate;
    }

    public String getInformation() {
        return information;
    }

    public void setInformation(String information) {
        this.information = information;
    }
}
