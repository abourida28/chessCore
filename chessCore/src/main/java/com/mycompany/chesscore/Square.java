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
}
