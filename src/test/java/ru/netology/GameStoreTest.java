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

    // тест-кейсы написаны по технике "попарное тестирование" для проверки
    // двух связанных методов - addPlayTime и getMostPlayer

    @Test
    public void shouldShowMostPlayerIfOneGameInRepoIfUsersPlayedOneTimesIfWinnerPlayedOneHour() {
        Game game = store.publishGame("Титаны", "Хорроры");
        store.addPlayTime("moon11", 1, game);
        store.addPlayTime("moon9", 0, game);
        store.addPlayTime("looser", 0, game);

        String expected = "moon11";
        String actual = store.getMostPlayer();

        Assertions.assertEquals(expected, actual);
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

        /*String[] expected = {"moon11", "looser"};
        String[] actual = */
        store.getMostPlayer();

        /*Assertions.assertArrayEquals(expected, actual);*/
    }

    @Test
    public void shouldShowTwoOrMoreMostPlayerIfUsersPlayedEqualHours() {
        Game game = store.publishGame("Титаны", "Хорроры");

        store.addPlayTime("moon11", 5, game);
        store.addPlayTime("moon9", 5, game);

        /*String[] expected = {"moon11", "moon9"};
        String actual = */
        store.getMostPlayer();

        /*Assertions.assertArrayEquals(expected, actual);*/
    }

    /*@Test
    public void shouldShowMoreThatTwoMostPlayerIfOneGameInRepoIfUsersPlayedOneTimesIfEachWinnerPlayedOneHour() {
        Game game = store.publishGame("Титаны", "Хорроры");
        store.addPlayTime("moon11", 1);
        store.addPlayTime("moon9", 1);
        store.addPlayTime("looser", 1);
        String[] expected = {"moon11", "moon9", "looser"};
        String[] actual = store.getMostPlayer();
        Assertions.assertArrayEquals(expected, actual);
    }*/

    /*@Test
    public void shouldShowMostPlayerIfManyGamesInRepoIfUsersPlayedInSameGamesIfUsersPlayedSeveralTimesIfWinnerPlayedManyHours() {
        Game game = store.publishGame("Титаны", "Хорроры");
        Game game1 = store.publishGame("Сапер", "Шутеры");
        Game game2 = store.publishGame("Казаки", "Стратегия");
        store.addPlayTime("moon11", 1);
        //нет возможности указать, в какие игры играли юзеры
        store.addPlayTime("moon9", 0);
        //нет возможности показать, что юзеры играли в несколько одинаковых игр
        store.addPlayTime("looser", 0);
         expected =
         actual =
        Assertions.;
    }*/

    // ТУТ НУЖНО ДОПИСАТЬ ЕЩЕ 8 ТЕСТ-КЕЙСОВ ИЗ ТАБЛИЦЫ ПОПАРНОГО ТЕСТИРОВАНИЯ

    @Test
    public void shouldShowMostPlayerIfThereIsOnlyOneUserInStore() {
        Game game = store.publishGame("Титаны", "Хорроры");
        store.addPlayTime("moon11", 5, game);

        String expected = "moon11";
        String actual = store.getMostPlayer();

        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void shouldAddPlayTimeAndShowMostPlayerIfOneUserPlayedZeroHours() {
        Game game = store.publishGame("Титаны", "Хорроры");
        store.addPlayTime("moon11", 0, game);

        String expected = "moon11";
        String actual = store.getMostPlayer();

        Assertions.assertEquals(expected, actual);
    }

    /*
    @Test
    public void shouldNotAddPlayTimeForNewGameIfGameIsNotInThisStore() {}
    */

    @Test
    public void shouldNotAddPlayTimeForNewGameWhenTimeIsLessZero() {
        Game game = store.publishGame("Титаны", "Хорроры");
        store.addPlayTime("moon11", -1, game);

        String expected = null;
        String actual = store.getMostPlayer();

        Assertions.assertEquals(expected, actual);
    }

    @Test

    public void shouldNotAddPlayTimeIfStoreIsEmpty() {
        GameStore fakeStore = new GameStore();
        Game game = fakeStore.publishGame("Титаны", "Хорроры");

        store.addPlayTime("moon11", 5, game);

        String expected = null;
        String actual = store.getMostPlayer();

        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void shouldNotCrashSystemIfGetMostPlayedButThereAreNoOneUserSavedInSystem() {
        Game game = store.publishGame("Титаны", "Хорроры");

        String expected = null;
        String actual = store.getMostPlayer();

        Assertions.assertEquals(expected, actual);
    }

    // ТЕСТЫ НА GETSUM
    // GETSUM ДОЛЖНО РАБОТАТЬ В ПУСТОМ РЕПО
    // И ЕСЛИ НЕТ ЮЗЕРОВ В КАТАЛОГЕ
    // GETSUM ДОЛЖНА РАБОТАТЬ ПРИ ОДНОМ ЮЗЕРЕ В ПРОГЕ
    // ПРИ МНОГИХ ЮЗЕРАХ В ПРОГЕ
    // ПРИ МНОГИХ ИГРАХ, КОГДА МНОГО ЮЗЕРОВ ИГРАЕТ В РАЗНЫЕ ИГРЫ
    // ПРИ МНОГИХ ИГРАХ И ЮЗЕРАХ И СЕССИЯХ ИГРЫ

    /*@Test
    тест не дописан
    public void shouldSumPlayTimeOfAllUsersIfManyUsersPlayedInGameStore() {

        store.addPlayTime("moon11", 1);
        store.addPlayTime("moon9", 6);
        store.addPlayTime("looser", 4);

        int expected = 11;
        int actual = store.getSumPlayedTime();

        Assertions.assertEquals(expected, actual);
    }*/
}
