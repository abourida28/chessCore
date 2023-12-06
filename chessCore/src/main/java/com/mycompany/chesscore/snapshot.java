/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.chesscore;

import com.mycompany.chesscore.pieces.Bishop;
import com.mycompany.chesscore.pieces.King;
import com.mycompany.chesscore.pieces.Knight;
import com.mycompany.chesscore.pieces.Pawn;
import com.mycompany.chesscore.pieces.Piece;
import com.mycompany.chesscore.pieces.Queen;
import com.mycompany.chesscore.pieces.Rook;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author omara
 */
public class snapshot {
    private ArrayList<Pawn> whitePawns;
    private ArrayList<Pawn> blackPawns;
    private ArrayList<Piece> whitePieces;
    private ArrayList<Piece> blackPieces;
    

    public snapshot(ArrayList<Pawn> whitePawns, ArrayList<Pawn> blackPawns, ArrayList<Piece> whitePieces, ArrayList<Piece> blackPieces) {
        this.whitePawns = deepClonePawns(whitePawns);
        this.blackPawns = deepClonePawns(blackPawns);
        
        this.whitePieces = deepClonePieces(whitePieces);
        this.blackPieces = deepClonePieces(blackPieces);
    }
    
    public void printState() {
        System.out.println("White Pawns: " + whitePawns);
        System.out.println("Black Pawns: " + blackPawns);
        System.out.println("White Pieces: " + whitePieces);
        System.out.println("Black Pieces: " + blackPieces);
    }

    public ArrayList<Pawn> getWhitePawns() {
        ArrayList<Pawn> list = deepClonePawns(whitePawns);
        return list;
    }

    public ArrayList<Pawn> getBlackPawns() {
        ArrayList<Pawn> list = deepClonePawns(blackPawns);
        return  list;
    }

    public ArrayList<Piece> getWhitePieces() {
        ArrayList <Piece> list = deepClonePieces(whitePieces);
        return list;
    }

    public ArrayList<Piece> getBlackPieces() {
        ArrayList <Piece> list = deepClonePieces(blackPieces);
        return list;
    }
    
    private ArrayList<Piece> deepClonePieces(ArrayList<Piece> source)
    {
        ArrayList<Piece> dest = new ArrayList<Piece>();
        for (Piece piece : source)
        {
            if (piece instanceof Bishop)
            {
                try { 
                    dest.add((Bishop)piece.clone());
                } catch (CloneNotSupportedException ex) {
                    Logger.getLogger(snapshot.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if (piece instanceof King)
            {
                try { 
                    dest.add((King)piece.clone());
                } catch (CloneNotSupportedException ex) {
                    Logger.getLogger(snapshot.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if (piece instanceof Knight)
            {
                try { 
                    dest.add((Knight)piece.clone());
                } catch (CloneNotSupportedException ex) {
                    Logger.getLogger(snapshot.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if (piece instanceof Pawn)
            {
                try { 
                    dest.add((Pawn)piece.clone());
                } catch (CloneNotSupportedException ex) {
                    Logger.getLogger(snapshot.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if (piece instanceof Queen)
            {
                try { 
                    dest.add((Queen)piece.clone());
                } catch (CloneNotSupportedException ex) {
                    Logger.getLogger(snapshot.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if (piece instanceof Rook)
            {
                try { 
                    dest.add((Rook)piece.clone());
                } catch (CloneNotSupportedException ex) {
                    Logger.getLogger(snapshot.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        return dest;
    }
    
    private ArrayList<Pawn> deepClonePawns(ArrayList<Pawn> source)
    {
        ArrayList<Pawn> dest = new ArrayList<Pawn>();
        for (Pawn pawn : source)
        {
            try {
                dest.add((Pawn) pawn.clone());
            } catch (CloneNotSupportedException ex) {
                Logger.getLogger(snapshot.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return dest;
    }
    
    
}
