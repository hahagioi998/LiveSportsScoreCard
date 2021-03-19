package com.scorecard.websocket.listeners;

import com.scorecard.config.queue.EventSource;
import com.scorecard.persistence.models.Event;
import com.scorecard.persistence.models.Game;
import com.scorecard.persistence.service.GameService;
import com.scorecard.utils.WebSocketUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.stereotype.Component;

@Component
@EnableBinding(EventSource.class)
public class MessageQueueListeners {

    @Autowired
    private GameService gameService;

    @StreamListener(target = EventSource.SOCCER_INPUT)
    public void receivedSoccerEvents(String soccerEvent){
        parseAndSaveMessageFromQueue(soccerEvent);
    }

    @StreamListener(target = EventSource.HOCKEY_INPUT)
    public void receivedHockeyEvents(String hockeyEvent){
        parseAndSaveMessageFromQueue(hockeyEvent);
    }

    @StreamListener(target = EventSource.BASKETBALL_INPUT)
    public void receivedBasketballEvents(String basketballEvent){
        parseAndSaveMessageFromQueue(basketballEvent);
    }

    private void parseAndSaveMessageFromQueue(String messageQueuePayload){
        if(WebSocketUtils.isInitialEvent(messageQueuePayload)){
            Game game = WebSocketUtils.parseGamePayload(messageQueuePayload);
            gameService.save(game);
//            webSocketSessionManager.sendEvent(game, null);
        } else {
            Event event = WebSocketUtils.parseEventPayload(messageQueuePayload);
            Game game = gameService.loadGame(event.getGameId());
            game.getEvents().add(event);
            gameService.save(game);
//            webSocketSessionManager.sendEvent(game, event);
        }

    }

}
