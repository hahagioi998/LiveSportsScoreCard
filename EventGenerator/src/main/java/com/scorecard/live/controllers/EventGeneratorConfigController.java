package com.scorecard.live.controllers;

import com.scorecard.config.rabbitmq.channel.EventSource;
import com.scorecard.utils.ScoreCardConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * This class annotated with the '@Controller' annotation will instruct the framework to use it to process user requests from the screen.
 *
 */
@Controller
public class EventGeneratorConfigController extends ScoreCardConfig {
    @Autowired
    private EventGenerator eventGenerator;

    @Autowired
    private EventSource eventSource;
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


}
