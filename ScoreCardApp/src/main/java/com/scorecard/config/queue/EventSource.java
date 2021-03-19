package com.scorecard.config.queue;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.messaging.MessageChannel;

public interface EventSource {
    String SOCCER_INPUT = "soccer-input";
    String HOCKEY_INPUT = "hockey-input";
    String BASKETBALL_INPUT = "basketball-input";

    @Input(SOCCER_INPUT)
    MessageChannel soccerInput();

    @Input(HOCKEY_INPUT)
    MessageChannel hockerInput();

    @Input(BASKETBALL_INPUT)
    MessageChannel basketballInput();

}
