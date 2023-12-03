/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.chessgui;

import com.mycompany.chesscore.Square;
import java.awt.Color;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.ImageIcon;
import javax.swing.JButton;

/**
 *
 * @author Mashaal
 */
public class SquareGUI extends JButton {

    private Square square;
    private Color color;

    SquareGUI(Square square, Color color) {
        super();
        this.square = square;
        this.color = color;
        this.setBackground(this.color);
        style();
    }

    private void style()
    {
        setBorderPainted(false);
//        setFocusPainted(false);
//        setFocusable(false);
    }
    
    
    public void highlight()
    {
        if (this.color == Color.WHITE)
            this.setBackground(Color.lightGray);
        else
            this.setBackground(Color.GRAY);
    }
    
    public void highlightInCheck()
    {
        this.setBackground(Color.RED);
    }
    
    public void removeHighlight()
    {
        this.setBackground(this.color);
    }
    
    public Square getSquare() {
        return this.square;
    }

    public Color getColor() {
        return this.color;
    }

    public void setPieceIcon(String imagePath) {
        if (imagePath == null) {
            this.setIcon(null);
            return;
        }
        ImageIcon icon = new ImageIcon(imagePath);
        Image before = icon.getImage();
        Image after = before.getScaledInstance(80, 80, java.awt.Image.SCALE_SMOOTH);
        icon = new ImageIcon(after);
        this.setIcon(icon);
    }

}
