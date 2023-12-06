/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.chesscore.pieces;

import com.mycompany.chesscore.ChessBoard;
import com.mycompany.chesscore.ChessGameException;
import com.mycompany.chesscore.Square;
import com.mycompany.chesscore.constants;
import com.mycompany.chesscore.constants.Letter;

/**
 *
 * @author omara
 */
public class Rook extends Piece {
    
    private boolean moved;
    public Rook(constants.Color color, int row, constants.Letter column, ChessBoard board) {
        super(color, row, column, board);
        moved = false;
    }
    
    private Rook(constants.Color color, int row, constants.Letter column, ChessBoard board, boolean moved) {
        super(color, row, column, board);
        this.moved = moved;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        Rook rook = new Rook(this.getColor(), this.row, this.column, this.getBoard(), this.moved);
        return rook;
    }
    
    public boolean isMoved() {
        return moved;
    }

    @Override
    public void move(Square target) {
        super.move(target); 
        moved = true;
    }

    @Override
    public boolean isValidMove(Square target)  throws ChessGameException{
        //First check that target is empty or enemy
        if (!super.isValidMove(target)) {
            return false;
        }
        
        
        int targetV = target.getRow();
        Letter targetH = target.getColumn();
        Square[][] board = super.getBoard().board;
        
        //check that rook is moving on a row or a column
        if (targetH == super.column) {
            Square s;
            //check rook moving up
            if (targetV > super.row) {
                for (int i = super.row; i < targetV - 1; i++) {
                    s = board[i][targetH.ordinal()];
                    if (s.getPiece() != null) {
                        return false;
                    }
                }
            } //check if rook is moving down
            else if (targetV < super.row) {
                for (int i = targetV; i < super.row - 1; i++) {
                    s = board[i][targetH.ordinal()];
                    if (s.getPiece() != null) {
                        return false;
                    }
                }
            }
                return true;
        } //check if rook is moving horizontally
        else if (targetV == super.row) {
            Square s;
            //check if moving to the right
            if (targetH.ordinal() > super.column.ordinal()) {
                for (Letter letter : Letter.values()) {
                    if (letter.ordinal() > super.column.ordinal() && letter.ordinal() < targetH.ordinal()) {
                        s = board[targetV - 1][letter.ordinal()];
                        if (s.getPiece() != null) {
                            return false;
                        }
                    }
                }
            }
            if (targetH.ordinal() < super.column.ordinal()) {
                for (Letter letter : Letter.values()) {
                    if (letter.ordinal() > targetH.ordinal() && letter.ordinal() < super.column.ordinal()) {
                        s = board[targetV - 1][letter.ordinal()];
                        if (s.getPiece() != null) {
                            return false;
                        }
                    }
                }
            }
                return true;
        }
        return false;

    }
}