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
    private constants.GAME_STATUS status;

    public constants.GAME_STATUS getGameStatus() {
        return status;
    }
    
    public Square getKingSquare()
    {
        return board.findKing(hasTurn);
    }
    
    public Color getHasTurn() {
        return hasTurn;
    }
    private Color hasTurn;
    private boolean isEnded;

    public chessGame() {
        board = new ChessBoard();
        hasTurn = Color.WHITE;
        isEnded = false;
        status = constants.GAME_STATUS.GAME_IN_PROGRESS;
    }
    
    public Square getSquare(int row, constants.Letter col)
    {
        return board.board[row][col.ordinal()];
    }
    
    public Piece getPiece(Square square){
        return board.board[square.getRow()][square.getColumn().ordinal()].getPiece();
    }

    public boolean isValid(Square start, Square target, Color color) throws ChessGameException {
        start = board.board[start.getRow() - 1][start.getColumn().ordinal()];
        target = board.board[target.getRow() - 1][target.getColumn().ordinal()];
        if (start.getPiece() == null) {
            return false;
        }

        if (!start.getPiece().isValidMove(target)) {
//                                 System.out.println("Piece don't move like that");
            return false;
        }

        boolean putsKingInCheck = false;

        Piece atTarget = target.getPiece();

        board.delete(atTarget);
        board.testMove(start, target);

        if (isCheck(color)) {
            putsKingInCheck = true;
        }

        board.testMove(target, start);
        target.setPiece(atTarget);
        if (atTarget != null) {
            if (color == Color.WHITE) {
                board.blackPieces.add(atTarget);
                if (atTarget instanceof Pawn) {
                    board.blackPawns.add((Pawn) atTarget);
                }
            }
            if (color == Color.BLACK) {
                board.whitePieces.add(atTarget);
                if (atTarget instanceof Pawn) {
                    board.whitePawns.add((Pawn) atTarget);
                }
            }

        }

        return !putsKingInCheck;
    }

    public ArrayList<Square> getAllValid(Square start) throws ChessGameException {
        
        ArrayList<Square> availableMoves = new ArrayList<Square>();
        
        Square square;
        for (int number = 1; number <= 8; number++) {
            for (constants.Letter letter : constants.Letter.values()) {
//                square = new Square(number, letter);
                square = board.board[number - 1][letter.ordinal()];
                if (isValid(start, square, start.getPiece().getColor())) {
                    availableMoves.add(square);
                }
            }
        }
        return availableMoves;
    }

    private boolean isCheck(Color color) throws ChessGameException {
        Square kingSquare;
        if (color == Color.WHITE) {
            kingSquare = board.findKing(color);
        } else {
            kingSquare = board.findKing(color);
        }
        return board.isDangerous(kingSquare, color);
    }

    private boolean isCheckMate(Color color) throws ChessGameException {
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
        isEnded = true;
        if (color == Color.WHITE)
            status = constants.GAME_STATUS.BLACK_WON;
        else
            status = constants.GAME_STATUS.WHITE_WON;
        return true;
    }

    private boolean isStaleMate(Color color) throws ChessGameException {
        ArrayList<Square> availableMoves;
        Square square;
        if (color == Color.WHITE) {
            for (Piece piece : board.whitePieces) {
                availableMoves = getAllValid(piece.getSquare());
                if (!availableMoves.isEmpty()) {
                    return false;
                }

//                for (int number = 1; number <= 8; number++) {
//                    for (constants.Letter letter : constants.Letter.values()) {
////                        square = new Square(number, letter);
//                        square = board.board[number - 1][letter.ordinal()];
//                        if (isValid(piece.getSquare(), square, color)) {
//                            return false;
//                        }
//                    }
//                }
            }
        }
        if (color == Color.BLACK) {
            for (Piece piece : board.blackPieces) {
                availableMoves = getAllValid(piece.getSquare());
                if (!availableMoves.isEmpty()) {
                    return false;
                }
//                for (int number = 1; number <= 8; number++) {
//                    for (constants.Letter letter : constants.Letter.values()) {
////                        square = new Square(number, letter);
//                        square = board.board[number - 1][letter.ordinal()];
//                        if (isValid(piece.getSquare(), square, color)) {
//                            return false;
//                        }
//                    }
//                }
            }
        }
        isEnded = true;
        status = constants.GAME_STATUS.STALEMATE;
        return true;
    }

    private static void validateMoveCoordinates(String square) throws ChessGameException {
        if (square.length() != 2) {
            throw new ChessGameException("Invalid move format: " + square);
        }

        char letterChar = square.charAt(0);
        int numberInt;

        try {
            numberInt = Character.getNumericValue(square.charAt(1));
        } catch (NumberFormatException e) {
            throw new ChessGameException("Invalid move format: " + square);
        }

        if (!(letterChar >= 'a' && letterChar <= 'h' && numberInt >= 1 && numberInt <= 8)) {
            throw new ChessGameException("Invalid move coordinates: " + square);
        }
    }

    private static void validatePromoteStr(String promoteStr) throws ChessGameException {
        if (promoteStr.equals(""))
            return;
        if (promoteStr.length() != 1) {
            throw new ChessGameException("Invalid promotion string: " + promoteStr);
        }
        char[] availablePromotions = new char[]{'R', 'N', 'B', 'Q'};
        for (char available : availablePromotions) {
            if (promoteStr.charAt(0) == available) {
                return;
            }
        }
        throw new ChessGameException("Invalid promotion string: " + promoteStr);
    }

    public boolean move(Square start, Square target, String promoteStr) throws ChessGameException {
//        validateMoveCoordinates(startStr);
//        validateMoveCoordinates(targetStr);
        validatePromoteStr(promoteStr);

//        Square start = Square.parseSquare(startStr);
//        Square target = Square.parseSquare(targetStr);

        start = board.board[start.getRow() - 1][start.getColumn().ordinal()];
        target = board.board[target.getRow() - 1][target.getColumn().ordinal()];

        if (isEnded) {
            System.out.println("Game already ended");
            return false;
        }

        Piece piece = start.getPiece();
        if (isValid(start, target, hasTurn)) {
            if (start.getPiece().getColor() != hasTurn) {
                System.out.println("Invalid move");
                return false;
            }

            // Check for castling need to be implmented in king with all its conditons
            if (piece instanceof King && Math.abs(start.getColumn().ordinal() - target.getColumn().ordinal()) == 2) {
                System.out.println("Castle");
                Rook rook = null;
                Square newRookPlace = null;
                if (target.getColumn() == constants.Letter.G) {
                    rook = (Rook) board.board[start.getRow() - 1][constants.Letter.H.ordinal()].getPiece();
                    newRookPlace = new Square(start.getRow(), constants.Letter.F);
                }
                if (target.getColumn() == constants.Letter.C) {
                    rook = (Rook) board.board[start.getRow() - 1][constants.Letter.A.ordinal()].getPiece();
                    newRookPlace = new Square(start.getRow(), constants.Letter.D);
                }
                board.move(rook.getSquare(), newRookPlace);
            } // Check for en-passant
            else if (piece instanceof Pawn && piece.isValidMove(target) && ((Pawn) piece).isEnPassant()) {
                System.out.println("Enpassant");
                //TODO: remove eaten pawn
                Piece eaten = null;
                if (hasTurn == Color.WHITE) {
                    eaten = board.board[target.getRow() - 2][target.getColumn().ordinal()].getPiece();
                }
                if (hasTurn == Color.BLACK) {
                    eaten = board.board[target.getRow()][target.getColumn().ordinal()].getPiece();
                }
                board.delete(eaten);
            } // Check for capturing
            else if (target.getPiece() != null) {
                System.out.println("Captured " + target.getPiece().getClass().getSimpleName());
            }

            // Move the piece on the board
            board.move(start, target);
            
            if (!promoteStr.equals("") && target.getPiece() instanceof Pawn && ((Pawn) target.getPiece()).isPromotable()) {
                if ("K".equals(promoteStr)) {
                    Knight knight = new Knight(hasTurn, target.getRow(), target.getColumn(), board);
                    target.setPiece(knight);
                } else if ("B".equals(promoteStr)) {
                    Bishop bishop = new Bishop(hasTurn, target.getRow(), target.getColumn(), board);
                    target.setPiece(bishop);
                } else if ("Q".equals(promoteStr)) {
                    Queen queen = new Queen(hasTurn, target.getRow(), target.getColumn(), board);
                    target.setPiece(queen);
                } else if ("R".equals(promoteStr)) {
                    Rook rook = new Rook(hasTurn, target.getRow(), target.getColumn(), board);
                    target.setPiece(rook);
                }
            }

            // Check for checkmate or stalemate
            if (isCheckMate(hasTurn.getOpponentColor())) {
                System.out.println(hasTurn + " Won");
            } else if (isStaleMate(hasTurn.getOpponentColor())) {
                System.out.println("Stalemate");
            }// Check if the move puts the opponent's king in check
            else if (isCheck(hasTurn.getOpponentColor())) {
                System.out.println(hasTurn.getOpponentColor() + " in check");
                if (hasTurn == Color.WHITE)
                    status = constants.GAME_STATUS.BLACK_IN_CHECK;
                else
                    status = constants.GAME_STATUS.WHITE_IN_CHECK;
            }

            // Check for insufficient material
            else if (isInsufficientMaterial()) {
                System.out.println("Insufficient Material");
            }
            else 
                status = constants.GAME_STATUS.GAME_IN_PROGRESS;
            
            // Switch turn
            hasTurn = hasTurn.getOpponentColor();
            return true;
        } else {
            System.out.println("Invalid move");
            return false;
        }
//        board.print();
    }

    private boolean isInsufficientMaterial() throws ChessGameException {

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
        
        isEnded = true;
        status = constants.GAME_STATUS.INSUFFICIENT_MATERIAL;
        return true;
    }
}
