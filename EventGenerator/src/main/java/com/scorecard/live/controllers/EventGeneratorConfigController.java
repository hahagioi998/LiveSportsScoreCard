package com.scorecard.live.controllers;

import com.scorecard.config.rabbitmq.channel.EventSource;
import com.scorecard.event.handler.EventGenerator;
import com.scorecard.utils.ScoreCardConfig;
import com.scorecard.utils.enums.Sports;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

/**
 * '@Controller' annotation will instruct the framework to use it to process user requests from the screen.
 *
 */
@Controller
public class EventGeneratorConfigController extends ScoreCardConfig {
    @Autowired
    private EventGenerator eventGenerator;

    @Autowired
    private EventSource eventSource;

    @RequestMapping(value = { "/"})
    public RedirectView root(){
        return new RedirectView("home");
    }


    @RequestMapping(value = { "/home"})
    public ModelAndView home(){
        return getHomeModelAndView();
    }
    /*
     * The below 2 methods will be used to start and stop, in general, the event generation process.
     */
    @PostMapping("/start-event-generation")
    public ModelAndView startEventGeneration() {
        this.eventGenerator.initEventGeneration();
        return getHomeModelAndView();
    }

    @PostMapping("/stop-event-generation")
    public ModelAndView stopEventGeneration() {
        this.eventGenerator.stopEventGeneration();
        return getHomeModelAndView();
    }



    /*
     * The following 2 methods can be used to pause / unpause the threads corresponding to a certain sport.
     * We can have a group of threads (from soccer for example) emitting values and the threads from the hockey group paused.
     */
    @PostMapping("/pause-event-generation")
    public ModelAndView pauseEventGeneration(@RequestParam(name = "sport") String sport) {
        this.eventGenerator.pauseSportEventGeneration(sport);
        return getHomeModelAndView();
    }

    @PostMapping("/unpause-event-generation")
    public ModelAndView unPauseEventGeneration(@RequestParam(name = "sport") String sport) {
        this.eventGenerator.unPauseSportEventGeneration(sport);
        return getHomeModelAndView();
    }

    /*
     * This handler method will receive as params an arbitrary information and the queue to be used to publish
     * the info to.
     */
    @PostMapping("/publish-event")
    public ModelAndView publishEvent(@RequestParam(name = "information") String information,
                                     @RequestParam(name = "queue") String queueToPublish) {

        JSONObject dataEvent = new JSONObject();
        dataEvent.put("information", information);
        String eventData = dataEvent.toString();
        if(queueToPublish.equals("soccer")) {
            this.eventSource.soccer().send(MessageBuilder.withPayload(eventData).build());
        } else if(queueToPublish.equals("hockey")) {
            this.eventSource.hockey().send(MessageBuilder.withPayload(eventData).build());
        } else {
            this.eventSource.basketball().send(MessageBuilder.withPayload(eventData).build());
        }
        return getHomeModelAndView();
    }

    /*
     * This method will be used to pass to the JSP page the corresponding variables so that we know is the data generation is active
     * and which sports are configured and which of them are currently active (which threads are emitting values)
     */
    private ModelAndView getHomeModelAndView(){
        ModelAndView homeView = new ModelAndView("home");

        homeView.addObject("soccerConfigured", this.isSportConfigured(Sports.SOCCER.getName()));
        homeView.addObject("hockeyConfigured", this.isSportConfigured(Sports.HOCKEY.getName()));
        homeView.addObject("basketballConfigured", this.isSportConfigured(Sports.BASKETBALL.getName()));

        homeView.addObject("eventStatus", this.eventGenerator.getEventStatus());
        homeView.addObject("soccerStatus", this.eventGenerator.getSportStatus(Sports.SOCCER.getName()));
        homeView.addObject("hockeyStatus", this.eventGenerator.getSportStatus(Sports.HOCKEY.getName()));
        homeView.addObject("basketballStatus", this.eventGenerator.getSportStatus(Sports.BASKETBALL.getName()));
        return homeView;
    }
}
