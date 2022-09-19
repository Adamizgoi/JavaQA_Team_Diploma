package ru.netology;

public class GameIsAlreadyInstalledException extends RuntimeException {
    public GameIsAlreadyInstalledException(String msg) {
        super(msg);
    }
}

