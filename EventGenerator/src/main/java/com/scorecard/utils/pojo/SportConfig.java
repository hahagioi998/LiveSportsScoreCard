package com.scorecard.utils.pojo;

public class SportConfig {

    private String name;
    private long aliveTime;
    private long mininimumSleepInterval;
    private long maximumSleepInterval;

    public SportConfig(String name, long aliveTime, long mininimumSleepInterval, long maximumSleepInterval) {
        this.name = name;
        this.aliveTime = aliveTime;
        this.mininimumSleepInterval = mininimumSleepInterval;
        this.maximumSleepInterval = maximumSleepInterval;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getAliveTime() {
        return aliveTime;
    }

    public void setAliveTime(long aliveTime) {
        this.aliveTime = aliveTime;
    }

    public long getMininimumSleepInterval() {
        return mininimumSleepInterval;
    }

    public void setMininimumSleepInterval(long mininimumSleepInterval) {
        this.mininimumSleepInterval = mininimumSleepInterval;
    }

    public long getMaximumSleepInterval() {
        return maximumSleepInterval;
    }

    public void setMaximumSleepInterval(long maximumSleepInterval) {
        this.maximumSleepInterval = maximumSleepInterval;
    }
}
