/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.chesscore.pieces;

import com.mycompany.chesscore.ChessBoard;
import com.mycompany.chesscore.constants.Color;
import com.mycompany.chesscore.constants.Letter;

public class PieceFactory {

    public static Piece createPiece(String pieceType, Color color, int row, Letter column, ChessBoard board) {
        switch(pieceType.toLowerCase()) {
            case "pawn":
                return new Pawn(color, row, column, board);
            case "rook":
                return new Rook(color, row, column, board);
            case "knight":
                return new Knight(color, row, column, board);
            case "bishop":
                return new Bishop(color, row, column, board);
            case "queen":
                return new Queen(color, row, column, board);
            case "king":
                return new King(color, row, column, board);
            default:
                throw new IllegalArgumentException("Invalid piece type: " + pieceType);
        }
    }
}

