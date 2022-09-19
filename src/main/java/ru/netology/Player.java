package ru.netology;

import java.util.HashMap;
import java.util.Map;

public class Player {
    private String name;

    /**
     * информация о том, в какую игру сколько часов было сыграно
     * ключ - игра
     * значение - суммарное количество часов игры в эту игру
     */
    private Map<Game, Integer> playedTime = new HashMap<>();

    public Player(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    /**
     * добавление игры игроку
     * если игра уже была, никаких изменений происходить не должно
     */
    public void installGame(Game game) {
        if (playedTime.containsKey(game)) {
            throw new GameIsAlreadyInstalledException(
                    "Игра уже установлена. Невозможно применить метод install"
            );
        }
        playedTime.put(game, 0);
    }

    /**
     * игрок играет в игру game на протяжении hours часов
     * об этом нужно сообщить объекту-каталогу игр, откуда была установлена игра
     * также надо обновить значения в мапе игрока, добавив проигранное количество часов
     * возвращает суммарное количество часов, проигранное в эту игру.
     * если игра не была установлена, то надо выкидывать RuntimeException
     */
    public int play(Game game, int hours) {
        if (checkInstall(game) != true) {
            throw new GameIsNotInstalledException(
                    "Игра не установлена. Пожалуйста, используйте метод install"
            );
        }
        if (checkHours(hours) != true) {
            throw new HoursCanNotBeLessThanZeroException(
                    "Количество часов, проведенных в игре, не может быть меньше нуля"
            );
        }
        playedTime.put(game, playedTime.get(game) + hours);
        game.getStore().addPlayTime(name, hours, game);
        return playedTime.get(game);
    }

    /**
     * Проверяет, что в метод play не пытаются передать отрицательное число
     */
    private boolean checkHours(int hours) {
        return hours >= 0 ? true : false;
    }

    /**
     * Проверяет, скачана ли игра, в которую хочет поиграть игрок
     */
    public boolean checkInstall(Game game) {
        return playedTime.containsKey(game) ? true : false;
    }

    /**
     * Метод принимает жанр игры (одно из полей объекта игры) и
     * суммирует время, проигранное во все игры этого жанра этим игроком
     */
    public int sumGenre(String genre) {
        int sum = 0;
        for (Game game : playedTime.keySet()) {
            if (game.getGenre().equals(genre)) {
                sum += playedTime.get(game);
            }
        }
        return sum;
    }

    public Game[] mostPlayerByGenre(String genre) {
        if (countMostPlayerByGenre(genre) == 0) {
            return null;
        }
        Game[] mostPlayed = new Game[countMostPlayerByGenre(genre)];
        int copyToIndex = 0;

        int mostTime = findMaxPlayedHoursByGenre(genre);
        for (Game game : playedTime.keySet()) {
            if (game.getGenre().equals(genre) && checkIfGamePlayedOrJustInstalled(game)) {
                if (playedTime.get(game) == mostTime) {
                    mostPlayed[copyToIndex] = game;
                    copyToIndex++;
                }
            }
        }
        return mostPlayed;
    }

    /**
     * Показывает, сколько часов максимум юзер играл в игру конкретного жанра
     */
    private int findMaxPlayedHoursByGenre(String genre) {
        int mostTime = 0;
        for (Game game : playedTime.keySet()) {
            if (checkIfGamePlayedOrJustInstalled(game)) {
                if (game.getGenre().equals(genre) && playedTime.get(game) >= mostTime) {
                    mostTime = playedTime.get(game);
                }
            }
        }
        return mostTime;
    }

    /**
     * Если у игрока в данном жанре максимум часов в игре/ах 0, то этот метод проверяет
     * играли ли хоть раз в эти игры или они были просто установлены (installed)
     */
    public boolean checkIfGamePlayedOrJustInstalled(Game game) {
        return game.getPlayTimes(getName()) > 0 ? true : false;
    }

    /**
     * Показывает, в какое число игр определенного жанра игралось одинаково большое число часов
     */
    private int countMostPlayerByGenre(String genre) {
        int time = findMaxPlayedHoursByGenre(genre);
        int amount = 0;
        for (Game game : playedTime.keySet()) {
            if (playedTime.get(game) == time && game.getGenre().equals(genre) && checkIfGamePlayedOrJustInstalled(game)) {
                amount++;
            }
        }
        return amount;
    }
}
