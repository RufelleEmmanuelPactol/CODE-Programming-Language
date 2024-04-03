package com.code.virtualmachine;

public class SimpleTimer {
    // Store the start time
    private static long startTime = 0;

    /**
     * Starts the timer by recording the current time.
     */
    public static void startTime() {
        startTime = System.nanoTime();
    }

    /**
     * Ends the timer, calculates the elapsed time since startTime() was called,
     * and returns the elapsed time in seconds as a string with floating-point precision.
     *
     * @return The elapsed time in seconds as a String.
     */
    public static String endTime() {


        long endTime = System.nanoTime();
        double elapsedTimeInSeconds = (endTime - startTime) / 1_000_000_000.0;

        // Reset startTime to allow the timer to be started again in the future.
        startTime = 0;

        // Return the elapsed time formatted as a string
        return String.format("%.3f seconds", elapsedTimeInSeconds);
    }
}
