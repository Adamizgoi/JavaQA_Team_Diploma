package ru.netology;

public class HoursCanNotBeLessThanZeroException extends RuntimeException {
    public HoursCanNotBeLessThanZeroException(String msg) {
        super(msg);
    }
}

