package com.podorozhnick.moneytracker;

import org.apache.commons.text.RandomStringGenerator;

import static org.apache.commons.text.CharacterPredicates.DIGITS;
import static org.apache.commons.text.CharacterPredicates.LETTERS;

public class TestHelper {

    private static final int NAME_LENGTH = 10;
    private static RandomStringGenerator randomStringGenerator;

    static {
        randomStringGenerator = new RandomStringGenerator.Builder()
                .withinRange('0', 'z')
                .filteredBy(LETTERS, DIGITS)
                .build();
    }

    public static String getRandomName() {
        return getRandomString(NAME_LENGTH);
    }

    private static String getRandomString(int length) {
        return randomStringGenerator.generate(length);
    }

}
