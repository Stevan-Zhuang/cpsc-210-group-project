package model;

import model.exceptions.InvalidSquareException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class SquareTest {

    @Test
    void testConstructor() {
        try {
            Square square = new Square(Chess.B_KING);
            assertEquals(Chess.B_KING, square.getPiece());

            square = new Square(Chess.W_PAWN);
            assertEquals(Chess.W_PAWN, square.getPiece());
        } catch (InvalidSquareException e) {
            fail("InvalidSquareException thrown when none expected");
        }
    }

    @Test
    void testInvalidPiece() {
        try {
            new Square('f');
            fail("InvalidSquareException expected but not thrown");
        } catch (InvalidSquareException e) {
            // pass
        }
    }
}
