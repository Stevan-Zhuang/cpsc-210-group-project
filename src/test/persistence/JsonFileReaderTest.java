package persistence;

import model.exceptions.ChessGameException;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.fail;

public class JsonFileReaderTest extends JsonFileTest {

    @Test
    void testJsonFileDoesNotExist() {
        try {
            reader = new JsonFileReader("data/test.json");
            reader.readFile();
            fail("No exceptions thrown when IOException expected");
        } catch (IOException e) {
            // pass
        } catch (ChessGameException e) {
            fail("ChessGameException thrown when IOException expected");
        }
    }

    @Override
    @Test
    void testJsonFileNoGames() {
        reader = new JsonFileReader("data/testFileNoGames.json");
        super.testJsonFileNoGames();
    }

    @Override
    @Test
    void testJsonFileNewGame() {
        reader = new JsonFileReader("data/testFileNewGame.json");
        super.testJsonFileNewGame();
    }

    @Override
    @Test
    void testJsonFileGame() {
        reader = new JsonFileReader("data/testFileGame.json");
        super.testJsonFileGame();
    }

    @Override
    @Test
    void testJsonFileGameMultipleMoves() {
        reader = new JsonFileReader("data/testFileGameMultipleMoves.json");
        super.testJsonFileGameMultipleMoves();
    }

    @Override
    @Test
    void testJsonFileMultipleGames() {
        reader = new JsonFileReader("data/testFileMultipleGames.json");
        super.testJsonFileMultipleGames();
    }
}
