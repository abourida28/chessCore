/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.chesscore.pieces;

import com.mycompany.chesscore.ChessBoard;
import com.mycompany.chesscore.Square;
import com.mycompany.chesscore.constants;

/**
 *
 * @author omara
 */
public class Knight extends Piece {

    public Knight(constants.Color color, int row, constants.Letter column, ChessBoard board) {
        super(color, row, column, board);
    }

    @Override
    public boolean isValidMove(Square target) {
        super.isValidMove(target);
        
        int distanceH = Math.abs(super.column.ordinal() - target.getColumn().ordinal());
        int distanceV = Math.abs(super.row - target.getRow());
        if (distanceV == 2 && distanceH == 1)
            return true;
        if (distanceV == 1 && distanceH == 2)
            return true;
        return false;
    }
    
}
