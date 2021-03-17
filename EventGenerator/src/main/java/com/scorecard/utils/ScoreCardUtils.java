package com.scorecard.utils;

import java.util.concurrent.ThreadLocalRandom;

public class ScoreCardUtils {

    public static long generateRandom(long leftLimit, long rightLimit){
        return ThreadLocalRandom.current().nextLong(leftLimit, rightLimit);
    }

}
