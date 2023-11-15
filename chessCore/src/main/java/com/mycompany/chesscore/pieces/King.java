/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.chesscore.pieces;

import com.mycompany.chesscore.ChessBoard;
import com.mycompany.chesscore.Square;
import com.mycompany.chesscore.constants.Color;
import com.mycompany.chesscore.constants.Letter;
import java.lang.Math;
/**
 *
 * @author omara
 */
public class King extends Piece {

    public King(Color color, int row, Letter column, ChessBoard board) {
        super(color, row, column, board);
    }

    @Override
    public boolean isValidMove(Square targetSquare) {
        int distanceH = Math.abs(super.column.ordinal() - targetSquare.getColumn().ordinal());
        int distanceV = Math.abs(super.row - targetSquare.getRow());
        if (distanceH <= 1 && distanceV <= 1)
        {
            Piece atTarget = targetSquare.getPiece();
            if(atTarget == null)
            {
                return true;
            }
            else if (atTarget.getColor() != super.getColor())
            {
                return true;
            }
            else
                return false;
        }
        return false;
    }
    
}
