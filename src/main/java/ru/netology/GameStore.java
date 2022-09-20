package ru.netology;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GameStore {
    private final List<Game> games = new ArrayList<>();

    /**
     * Информация о том, какой игрок сколько играл в игры этого каталога
     * Ключ - имя игрока
     * Значение - суммарное количество часов в игры этого каталога
     */
    private final Map<String, Integer> playedTime = new HashMap<>();

    /**
     * Создание объекта игры с заданными заголовком и жанром
     * Каждый объект игры помнит объект каталога, которому она принадлежит
     */
    public Game publishGame(String title, String genre) {
        Game game = new Game(title, genre, this);
        games.add(game);
        return game;
    }

    /**
     * Проверяет наличие игры в каталоге и возвращает true
     * если игра есть и false иначе
     */
    public boolean containsGame(Game game) {
        for (Game value : games) {
            if (value.equals(game)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Регистрирует количество времени, которое проиграл игрок
     * за игрой этого каталога. Игрок задаётся по имени. Время должно
     * суммироваться с прошлым значением для этого игрока
     */
    public void addPlayTime(String playerName, int hours, Game game) {
        if (checkUserPlayTime(game) && checkHours(hours)) {
            if (playedTime.containsKey(playerName)) {
                playedTime.put(playerName, playedTime.getOrDefault(playerName, hours) + hours);
            } else {
                playedTime.put(playerName, hours);
            }
        }
    }

    /**
     * Проверяет, что число часов игры больше или равно нулю
     */
    private boolean checkHours(int hours) {
        return hours >= 0;
    }

    /**
     * Проверяет при попытке добавить в каталог юзера и / или часы его игры,
     * что юзер играл именно в игры, сохраненные в каталоге.
     */
    private boolean checkUserPlayTime(Game game) {
        return games.contains(game);
    }

    /**
     * Ищет имя игрока, который играл в игры этого каталога больше всего
     * времени. Если игроков нет, то возвращается null
     */
    public String[] getMostPlayer() {
        String[] bestPlayer = new String[amountOfMostPlayer()];

        int copyToIndex = 0;
        int mostTime = mostPlayedTime();
        for (String playerName : playedTime.keySet()) {
            int playerTime = playedTime.get(playerName);
            if (playerTime == mostTime) {
                bestPlayer[copyToIndex] = playerName;
                copyToIndex++;
            }
        }
        return bestPlayer;
    }

    /**
     * Считает, сколько игроков играли больше всего часов
     */
    private int amountOfMostPlayer() {
        int result = 0;

        int mostTime = mostPlayedTime();

        for (String playerName : playedTime.keySet()) {
            int playerTime = playedTime.get(playerName);
            if (playerTime == mostTime) {
                result++;
            }
        }

        if (result == 0) {
            result++;
        }

        return result;
    }

    /**
     * Ищет, сколько часов максимум было наиграно каким-либо юзером в игры
     * из каталога.
     */
    private int mostPlayedTime() {
        int mostTime = 0;

        for (String playerName : playedTime.keySet()) {
            int playerTime = playedTime.get(playerName);
            if (playerTime >= mostTime) {
                mostTime = playerTime;
            }
        }
        return mostTime;
    }

    /**
     * Суммирует общее количество времени всех игроков, проведённого
     * за играми этого каталога
     */
    public int getSumPlayedTime() {
        int sum = 0;
        for (String playerName : playedTime.keySet()) {
            sum = sum + playedTime.get(playerName);
        }
        return sum;
    }
}





