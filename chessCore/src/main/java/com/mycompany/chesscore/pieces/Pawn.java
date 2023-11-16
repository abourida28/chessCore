package com.mycompany.chesscore.pieces;

import com.mycompany.chesscore.ChessBoard;
import com.mycompany.chesscore.Square;
import com.mycompany.chesscore.constants.Color;
import com.mycompany.chesscore.constants.Letter;
import com.mycompany.chesscore.pieces.*;
public class Pawn extends Piece {

    private boolean moved;
    private boolean lastMoveDouble;

    public void setMovedFalse() {
        this.moved = false;
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

    @Override
    public void move(Square target) {
        super.move(target); 
        moved = true;
        if (Math.abs(target.getRow() - this.row) == 2)
            lastMoveDouble = true;
    }

    public boolean promoteTo(char promoteTo) {
        // to be implemented put newPiece with the corresponding Case either in pawn class of gamelogic class
//        Piece newPiece;
        switch (promoteTo) {
            case 'K':
//                getBoard().board[row - 1][column.ordinal()].setPiece(newPiece);
                return true;
            case 'B':
//                getBoard().board[row - 1][column.ordinal()].setPiece(newPiece);
                return true;
            case 'R':
//                getBoard().board[row - 1][column.ordinal()].setPiece(newPiece);
                return true;
            case 'Q':
//                getBoard().board[row - 1][column.ordinal()].setPiece(newPiece);
                return true;
            default:
                return false;
        }
    }

    @Override
    public boolean isValidMove(Square target) {
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
                if(targetColumn < currentColumn){
                     if (currentColumn > 1 && getBoard().board[currentRow][currentColumn - 1].getPiece() != null
                        && getBoard().board[currentRow][currentColumn - 1].getPiece() instanceof Pawn
                        && getBoard().board[currentRow][currentColumn - 1].getPiece().getColor() != Color.WHITE
                        && ((Pawn) getBoard().board[currentRow][currentColumn - 1].getPiece()).hasMovedFirstDoubleMove()) {
                         System.out.println("EN PASSANT");
                         return true;
                }else{
                      if (currentColumn < 8 && getBoard().board[currentRow][currentColumn + 1].getPiece() != null
                        && getBoard().board[currentRow][currentColumn + 1].getPiece() instanceof Pawn
                        && getBoard().board[currentRow][currentColumn + 1].getPiece().getColor() != Color.WHITE
                        && ((Pawn) getBoard().board[currentRow][currentColumn + 1].getPiece()).hasMovedFirstDoubleMove()) {
                          System.out.println("EN PASSANT");
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
                    && target.getPiece() == null && getBoard().board[currentRow - 1][currentColumn].getPiece() == null) {
                // First two steps forward
                return true;
            } else if (rowDifference == -1 && columnDifference == 1 && target.getPiece() != null
                    && target.getPiece().getColor() == Color.WHITE) {
                // Capture diagonally
                return true;
            } else if (rowDifference == -1 && columnDifference == 1 && target.getPiece() == null) {
                // En passant capture (check both sides)
                if(targetColumn < currentColumn){
                     if (currentColumn > 1 && getBoard().board[currentRow][currentColumn - 1].getPiece() != null
                        && getBoard().board[currentRow][currentColumn - 1].getPiece() instanceof Pawn
                        && getBoard().board[currentRow][currentColumn - 1].getPiece().getColor() != Color.BLACK
                        && ((Pawn) getBoard().board[currentRow][currentColumn - 1].getPiece()).hasMovedFirstDoubleMove()) {
                         System.out.println("EN PASSANT");
                    return true;
                }
                }else{
                      if (currentColumn < 8 && getBoard().board[currentRow][currentColumn + 1].getPiece() != null
                        && getBoard().board[currentRow][currentColumn + 1].getPiece() instanceof Pawn
                        && getBoard().board[currentRow][currentColumn + 1].getPiece().getColor() != Color.BLACK
                        && ((Pawn) getBoard().board[currentRow][currentColumn + 1].getPiece()).hasMovedFirstDoubleMove()) {
                          System.out.println("EN PASSANT");
                    return true;
                }
                }
            }
        }
        
        return false;
    }
}
