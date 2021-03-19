package com.scorecard.event.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.Date;
import java.util.concurrent.atomic.AtomicBoolean;

@JsonIgnoreProperties(value = {"done"})
public class Event {

    private Long id;
    private Long gameId;
    private Date eventDate;
    private String information;

    private AtomicBoolean done;

    public Event(Long id, Long gameId, Date eventDate, String information) {
        this.id = id;
        this.gameId = gameId;
        this.eventDate = eventDate;
        this.information = information;
        this.done = new AtomicBoolean(false);
    }

    public Event() {
        this.done = new AtomicBoolean(false);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public AtomicBoolean getDone() {
        return done;
    }
}
