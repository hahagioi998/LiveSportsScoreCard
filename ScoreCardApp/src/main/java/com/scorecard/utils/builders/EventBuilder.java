package com.scorecard.utils.builders;

import com.scorecard.persistence.models.Event;

import java.util.Date;

public class EventBuilder {

    private Long eventId;
    private Long gameId;
    private Date eventDate;
    private String information;

    public static EventBuilder getInstance(){
        return new EventBuilder();
    }

    public EventBuilder eventId(Long eventId){
        this.eventId = eventId;
        return this;
    }

    public EventBuilder gameId(Long gameId){
        this.gameId = gameId;
        return this;
    }

    public EventBuilder eventDate(Date eventDate){
        this.eventDate = eventDate;
        return this;
    }

    public EventBuilder information(String information){
        this.information = information;
        return this;
    }

    public Event build(){
        return new Event(this.eventId, this.gameId, this.eventDate, this.information);
    }

}
