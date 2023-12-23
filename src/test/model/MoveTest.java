package model;

import model.exceptions.InvalidActionException;
import model.exceptions.InvalidPosException;
import model.exceptions.InvalidSquareException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class MoveTest {
    private Move move;
    private Action action1;
    private Action action2;
    private Action action3;

    @BeforeEach
    void runBefore() throws InvalidSquareException, InvalidPosException, InvalidActionException {
        move = new Move();
        action1 = new Action(new Square(Chess.B_PAWN),
                new Pos(2, 5), "delete");
        action2 = new Action(new Square(Chess.W_KNIGHT),
                new Pos(0, 6), "delete");
        action3 = new Action(new Square(Chess.W_KNIGHT),
                new Pos(2, 5), "insert");
    }

    @Test
    void testConstructor() {
        assertTrue(move.getActions().isEmpty());
    }

    @Test
    void testAddAction() {
        move.addAction(action1);
        assertEquals(1, move.getActions().size());
        Action action1 = move.getActions().get(0);
        assertEquals(Chess.B_PAWN, action1.getSquare().getPiece());
        assertEquals(2, action1.getPos().getRank());
        assertEquals(5, action1.getPos().getFile());
        assertEquals("delete", action1.getType());
    }

    @Test
    void testAddMultipleActions() {
        move.addAction(action1);
        move.addAction(action2);
        assertEquals(2, move.getActions().size());
        Action action2 = move.getActions().get(1);
        assertEquals(Chess.W_KNIGHT, action2.getSquare().getPiece());
        assertEquals(0, action2.getPos().getRank());
        assertEquals(6, action2.getPos().getFile());
        assertEquals("delete", action2.getType());

        move.addAction(action3);
        assertEquals(3, move.getActions().size());
        Action action3 = move.getActions().get(2);
        assertEquals(Chess.W_KNIGHT, action3.getSquare().getPiece());
        assertEquals(2, action3.getPos().getRank());
        assertEquals(5, action3.getPos().getFile());
        assertEquals("insert", action3.getType());
    }
}
