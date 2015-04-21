package com.util;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by tmcdaniel on 4/20/2015.
 */
public class CardMap {

    public static Map<String,String> map;
    static {
        Map<String,String> temp = new HashMap<>();


        temp.put("Hit Card: knight Color: Blue", "blueKnightsmall");
        temp.put("Hit Card: knight Color: Green", "greenKnightsmall");
        temp.put("Hit Card: knight Color: Red", "redKnightsmall");
        temp.put("Hit Card: knight Color: Any", "anyKnightsmall");

        temp.put("Hit Card: archer Color: Blue", "blueArchersmall");
        temp.put("Hit Card: archer Color: Green", "greenArchersmall");
        temp.put("Hit Card: archer Color: Red", "redArchersmall");
        temp.put("Hit Card: archer Color: Any", "anyArchersmall");

        temp.put("Hit Card: hero Color: Blue", "blueHerosmall");
        temp.put("Hit Card: hero Color: Green", "greenHerosmall");
        temp.put("Hit Card: hero Color: Red", "redHerosmall");
        temp.put("Hit Card: hero Color: Any", "anyHerosmall");

        temp.put("Card: barbarian", "barbarianCardsmall");
        temp.put("Card: missing", "missingCardsmall");
        temp.put("Card: time slap", "timeSlapsmall");
        temp.put("Card: time stop", "timeStopCardsmall");
        temp.put("Card: rewind", "rewindCardsmall");
        temp.put("Card: turret", "timeStopCardsmall"); //not uploaded yet

        map = Collections.unmodifiableMap(temp);
    }
}
