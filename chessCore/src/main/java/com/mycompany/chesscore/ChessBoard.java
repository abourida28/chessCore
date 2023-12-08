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
import com.mycompany.chesscore.constants.Color;
import com.mycompany.chesscore.pieces.PieceFactory;
import java.util.ArrayList;
import java.util.Arrays;

/**
 *
 * @author omara
 */
public class ChessBoard implements ChessObserver{
    
    public Square[][] board =  new Square[8][8];;
    ArrayList<Pawn> whitePawns;
    ArrayList<Pawn> blackPawns;
    ArrayList<Piece> whitePieces;
    ArrayList<Piece> blackPieces;
    private TimeLine timeline;
    
    public ChessBoard() {
        //Piece piece;
        whitePawns = new ArrayList<Pawn>();
        blackPawns = new ArrayList<Pawn>();
        whitePieces = new ArrayList<Piece>();
        blackPieces = new ArrayList<Piece>();
        timeline = new TimeLine();
        for (int number = 1; number <= 8; number++) {
            for (Letter letter : Letter.values()) {
               board[number - 1][letter.ordinal()] = new Square(number, letter);
            }
        }
    }
    

    
    public ChessBoard withPawns() {
        initializePawnsForColor(Color.WHITE, 2);
        initializePawnsForColor(Color.BLACK, 7);
        return this;
    }

    public ChessBoard withRooks() {
        initializeRooksForColor(Color.WHITE, 1);
        initializeRooksForColor(Color.BLACK, 8);
        return this;
    }

    public ChessBoard withKnights() {
        initializeKnightsForColor(Color.WHITE, 1);
        initializeKnightsForColor(Color.BLACK, 8);
        return this;
    }

    public ChessBoard withBishops() {
        initializeBishopsForColor(Color.WHITE, 1);
        initializeBishopsForColor(Color.BLACK, 8);
        return this;
    }

    public ChessBoard withQueens() {
        initializeQueensForColor(Color.WHITE, 1);
        initializeQueensForColor(Color.BLACK, 8);
        return this;
    }

    public ChessBoard withKings() {
        initializeKingsForColor(Color.WHITE, 1);
        initializeKingsForColor(Color.BLACK, 8);
        return this;
    }
    
    public ChessBoard build() {
        return this;
    }
    
    private void initializePawnsForColor(Color color, int row) {
        for (Letter letter : Letter.values()) {
            Piece piece = PieceFactory.createPiece("pawn",color,row,letter,this);
            board[row - 1][letter.ordinal()].setPiece(piece);
            if (color == Color.WHITE) {
                    whitePawns.add((Pawn) piece);
                    whitePieces.add(piece);
            } else {
                blackPawns.add((Pawn) piece);
                blackPieces.add(piece);
            }
        }
    }

    private void initializeRooksForColor(Color color, int row) {
        for (Letter letter : Arrays.asList(Letter.A, Letter.H)) {
            Piece piece = PieceFactory.createPiece("rook",color,row,letter,this);
            board[row - 1][letter.ordinal()].setPiece(piece);
            if (color == Color.WHITE) {
                whitePieces.add(piece);
            } else {
                blackPieces.add(piece);
            }
        }
    }
    
        private void initializeKnightsForColor(Color color, int row) {
        for (Letter letter : Arrays.asList(Letter.B, Letter.G)) {
            Piece piece = PieceFactory.createPiece("knight",color,row,letter,this);
            board[row - 1][letter.ordinal()].setPiece(piece);
            if (color == Color.WHITE) {
                whitePieces.add(piece);
            } else {
                blackPieces.add(piece);
            }
        }
    }

    private void initializeBishopsForColor(Color color, int row) {
        for (Letter letter : Arrays.asList(Letter.C, Letter.F)) {
            Piece piece = PieceFactory.createPiece("bishop",color,row,letter,this);
            board[row - 1][letter.ordinal()].setPiece(piece);
            if (color == Color.WHITE) {
                whitePieces.add(piece);
            } else {
                blackPieces.add(piece);
            }
        }
    }

    private void initializeQueensForColor(Color color, int row) {
        Piece piece = PieceFactory.createPiece("queen",color,row,Letter.D,this);
        board[row - 1][3].setPiece(piece);
        if (color == Color.WHITE) {
            whitePieces.add(piece);
        } else {
            blackPieces.add(piece);
        }
    }

    private void initializeKingsForColor(Color color, int row) {
        Piece piece = PieceFactory.createPiece("king",color,row,Letter.E,this);
        board[row - 1][4].setPiece(piece);
        if (color == Color.WHITE) {
            whitePieces.add(piece);
        } else {
            blackPieces.add(piece);
        }
    }
    
    @Override
    public void update() {
        reDrawBoard();
    }
    
    protected void move(Square start, Square finish) throws ChessGameException {
        saveSnapshot();
        start = board[start.getRow() - 1][start.getColumn().ordinal()];
        finish = board[finish.getRow() - 1][finish.getColumn().ordinal()];
        if (start.getPiece().getColor() == constants.Color.WHITE) {
            for (Pawn pawn : whitePawns) {
                pawn.setLastDoubleMovedFalse();
            }
        }
        if (start.getPiece().getColor() == constants.Color.BLACK) {
            for (Pawn pawn : blackPawns) {
                pawn.setLastDoubleMovedFalse();
            }
        }
        Piece atFinish = finish.getPiece();
        delete(atFinish);
        Piece piece = start.getPiece();
        finish.setPiece(piece);
        start.setPiece(null);
        piece.move(finish);
    }
    
    protected void delete(Piece piece) throws ChessGameException {
        if (piece != null) {
            board[piece.getSquare().getRow() - 1][piece.getSquare().getColumn().ordinal()].setPiece(null);
            if (whitePieces.contains(piece)) {
                whitePieces.remove(piece);
            }
            if (blackPieces.contains(piece)) {
                blackPieces.remove(piece);
            }
            if (whitePawns.contains(piece)) {
                whitePawns.remove(piece);
            }
            if (blackPawns.contains(piece)) {
                blackPawns.remove(piece);
            }
        }
    }
    
    public void saveSnapshot() {
        snapshot snapshot = new snapshot(whitePawns, blackPawns, whitePieces, blackPieces);
        timeline.pushSnapshot(snapshot);
    }
    
    public TimeLine getTimeLine() {
        return timeline;
    }
    
    public void restoreSnapshot() {
        if (timeline.hasSnapshots()) {
            snapshot snapshot = timeline.popSnapshot();
            this.whitePawns = snapshot.getWhitePawns();
            this.blackPawns = snapshot.getBlackPawns();
            this.whitePieces = snapshot.getWhitePieces();
            this.blackPieces = snapshot.getBlackPieces();
            update();
        }
    }
    
    public void reDrawBoard() {
        for (int number = 1; number <= 8; number++) {
            for (Letter letter : Letter.values()) {
                board[number - 1][letter.ordinal()].setPiece(null);
            }            
        }
        for (Piece piece : whitePieces) {
            board[piece.getSquare().getRow() - 1][piece.getSquare().getColumn().ordinal()].setPiece(piece);
        }
        for (Piece piece : blackPieces) {
            board[piece.getSquare().getRow() - 1][piece.getSquare().getColumn().ordinal()].setPiece(piece);
        }
    }
    
    protected void testMove(Square start, Square finish) throws ChessGameException {
        start = board[start.getRow() - 1][start.getColumn().ordinal()];
        finish = board[finish.getRow() - 1][finish.getColumn().ordinal()];
        
        Piece piece = start.getPiece();
        finish.setPiece(piece);
        start.setPiece(null);
        
        piece.setRow(finish.getRow());
        piece.setColumn(finish.getColumn());
    }
    
    protected Square findKing(Color color) throws ChessGameException {
        if (color == Color.WHITE) {
            for (Piece piece : whitePieces) {
                if (piece instanceof King) {
                    return piece.getSquare();
                }
            }
        } else {
            for (Piece piece : blackPieces) {
                if (piece instanceof King) {
                    return piece.getSquare();
                }
            }
        }
        return null;
    }
    
    public boolean isDangerous(Square square, Color color) throws ChessGameException {
        square = board[square.getRow() - 1][square.getColumn().ordinal()];
        if (color == Color.WHITE) {
            for (Piece piece : blackPieces) {
                if (piece.isValidMove(square)) {
                    return true;
                }
            }
        } else if (color == Color.BLACK) {
            for (Piece piece : whitePieces) {
                if (piece.isValidMove(square)) {
                    return true;
                }
            }
        }
        return false;
    }
    
//    protected void print() {
//        System.out.println("");
//        System.out.println("");
//        for (int number = 8; number >= 1; number--) {
//            for (Letter letter : Letter.values()) {
//                if (board[number - 1][letter.ordinal()].getPiece() == null) {
//                    if ((number + letter.ordinal()) % 2 == 1) {
//                        System.out.print("_");
//                    } else {
//                        System.out.print("_");
//                    }
//                } else if (board[number - 1][letter.ordinal()].getPiece() instanceof Pawn) {
//                    System.out.print("P");
//                } else if (board[number - 1][letter.ordinal()].getPiece() instanceof Rook) {
//                    System.out.print("R");
//                } else if (board[number - 1][letter.ordinal()].getPiece() instanceof Knight) {
//                    System.out.print("N");
//                } else if (board[number - 1][letter.ordinal()].getPiece() instanceof Bishop) {
//                    System.out.print("B");
//                } else if (board[number - 1][letter.ordinal()].getPiece() instanceof King) {
//                    System.out.print("K");
//                } else if (board[number - 1][letter.ordinal()].getPiece() instanceof Queen) {
//                    System.out.print("Q");
//                }
//            }
//            System.out.println("");
//        }
//        System.out.println("*********************************");
//    }
}
