/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.chesscore;

import com.mycompany.chesscore.Square;
import com.mycompany.chesscore.constants.Letter;
import com.mycompany.chesscore.pieces.Bishop;
import com.mycompany.chesscore.pieces.King;
import com.mycompany.chesscore.pieces.Knight;
import com.mycompany.chesscore.pieces.Pawn;
import com.mycompany.chesscore.pieces.Piece;
import com.mycompany.chesscore.pieces.Queen;
import com.mycompany.chesscore.pieces.Rook;
import java.util.ArrayList;

/**
 *
 * @author omara
 */
public class ChessBoard {
    public Square[][] board = new Square[8][8];
    ArrayList<Pawn> whitePawns;
    ArrayList<Pawn> blackPawns;
    ArrayList<Piece> whitePieces;
    ArrayList<Piece> blackPieces;
    public ChessBoard()
    {
        Piece piece;
        whitePawns = new ArrayList<Pawn>();
        blackPawns = new ArrayList<Pawn>();
        whitePieces = new ArrayList<Piece>();
        blackPieces = new ArrayList<Piece>();
        for (int number = 1; number <= 8; number++)
        {
            for (Letter letter : Letter.values())
            {
                board[number - 1][letter.ordinal()] = new Square(number, letter);
                
                if (number == 2)
                {
                    piece = new Pawn(constants.Color.WHITE, number, letter, this);
                    board[number - 1][letter.ordinal()].setPiece(piece);
                    whitePawns.add((Pawn) piece);
                    whitePieces.add(piece);
                }
                else if (number == 7)
                {
                    piece = new Pawn(constants.Color.BLACK, number, letter, this);
                    board[number - 1][letter.ordinal()].setPiece(piece);
                    blackPawns.add((Pawn) piece);
                    blackPieces.add(piece);
                }
                else if (number == 1 && (letter == Letter.A || letter == Letter.H))
                {
                    piece = new Rook(constants.Color.WHITE, number, letter, this);
                    board[number - 1][letter.ordinal()].setPiece(piece);
                    whitePieces.add(piece);
                }
                else if (number == 8 && (letter == Letter.A || letter == Letter.H))
                {
                    piece = new Rook(constants.Color.BLACK, number, letter, this);
                    board[number - 1][letter.ordinal()].setPiece(piece);
                    blackPieces.add(piece);
                }
                
                else if (number == 1 && (letter == Letter.B || letter == Letter.G))
                {
                    piece = new Knight(constants.Color.WHITE, number, letter, this);
                    board[number - 1][letter.ordinal()].setPiece(piece);
                    whitePieces.add(piece);
                }
                
                else if (number == 8 && (letter == Letter.B || letter == Letter.G))
                {
                    piece = new Knight(constants.Color.BLACK, number, letter, this);
                    board[number - 1][letter.ordinal()].setPiece(piece);
                    blackPieces.add(piece);
                }
                
                else if (number == 1 && (letter == Letter.C || letter == Letter.F))
                {
                    piece = new Bishop(constants.Color.WHITE, number, letter, this);
                    board[number - 1][letter.ordinal()].setPiece(piece);
                    whitePieces.add(piece);
                }
                
                else if (number == 8 && (letter == Letter.C || letter == Letter.F))
                {
                    piece = new Bishop(constants.Color.BLACK, number, letter, this);
                    board[number - 1][letter.ordinal()].setPiece(piece);
                    blackPieces.add(piece);
                }
                
                else if (number == 1 && letter == Letter.D)
                {
                    piece = new Queen(constants.Color.WHITE, number, letter, this);
                    board[number - 1][letter.ordinal()].setPiece(piece);
                    whitePieces.add(piece);
                }
                
                else if (number == 8 && letter == Letter.D)
                {
                    piece = new Queen(constants.Color.BLACK, number, letter, this);
                    board[number - 1][letter.ordinal()].setPiece(piece);
                    blackPieces.add(piece);
                }
                
                else if (number == 1 && letter == Letter.E)
                {
                    piece = new King(constants.Color.WHITE, number, letter, this);
                    board[number - 1][letter.ordinal()].setPiece(piece);
                    whitePieces.add(piece);
                }
                
                else if (number == 8 && letter == Letter.E)
                {
                    piece = new King(constants.Color.BLACK, number, letter, this);
                    board[number - 1][letter.ordinal()].setPiece(piece);
                    blackPieces.add(piece);
                }
            }
        }
    }
    protected void move(Square start, Square finish)
    {
        if(start.getPiece().getColor() == constants.Color.WHITE)
        {
            for(Pawn pawn : whitePawns)
            {
                pawn.setMovedFalse();
            }
        }
        if(start.getPiece().getColor() == constants.Color.BLACK)
        {
            for(Pawn pawn : blackPawns)
            {
                pawn.setMovedFalse();
            }
        }
        Piece piece = start.getPiece();
        finish.setPiece(piece);
        start.setPiece(null);
        piece.move(finish);
    }
}
