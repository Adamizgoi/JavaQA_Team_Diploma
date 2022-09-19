package ru.netology;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Game {
    private final String title;
    private final String genre;
    private final GameStore store;

    /**
     * информация о том, какой игрок сколько раз запускал (play) эту игру
     * ключ - имя игрока (name)
     * значение - суммарное количество раз запуска (play) игры
     */
    private Map<String, Integer> openTimes = new HashMap<>();

    public Game(String title, String genre, GameStore store) {
        this.title = title;
        this.genre = genre;
        this.store = store;
    }

    public String getTitle() {
        return title;
    }

    public String getGenre() {
        return genre;
    }

    public GameStore getStore() {
        return store;
    }

    /**
     * Если игрок после скачивания (install) играл в игру один или более раз, этот метод позволит
     * записать количество открытий игры (play) по имени игрока в мапу openTimes
     */
    public void counterPlayTimeByPlayerName(String name) {
        if (openTimes.containsKey(name)) {
            openTimes.put(name, openTimes.get(name) + 1);
        } else {
            openTimes.put(name, 1);
        }
    }

    /**
     * Отдает по имени игрока количество раз, которое он играл в эту игру
     */
    public int getPlayTimes(String name) {
        return openTimes.containsKey(name) ? openTimes.get(name) : 0;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Game game = (Game) o;
        return Objects.equals(title, game.title) &&
                Objects.equals(genre, game.genre) &&
                Objects.equals(store, game.store);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, genre, store);
    }
}

