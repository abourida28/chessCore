/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.chesscore;

import com.mycompany.chesscore.constants.Color;
import com.mycompany.chesscore.pieces.Piece;
import java.util.ArrayList;

/**
 *
 * @author omara
 */
public class chessGame {

    private ChessBoard board;
    private Color hasTurn;

    public chessGame() {
        board = new ChessBoard();
        hasTurn = Color.WHITE;
    }

    public boolean isValid(Square start, Square target) {
        if (start.getPiece() == null)
            return false;
        
        if (start.getPiece().getColor() != hasTurn)
            return false;
        
        if (!start.getPiece().isValidMove(target))
            return false;
        
        boolean putsKingInCheck = false;
        
        Piece atTarget = target.getPiece();
        
        board.move(start, target);
        
        if (isCheck(hasTurn))
            putsKingInCheck = true;
        
        board.move(target, start);
        target.setPiece(atTarget);
            
        return putsKingInCheck;
    }

    public ArrayList<Square> getAllValid(Square start) {
        
        ArrayList<Square> availableMoves = new ArrayList<Square>();
        Square square;
        for (int number = 1; number <= 8; number++) {
            for (constants.Letter letter : constants.Letter.values()) {
                square = new Square(number, letter);
                if (isValid(start, square))
                    availableMoves.add(square);
            }
        }
        return availableMoves;
    }

    private boolean isCheck(Color color) {
        Square kingSquare;
        if (color == Color.WHITE) {
            kingSquare = board.findKing(color);
        } else {
            kingSquare = board.findKing(color);
        }
        return board.isSafe(kingSquare, color);
    }

   

    private boolean isCheckMate(Color color) {
        if (!isCheck(color)) {
            return false;
        }

        ArrayList<Square> availableMoves;
        if (color == Color.WHITE) {
            for (Piece piece : board.whitePieces) {
                availableMoves = getAllValid(piece.getSquare());
                if (!availableMoves.isEmpty()) {
                    return false;
                }
            }
        }
        if (color == Color.BLACK) {
            for (Piece piece : board.blackPieces) {
                availableMoves = getAllValid(piece.getSquare());
                if (!availableMoves.isEmpty()) {
                    return false;
                }
            }
        }
        return true;
    }

    private boolean isStalekMate(Color color) {
        ArrayList<Square> availableMoves;
        if (color == Color.WHITE) {
            for (Piece piece : board.whitePieces) {
                availableMoves = getAllValid(piece.getSquare());
                if (!availableMoves.isEmpty()) {
                    return false;
                }
            }
        }
        if (color == Color.BLACK) {
            for (Piece piece : board.blackPieces) {
                availableMoves = getAllValid(piece.getSquare());
                if (!availableMoves.isEmpty()) {
                    return false;
                }
            }
        }
        return true;
    }

    private void move(Square start, Square target)
    {
        
    }
}
