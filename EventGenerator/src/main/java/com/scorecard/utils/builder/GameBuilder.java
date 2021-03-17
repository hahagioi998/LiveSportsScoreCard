package com.scorecard.utils.builder;

import com.scorecard.event.model.Event;
import com.scorecard.event.model.Game;
import com.scorecard.utils.enums.Sports;

import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

public class GameBuilder {

    private Long id;
    private Sports sport;
    private String[] teams;
    private String information;
    private Date startDate;
    private List<Event> events;
    private AtomicBoolean done;

    public static GameBuilder getInstance(){
        return new GameBuilder();
    }

    public GameBuilder id(Long id){
        this.id = id;
        return this;
    }

    public GameBuilder sport(Sports sport){
        this.sport = sport;
        return this;
    }

    public GameBuilder teams(String[] teams){
        this.teams = teams;
        return this;
    }

    public GameBuilder information(String information){
        this.information = information;
        return this;
    }

    public GameBuilder startDate(Date startDate){
        this.startDate = startDate;
        return this;
    }

    public GameBuilder events(List<Event> events){
        this.events = events;
        return this;
    }

    public Game build(){
        return new Game(this.id, this.sport, this.teams, this.information, this.startDate, this.events);
    }
}
