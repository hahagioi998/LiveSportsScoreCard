package com.scorecard.persistence.service;

import com.scorecard.persistence.models.Game;

import java.util.List;

public interface GameService {

    Game save(Game game);
    Game loadGame(Long gameId);
    List<Game> loadAll();

}
