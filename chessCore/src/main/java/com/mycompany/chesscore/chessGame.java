/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.chesscore;

import com.mycompany.chesscore.constants.Color;
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
public class chessGame {

    private ChessBoard board;
    private Color hasTurn;
    private boolean isEnded;

    public chessGame() {
        board = new ChessBoard();
        hasTurn = Color.WHITE;
        isEnded = false;
    }

    public boolean isValid(Square start, Square target) {
        start = board.board[start.getRow() - 1][start.getColumn().ordinal()];
        target = board.board[target.getRow() - 1][target.getColumn().ordinal()];
        if (start.getPiece() == null) {
            System.out.println("No piece");
            return false;
        }

        if (!start.getPiece().isValidMove(target)) {
//            System.out.println("Piece don't move like that");
            return false;
        }

        boolean putsKingInCheck = false;
        
        Piece atTarget = target.getPiece();
        
        board.delete(atTarget);
        board.testMove(start, target);

        if (isCheck(hasTurn)) {
            putsKingInCheck = true;
        }

        board.testMove(target, start);
        target.setPiece(atTarget);
        if (atTarget != null)
        {
        if(hasTurn == Color.WHITE)
        {
            board.blackPieces.add(atTarget);
            if (atTarget instanceof Pawn)
                board.blackPawns.add((Pawn) atTarget); 
        }
        if(hasTurn == Color.BLACK)
        {
            board.whitePieces.add(atTarget);
            if (atTarget instanceof Pawn)
                board.whitePawns.add((Pawn) atTarget); 
        }
            
        }
        
        if (putsKingInCheck)
            System.out.println("Puts king in check");
        return !putsKingInCheck;
    }

    public ArrayList<Square> getAllValid(Square start) {

        ArrayList<Square> availableMoves = new ArrayList<Square>();
        Square square;
        for (int number = 1; number <= 8; number++) {
            for (constants.Letter letter : constants.Letter.values()) {
                square = new Square(number, letter);
                if (isValid(start, square)) {
                    availableMoves.add(square);
                }
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
//        if (!isCheck(color)) {
//            return false;
//        }

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
        isEnded = true;
        return true;
    }

    private boolean isStaleMate(Color color) {
        ArrayList<Square> availableMoves;
        Square square;
        if (color == Color.WHITE) {
            for (Piece piece : board.whitePieces) {

                for (int number = 1; number <= 8; number++) {
                    for (constants.Letter letter : constants.Letter.values()) {
                        square = new Square(number, letter);
                        if (isValid(piece.getSquare(), square)) {
                            return false;
                        }
                    }
                }
            }
        }
        if (color == Color.BLACK) {
            for (Piece piece : board.blackPieces) {
                for (int number = 1; number <= 8; number++) {
                    for (constants.Letter letter : constants.Letter.values()) {
                        square = new Square(number, letter);
                        if (isValid(piece.getSquare(), square)) {
                            return false;
                        }
                    }
                }
            }
        }
        isEnded = true;
        return true;
    }

    public void move(String startStr, String targetStr) {
        Square start = Square.parseSquare(startStr);
        Square target = Square.parseSquare(targetStr);
        
        start = board.board[start.getRow() - 1][start.getColumn().ordinal()];
        target = board.board[target.getRow() - 1][target.getColumn().ordinal()];

        if (isEnded) {
            System.out.println("Game already ended");
            return;
        }

        
        
        
        Piece piece = start.getPiece();
        if (isValid(start, target)) {
            if (start.getPiece().getColor() != hasTurn) {
            System.out.println("Not turn");
            return;
        }
            // Check for castling need to be implmented in king with all its conditons
            if (piece instanceof King && Math.abs(start.getColumn().ordinal() - target.getColumn().ordinal()) == 2) {
                System.out.println("Castle");
                //TODO: move rook
            } // Check for en-passant
            else if (piece instanceof Pawn && piece.isValidMove(target) && ((Pawn) piece).isEnPassant()) {
                System.out.println("Enpassant");
                //TODO: remove eaten pawn
                Piece eaten = null;
                if (hasTurn == Color.WHITE)
                {
                    eaten = board.board[target.getRow() - 2][target.getColumn().ordinal()].getPiece();
                }
                if (hasTurn == Color.BLACK)
                {
                    eaten = board.board[target.getRow()][target.getColumn().ordinal()].getPiece();
                }
                board.delete(eaten);
            } // Check for capturing
            else if (target.getPiece() != null) {
                System.out.println("Captured " + target.getPiece().getClass().getSimpleName());
            }

            // Move the piece on the board
            board.move(start, target);

            // Check for checkmate or stalemate
            if (isCheckMate(hasTurn.getOpponentColor())) {
                System.out.println(hasTurn + " Won");
            } else if (isStaleMate(hasTurn.getOpponentColor())) {
                System.out.println("Stalemate");
            }// Check if the move puts the opponent's king in check
            else if (isCheck(hasTurn.getOpponentColor())) {
                System.out.println(hasTurn.getOpponentColor() + " in check");
            }

            // Check for insufficient material
            if (isInsufficientMaterial()) {
                System.out.println("Insufficient Material");
            }

            // Switch turn
            hasTurn = hasTurn.getOpponentColor();

            // Check if the move puts the king in checkmate
//                if (putsKingInCheck && isCheckMate(hasTurn)) {
//                    System.out.println(hasTurn + " Won");
//                }
//            } else {
//                System.out.println("Invalid move");
//            }
        } else {
            System.out.println("Invalid move");
        }
        board.print();
    }

    private boolean isInsufficientMaterial() {
        
        if (!board.blackPawns.isEmpty() || !board.whitePawns.isEmpty()) {
            return false;
        }

        int bishopCount = 0;

        for (Piece piece : board.whitePieces) {
            if (piece instanceof Bishop) {
                bishopCount++;
            }
            if (bishopCount == 2) {
                return false;
            }
            if (piece instanceof Rook) {
                return false;
            }
            if (piece instanceof Queen) {
                return false;
            }
        }

        bishopCount = 0;
        for (Piece piece : board.blackPieces) {
            if (piece instanceof Bishop) {
                bishopCount++;
            }
            if (bishopCount == 2) {
                return false;
            }
            if (piece instanceof Rook) {
                return false;
            }
            if (piece instanceof Queen) {
                return false;
            }
        }

        return true;
    }
}
