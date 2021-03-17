package com.scorecard.event.handler;

import com.scorecard.config.rabbitmq.channel.EventSource;
import com.scorecard.event.generator.ThreadEventGenerator;
import com.scorecard.event.model.Game;
import com.scorecard.utils.pojo.SportConfig;

import java.util.*;
import java.util.stream.Collectors;

/*
 * This class will be like an ORCHESTRATOR or in charge of handling the threads that are emitting data to the queue.
 *
 * The properties that are used are the following:
 * a] A list of object of type 'SportConfig', which will tell us which are the configured sports.
 * b] A map in which the key are the names of the configured sports and the value List of objects of type 'ThreadEventGenerator'. This
 *    last one, is the thread class that will emit dummy data to the queues. We acccess its data know which sports are currently active
 *    or paused.
 * c] A map in which the keys are the configured sports and the values are Lists of Objects of type 'Game', which are the objects that hold
 *    the data emitted to the queue.
 * d] An object the implements the 'EventSource' interface, which we are going to use to reference the outbound channels to publish to the queue.
 * e] A variable of type boolean to know if the data is being emitted or if on the contrary the process is not running.
 */
public class EventGenerator {
    private List<SportConfig> sportConfigList;
    private Map<String, List<ThreadEventGenerator>> threadEventMap;
//    private List<News> news;
    private Map<String, List<Game>> sportMapGames;
    private EventSource eventSource;
    private boolean eventStatus = false;

    public EventGenerator(List<SportConfig> sportConfigList, EventSource eventSource){
        this.sportConfigList = sportConfigList;
        this.eventSource = eventSource;
        this.sportMapGames = new HashMap<String, List<Game>>();
    }

    // This process triggers the code that starts the threads which publish to the queues.
    public void initEventGeneration(){
        this.threadEventMap = this.sportConfigList.stream().collect(Collectors.toMap(SportConfig::getName, sportConfig -> {
            return sportMapGames.get(sportConfig.getName()).stream().map(
                    game -> {
                        ThreadEventGenerator eventGenerator = new ThreadEventGenerator(sportConfig, eventSource);
                        eventGenerator.setGameEvent(game);
                        eventGenerator.start();
                        return eventGenerator;
                    }).collect(Collectors.toList());
        }));
        this.setEventStatus(true);
    }

    // This method stops the threads that emit value to queues.
    public void stopEventGeneration(){
        this.threadEventMap.entrySet()
                .forEach(entry -> {
                    entry.getValue()
                            .forEach(eventThread -> {
                                try {
                                    eventThread.getRunning().set(false);
                                } catch (Exception ex){
                                    ex.printStackTrace();
                                }
                            });
                });
        this.threadEventMap.clear();
        this.setEventStatus(false);
        this.sportMapGames.values().forEach(gameList -> {
            gameList.forEach(game -> {
                game.getDone().set(false);
                game.getEvents().forEach(event -> event.getDone().set(false));
            });
        });
    }

    // This will let us know what is the status of all the threads corresponding to a certain sports.
    public boolean getSportStatus(String sportName){
        return Objects.nonNull(this.threadEventMap) && Objects.nonNull(this.threadEventMap.get(sportName)) && this.threadEventMap.get(sportName)
                .stream()
                .allMatch(threadEvent -> {
                            return !threadEvent.getPaused().get();
                        }
                );
    }

    // This method will allow us to pause all the threads corresponding to a certain sport.
    // So if we use it, all the threads corresponding to soccer will be paused and we wont emit values.
    public void pauseSportEventGeneration(String sportName){
        this.threadEventMap.get(sportName)
                .forEach(threadEvent -> threadEvent.getPaused().set(true));
    }

    public void unPauseSportEventGeneration(String sportName){
        this.threadEventMap.get(sportName)
                .forEach(threadEvent -> threadEvent.getPaused().set(false));
    }

//    public void setNews(List<News> news) {
//
//    }

    public void setSportMapGames(Map<String, List<Game>> sportMapGames) {
        this.sportMapGames = sportMapGames;
    }

    public boolean getEventStatus() {
        return eventStatus;
    }

    public void setEventStatus(boolean eventStatus) {
        this.eventStatus = eventStatus;
    }
}
