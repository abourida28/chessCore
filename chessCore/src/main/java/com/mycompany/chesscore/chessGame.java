/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.chesscore;

import com.mycompany.chesscore.constants.Color;
import com.mycompany.chesscore.pieces.Bishop;
import com.mycompany.chesscore.ChessObserver;
import com.mycompany.chesscore.pieces.King;
import com.mycompany.chesscore.pieces.Knight;
import com.mycompany.chesscore.pieces.Pawn;
import com.mycompany.chesscore.pieces.Piece;
import com.mycompany.chesscore.pieces.PieceFactory;
import com.mycompany.chesscore.pieces.Queen;
import com.mycompany.chesscore.pieces.Rook;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author omara
 */
public class chessGame{

    private ChessBoard board;
    private constants.GAME_STATUS status;
    
    private List<ChessObserver> observers = new ArrayList<>();
    private Color hasTurn;
    private boolean isEnded;


    public constants.GAME_STATUS getGameStatus() {
        return status;
    }

    public Square getKingSquare() {
        return board.findKing(hasTurn);
    }

    public Color getHasTurn() {
        return hasTurn;
    }

    public chessGame() {
        board = new ChessBoard()
                .withPawns()
                .withBishops()
                .withKings()
                .withKnights()
                .withQueens()
                .withRooks()
                .build();
        hasTurn = Color.WHITE;
        isEnded = false;
        status = constants.GAME_STATUS.GAME_IN_PROGRESS;
        addObserver(board);
//        saveSnap();
    }

    public TimeLine getTimeLine() {
        return board.getTimeLine();
    }

    public Square getSquare(int row, constants.Letter col) {
        return board.board[row - 1][col.ordinal()];
    }
    
    private void addObserver(ChessObserver observer) {
        observers.add(observer);
    }
    

    private void removeObserver(ChessObserver observer) {
        observers.remove(observer);
    }

    private void notifyObservers() {
        for (ChessObserver observer : observers) {
            observer.update();
        }
    }

    
    
    private boolean isValid(Square start, Square target, Color color) throws ChessGameException {
        start = board.board[start.getRow() - 1][start.getColumn().ordinal()];
        target = board.board[target.getRow() - 1][target.getColumn().ordinal()];
        if (start.getPiece() == null) {
            return false;
        }

        if (!start.getPiece().isValidMove(target)) {
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
        if (color == Color.WHITE) {
            status = constants.GAME_STATUS.BLACK_WON;
        } else {
            status = constants.GAME_STATUS.WHITE_WON;
        }
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

    public boolean move(Square start, Square target, String promoteStr) throws ChessGameException {
        
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
            
            board.saveSnapshot();
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
            }
              // Check for en-passant
            else if (piece instanceof Pawn && piece.isValidMove(target) && ((Pawn) piece).isEnPassant()) {
                System.out.println("Enpassant");
                Piece eaten = null;
                if (hasTurn == Color.WHITE) {
                    eaten = board.board[target.getRow() - 2][target.getColumn().ordinal()].getPiece();
                }
                if (hasTurn == Color.BLACK) {
                    eaten = board.board[target.getRow()][target.getColumn().ordinal()].getPiece();
                }
                board.delete(eaten);
            }
            // Check for capturing
            else if (target.getPiece() != null) {
                System.out.println("Captured " + target.getPiece().getClass().getSimpleName());
            }

            // Move the piece on the board
            board.move(start, target);
            
           
            
            
            //Check for promotion
            if (target.getPiece() instanceof Pawn && ((Pawn) target.getPiece()).isPromotable()) {
                Piece pawn = target.getPiece();
                board.delete(pawn);
                Piece promotedPiece = PieceFactory.createPiece(promoteStr, hasTurn, target.getRow(), target.getColumn(), board);
                target.setPiece(promotedPiece);
                if (hasTurn == Color.WHITE)
                        board.whitePieces.add(promotedPiece);
                     else 
                        board.blackPieces.add(promotedPiece);
            }
              setGameStatus();
              
            // Switch turn
            hasTurn = hasTurn.getOpponentColor();
            return true;
        } else {
            System.out.println("Invalid move");
            return false;
        }
//        board.print();
    }

    public void unDo() {
        board.restoreSnapshot();
        setGameStatus();
        hasTurn = hasTurn.getOpponentColor();
    }

    private void setGameStatus()
    {
        if (isCheckMate(hasTurn.getOpponentColor())) {
                System.out.println(hasTurn + " Won");
            } else if (isStaleMate(hasTurn.getOpponentColor())) {
                System.out.println("Stalemate");
            }// Check if the move puts the opponent's king in check
            else if (isCheck(hasTurn.getOpponentColor())) {
                System.out.println(hasTurn.getOpponentColor() + " in check");
                if (hasTurn == Color.WHITE) {
                    status = constants.GAME_STATUS.BLACK_IN_CHECK;
                } else {
                    status = constants.GAME_STATUS.WHITE_IN_CHECK;
                }
            } // Check for insufficient material
            else if (isInsufficientMaterial()) {
                System.out.println("Insufficient Material");
            } else {
                status = constants.GAME_STATUS.GAME_IN_PROGRESS;
            }
        notifyObservers();
    }
    
    public void saveSnap() {
        board.saveSnapshot();
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
