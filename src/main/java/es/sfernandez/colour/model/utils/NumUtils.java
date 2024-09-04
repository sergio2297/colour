package es.sfernandez.colour.model.utils;

public class NumUtils {

    public static float max(final float ... numbers) {
        if(numbers == null || numbers.length == 0)
            throw new IllegalArgumentException("Error. It's necessary to specify at least one number");

        float max = numbers[0];

        for(int i = 1; i < numbers.length; ++i)
            if(max < numbers[i])
                max = numbers[i];

        return max;
    }

    public static float min(final float ... numbers) {
        if(numbers == null || numbers.length == 0)
            throw new IllegalArgumentException("Error. It's necessary to specify at least one number");

        float min = numbers[0];

        for(int i = 1; i < numbers.length; ++i)
            if(min > numbers[i])
                min = numbers[i];

        return min;
    }

    public static float round(final float number, final int numOfDecimals) {
        float offset = (float) Math.pow(10.0, numOfDecimals);
        return Math.round(number * offset) / offset;
    }

    public static float normalize(int min, int max, int number) {
        if(min == max)
            throw new IllegalArgumentException("Range has to contain at least one natural number. (Range: [" + min + ", " + max + "])");

        if(isNotBetween(min, max, number))
            throw new IllegalArgumentException(number + " is out of range [" + min + ", " + max + "], it's not possible to normalize it.");

        return ((number - min) * 1.0f) / ((max - min) * 1.0f);
    }

    public static int denormalize(int min, int max, float number) {
        if(min == max)
            throw new IllegalArgumentException("Range has to contain at least one natural number. (Range: [" + min + ", " + max + "])");

        if(isNotBetween(0f, 1f, number))
            throw new IllegalArgumentException(number + " is out of range [0, 1], it's not possible to denormalize it.");

        return (int) round((number * max + min * (1 - number)), 0);
    }

    public static boolean isBetween(int min, int max, int number) {
        return min <= number && number <= max;
    }

    public static boolean isNotBetween(int min, int max, int number) {
        return !isBetween(min, max, number);
    }

    public static boolean isBetween(float min, float max, float number) {
        return min <= number && number <= max;
    }

    public static boolean isNotBetween(float min, float max, float number) {
        return !isBetween(min, max, number);
    }

    public static int castHexToInt(String hexNumber) {
        return Integer.parseInt(hexNumber,16);
    }

}
