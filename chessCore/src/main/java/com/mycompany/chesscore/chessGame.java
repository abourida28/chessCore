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
        isEnded = true;
        return true;
    }

    private boolean isStaleMate(Color color) {
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
    
    private boolean isEnded;

    protected void move(String startStr, String targetStr) {
        Square start = Square.parseSquare(startStr);
        Square target = Square.parseSquare(targetStr);
        
        if (isEnded) {
            System.out.println("Game already ended");
            return;
        }
        
        Piece piece = start.getPiece();
        if (piece != null && piece.getColor() == hasTurn) {
            boolean putsKingInCheck = false;
            if (isValid(start, target)) {
                // Check for castling need to be implmented in king with all its conditons
                if (piece instanceof King && Math.abs(start.getColumn().ordinal() - target.getColumn().ordinal()) == 2) {
                    System.out.println("Castle");
                }
                // Check for en-passant
                else if (piece instanceof Pawn && piece.isValidMove(target) && ((Pawn) piece).isEnPassant()) {
                    System.out.println("Enpassant");
                }
                // Check for capturing
                else if (target.getPiece() != null) {
                    System.out.println("Captured " + target.getPiece().getClass().getSimpleName());
                }

                // Move the piece on the board
                board.move(start, target);

                // Check if the move puts the opponent's king in check
                if (isCheck(hasTurn.getOpponentColor())) {
                    putsKingInCheck = true;
                    System.out.println(hasTurn.getOpponentColor() + " in check");
                }

                // Check for checkmate or stalemate
                if (isCheckMate(hasTurn.getOpponentColor())) {
                    System.out.println(hasTurn + " Won");
                } else if (isStaleMate(hasTurn)) {
                    System.out.println("Stalemate");
                }

                // Check for insufficient material
                if (isInsufficientMaterial()) {
                    System.out.println("Insufficient Material");
                }

                // Switch turn
                hasTurn = hasTurn.getOpponentColor();

                // Check if the move puts the king in checkmate
                if (putsKingInCheck && isCheckMate(hasTurn)) {
                    System.out.println(hasTurn + " Won");
                }
            } else {
                System.out.println("Invalid move");
            }
        } else {
            System.out.println("Invalid move");
        }
    }


private boolean isInsufficientMaterial() {
    // Check if both sides have insufficient material
    if (hasInsufficientMaterial(Color.WHITE) && hasInsufficientMaterial(Color.BLACK)) {
        // No pawns on the board
        return !arePawnsOnBoard();
    }
    return false;
}

private boolean hasInsufficientMaterial(Color color) {
    ArrayList<Piece> pieces = (color == Color.WHITE) ? board.whitePieces : board.blackPieces;

    // Check if there is only a lone king
    if (pieces.size() == 1 && pieces.get(0) instanceof King) {
        return true;
    }

    // Check if there is a king and a bishop
    if (pieces.size() == 2 && containsKing(pieces) && containsBishop(pieces)) {
        return true;
    }

    // Check if there is a king and a knight
    if (pieces.size() == 2 && containsKing(pieces) && containsKnight(pieces)) {
        return true;
    }

    // Check if there is a king and two knights
    if (pieces.size() == 3 && containsKing(pieces) && containsTwoKnights(pieces)) {
        return true;
    }

    return false;
}

private boolean arePawnsOnBoard() {
    for (int i = 0; i < 8; i++) {
        for (int j = 0; j < 8; j++) {
            if (board.board[i][j].getPiece() instanceof Pawn) {
                return true;
            }
        }
    }
    return false;
}

private boolean containsKing(ArrayList<Piece> pieces) {
    for (Piece piece : pieces) {
        if (piece instanceof King) {
            return true;
        }
    }
    return false;
}

private boolean containsBishop(ArrayList<Piece> pieces) {
    for (Piece piece : pieces) {
        if (piece instanceof Bishop) {
            return true;
        }
    }
    return false;
}

private boolean containsKnight(ArrayList<Piece> pieces) {
    for (Piece piece : pieces) {
        if (piece instanceof Knight) {
            return true;
        }
    }
    return false;
}

private boolean containsTwoKnights(ArrayList<Piece> pieces) {
    int knightCount = 0;
    for (Piece piece : pieces) {
        if (piece instanceof Knight) {
            knightCount++;
        }
    }
    return knightCount == 2;
}

}
