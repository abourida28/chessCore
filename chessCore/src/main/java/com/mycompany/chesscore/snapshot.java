/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.chesscore;

import com.mycompany.chesscore.pieces.Pawn;
import com.mycompany.chesscore.pieces.Piece;
import java.util.ArrayList;

/**
 *
 * @author omara
 */
public class snapshot {
    private ArrayList<Pawn> whitePawns;
    private ArrayList<Pawn> blackPawns;
    private ArrayList<Piece> whitePieces;
    private ArrayList<Piece> blackPieces;
    
    private deepClone<Pawn> deepClonePawns = new deepClone<Pawn>();
    private deepClone<Piece> deepClonePieces =  new deepClone<Piece>();

    public snapshot(ArrayList<Pawn> whitePawns, ArrayList<Pawn> blackPawns, ArrayList<Piece> whitePieces, ArrayList<Piece> blackPieces) {
        this.whitePawns = deepClonePawns.deepClone(whitePawns);
        this.blackPawns = deepClonePawns.deepClone(blackPawns);
        
        this.whitePieces = deepClonePieces.deepClone(whitePieces);
        this.blackPieces = deepClonePieces.deepClone(blackPieces);
    }

    public ArrayList<Pawn> getWhitePawns() {
        return deepClonePawns.deepClone(whitePawns);
    }

    public ArrayList<Pawn> getBlackPawns() {
        return deepClonePawns.deepClone(blackPawns);
    }

    public ArrayList<Piece> getWhitePieces() {
        return deepClonePieces.deepClone(whitePieces);
    }

    public ArrayList<Piece> getBlackPieces() {
        return deepClonePieces.deepClone(blackPieces);
    }
    
    
    private class deepClone<Type>
    {
        public ArrayList<Type> deepClone (ArrayList<Type> source)
        {
            ArrayList<Type> dest;
            dest = new ArrayList<Type>();
            for (Type t : source)
            {
                dest.add(t);
            }
            return dest;
        }
    }
}
