package model;

// Provides generic testing methods for chess games
public abstract class GameTest {

    protected Boolean boardEquals(Square[][] board1, Square[][] board2) {
        for (int r = 0; r < 8; r++) {
            for (int f = 0; f < 8; f++) {
                if (board1[r][f].getPiece() != board2[r][f].getPiece()) {
                    return false;
                }
            }
        }
        return true;
    }

    protected Boolean actionMatches(Action action, char piece, int rank, int file, String type) {
        return (action.getSquare().getPiece() == piece
                && action.getPos().getRank() == rank
                && action.getPos().getFile() == file
                && action.getType().equals(type));
    }
}
