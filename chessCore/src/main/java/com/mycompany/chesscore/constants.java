/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.chesscore;

/**
 *
 * @author omara
 */
public class constants {

    public enum Color {
        WHITE,
        BLACK;
     public Color getOpponentColor() {
        return (this == WHITE) ? BLACK : WHITE;
    }
    }
    public enum Letter
    {
        A,
        B,
        C,
        D,
        E,
        F,
        G,
        H
    }
    
    public enum GAME_STATUS{
        BLACK_IN_CHECK,
        WHITE_IN_CHECK,
        DRAW,
        BLACK_WON,
        WHITE_WON,
        GAME_IN_PROGRESS,
        GAME_ENDED
    }
}
