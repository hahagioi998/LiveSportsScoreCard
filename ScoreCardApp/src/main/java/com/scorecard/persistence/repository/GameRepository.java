package com.scorecard.persistence.repository;

import com.scorecard.persistence.models.Game;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface GameRepository extends MongoRepository<Game, String> {
    Game findFirstByGameIdEquals(Long gameId);
}
