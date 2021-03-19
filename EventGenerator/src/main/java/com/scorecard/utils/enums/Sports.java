package com.scorecard.utils.enums;

public enum Sports {

    SOCCER("soccer", 2, 2),
    HOCKEY("hockey", 2, 2),
    BASKETBALL("basketball", 2, 2);

    private String name;
    private int gameAmount;
    private int eventAmount;

    Sports(String name, int gameAmount, int eventAmount) {
        this.name = name;
        this.gameAmount = gameAmount;
        this.eventAmount = eventAmount;
    }

    public String getName() {
        return this.name;
    }

    public int getGameAmount() {
        return gameAmount;
    }

    public int getEventAmount() {
        return eventAmount;
    }
}
