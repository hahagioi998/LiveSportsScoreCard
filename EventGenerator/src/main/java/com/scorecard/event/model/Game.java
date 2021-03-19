package com.scorecard.event.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.scorecard.utils.enums.Sports;

import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

@JsonIgnoreProperties(value = {"done"})
public class Game {

    private Long id;
    private Sports sport;
    private String[] teams;
    private String information;
    private Date startDate;
    private List<Event> events;
    private AtomicBoolean done;

    public Game() {
        this.done = new AtomicBoolean(false);
    }

    public Game(Long id, Sports sport, String[] teams, String information, Date startDate, List<Event> events) {
        this.id = id;
        this.sport = sport;
        this.teams = teams;
        this.information = information;
        this.startDate = startDate;
        this.events = events;
        this.done = new AtomicBoolean(false);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Sports getSport() {
        return sport;
    }

    public void setSport(Sports sport) {
        this.sport = sport;
    }

    public String[] getTeams() {
        return teams;
    }

    public void setTeams(String[] teams) {
        this.teams = teams;
    }

    public String getInformation() {
        return information;
    }

    public void setInformation(String information) {
        this.information = information;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public List<Event> getEvents() {
        return events;
    }

    public void setEvents(List<Event> events) {
        this.events = events;
    }

    public AtomicBoolean getDone() {
        return done;
    }
}
