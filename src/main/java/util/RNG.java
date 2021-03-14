package util;

import java.util.Random;

public class RNG {

    private static final Random random = new Random();

    public static int randomInt(int upperBound) {
        return random.nextInt(upperBound);
    }

    /**
     *
     * @param upperBound
     * @return return a random integer between 1 (inclusive) and upperBound (exclusive)
     */
    public static int randomPositiveInt(int upperBound) {
        int result = 1;
        if(upperBound > 2) result += random.nextInt(upperBound - 1);
        return result;
    }

    public static boolean randomBoolean() {
        return random.nextBoolean();
    }

    public static int randomIntegerBetween(int lowerBound, int upperBound) {
        assert lowerBound < upperBound;
        return lowerBound + random.nextInt(upperBound - lowerBound);
    }

}
