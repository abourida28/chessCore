package com.mycompany.chesscore.pieces;

import com.mycompany.chesscore.ChessBoard;
import com.mycompany.chesscore.Square;
import com.mycompany.chesscore.constants.Color;
import com.mycompany.chesscore.constants.Letter;

public class Bishop extends Piece {

    public Bishop(Color color, int row, Letter column, ChessBoard board) {
        super(color, row, column, board);
    }

    @Override
    public boolean isValidMove(Square target) {
        int targetRow = target.getRow();
        int targetColumn = target.getColumn().ordinal();
        int currentRow = this.row;
        int currentColumn = this.column.ordinal();

        int rowDifference = Math.abs(targetRow - currentRow);
        int columnDifference = Math.abs(targetColumn - currentColumn);

        // Check if the move is diagonal (equal row and column differences)
        if (rowDifference == columnDifference) {
            //up or down, left or right by the sign being incremented
            int rowDirection = (targetRow > currentRow) ? 1 : -1;
            int colDirection = (targetColumn > currentColumn) ? 1 : -1;
            
            int checkRow = currentRow + rowDirection;
            int checkColumn = currentColumn + colDirection;

            while (checkRow != targetRow && checkColumn != targetColumn) {
                // Check if there are no pieces blocking the path
                if (getBoard().board[checkRow - 1][checkColumn].getPiece() != null) {
                    // There is a piece blocking the path
                    return false;
                }
                checkRow += rowDirection;
                checkColumn += colDirection;
            }
            // Check if the target square is empty or has an opponent's piece
            return target.getPiece() == null || target.getPiece().getColor() != getColor();
        }
        return false;  // Not a valid diagonal move
    }
}
