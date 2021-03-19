package com.scorecard.controllers;

import com.scorecard.persistence.models.Game;
import com.scorecard.persistence.service.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

@RestController
public class ScoreCardController {

    @Autowired
    private GameService gameService;

    @GetMapping(value = { "/v1/sports/options"})
    public Map<String, List<String>> root(){

        List<Game> games = gameService.loadAll();

        Map<String, List<String>> sportOptionsMap = new HashMap<String, List<String>>();

       
        return sportOptionsMap;
    }
}
