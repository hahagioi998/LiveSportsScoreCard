package com.scorecard.utils.builders;

import com.scorecard.persistence.models.Game;
import com.scorecard.utils.enums.Sports;

import java.util.Date;

public class GameBuilder {

    private Long gameId;
    private Sports sport;
    private String teams;
    private String information;
    private Date startDate;

    public static GameBuilder getInstance(){
        return new GameBuilder();
    }

    public GameBuilder gameId(Long gameId){
        this.gameId = gameId;
        return this;
    }

    public GameBuilder sport(Sports sport){
        this.sport = sport;
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

    public GameBuilder teams(String teams[]){
        this.teams = teams[0] + "-" + teams[1];
        return this;
    }

    public Game build(){
        return new Game(this.gameId, this.sport, this.teams, this.information, this.startDate);
    }
}
