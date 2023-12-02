/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.chessgui;

import com.mycompany.chesscore.Square;
import java.awt.Color;
import javax.swing.JButton;

/**
 *
 * @author Mashaal
 */
public class SquareGUI extends JButton {
    private Square square;
    private Color color;
    
   SquareGUI(Square square,Color color){
       super();
       this.square = square;
       this.color = color;
       this.setBackground(this.color);
   }
   
   public Square getSquare(){
       return this.square;
   }
   
   public Color getColor(){
       return this.color;
   }
}
