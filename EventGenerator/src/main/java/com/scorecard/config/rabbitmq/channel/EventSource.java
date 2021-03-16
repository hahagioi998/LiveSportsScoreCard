package com.scorecard.config.rabbitmq.channel;

import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;

/*
 * This is the interface that will be used by the framework to know of which type are the channels declared in the
 * "application.yml". For this case, the 'soccer-channel' / 'hockey-channel' / 'basketball-channel' channel will be
 * created in the message broker as Outbound channels.
 */
public interface EventSource {

    @Output("soccer-channel")
    MessageChannel soccer();

    @Output("hockey-channel")
    MessageChannel hockey();

    @Output("basketball-channel")
    MessageChannel basketball();

    @Output("news-channel")
    MessageChannel news();
}
