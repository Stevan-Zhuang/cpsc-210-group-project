package model;

import model.exceptions.ChessGameException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ChessAnalyzerTest {
    private ChessAnalyzer analyzer;
    private Chess game1;
    private Chess game2;

    @BeforeEach
    void runBefore() throws ChessGameException {
        analyzer = new ChessAnalyzer();

        game1 = new Chess("Game 1");
        game1.move(new Pos(1, 4), new Pos(3, 4));

        game2 = new Chess("Game 2");
        game2.move(new Pos(1, 3), new Pos(3, 3));
        game2.move(new Pos(1, 3), new Pos(4, 4));
    }

    @Test
    void testConstructor() {
        assertEquals(0, analyzer.getSelected());
        assertEquals(1, analyzer.getGames().size());

        assertEquals("Untitled-1", analyzer.getGames().get(0).getName());
        assertEquals(0, analyzer.getGames().get(0).getTurn());
    }

    @Test
    void testAddGame() {
        analyzer.addGame(game1);
        assertEquals(2, analyzer.getGames().size());

        assertEquals("Untitled-1", analyzer.getGames().get(0).getName());
        assertEquals(0, analyzer.getGames().get(0).getTurn());

        assertEquals("Game 1", analyzer.getGames().get(1).getName());
        assertEquals(1, analyzer.getGames().get(1).getTurn());
    }

    @Test
    void testAddMultipleGames() {
        analyzer.addGame(game1);
        analyzer.addGame(game2);
        assertEquals(3, analyzer.getGames().size());

        assertEquals("Untitled-1", analyzer.getGames().get(0).getName());
        assertEquals(0, analyzer.getGames().get(0).getTurn());

        assertEquals("Game 1", analyzer.getGames().get(1).getName());
        assertEquals(1, analyzer.getGames().get(1).getTurn());
        assertEquals("Game 2", analyzer.getGames().get(2).getName());
        assertEquals(2, analyzer.getGames().get(2).getTurn());
    }

    @Test
    void testAddNewGame() {
        analyzer.addNewGame();
        assertEquals(2, analyzer.getGames().size());

        assertEquals("Untitled-1", analyzer.getGames().get(0).getName());
        assertEquals(0, analyzer.getGames().get(0).getTurn());
        assertEquals("Untitled-2", analyzer.getGames().get(1).getName());
        assertEquals(0, analyzer.getGames().get(1).getTurn());
    }

    @Test
    void testAddMultipleNewGames() {
        analyzer.addNewGame();
        analyzer.addNewGame();
        assertEquals(3, analyzer.getGames().size());

        assertEquals("Untitled-1", analyzer.getGames().get(0).getName());
        assertEquals(0, analyzer.getGames().get(0).getTurn());
        assertEquals("Untitled-2", analyzer.getGames().get(1).getName());
        assertEquals(0, analyzer.getGames().get(1).getTurn());
        assertEquals("Untitled-3", analyzer.getGames().get(2).getName());
        assertEquals(0, analyzer.getGames().get(2).getTurn());
    }

    @Test
    void testRemoveGame() {
        analyzer.addGame(game1);
        analyzer.addGame(game2);
        analyzer.setSelected(1);
        analyzer.removeGame();
        assertEquals(2, analyzer.getGames().size());

        assertEquals("Untitled-1", analyzer.getGames().get(0).getName());
        assertEquals(0, analyzer.getGames().get(0).getTurn());
        assertEquals("Game 2", analyzer.getGames().get(1).getName());
        assertEquals(2, analyzer.getGames().get(1).getTurn());

        assertEquals(0, analyzer.getSelected());
    }

    @Test
    void testRemoveMultipleGames() {
        analyzer.addGame(game1);
        analyzer.addGame(game2);
        analyzer.setSelected(2);

        analyzer.removeGame();
        assertEquals(2, analyzer.getGames().size());

        assertEquals("Untitled-1", analyzer.getGames().get(0).getName());
        assertEquals(0, analyzer.getGames().get(0).getTurn());
        assertEquals("Game 1", analyzer.getGames().get(1).getName());
        assertEquals(1, analyzer.getGames().get(1).getTurn());
        assertEquals(1, analyzer.getSelected());

        analyzer.removeGame();
        assertEquals(1, analyzer.getGames().size());

        assertEquals("Untitled-1", analyzer.getGames().get(0).getName());
        assertEquals(0, analyzer.getGames().get(0).getTurn());

        assertEquals(0, analyzer.getSelected());
    }

    @Test
    void testRemoveFirstGame() {
        analyzer.addGame(game1);
        analyzer.removeGame();
        assertEquals(1, analyzer.getGames().size());

        assertEquals("Game 1", analyzer.getGames().get(0).getName());
        assertEquals(1, analyzer.getGames().get(0).getTurn());

        assertEquals(0, analyzer.getSelected());
    }

    @Test
    void testRemoveOnlyGame() {
        analyzer.removeGame();
        assertEquals(1, analyzer.getGames().size());

        assertEquals("Untitled-1", analyzer.getGames().get(0).getName());
        assertEquals(0, analyzer.getGames().get(0).getTurn());

        assertEquals(0, analyzer.getSelected());
    }

    @Test
    void testGetSelectedGame() {
        analyzer.addGame(game1);
        assertEquals(0, analyzer.getSelectedGame().getTurn());
        analyzer.setSelected(1);
        assertEquals(1, analyzer.getSelectedGame().getTurn());
    }
}
