package com.scorecard.utils;

import com.scorecard.utils.builder.SportConfigBuilder;
import com.scorecard.utils.pojo.SportConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class ScoreCardConfig {
    @Autowired
    private Environment environment;

    private SportConfig getSportConfig(String sportName) {
        String aliveTime = environment.getProperty("scorecard.event.handler." + sportName + ".aliveTime");
        String mininimumSleepInterval = environment.getProperty("scorecard.event.handler." + sportName + ".mininimumSleepInterval");
        String maximumSleepInterval = environment.getProperty("scorecard.event.handler." + sportName + ".maximumSleepInterval");
        return SportConfigBuilder.getInstance()
                .name(sportName)
                .aliveTime(Long.parseLong(aliveTime))
                .mininimumSleepInterval(Long.parseLong(mininimumSleepInterval))
                .maximumSleepInterval(Long.parseLong(maximumSleepInterval))
                .build();
    }

    public List<SportConfig> getConfiguredSports()  {
        String configuredSports[] = environment.getProperty("scorecard.sports-configured").split(" ");
        return Arrays.stream(configuredSports)
                .map(this::getSportConfig)
                .collect(Collectors.toList());
    }

    public boolean isNewsEnabled(){
        return Boolean.parseBoolean(environment.getProperty("scorecard.news-enabled"));
    }

    public boolean isSportConfigured(String sportName){
        List<SportConfig> sportConfigList = this.getConfiguredSports();
        return sportConfigList.stream()
                .filter(sportConfig -> sportConfig.getName().equals(sportName))
                .findFirst().isPresent();
    }
}
