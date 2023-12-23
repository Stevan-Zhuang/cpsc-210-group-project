package model;

import java.util.List;

import model.exceptions.ChessGameException;
import model.exceptions.InvalidTurnException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ChessTest extends GameTest {
    private Chess game;

    @BeforeEach
    void runBefore() {
        game = new Chess("My Chess Game");
    }

    @Test
    void testConstructor() {
        assertTrue(game.getGameRecord().isEmpty());
        assertEquals("My Chess Game", game.getName());
        assertEquals(0, game.getTurn());

        try {
            Square[][] board = game.getNewBoard();
            assertTrue(this.boardEquals(game.getBoard(), board));
        } catch (ChessGameException e) {
            fail("ChessGameException thrown when none expected");
        }
    }

    @Test
    void testMove() {
        try {
            Square[][] board = game.getNewBoard();
            game.move(new Pos(1, 3), new Pos(3, 3)); // d4
            board[1][3] = new Square(Chess.EMPTY);
            board[3][3] = new Square(Chess.W_PAWN);

            assertTrue(boardEquals(game.getBoard(), board));

            assertEquals(1, game.getTurn());
            List<Move> record = game.getGameRecord();
            assertEquals(1, record.size());
            List<Action> actions = record.get(0).getActions();
            assertEquals(2, actions.size());

            assertTrue(this.actionMatches(actions.get(0), Chess.W_PAWN, 1, 3, "delete"));
            assertTrue(this.actionMatches(actions.get(1), Chess.W_PAWN, 3, 3, "insert"));
        } catch (ChessGameException e) {
            fail("ChessGameException thrown when none expected");
        }
    }

    @Test
    void testMultipleMoves() {
        try {
            Square[][] board = game.getNewBoard();
            game.move(new Pos(0, 1), new Pos(2, 2)); // Nc3
            game.move(new Pos(6, 7), new Pos(4, 7)); // h5
            board[0][1] = new Square(Chess.EMPTY);
            board[2][2] = new Square(Chess.W_KNIGHT);
            board[6][7] = new Square(Chess.EMPTY);
            board[4][7] = new Square(Chess.B_PAWN);

            assertTrue(boardEquals(game.getBoard(), board));

            assertEquals(2, game.getTurn());
            List<Move> record = game.getGameRecord();
            assertEquals(2, record.size());
            List<Action> actions1 = record.get(0).getActions();
            List<Action> actions2 = record.get(1).getActions();
            assertEquals(2, actions1.size());
            assertEquals(2, actions2.size());

            assertTrue(this.actionMatches(actions1.get(0), Chess.W_KNIGHT, 0, 1, "delete"));
            assertTrue(this.actionMatches(actions1.get(1), Chess.W_KNIGHT, 2, 2, "insert"));
            assertTrue(this.actionMatches(actions2.get(0), Chess.B_PAWN, 6, 7, "delete"));
            assertTrue(this.actionMatches(actions2.get(1), Chess.B_PAWN, 4, 7, "insert"));
        } catch (ChessGameException e) {
            fail("ChessGameException thrown when none expected");
        }
    }

    @Test
    void testMoveWithCapture() {
        try {
            Square[][] board = game.getNewBoard();
            game.move(new Pos(1, 4), new Pos(3, 4)); // e4
            game.move(new Pos(6, 3), new Pos(4, 3)); // d5
            game.move(new Pos(3, 4), new Pos(4, 3)); // exd5
            board[1][4] = new Square(Chess.EMPTY);
            board[6][3] = new Square(Chess.EMPTY);
            board[4][3] = new Square(Chess.W_PAWN);

            assertTrue(boardEquals(game.getBoard(), board));

            assertEquals(3, game.getTurn());
            List<Move> record = game.getGameRecord();
            assertEquals(3, record.size());
            List<Action> actions = record.get(2).getActions();
            assertEquals(3, actions.size());

            assertTrue(this.actionMatches(actions.get(0), Chess.B_PAWN, 4, 3, "delete"));
            assertTrue(this.actionMatches(actions.get(1), Chess.W_PAWN, 3, 4, "delete"));
            assertTrue(this.actionMatches(actions.get(2), Chess.W_PAWN, 4, 3, "insert"));
        } catch (ChessGameException e) {
            fail("ChessGameException thrown when none expected");
        }
    }

    @Test
    void testMoveWhileNotLatestTurn() {
        try {
            Square[][] board = game.getNewBoard();
            game.move(new Pos(1, 3), new Pos(3, 3)); // d4
            game.move(new Pos(6, 3), new Pos(4, 3)); // d5
            game.move(new Pos(0, 6), new Pos(2, 5)); // Nf3
            game.undo();
            game.undo();
            game.move(new Pos(6, 0), new Pos(4, 0)); // a5

            board[1][3] = new Square(Chess.EMPTY);
            board[3][3] = new Square(Chess.W_PAWN);
            board[6][0] = new Square(Chess.EMPTY);
            board[4][0] = new Square(Chess.B_PAWN);
            assertTrue(boardEquals(game.getBoard(), board));
            assertEquals(2, game.getTurn());

            List<Move> record = game.getGameRecord();
            assertEquals(2, record.size());

            List<Action> actions1 = record.get(0).getActions();
            assertEquals(2, actions1.size());
            assertTrue(this.actionMatches(actions1.get(0), Chess.W_PAWN, 1, 3, "delete"));
            assertTrue(this.actionMatches(actions1.get(1), Chess.W_PAWN, 3, 3, "insert"));

            List<Action> actions2 = record.get(1).getActions();
            assertEquals(2, actions2.size());
            assertTrue(this.actionMatches(actions2.get(0), Chess.B_PAWN, 6, 0, "delete"));
            assertTrue(this.actionMatches(actions2.get(1), Chess.B_PAWN, 4, 0, "insert"));

        } catch (InvalidTurnException e) {
            fail("InvalidTurnException thrown when none expected");
        } catch (ChessGameException e) {
            fail("ChessGameException thrown when none expected");
        }
    }

    @Test
    void testGetNewBoard() {
        try {
            Square[][] board = game.getNewBoard();
            // First rank
            assertEquals(Chess.W_ROOK, board[0][0].getPiece());
            assertEquals(Chess.W_KNIGHT, board[0][1].getPiece());
            assertEquals(Chess.W_BISHOP, board[0][2].getPiece());
            assertEquals(Chess.W_QUEEN, board[0][3].getPiece());
            assertEquals(Chess.W_KING, board[0][4].getPiece());
            assertEquals(Chess.W_BISHOP, board[0][5].getPiece());
            assertEquals(Chess.W_KNIGHT, board[0][6].getPiece());
            assertEquals(Chess.W_ROOK, board[0][7].getPiece());
            for (int f = 0; f < 8; f++) {
                // Second rank
                assertEquals(Chess.W_PAWN, board[1][f].getPiece());
                for (int r = 2; r < 6; r++) {
                    // Third to sixth rank
                    assertEquals(Chess.EMPTY, board[r][f].getPiece());
                }
                // Seventh rank
                assertEquals(Chess.B_PAWN, board[6][f].getPiece());
            }
            // Eighth rank
            assertEquals(Chess.B_ROOK, board[7][0].getPiece());
            assertEquals(Chess.B_KNIGHT, board[7][1].getPiece());
            assertEquals(Chess.B_BISHOP, board[7][2].getPiece());
            assertEquals(Chess.B_QUEEN, board[7][3].getPiece());
            assertEquals(Chess.B_KING, board[7][4].getPiece());
            assertEquals(Chess.B_BISHOP, board[7][5].getPiece());
            assertEquals(Chess.B_KNIGHT, board[7][6].getPiece());
            assertEquals(Chess.B_ROOK, board[7][7].getPiece());
        } catch (ChessGameException e) {
            fail("ChessGameException thrown when none expected");
        }
    }

    @Test
    void testUndo() {
        try {
            Square[][] board = game.getNewBoard();
            game.move(new Pos(1, 3), new Pos(3, 3)); // d4

            game.undo();
            assertTrue(boardEquals(game.getBoard(), board));
            assertEquals(0, game.getTurn());
        } catch (InvalidTurnException e) {
            fail("InvalidTurnException thrown when none expected");
        } catch (ChessGameException e) {
            fail("ChessGameException thrown when none expected");
        }
    }

    @Test
    void testMultipleUndo() {
        try {
            Square[][] board = game.getNewBoard();
            game.move(new Pos(1, 3), new Pos(3, 3)); // d4
            game.move(new Pos(6, 3), new Pos(4, 3)); // d5
            game.move(new Pos(0, 6), new Pos(2, 5)); // Nf3

            game.undo();
            board[1][3] = new Square(Chess.EMPTY);
            board[3][3] = new Square(Chess.W_PAWN);
            board[6][3] = new Square(Chess.EMPTY);
            board[4][3] = new Square(Chess.B_PAWN);
            assertTrue(boardEquals(game.getBoard(), board));
            assertEquals(2, game.getTurn());

            game.undo();
            board[6][3] = new Square(Chess.B_PAWN);
            board[4][3] = new Square(Chess.EMPTY);
            assertTrue(boardEquals(game.getBoard(), board));
            assertEquals(1, game.getTurn());

            game.undo();
            board[1][3] = new Square(Chess.W_PAWN);
            board[3][3] = new Square(Chess.EMPTY);
            assertTrue(boardEquals(game.getBoard(), board));
            assertEquals(0, game.getTurn());
        } catch (InvalidTurnException e) {
            fail("InvalidTurnException thrown when none expected");
        } catch (ChessGameException e) {
            fail("ChessGameException thrown when none expected");
        }
    }

    @Test
    void testPlay() {
        try {
            Square[][] board = game.getNewBoard();
            game.move(new Pos(1, 4), new Pos(3, 4)); // e4
            game.move(new Pos(6, 4), new Pos(4, 4)); // e5
            game.move(new Pos(0, 1), new Pos(2, 2)); // Nc3
            game.undo();
            game.undo();

            game.play();
            board[1][4] = new Square(Chess.EMPTY);
            board[3][4] = new Square(Chess.W_PAWN);
            board[6][4] = new Square(Chess.EMPTY);
            board[4][4] = new Square(Chess.B_PAWN);
            assertTrue(boardEquals(game.getBoard(), board));
            assertEquals(2, game.getTurn());
        } catch (InvalidTurnException e) {
            fail("InvalidTurnException thrown when none expected");
        } catch (ChessGameException e) {
            fail("ChessGameException thrown when none expected");
        }
    }

    @Test
    void testMultiplePlay() {
        try {
            Square[][] board = game.getNewBoard();
            game.move(new Pos(1, 4), new Pos(3, 4)); // e4
            game.move(new Pos(6, 4), new Pos(4, 4)); // e5
            game.move(new Pos(0, 1), new Pos(2, 2)); // Nc3
            game.undo();
            game.undo();
            game.undo();

            game.play();
            board[1][4] = new Square(Chess.EMPTY);
            board[3][4] = new Square(Chess.W_PAWN);
            assertTrue(boardEquals(game.getBoard(), board));
            assertEquals(1, game.getTurn());

            game.play();
            board[6][4] = new Square(Chess.EMPTY);
            board[4][4] = new Square(Chess.B_PAWN);
            assertTrue(boardEquals(game.getBoard(), board));
            assertEquals(2, game.getTurn());

            game.play();
            board[0][1] = new Square(Chess.EMPTY);
            board[2][2] = new Square(Chess.W_KNIGHT);
            assertTrue(boardEquals(game.getBoard(), board));
            assertEquals(3, game.getTurn());
        } catch (InvalidTurnException e) {
            fail("InvalidTurnException thrown when none expected");
        } catch (ChessGameException e) {
            fail("ChessGameException thrown when none expected");
        }
    }

    @Test
    void testUndoNoMoves() {
        try {
            game.undo();
        } catch (InvalidTurnException e) {
            // pass
        } catch (ChessGameException e) {
            fail("ChessGameException thrown when InvalidTurnException expected");
        }
    }

    @Test
    void testUndoNoPreviousMoves() {
        try {
            game.undo();
        } catch (InvalidTurnException e) {
            // pass
        } catch (ChessGameException e) {
            fail("ChessGameException thrown when InvalidTurnException expected");
        }
    }

    @Test
    void testPlayNoMoves() {
        try {
            game.move(new Pos(1, 4), new Pos(3, 4));
            game.undo();
        } catch (InvalidTurnException e) {
            fail("InvalidTurnException thrown when none expected");
        } catch (ChessGameException e) {
            fail("ChessGameException thrown when none expected");
        }
        try {
            game.undo();
        } catch (InvalidTurnException e) {
            // pass
        } catch (ChessGameException e) {
            fail("ChessGameException thrown when InvalidTurnException expected");
        }
    }

    @Test
    void testPlayNoNextMoves() {
        try {
            game.move(new Pos(1, 4), new Pos(3, 4));
            game.play();
        } catch (InvalidTurnException e) {
            // pass
        } catch (ChessGameException e) {
            fail("ChessGameException thrown when InvalidTurnException expected");
        }
    }
}