package persistence;

import model.Chess;
import model.Pos;
import model.exceptions.ChessGameException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;

import static org.junit.jupiter.api.Assertions.fail;

public class JsonFileWriterTest extends JsonFileTest {
    private JsonFileWriter writer;

    @Override
    @BeforeEach
    void runBefore() {
        writer = new JsonFileWriter("data/testFileWriter.json");
        reader = new JsonFileReader("data/testFileWriter.json");
        super.runBefore();
    }

    @Test
    void testJsonFileWithIllegalName() {
        try {
            writer = new JsonFileWriter("data/???.json");
            writer.writeFile(games);
            fail("No model.exceptions thrown when FileNotFoundException expected");
        } catch (FileNotFoundException e) {
            // pass
        }
    }

    @Override
    @Test
    void testJsonFileNoGames() {
        try {
            writer.writeFile(games);
        } catch (FileNotFoundException e) {
            fail("FileNotFoundException thrown when none expected");
        }
        super.testJsonFileNoGames();
    }

    @Override
    @Test
    void testJsonFileNewGame() {
        games.add(new Chess("Untitled-1"));

        try {
            writer.writeFile(games);
        } catch (FileNotFoundException e) {
            fail("FileNotFoundException thrown when none expected");
        }
        super.testJsonFileNewGame();
    }

    @Override
    @Test
    void testJsonFileGame() {
        try {
            games.add(new Chess("Untitled-1"));
            games.get(0).move(new Pos(1, 3), new Pos(3, 3));
        } catch (ChessGameException e) {
            fail("ChessGameException thrown when none expected");
        }

        try {
            writer.writeFile(games);
        } catch (FileNotFoundException e) {
            fail("FileNotFoundException thrown when none expected");
        }
        super.testJsonFileGame();
    }

    @Override
    @Test
    void testJsonFileGameMultipleMoves() {
        try {
            games.add(new Chess("Untitled-1"));
            games.get(0).setName("Game 1");
            games.get(0).move(new Pos(1, 4), new Pos(3, 4));
            games.get(0).move(new Pos(6, 3), new Pos(4, 3));
            games.get(0).move(new Pos(3, 4), new Pos(4, 3));
        } catch (ChessGameException e) {
            fail("ChessGameException thrown when none expected");
        }

        try {
            writer.writeFile(games);
        } catch (FileNotFoundException e) {
            fail("FileNotFoundException thrown when none expected");
        }
        super.testJsonFileGameMultipleMoves();
    }

    @Override
    @Test
    void testJsonFileMultipleGames() {
        try {
            games.add(new Chess("Untitled-1"));
            games.get(0).setName("My chess game");
            games.get(0).move(new Pos(0, 6), new Pos(2, 5));
            games.get(0).move(new Pos(7, 1), new Pos(5, 2));

            games.add(new Chess("Untitled-2"));
            games.get(1).setName("Game 2");
            games.get(1).move(new Pos(1, 0), new Pos(3, 0));

            games.add(new Chess("Untitled-3"));
        } catch (ChessGameException e) {
            fail("ChessGameException thrown when none expected");
        }

        try {
            writer.writeFile(games);
        } catch (FileNotFoundException e) {
            fail("FileNotFoundException thrown when none expected");
        }
        super.testJsonFileMultipleGames();
    }
}
