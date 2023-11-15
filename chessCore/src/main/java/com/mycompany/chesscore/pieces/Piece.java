/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.chesscore.pieces;
import com.mycompany.chesscore.ChessBoard;
import com.mycompany.chesscore.constants.Letter;
import com.mycompany.chesscore.constants.Color;
/**
 *
 * @author omara
 */
public abstract class Piece {
    protected int row;
    protected Letter column;
    
    protected Color color;
    
    protected ChessBoard board;
    public Piece(Color color, int row, Letter column, ChessBoard board)
    {
        this.color = color;
        this.column = column;
        this.row = row;
        this.board = board;
    }
    
    abstract public boolean isValidMove(String target);
    
    public void setRow(int row) {
        this.row = row;
    }

    public void setColumn(Letter column) {
        this.column = column;
    }
    
}
