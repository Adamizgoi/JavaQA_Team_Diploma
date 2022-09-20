package ru.netology;

public class GameIsNotInstalledException extends RuntimeException {
    public GameIsNotInstalledException(String msg) {
        super(msg);
    }
}

