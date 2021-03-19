package com.scorecard.persistence.service;

import com.scorecard.persistence.models.Game;
import com.scorecard.persistence.repository.GameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class GameServiceImpl implements GameService {

    @Autowired
    private GameRepository gameRepository;

    @Override
    public Game save(Game game) {
        return gameRepository.save(game);
    }

    @Override
    public Game loadGame(Long gameId) {
        return gameRepository.findFirstByGameIdEquals(gameId);
    }

    @Override
    public List<Game> loadAll() {
        return gameRepository.findAll();
    }
}
