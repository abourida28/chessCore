package com.mycompany.chesscore.pieces;

import com.mycompany.chesscore.ChessBoard;
import com.mycompany.chesscore.ChessGameException;
import com.mycompany.chesscore.Square;
import com.mycompany.chesscore.constants.Color;
import com.mycompany.chesscore.constants.Letter;
import com.mycompany.chesscore.pieces.*;

public class Pawn extends Piece {

    private boolean moved;
    private boolean lastMoveDouble;
    private boolean enPassant = false;

    public void setLastDoubleMovedFalse() {
        this.lastMoveDouble = false;
    }

    public boolean isEnPassant() {
        return enPassant;
    }

    public Pawn(Color color, int row, Letter column, ChessBoard board) {
        super(color, row, column, board);
        this.moved = false;
        this.lastMoveDouble = false;
    }

    public boolean hasMovedFirstDoubleMove() {
        return lastMoveDouble;
    }

    public boolean isPromotable() {
        return (getColor() == Color.WHITE && row == 8) || (getColor() == Color.BLACK && row == 1);
    }
    public boolean isPromotable(Square target) {
        return (getColor() == Color.WHITE && row == 7 && target.getRow() == 8) || (getColor() == Color.BLACK && row == 2 && target.getRow() == 1);
    }

    @Override
    public void move(Square target) {
        moved = true;
        if (Math.abs(target.getRow() - this.row) == 2) {
            lastMoveDouble = true;
        }
        super.move(target);
    }

    @Override
    public boolean isValidMove(Square target) throws ChessGameException {
        super.isValidMove(target);
        int targetRow = target.getRow();
        int targetColumn = target.getColumn().ordinal();
        int currentRow = this.row;
        int currentColumn = this.column.ordinal();

        int rowDifference = targetRow - currentRow;
        int columnDifference = Math.abs(targetColumn - currentColumn);

        if (getColor() == Color.WHITE) {
            // White pawn moves
            if (rowDifference == 1 && columnDifference == 0 && target.getPiece() == null) {
                // Regular one step forward
                return true;
            } else if (!lastMoveDouble && !moved && rowDifference == 2 && columnDifference == 0
                    && target.getPiece() == null && getBoard().board[currentRow + 1][currentColumn].getPiece() == null) {
                // First two steps forward
                return true;
            } else if (rowDifference == 1 && columnDifference == 1 && target.getPiece() != null
                    && target.getPiece().getColor() == Color.BLACK) {
                // Capture diagonally
                return true;
            } else if (rowDifference == 1 && columnDifference == 1 && target.getPiece() == null) {
                // En passant capture (check both sides)
                if (targetColumn < currentColumn) {
                    if (currentColumn > 1) {
                        if (getBoard().board[currentRow - 1][currentColumn - 1].getPiece() != null
                                && getBoard().board[currentRow - 1][currentColumn - 1].getPiece() instanceof Pawn
                                && getBoard().board[currentRow - 1][currentColumn - 1].getPiece().getColor() != Color.WHITE
                                && ((Pawn) getBoard().board[currentRow - 1][currentColumn - 1].getPiece()).hasMovedFirstDoubleMove()) {
                            enPassant = true;
                            return true;
                        }
                    }
                } else {
                    if (currentColumn < 8) {
                        if (getBoard().board[currentRow - 1][currentColumn + 1].getPiece() != null
                                && getBoard().board[currentRow - 1][currentColumn + 1].getPiece() instanceof Pawn
                                && getBoard().board[currentRow - 1][currentColumn + 1].getPiece().getColor() != Color.WHITE
                                && ((Pawn) getBoard().board[currentRow - 1][currentColumn + 1].getPiece()).hasMovedFirstDoubleMove()) {
                            enPassant = true;
                            return true;
                        }
                    }
                }
            }
        } else {
            // Black pawn moves
            if (rowDifference == -1 && columnDifference == 0 && target.getPiece() == null) {
                // Regular one step forward
                return true;
            } else if (!lastMoveDouble && !moved && rowDifference == -2 && columnDifference == 0
                    && target.getPiece() == null && getBoard().board[currentRow - 2][currentColumn].getPiece() == null) {
                // First two steps forward
                return true;
            } else if (rowDifference == -1 && columnDifference == 1 && target.getPiece() != null
                    && target.getPiece().getColor() == Color.WHITE) {
                // Capture diagonally
                return true;
            } else if (rowDifference == -1 && columnDifference == 1 && target.getPiece() == null) {
                // En passant capture (check both sides)
                if (targetColumn < currentColumn) {
                    if (currentColumn > 1) {
                        if (getBoard().board[currentRow - 1][currentColumn - 1].getPiece() != null
                                && getBoard().board[currentRow - 1][currentColumn - 1].getPiece() instanceof Pawn
                                && getBoard().board[currentRow - 1][currentColumn - 1].getPiece().getColor() != Color.BLACK
                                && ((Pawn) getBoard().board[currentRow - 1][currentColumn - 1].getPiece()).hasMovedFirstDoubleMove()) {
                            enPassant = true;
                            //System.out.println("EN PASSANT");
                            return true;
                        }
                    }
                } else {
                    if (currentColumn < 8) {
                        if (getBoard().board[currentRow - 1][currentColumn + 1].getPiece() != null
                                && getBoard().board[currentRow - 1][currentColumn + 1].getPiece() instanceof Pawn
                                && getBoard().board[currentRow - 1][currentColumn + 1].getPiece().getColor() != Color.BLACK
                                && ((Pawn) getBoard().board[currentRow - 1][currentColumn + 1].getPiece()).hasMovedFirstDoubleMove()) {
                            enPassant = true;
                            //System.out.println("EN PASSANT");
                            return true;
                        }
                    }
                }
            }
        }

        return false;
    }
}
