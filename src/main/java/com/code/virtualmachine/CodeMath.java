package com.code.virtualmachine;

public class CodeMath {

    public static double add(double a, double b) {
        return a + b;
    }

    public static double subtract(double a, double b) {
        return a - b;
    }

    public static double multiply(double a, double b) {
        return a * b;
    }

    public static double divide(double a, double b) {
        // Ensure division by zero is handled appropriately, potentially throwing an ArithmeticException
        if (b == 0) {
            throw new ArithmeticException("Division by zero.");
        }
        return a / b;
    }

    public static double mod(double a, double b) {
        return a % b;
    }

    public static double pow(double a, double b) {
        return Math.pow(a, b);
    }

    public static double sqrt(double a) {
        return Math.sqrt(a);
    }

    public static double abs(double a) {
        return Math.abs(a);
    }

    public static double sin(double a) {
        return Math.sin(a);
    }

    public static double cos(double a) {
        return Math.cos(a);
    }

    public static double tan(double a) {
        return Math.tan(a);
    }

    public static double asin(double a) {
        return Math.asin(a);
    }

    public static double acos(double a) {
        return Math.acos(a);
    }

    public static double atan(double a) {
        return Math.atan(a);
    }

    public static double log(double a) {
        return Math.log(a);
    }

    public static double log10(double a) {
        return Math.log10(a);
    }

    public static double log1p(double a) {
        // Returns the natural logarithm of the sum of the argument and 1
        return Math.log1p(a);
    }

    public static double exp(double a) {
        return Math.exp(a);
    }

    public static double expm1(double a) {
        // Returns exp(a) - 1
        return Math.expm1(a);
    }

    public static double cbrt(double a) {
        // Returns the cube root of a double value
        return Math.cbrt(a);
    }

    public static double ceil(double a) {
        // Returns the smallest (closest to negative infinity) double value that is greater than or equal to the argument and is equal to a mathematical integer.
        return Math.ceil(a);
    }

    public static double floor(double a) {
        // Returns the largest (closest to positive infinity) double value that is less than or equal to the argument and is equal to a mathematical integer.
        return Math.floor(a);
    }

    public static double rint(double a) {
        // Returns the double value that is closest in value to the argument and is equal to a mathematical integer.
        return Math.rint(a);
    }

    public static double round(double a) {
        // Returns the closest long to the argument, with ties rounding to positive infinity.
        return Math.round(a);
    }

    public static double signum(double a) {
        // Returns the signum function of the argument; zero if the argument is zero, 1.0 if the argument is greater than zero, -1.0 if the argument is less than zero.
        return Math.signum(a);
    }

    public static double sinh(double a) {
        // Returns the hyperbolic sine of a double value.
        return Math.sinh(a);
    }

    public static double cosh(double a) {
        // Returns the hyperbolic cosine of a double value.
        return Math.cosh(a);
    }

    public static double tanh(double a) {
        // Returns the hyperbolic tangent of a double value.
        return Math.tanh(a);
    }

    // Feel free to add any other mathematical operations here.
}
