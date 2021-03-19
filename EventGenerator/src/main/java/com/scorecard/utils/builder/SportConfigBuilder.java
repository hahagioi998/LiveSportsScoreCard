package com.scorecard.utils.builder;

import com.scorecard.utils.pojo.SportConfig;

import java.util.Objects;

/* builder design pattern */
public class SportConfigBuilder {
    private String name;
    private long aliveTime;
    private long mininimumSleepInterval;
    private long maximumSleepInterval;

    public static SportConfigBuilder getInstance() {
        return new SportConfigBuilder();
    }

    public SportConfigBuilder name(String name) {
        this.name = name;
        return this;
    }

    public SportConfigBuilder aliveTime(long aliveTime) {
        this.aliveTime = aliveTime;
        return this;
    }

    public SportConfigBuilder mininimumSleepInterval(long mininimumSleepInterval) {
        this.mininimumSleepInterval = mininimumSleepInterval;
        return this;
    }

    public SportConfigBuilder maximumSleepInterval(long maximumSleepInterval) {
        this.maximumSleepInterval = maximumSleepInterval;
        return this;
    }

    public SportConfig build() {

        if (this.mininimumSleepInterval >= this.maximumSleepInterval) {
            throw new RuntimeException("Sleep Internal Range Was Not Configured Correctly.");
        }

        if (this.aliveTime <= 0) {
            throw new RuntimeException("Alive Time Was Not Configured Correctly.");
        }

        if (Objects.isNull(this.name)) {
            throw new RuntimeException("Sport Name Was Not Configured Correctly.");
        }

        return new SportConfig(this.name, this.aliveTime, this.mininimumSleepInterval, this.maximumSleepInterval);
    }
}
