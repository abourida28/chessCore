/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.chesscore;

import com.mycompany.chesscore.pieces.Piece;
import com.mycompany.chesscore.constants.Letter;
/**
 *
 * @author omara
 */
public class Square {

    private int row;
    private Letter column;
    private Piece piece;
    

    public Square(int row, Letter column, Piece piece) {
        this.row = row;
        this.column = column;
        this.piece = piece;
    }

    public Square(int row, Letter column) {
        this.row = row;
        this.column = column;
        this.piece = null;
    }
    
        protected static Square parseSquare(String coordinate) {
        if (coordinate.length() == 2) {
            char fileChar = coordinate.charAt(0);
            char rankChar = coordinate.charAt(1);

            // Check if file and rank are valid letters and numbers
            if (fileChar >= 'a' && fileChar <= 'h' && rankChar >= '1' && rankChar <= '8') {
                constants.Letter file = constants.Letter.values()[fileChar - 'a'];
                int rank = Character.getNumericValue(rankChar);

                return new Square(rank, file);
            }
        }

        System.out.println("Invalid square coordinate: " + coordinate);
        return null;
    }
        
    public Piece getPiece() {
        return piece;
    }

    public void setPiece(Piece piece) {
        this.piece = piece;
    }

    public int getRow() {
        return row;
    }

    public Letter getColumn() {
        return column;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Square))
            return false;
        Square sq = (Square)obj;
        if(sq.getRow() == this.row && sq.getColumn() == this.column)
            return true;
        return false;
    }

    
    
    
}
