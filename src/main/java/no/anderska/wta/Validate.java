package no.anderska.wta;

public class Validate {

    public static int numberInRange(int number, String name, int min, int max) {
        if (number < min || number > max) {
            String message = name + " " + number + " should be in range [" + min + "-" + max + "]";
            throw new IllegalArgumentException(message);
        }
        return number;
    }

    public static int positiveNumber(int number, String name) {
        if (number <= 0) {
            String message = name + " " + number + " should be a positive number";
            throw new IllegalArgumentException(message);
        }
        return number;
    }

}
