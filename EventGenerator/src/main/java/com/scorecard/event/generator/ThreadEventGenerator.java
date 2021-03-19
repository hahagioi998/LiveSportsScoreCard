package com.scorecard.event.generator;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.scorecard.config.rabbitmq.channel.EventSource;
import com.scorecard.event.model.Event;
import com.scorecard.event.model.Game;
import com.scorecard.utils.ScoreCardUtils;
import com.scorecard.utils.broker.EventSourceUtils;
import com.scorecard.utils.pojo.SportConfig;

import java.util.Date;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicBoolean;

/*
 * This is the class that will be used to emit data to the queues. This process is done asynchronously, that is, not in the same thread
 * of the caller. This will make your code responsive because if not it would get stacked. Depending on the numbers of cores, the work
 * of the different threads will be done in parallel or sequentially.
 *
 * Properties to be used:
 * a] An object of type 'SportConfig' which tell us if the game and the events that are emitted are soccer / hockey or basketball events and
 *    also which is the range of milliseconds that will be used to generate a random number to pause the thread after each time we send data
 *    the queue.
 *
 * b] An object of type AtomicBoolean, which will be used to pause this thread task.
 *
 * c] An object that implements the "EventSource" interface, which will be used to reference the corresponding outbound channels
 *    and push data to the queues.
 *
 * d] An object of type "Game", which is the object that holds all the data to be sent to the queues.
 */

public class ThreadEventGenerator extends Thread {

    private SportConfig sportConfig;

    private AtomicBoolean paused;
    private AtomicBoolean running;

    private EventSource eventSource;
    private Game gameEvent;

    private final boolean WITH_EVENTS = true;
    private final boolean WITH_OUT_EVENTS = false;

    public ThreadEventGenerator(SportConfig sportConfig, EventSource eventSource) {
        this.sportConfig = sportConfig;
        this.eventSource = eventSource;
        this.paused = new AtomicBoolean(false);
        this.running = new AtomicBoolean(true);
    }

    @Override
    public void run() {
        EventSourceUtils sourceUtils = new EventSourceUtils(eventSource);
        // Check If The Thread Is Running
        while (this.getRunning().get()) {
            // Check If The Thread Is Paused
            if (!this.paused.get()) {
                // Check If We Already Emit The Initial Data From The Game
                if (!gameEvent.getDone().get()) {
                    try {

                        // We change the value of the "done" property to true so that we are awared on next loop that the data was already sent.
                        gameEvent.getDone().set(true);

                        // We serialize the Game object without the corresponding events.
                        String gameEventStr = sourceUtils.serializeForMessage(gameEvent, WITH_OUT_EVENTS);

                        // We send the data to the corresponding queue using the sport type ('soccer' / 'hockey' / 'basketball')
                        // And An Object Of Type Message Which Will Hold The Payload.
                        sourceUtils.sendEvent(gameEventStr, gameEvent.getSport());
                    } catch (JsonProcessingException e) {
                        e.printStackTrace();
                    }
                } else {
                    /*
                     * From all the events from the game, we get the first one that was not sent to the queue
                     * and, after that, change the "done" property to true so that we indicate that this event was
                     * already sent.
                     */
                    Event eventData = gameEvent.getEvents().stream().filter(event -> !event.getDone().get()).findFirst().orElse(null);
                    if (Objects.nonNull(eventData)) {
                        String eventDataStr = null;
                        try {
                            eventData.getDone().set(true);
                            eventData.setEventDate(new Date());
                            eventDataStr = sourceUtils.serializeForMessage(eventData, WITH_EVENTS);
                            sourceUtils.sendEvent(eventDataStr, gameEvent.getSport());
                        } catch (JsonProcessingException e) {
                            e.printStackTrace();
                        }

                    }
                }

                // We generate a random number to sleep the thread as to simulate a real feed.
                long sleepTime = ScoreCardUtils.generateRandom(this.sportConfig.getMininimumSleepInterval(), this.sportConfig.getMaximumSleepInterval());
                try {
                    Thread.sleep(sleepTime);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            } else {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public AtomicBoolean getRunning() {
        return running;
    }

    public AtomicBoolean getPaused() {
        return paused;
    }

    public void setGameEvent(Game gameEvent) {
        this.gameEvent = gameEvent;
    }

}
