package persistence;

import model.*;
import model.exceptions.ChessGameException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public abstract class JsonFileTest extends GameTest {
    protected JsonFileReader reader;
    protected List<Chess> games;

    @BeforeEach
    void runBefore() {
        games = new ArrayList<>();
    }

    @Test
    void testJsonFileNoGames() {
        try {
            games = reader.readFile();
        } catch (IOException e) {
            fail("IOException thrown when none expected");
        } catch (ChessGameException e) {
            fail("ChessGameException thrown when none expected");
        }
        assertEquals(0, games.size());
    }

    @Test
    void testJsonFileNewGame() {
        try {
            games = reader.readFile();
        } catch (IOException e) {
            fail("IOException thrown when none expected");
        } catch (ChessGameException e) {
            fail("ChessGameException thrown when none expected");
        }
        assertEquals(1, games.size());

        assertEquals("Untitled-1", games.get(0).getName());
        List<Move> moves = games.get(0).getGameRecord();
        assertEquals(0, moves.size());
    }

    @Test
    void testJsonFileGame() {
        try {
            games = reader.readFile();
        } catch (IOException e) {
            fail("IOException thrown when none expected");
        } catch (ChessGameException e) {
            fail("ChessGameException thrown when none expected");
        }
        assertEquals(1, games.size());

        assertEquals("Untitled-1", games.get(0).getName());
        List<Move> moves = games.get(0).getGameRecord();
        assertEquals(1, moves.size());

        List<Action> actions = moves.get(0).getActions();
        assertEquals(2, actions.size());
        assertTrue(this.actionMatches(actions.get(0), Chess.W_PAWN, 1, 3, "delete"));
        assertTrue(this.actionMatches(actions.get(1), Chess.W_PAWN, 3, 3, "insert"));
    }

    @Test
    void testJsonFileGameMultipleMoves() {
        try {
            games = reader.readFile();
        } catch (IOException e) {
            fail("IOException thrown when none expected");
        } catch (ChessGameException e) {
            fail("ChessGameException thrown when none expected");
        }
        assertEquals(1, games.size());

        assertEquals("Game 1", games.get(0).getName());
        List<Move> moves = games.get(0).getGameRecord();
        assertEquals(3, moves.size());

        List<Action> actions1 = moves.get(0).getActions();
        assertEquals(2, actions1.size());
        assertTrue(this.actionMatches(actions1.get(0), Chess.W_PAWN, 1, 4, "delete"));
        assertTrue(this.actionMatches(actions1.get(1), Chess.W_PAWN, 3, 4, "insert"));

        List<Action> actions2 = moves.get(1).getActions();
        assertEquals(2, actions2.size());
        assertTrue(this.actionMatches(actions2.get(0), Chess.B_PAWN, 6, 3, "delete"));
        assertTrue(this.actionMatches(actions2.get(1), Chess.B_PAWN, 4, 3, "insert"));

        List<Action> actions3 = moves.get(2).getActions();
        assertEquals(3, actions3.size());
        assertTrue(this.actionMatches(actions3.get(0), Chess.B_PAWN, 4, 3, "delete"));
        assertTrue(this.actionMatches(actions3.get(1), Chess.W_PAWN, 3, 4, "delete"));
        assertTrue(this.actionMatches(actions3.get(2), Chess.W_PAWN, 4, 3, "insert"));
    }

    @Test
    void testJsonFileMultipleGames() {
        try {
            games = reader.readFile();
        } catch (IOException e) {
            fail("IOException thrown when none expected");
        } catch (ChessGameException e) {
            fail("ChessGameException thrown when none expected");
        }
        assertEquals(3, games.size());


        assertEquals("My chess game", games.get(0).getName());
        List<Move> moves1 = games.get(0).getGameRecord();
        assertEquals(2, moves1.size());

        List<Action> actions1 = moves1.get(0).getActions();
        assertEquals(2, actions1.size());
        assertTrue(this.actionMatches(actions1.get(0), Chess.W_KNIGHT, 0, 6, "delete"));
        assertTrue(this.actionMatches(actions1.get(1), Chess.W_KNIGHT, 2, 5, "insert"));

        List<Action> actions2 = moves1.get(1).getActions();
        assertEquals(2, actions2.size());
        assertTrue(this.actionMatches(actions2.get(0), Chess.B_KNIGHT, 7, 1, "delete"));
        assertTrue(this.actionMatches(actions2.get(1), Chess.B_KNIGHT, 5, 2, "insert"));


        assertEquals("Game 2", games.get(1).getName());
        List<Move> moves2 = games.get(1).getGameRecord();
        assertEquals(1, moves2.size());

        List<Action> actions = moves2.get(0).getActions();
        assertEquals(2, actions.size());
        assertTrue(this.actionMatches(actions.get(0), Chess.W_PAWN, 1, 0, "delete"));
        assertTrue(this.actionMatches(actions.get(1), Chess.W_PAWN, 3, 0, "insert"));


        assertEquals("Untitled-3", games.get(2).getName());
        List<Move> moves3 = games.get(2).getGameRecord();
        assertEquals(0, moves3.size());
    }
}
