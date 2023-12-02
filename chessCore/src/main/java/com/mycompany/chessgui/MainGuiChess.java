/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.chessgui;

/**
 *
 * @author Mashaal
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import com.mycompany.chesscore.*;
import com.mycompany.chesscore.constants.Letter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class MainGuiChess extends JFrame {
    private SquareGUI[][] boardSquares;
    private boolean isWhiteTurn = true;

    private static final String[] pieceImagePaths = {
            "BlackBishop.png",
            "BlackKing.png",
            "BlackKnight.png",
            "BlackPawn.png",
            "BlackQueen.png",
            "BlackRook.png",
            "WhiteBishop.png",
            "WhiteKing.png",
            "WhiteKnight.png",
            "WhitePawn.png",
            "WhiteQueen.png",
            "WhiteRook.png"
    };

    public MainGuiChess() {
        initializeBoard();
        //setPieceIcons();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setLocationRelativeTo(null);
        setTitle("Chess GUI");
        setVisible(true);
    }

    private void initializeBoard() {
        boardSquares = new SquareGUI[8][8];
        JPanel boardPanel = new JPanel(new GridLayout(8, 8));
        Color color;
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
               if ((row + col) % 2 == 0) {
                    color = Color.WHITE;
                } else {
                    color = new Color(139, 69, 19);
                }
                SquareGUI square = new SquareGUI(new Square(row,Letter.values()[col]),color);
                square.setPreferredSize(new Dimension(80, 80));
                square.addMouseListener(new ChessButtonListener(row, col));
                boardSquares[row][col] = square;
                
                boardPanel.add(square);
            }
        }

        add(boardPanel);
    }
    
private void setPieceIcons() {
    for(int row=0;row<8;row++){
       for (int col = 0; col < 8; col++) {
           
       }
    }

}


    private void setPieceIcon(Square square, String imagePath) {
        ImageIcon icon = new ImageIcon(getClass().getClassLoader().getResource(imagePath));
        //boardButtons[square.getRow()][square.getColumn().ordinal()].setIcon(icon);
    }
    
    private class ChessButtonListener implements MouseListener {
            //access board buttons with row and col and save a variable to see if it is first or second move
        private final int row;
        private final int col;
        private int clicks = 0;

        public ChessButtonListener(int row, int col) {
            this.row = row;
            this.col = col;
        }

        @Override
        public void mouseClicked(MouseEvent action) {    
            JButton clickedButton = (JButton) action.getSource();
            if(clickedButton.getIcon() != null){
                System.out.println("I have an icon");
            }
            System.out.println("clickaya rakam " + clicks++);
//            highlightValidMoves(row, col);
//            highlightKingInCheck(row, col);
//            isWhiteTurn = !isWhiteTurn;
//            updateBoard();
            //throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
        }

        @Override
        public void mousePressed(MouseEvent e) {
            //throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
        }

        @Override
        public void mouseReleased(MouseEvent e) {
            //throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
        }

        @Override
        public void mouseEntered(MouseEvent e) {
            //throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
        }

        @Override
        public void mouseExited(MouseEvent e) {
            //throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
        }
    }

    private void highlightValidMoves(int row, int col) {
        //highlight valid moves for the selected piece
    }

    private void highlightKingInCheck(int row, int col) {
        //highlight the king square when check
    }

    private void updateBoard() {
        //update the board based on the current state like flipping board and 
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MainGuiChess());
    }
}

