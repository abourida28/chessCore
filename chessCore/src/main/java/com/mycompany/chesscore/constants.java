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
}
