package com.scorecard.utils.builder;

import com.scorecard.event.model.Event;

import java.util.Date;

public class EventBuilder {

    private Long id;
    private Long gameId;
    private Date eventDate;
    private String information;

    public static EventBuilder getInstance(){
        return new EventBuilder();
    }

    public EventBuilder id(Long id){
        this.id = id;
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
        return new Event(this.id, this.gameId, this.eventDate, this.information);
    }
}
