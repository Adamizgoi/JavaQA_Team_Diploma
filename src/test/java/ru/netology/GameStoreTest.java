package ru.netology;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class GameStoreTest {
    GameStore store = new GameStore();

    @Test
    public void shouldAddGame() {
        Game game = store.publishGame("Нетология Баттл Онлайн", "Аркады");

        assertTrue(store.containsGame(game));
    }

    @Test
    public void shouldBeOpportunityToAddManyGames() {
        Game game = store.publishGame("Титаны", "Хорроры");
        Game game1 = store.publishGame("Цивилизация", "Стратегии");
        Game game2 = store.publishGame("Ведьмак", "Ролевая игра");

        assertTrue(store.containsGame(game));
        assertTrue(store.containsGame(game1));
        assertTrue(store.containsGame(game2));
    }

    @Test
    public void shouldNotCrashSystemIfWeTryFindGameInEmptyRepo() {
        GameStore notForUse = new GameStore();
        Game gameError = new Game("Несохраненная игра", "Симулятор", notForUse);

        assertFalse(store.containsGame(gameError));
    }

    @Test
    public void shouldNotFindUnsavedGameInFullRepo() {
        Game game = store.publishGame("Титаны", "Хорроры");
        Game game1 = store.publishGame("Цивилизация", "Стратегии");
        Game game2 = store.publishGame("Ведьмак", "Ролевая игра");

        GameStore notForUse = new GameStore();
        Game gameError = new Game("Несохраненная игра", "Симулятор", notForUse);

        assertFalse(store.containsGame(gameError));
    }

    /** 12 тест-кейсов ниже написаны по технике "попарное тестирование" для проверки
     * двух связанных методов - addPlayTime и getMostPlayer
     * https://docs.google.com/spreadsheets/d/1TQV4qyVK_25LtW5g_-Bl2sVml6QoHWKn/edit?usp=sharing&ouid=101540204448510628829&rtpof=true&sd=true
     */

    @Test
    public void shouldShowMostPlayerIfOneGameInRepoIfUsersPlayedOneTimesIfWinnerPlayedOneHour() {
        Game game = store.publishGame("Титаны", "Хорроры");
        store.addPlayTime("moon11", 1, game);
        store.addPlayTime("moon9", 0, game);
        store.addPlayTime("looser", 0, game);

        String[] expected = {"moon11"};
        String[] actual = store.getMostPlayer();

        Assertions.assertArrayEquals(expected, actual);
    }

    @Test
    public void shouldShowTwoMostPlayerIfOneGameInRepoIfUsersPlayedSeveralTimesIfWinnerPlayedALotOfHours() {
        Game game = store.publishGame("Титаны", "Хорроры");

        store.addPlayTime("moon11", 1, game);
        store.addPlayTime("moon11", 5, game);
        store.addPlayTime("moon9", 0, game);
        store.addPlayTime("moon9", 1, game);
        store.addPlayTime("moon9", 2, game);
        store.addPlayTime("looser", 6, game);

        String[] expected = {"moon11", "looser"};
        String[] actual = store.getMostPlayer();

        Assertions.assertArrayEquals(expected, actual);
    }

    @Test
    public void shouldShowMoreThenTwoMostPlayersIfOneGameInRepoIfUsersPlayedOneTimesIfWinnerPlayedOneHour() {
        Game game = store.publishGame("Титаны", "Хорроры");

        store.addPlayTime("moon11", 1, game);
        store.addPlayTime("moon9", 1, game);
        store.addPlayTime("looser", 1, game);

        String[] expected = {"moon11", "moon9", "looser"};
        String[] actual = store.getMostPlayer();

        Assertions.assertArrayEquals(expected, actual);
    }

    @Test
    public void shouldShowMostPlayerIfALotGamesInRepoIfPlayedInEqualGamesIfUsersPlayedSeveralTimesIfWinnerPlayedALotHour() {
        Game game = store.publishGame("Титаны", "Хорроры");
        Game game1 = store.publishGame("Безумцы", "Боевик");
        Game game2 = store.publishGame("Цивилизация", "Стратегия");

        store.addPlayTime("moon11", 1, game);
        store.addPlayTime("moon11", 2, game1);
        store.addPlayTime("moon11", 4, game2);
        store.addPlayTime("moon9", 1, game);
        store.addPlayTime("moon9", 2, game1);
        store.addPlayTime("moon9", 3, game2);
        store.addPlayTime("looser", 3, game);
        store.addPlayTime("looser", 2, game1);
        store.addPlayTime("looser", 1, game2);

        String[] expected = {"moon11"};
        String[] actual = store.getMostPlayer();

        Assertions.assertArrayEquals(expected, actual);
    }

    @Test
    public void shouldShowTwoMostPlayersIfALotGamesInRepoIfPlayedInEqualGamesIfUsersPlayedOneTimeIfWinnerPlayedOneHour() {
        Game game = store.publishGame("Титаны", "Хорроры");
        Game game1 = store.publishGame("Безумцы", "Боевик");
        Game game2 = store.publishGame("Цивилизация", "Стратегия");

        store.addPlayTime("moon11", 1, game1);
        store.addPlayTime("moon9", 1, game1);
        store.addPlayTime("looser", 0, game1);

        String[] expected = {"moon11", "moon9"};
        String[] actual = store.getMostPlayer();

        Assertions.assertArrayEquals(expected, actual);
    }

    @Test
    public void shouldShowALotMostPlayersIfALotGamesInRepoIfPlayedInEqualGamesIfUsersPlayedSeveralTimesIfWinnerPlayedALotHour() {
        Game game = store.publishGame("Титаны", "Хорроры");
        Game game1 = store.publishGame("Безумцы", "Боевик");
        Game game2 = store.publishGame("Цивилизация", "Стратегия");

        store.addPlayTime("moon11", 1, game);
        store.addPlayTime("moon11", 2, game1);
        store.addPlayTime("moon11", 3, game2);
        store.addPlayTime("moon9", 1, game);
        store.addPlayTime("moon9", 2, game1);
        store.addPlayTime("moon9", 3, game2);
        store.addPlayTime("looser", 3, game);
        store.addPlayTime("looser", 2, game1);
        store.addPlayTime("looser", 1, game2);

        String[] expected = {"moon11", "moon9", "looser"};
        String[] actual = store.getMostPlayer();

        Assertions.assertArrayEquals(expected, actual);
    }

    @Test
    public void shouldShowMostPlayerIfALotGamesInRepoIfPlayedInTotallyDifferentGamesIfUsersPlayedOneTimeIfWinnerPlayedALotHour() {
        Game game = store.publishGame("Титаны", "Хорроры");
        Game game1 = store.publishGame("Безумцы", "Боевик");
        Game game2 = store.publishGame("Цивилизация", "Стратегия");

        store.addPlayTime("moon11", 10, game);
        store.addPlayTime("moon9", 2, game1);
        store.addPlayTime("looser", 1, game2);

        String[] expected = {"moon11"};
        String[] actual = store.getMostPlayer();

        Assertions.assertArrayEquals(expected, actual);
    }

    @Test
    public void shouldShowTwoMostPlayersIfALotGamesInRepoIfPlayedInTotallyDifferentGamesIfUsersPlayedSeveralTimesIfWinnerPlayedOneHour() {
        Game game = store.publishGame("Титаны", "Хорроры");
        Game game1 = store.publishGame("Безумцы", "Боевик");
        Game game2 = store.publishGame("Цивилизация", "Стратегия");
        Game game3 = store.publishGame("Зира", "Симулятор");
        Game game4 = store.publishGame("Ковчег", "Симулятор");
        Game game5 = store.publishGame("Библиотека", "Образовательные");

        store.addPlayTime("moon11", 1, game);
        store.addPlayTime("moon11", 0, game3);
        store.addPlayTime("moon9", 1, game1);
        store.addPlayTime("moon9", 0, game4);
        store.addPlayTime("looser", 0, game2);
        store.addPlayTime("looser", 0, game5);

        String[] expected = {"moon11", "moon9"};
        String[] actual = store.getMostPlayer();

        Assertions.assertArrayEquals(expected, actual);
    }

    @Test
    public void shouldShowALotMostPlayersIfALotGamesInRepoIfPlayedInTotallyDifferentGamesIfUsersPlayedOneTimesIfWinnerPlayedManyHour() {
        Game game = store.publishGame("Титаны", "Хорроры");
        Game game1 = store.publishGame("Безумцы", "Боевик");
        Game game2 = store.publishGame("Цивилизация", "Стратегия");

        store.addPlayTime("moon11", 10, game);
        store.addPlayTime("moon9", 10, game1);
        store.addPlayTime("looser", 10, game2);


        String[] expected = {"moon11", "moon9", "looser"};
        String[] actual = store.getMostPlayer();

        Assertions.assertArrayEquals(expected, actual);
    }

    @Test
    public void shouldShowMostPlayerIfALotGamesInRepoIfPlayedInDifferentGamesIfUsersPlayedOneTimesIfWinnerPlayedOneHour() {
        Game game = store.publishGame("Титаны", "Хорроры");
        Game game1 = store.publishGame("Безумцы", "Боевик");

        store.addPlayTime("moon11", 1, game);
        store.addPlayTime("moon9", 0, game);
        store.addPlayTime("looser", 0, game1);


        String[] expected = {"moon11"};
        String[] actual = store.getMostPlayer();

        Assertions.assertArrayEquals(expected, actual);
    }
    @Test
    public void shouldShowTwoMostPlayersIfALotGamesInRepoIfPlayedInDifferentGamesIfUsersPlayedManyTimesIfWinnerPlayedManyHour() {
        Game game = store.publishGame("Титаны", "Хорроры");
        Game game1 = store.publishGame("Безумцы", "Боевик");

        store.addPlayTime("moon11", 1, game);
        store.addPlayTime("moon11", 20, game);
        store.addPlayTime("moon11", 9, game1);
        store.addPlayTime("moon9", 0, game);
        store.addPlayTime("moon9", 15, game1);
        store.addPlayTime("moon9", 15, game1);
        store.addPlayTime("looser", 29, game);

        String[] expected = {"moon11", "moon9"};
        String[] actual = store.getMostPlayer();

        Assertions.assertArrayEquals(expected, actual);
    }
    @Test
    public void shouldShowALotMostPlayersIfALotGamesInRepoIfPlayedInDifferentGamesIfUsersPlayedManyTimesIfWinnerPlayedManyHour() {
        Game game = store.publishGame("Титаны", "Хорроры");
        Game game1 = store.publishGame("Безумцы", "Боевик");

        store.addPlayTime("moon11", 1, game);
        store.addPlayTime("moon11", 20, game);
        store.addPlayTime("moon11", 9, game1);
        store.addPlayTime("moon9", 0, game);
        store.addPlayTime("moon9", 15, game1);
        store.addPlayTime("moon9", 15, game1);
        store.addPlayTime("looser", 29, game);
        store.addPlayTime("looser", 1, game);

        String[] expected = {"moon11", "moon9", "looser"};
        String[] actual = store.getMostPlayer();

        Assertions.assertArrayEquals(expected, actual);
    }

    /** Конец блока pairwise testing
     */

    @Test
    public void shouldShowMostPlayerIfThereIsOnlyOneUserInStore() {
        Game game = store.publishGame("Титаны", "Хорроры");
        store.addPlayTime("moon11", 5, game);

        String[] expected = {"moon11"};
        String[] actual = store.getMostPlayer();

        Assertions.assertArrayEquals(expected, actual);
    }

    @Test
    public void shouldAddPlayTimeAndShowMostPlayerIfOneUserPlayedZeroHours() {
        Game game = store.publishGame("Титаны", "Хорроры");
        store.addPlayTime("moon11", 0, game);

        String[] expected = {"moon11"};
        String[] actual = store.getMostPlayer();

        Assertions.assertArrayEquals(expected, actual);
    }

    /*@Test
    public void shouldNotAddPlayTimeForNewGameWhenTimeIsLessZero() {
        Game game = store.publishGame("Титаны", "Хорроры");
        store.addPlayTime("moon11", -1, game);
        store.addPlayTime("moon9", 1, game);

        int expected = 1;
        int actual = store.getSumPlayedTime();

        Assertions.assertEquals(expected, actual);
    }*/

    @Test

    public void shouldNotAddPlayTimeIfStoreIsEmpty() {
        GameStore fakeStore = new GameStore();
        Game game = fakeStore.publishGame("Титаны", "Хорроры");

        store.addPlayTime("moon11", 5, game);

        String[] expected = {null};
        String[] actual = store.getMostPlayer();

        Assertions.assertArrayEquals(expected, actual);
    }

    @Test
    public void shouldNotCrashSystemIfGetMostPlayedButThereAreNoOneUserSavedInSystem() {
        Game game = store.publishGame("Титаны", "Хорроры");

        String[] expected = {null};
        String[] actual = store.getMostPlayer();

        Assertions.assertArrayEquals(expected, actual);
    }

    @Test
    public void shouldSumPlayTimeOfAllUsersIfManyUsersPlayedInGameStore() {
        Game game = store.publishGame("Титаны", "Хорроры");

        store.addPlayTime("moon11", 1, game);
        store.addPlayTime("moon9", 6, game);
        store.addPlayTime("looser", 4, game);

        int expected = 11;
        int actual = store.getSumPlayedTime();

        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void getSumShouldWorkInEmptyRepo() {
        int expected = 0;
        int actual = store.getSumPlayedTime();

        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void shouldSumPlayedTimeIfOnePlayerPlayedOneTimes() {
        Game game = store.publishGame("Титаны", "Хорроры");

        store.addPlayTime("moon11", 1, game);

        int expected = 1;
        int actual = store.getSumPlayedTime();

        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void shouldSumPlayedTimeIfOnePlayerPlayedSeveralTimes() {
        Game game = store.publishGame("Титаны", "Хорроры");

        store.addPlayTime("moon11", 1, game);
        store.addPlayTime("moon11", 111, game);
        store.addPlayTime("moon11", 0, game);

        int expected = 112;
        int actual = store.getSumPlayedTime();

        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void shouldSumPlayedTimeIfALotPlayersPlayedSeveralTimesInDifferentGames() {
        Game game = store.publishGame("Титаны", "Хорроры");
        Game game1 = store.publishGame("Безумцы", "Боевик");
        Game game2 = store.publishGame("Цивилизация", "Стратегия");
        Game game3 = store.publishGame("Зира", "Симулятор");
        Game game4 = store.publishGame("Ковчег", "Симулятор");
        Game game5 = store.publishGame("Библиотека", "Образовательные");

        store.addPlayTime("moon11", 1, game);
        store.addPlayTime("moon9", 1, game3);
        store.addPlayTime("looser", 3, game5);
        store.addPlayTime("moon9", 5, game1);
        store.addPlayTime("moon11", 100, game1);
        store.addPlayTime("moon11", 0, game);
        store.addPlayTime("looser", 5, game4);
        store.addPlayTime("moon9", 5, game4);
        store.addPlayTime("moon9", 0, game2);

        int expected = 120;
        int actual = store.getSumPlayedTime();

        Assertions.assertEquals(expected, actual);
    }
}
