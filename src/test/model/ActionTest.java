package model;

import model.exceptions.ChessGameException;
import model.exceptions.InvalidActionException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class ActionTest {

    @Test
    void testConstructor() {
        try {
            Action action = new Action(new Square(Chess.B_KNIGHT), new Pos(7, 0), "delete");
            assertEquals(Chess.B_KNIGHT, action.getSquare().getPiece());
            assertEquals(7, action.getPos().getRank());
            assertEquals(0, action.getPos().getFile());
            assertEquals("delete", action.getType());

            action = new Action(new Square(Chess.W_ROOK), new Pos(6, 3), "insert");
            assertEquals(Chess.W_ROOK, action.getSquare().getPiece());
            assertEquals(6, action.getPos().getRank());
            assertEquals(3, action.getPos().getFile());
            assertEquals("insert", action.getType());
        } catch (ChessGameException e) {
            fail("ChessGameException thrown when none expected");
        }
    }

    @Test
    void testInvalidType() {
        try {
            new Action(new Square(Chess.W_KNIGHT), new Pos(1, 3), "not a valid type");
            fail("InvalidActionException expected but not thrown");
        } catch (InvalidActionException e) {
            // pass
        } catch (ChessGameException e) {
            fail("InvalidActionException expected but other ChessGameException thrown instead");
        }
    }
}
