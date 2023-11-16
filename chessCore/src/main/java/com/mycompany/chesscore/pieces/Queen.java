package com.mycompany.chesscore.pieces;

import com.mycompany.chesscore.ChessBoard;
import com.mycompany.chesscore.Square;
import com.mycompany.chesscore.constants.Color;
import com.mycompany.chesscore.constants.Letter;

public class Queen extends Piece {

    public Queen(Color color, int row, Letter column, ChessBoard board) {
        super(color, row, column, board);
    }

    @Override
    public boolean isValidMove(Square target) {
        super.isValidMove(target);
        int targetRow = target.getRow();
        int targetColumn = target.getColumn().ordinal();
        int currentRow = this.row;
        int currentColumn = this.column.ordinal();

        int rowDifference = Math.abs(targetRow - currentRow);
        int columnDifference = Math.abs(targetColumn - currentColumn);

        // Check if the move is horizontal, vertical, or diagonal
        if ((rowDifference == 0 && columnDifference > 0) ||     // Horizontal move
            (rowDifference > 0 && columnDifference == 0) ||     // Vertical move
            (rowDifference == columnDifference)) {               // Diagonal move
            // Check if there are no pieces blocking the path
            int rowDirection;
            int colDirection;
            
            if(targetRow > currentRow){
                rowDirection = 1;
            }else if(targetRow < currentRow){
                rowDirection = -1;
            }else{
                rowDirection = 0;
            }
            if(targetColumn > currentColumn){
                colDirection = 1;
            }else if(targetColumn < currentColumn){
                colDirection = -1;
            }else{
                colDirection = 0;
            }

            int checkRow = currentRow + rowDirection;
            int checkColumn = currentColumn + colDirection;

            while (checkRow != targetRow || checkColumn != targetColumn) {
                if (getBoard().board[checkRow - 1][checkColumn].getPiece() != null) {
                    // There is a piece blocking the path
                    return false;
                }
                checkRow += rowDirection;
                checkColumn += colDirection;
            }

            // Check if the target square is empty or has a different colored piece
            return target.getPiece() == null || target.getPiece().getColor() != getColor();
        }

        return false; // Not a valid horizontal, vertical, or diagonal move
    }
}
