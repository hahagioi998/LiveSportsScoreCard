package com.scorecard.persistence.models;

//import com.scorecard.persistence.annotation.CascadeSave;
import com.scorecard.utils.enums.Sports;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Document(collection = "games")
public class Game {
    @Id
    private String id;
    @Field(name = "game_id")
    private Long gameId;
    private Sports sport;
    @Field(name = "teams")
    private String teams;
    @Field(name = "information")
    private String information;
    @Field(name = "start_date")
    private Date startDate;
//    @DBRef(lazy = true)
    @Field("events")
    private List<Event> events;

    public Game(Long gameId, Sports sport, String teams, String information, Date startDate) {
        this.gameId = gameId;
        this.sport = sport;
        this.teams = teams;
        this.information = information;
        this.startDate = startDate;
    }

    public Game(){}

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Long getGameId() {
        return gameId;
    }

    public void setGameId(Long gameId) {
        this.gameId = gameId;
    }

    public Sports getSport() {
        return sport;
    }

    public void setSport(Sports sport) {
        this.sport = sport;
    }

    public String getTeams() {
        return teams;
    }

    public void setTeams(String teams) {
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
        if(Objects.isNull(events)){
            this.events = new ArrayList<Event>();
        }
        return this.events;
    }

    public void setEvents(List<Event> events) {
        this.events = events;
    }
}