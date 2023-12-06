/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.chesscore.pieces;

import com.mycompany.chesscore.ChessBoard;
import com.mycompany.chesscore.ChessGameException;
import com.mycompany.chesscore.Square;
import com.mycompany.chesscore.constants.Color;
import com.mycompany.chesscore.constants.Letter;
import java.lang.Math;

/**
 *
 * @author omara
 */
public class King extends Piece {

    private boolean moved;

    public boolean isMoved() {
        return moved;
    }

    public King(Color color, int row, Letter column, ChessBoard board) {
        super(color, row, column, board);
        moved = false;
    }

    @Override
    public void move(Square target) {
        super.move(target);
        moved = true;
    }

    @Override
    public boolean isValidMove(Square target)  throws ChessGameException{
        if (!super.isValidMove(target)) {
            return false;
        }

        int distanceH = Math.abs(super.column.ordinal() - target.getColumn().ordinal());
        int distanceV = Math.abs(super.row - target.getRow());
        if (distanceH <= 1 && distanceV <= 1) {
            return true;
        } 
        //Check if castling
        else if (distanceH == 2 && distanceV == 0 && !moved) {
            Piece rook = null;
            boolean safe = true;
            if (super.getBoard().isDangerous(super.getSquare(), super.getColor())) {
                safe = false;
            }
            if (target.getColumn() == Letter.G) {
                rook = super.getBoard().board[super.row - 1][Letter.H.ordinal()].getPiece();
//                Square square = new Square(super.row, Letter.F)
                  Square square = super.getBoard().board[super.row - 1][Letter.F.ordinal()];

                if (super.getBoard().isDangerous(square, super.getColor())) {
                    safe = false;
                }

            } else if (target.getColumn() == Letter.C) {
                rook = super.getBoard().board[super.row - 1][Letter.A.ordinal()].getPiece();
//                Square square = new Square(super.row, Letter.D);
                Square square = super.getBoard().board[row - 1][Letter.D.ordinal()];
                if (super.getBoard().isDangerous(square, super.getColor())) {
                    safe = false;
                }
                if (super.getBoard().board[super.row - 1][Letter.B.ordinal()].getPiece() != null)
                    safe = false;
            }
            if (rook == null)
                return false;
            else if (!(rook instanceof Rook))
                return false;
            if ((!((Rook)rook).isMoved()) && safe) {
                return true;
            }
        }
        return false;
    }

}
