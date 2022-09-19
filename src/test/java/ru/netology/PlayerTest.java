package ru.netology;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class PlayerTest {
    GameStore store = new GameStore();
    Game game1 = store.publishGame("Call of Duty", "Action");
    Game game1error = new Game("Not saved in store", "Error", store);

    /**
     * Блок тестов к методу installGame
     */
    @Test
    public void shouldAddGameIfGameIsNew() {
        Player crasher = new Player("crasher");
        crasher.installGame(game1);

        boolean expected = true;
        boolean actual = crasher.checkInstall(game1);

        Assertions.assertEquals(actual, expected);
    }

    @Test
    public void shouldNotAddGameIfGameIsAlreadySaved() {
        Player crasher = new Player("crasher");
        crasher.installGame(game1);

        Assertions.assertThrows(RuntimeException.class, () -> {
            crasher.installGame(game1);
        });
    }

    /** Конец блока тестов к методу installGame
     */


    /**
     * Блок тестов к методу play
     */
    @Test
    public void shouldAddPlayTimeToGameStore() {
        Player crasher = new Player("crasher");
        crasher.installGame(game1);
        crasher.play(game1, 5);

        int expected = 5;
        int actual = store.getSumPlayedTime();

        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void shouldAddPlayTimeToGameStoreIfPlayTimeIsZero() {
        Player crasher = new Player("crasher");
        crasher.installGame(game1);
        crasher.play(game1, 0);

        int expected = 0;
        int actual = store.getSumPlayedTime();

        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void shouldNotAddPlayTimeToGameStoreIfPlayTimeIsLessZero() {
        Player crasher = new Player("crasher");
        crasher.installGame(game1);
        crasher.play(game1, -1);

        int expected = 0;
        int actual = store.getSumPlayedTime();

        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void shouldAddPlayTimeToPlayerMapIfPlayTimeIsZero() {
        Player crasher = new Player("crasher");
        crasher.installGame(game1);

        int expected = 0;
        int actual = crasher.play(game1, 0);

        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void shouldAddPlayTimeToPlayerMap() {
        Player crasher = new Player("crasher");
        crasher.installGame(game1);

        int expected = 5;
        int actual = crasher.play(game1, 5);
        ;

        Assertions.assertEquals(expected, actual);
    }

    /**
     * ТУТ ВАЖНО ПОПРАВИТЬ МЕТОД В ФР
     */
    @Test
    public void shouldAddPlayTimeToPlayerMapIfUserPlayedSeveralTimes() {
        Player crasher = new Player("crasher");
        crasher.installGame(game1);
        crasher.play(game1, 5);
        crasher.play(game1, 10);

        int expected = 16;
        int actual = crasher.play(game1, 1);

        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void shouldThrowRuntimeExceptionIfUserTryAddPlayTimeInNotInstalledGame() {
        Player crasher = new Player("crasher");

        Assertions.assertThrows(RuntimeException.class, () -> {
                    crasher.play(game1error, 5);
                }
        );
    }

    /** Конец блока тестов к методу play
     */


    /**
     * Блок тестов к методу sumGenre
     */
    @Test
    public void shouldSumGenreIfOneGame() {
        GameStore store = new GameStore();
        Game game = store.publishGame("Нетология Баттл Онлайн", "Аркады");

        Player player = new Player("Petya");
        player.installGame(game);
        player.play(game, 3);

        int expected = 3;
        int actual = player.sumGenre(game.getGenre());
        assertEquals(expected, actual);
    }

    @Test
    public void shouldSumGenreIfALotOfGames() {
        GameStore store = new GameStore();
        Game game = store.publishGame("Нетология Баттл Онлайн", "Аркады");
        Game game1 = store.publishGame("Нетология Баттл Оффлайн", "Аркады");
        Game game2 = store.publishGame("Нетология Баттл Метавселенная", "Симулятор");

        Player player = new Player("Petya");
        player.installGame(game);
        player.installGame(game1);
        player.installGame(game2);

        player.play(game, 1);
        player.play(game, 3);
        player.play(game1, 6);
        player.play(game2, 5);

        int expected = 10;
        int actual = player.sumGenre(game.getGenre());
        assertEquals(expected, actual);
    }

    /** Конец блока тестов к методу sumGenre
     */


    /**
     * Блок тестов к методу mostPlayerByGenre
     */
    @Test
    public void shouldNotShowMostPlayedGameIfGameInstalledButNotPlayed() {
        Player crasher = new Player("crasher");
        crasher.installGame(game1);

        Game[] expected = {null};
        Game[] actual = crasher.mostPlayerByGenre("Action");

        Assertions.assertArrayEquals(expected, actual);
    }

    /** Конец блока тестов к методу mostPlayerByGenre
     */
}




