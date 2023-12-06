/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.chesscore.pieces;
import com.mycompany.chesscore.ChessBoard;
import com.mycompany.chesscore.ChessGameException;
import com.mycompany.chesscore.Square;
import com.mycompany.chesscore.constants.Letter;
import com.mycompany.chesscore.constants.Color;
/**
 *
 * @author omara
 */
public abstract class Piece{
    protected int row;

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone(); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/OverriddenMethodBody
    }
    
    
    
    public void setRow(int row) {
        this.row = row;
    }

    public void setColumn(Letter column) {
        this.column = column;
    }
    protected Letter column;

    public Color getColor() {
        return color;
    }

    public ChessBoard getBoard() {
        return board;
    }
    
    private Color color;
    
    private ChessBoard board;
    public Piece(Color color, int row, Letter column, ChessBoard board)
    {
        this.color = color;
        this.column = column;
        this.row = row;
        this.board = board;
    }
    
    public boolean isValidMove(Square target) throws ChessGameException
    {
        if (target.getPiece() != null) {
            if (target.getPiece().getColor() == getColor()) {
                return false;
            }
        }
        return true;
    }
    
    public void move(Square target) throws ChessGameException
    {
        this.column = target.getColumn();
        this.row = target.getRow();
    }
    
    public Square getSquare()
    {
        Square square = board.board[row - 1][column.ordinal()];
        return square;
    }
    
}
