package com.scorecard.config;

//import com.scorecard.config.handler.ErrorInterceptor;

import com.scorecard.config.rabbitmq.channel.EventSource;
import com.scorecard.event.handler.EventGenerator;
import com.scorecard.utils.ScoreCardConfig;
import com.scorecard.utils.pojo.SportConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

/*
 * This class will be used as a configuration class (due to this annotation @Configuration), which are classes that are used
 * to add beans to the application context. Think of the application context as a bag of objects which can be reference in
 * any part of your app by the @Autowired annotation.
 *
 * @EnableBinding is an annotation from the spring cloud module that will indicate the framework to analyze the EventSource interface,
 * provide an actual implementation for the bean of the interface type as well as for the abstract methods (methods without body).
 *
 * For example, if there is a plain simply class and you want to make available in your app context a bean of that type, you need to annotate
 * a method with the annotation @Bean. You need to return whatever type of object you want but configure it first or initialize it.
 * This can be accomplished also with the @Component annotation, which will indicate the framework to create an instance of the class
 * and inject into the app context.
 * In this case, an instance of ErrorInterceptor is injected and indicate the framework to use when the user enters a URI that doesnt exist
 * or that throws 404.
 *
 */
@Configuration
@EnableBinding(EventSource.class)
public class ApplicationConfig extends ScoreCardConfig implements WebMvcConfigurer {

    @Autowired
    private EventSource eventSource;

//    @Autowired
//    private ErrorInterceptor errorInterceptor;

    @Bean
    public EventGenerator eventGenerator() {
        List<SportConfig> configuredSports = this.getConfiguredSports();
        return new EventGenerator(configuredSports, eventSource);
    }

//    @Override
//    public void addInterceptors(InterceptorRegistry registry) {
//        registry.addInterceptor(errorInterceptor);
//    }
}
