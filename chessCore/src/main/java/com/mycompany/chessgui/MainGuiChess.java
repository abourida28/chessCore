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
import com.mycompany.chesscore.pieces.Bishop;
import com.mycompany.chesscore.pieces.King;
import com.mycompany.chesscore.pieces.Knight;
import com.mycompany.chesscore.pieces.Pawn;
import com.mycompany.chesscore.pieces.Piece;
import com.mycompany.chesscore.pieces.Queen;
import com.mycompany.chesscore.pieces.Rook;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class MainGuiChess extends JFrame {

    private SquareGUI[][] boardSquares;
    private boolean isWhiteTurn = true;
    chessGame game;
    JPanel boardPanel;

    private static final String[] pieceImagePaths = {
        "resources/WhitePawn.png",
        "resources/BlackPawn.png",
        "resources/WhiteRook.png",
        "resources/BlackRook.png",
        "resources/WhiteKnight.png",
        "resources/BlackKnight.png",
        "resources/WhiteBishop.png",
        "resources/BlackBishop.png",
        "resources/WhiteQueen.png",
        "resources/BlackQueen.png",
        "resources/WhiteKing.png",
        "resources/BlackKing.png"
    };

    public MainGuiChess() {
        initializeBoard();
        updateIcons();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setLocationRelativeTo(null);
        setResizable(false);
        setTitle("Chess GUI");
        setVisible(true);
    }

    private void initializeBoard() {
        game = new chessGame();
        boardSquares = new SquareGUI[8][8];
        boardPanel = new JPanel(new GridLayout(8, 8));
        Color color;
        for (int row = 7; row >= 0; row--) {
            for (int col = 0; col < 8; col++) {
                if ((row + col) % 2 == 1) {
                    color = Color.WHITE;
                } else {
                    color = new Color(139, 69, 19);
                }
                SquareGUI square = new SquareGUI(game.getSquare(row, Letter.values()[col]), color);
                square.setPreferredSize(new Dimension(80, 80));
                square.addMouseListener(new ChessButtonListener(row, col));
                boardSquares[row][col] = square;

                boardPanel.add(square);
            }
        }

        add(boardPanel);
    }

    private void updateIcons() {
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                Piece piece = boardSquares[row][col].getSquare().getPiece();
                if (piece instanceof Pawn) {
                    if (piece.getColor() == constants.Color.WHITE) {
                        boardSquares[row][col].setPieceIcon(pieceImagePaths[0]);
                    } else {
                        boardSquares[row][col].setPieceIcon(pieceImagePaths[1]);
                    }
                } else if (piece instanceof Rook) {
                    if (piece.getColor() == constants.Color.WHITE) {
                        boardSquares[row][col].setPieceIcon(pieceImagePaths[2]);
                    } else {
                        boardSquares[row][col].setPieceIcon(pieceImagePaths[3]);
                    }
                } else if (piece instanceof Knight) {
                    if (piece.getColor() == constants.Color.WHITE) {
                        boardSquares[row][col].setPieceIcon(pieceImagePaths[4]);
                    } else {
                        boardSquares[row][col].setPieceIcon(pieceImagePaths[5]);
                    }
                } else if (piece instanceof Bishop) {
                    if (piece.getColor() == constants.Color.WHITE) {
                        boardSquares[row][col].setPieceIcon(pieceImagePaths[6]);
                    } else {
                        boardSquares[row][col].setPieceIcon(pieceImagePaths[7]);
                    }
                } else if (piece instanceof Queen) {
                    if (piece.getColor() == constants.Color.WHITE) {
                        boardSquares[row][col].setPieceIcon(pieceImagePaths[8]);
                    } else {
                        boardSquares[row][col].setPieceIcon(pieceImagePaths[9]);
                    }
                } else if (piece instanceof King) {
                    if (piece.getColor() == constants.Color.WHITE) {
                        boardSquares[row][col].setPieceIcon(pieceImagePaths[10]);
                    } else {
                        boardSquares[row][col].setPieceIcon(pieceImagePaths[11]);
                    }
                }
                else
                    boardSquares[row][col].setPieceIcon(null);
            }
        }
    }

    private class ChessButtonListener implements MouseListener {
        //access board buttons with row and col and save a variable to see if it is first or second move

        private final int row;
        private final int col;
        private static Square firstClick = null;

        public ChessButtonListener(int row, int col) {
            this.row = row;
            this.col = col;
        }

@Override
public void mouseClicked(MouseEvent action) {
    SquareGUI clickedSquare = (SquareGUI) action.getSource();
    if (firstClick == null) {
        if (clickedSquare.getSquare().getPiece() != null) {
            firstClick = clickedSquare.getSquare();
            highlightValidMoves(clickedSquare);
        }
    } else {
        boolean moved = game.move(firstClick, clickedSquare.getSquare(), "");
        if (moved) {
            isWhiteTurn = !isWhiteTurn;
            updateBoard();
        }
        firstClick = null;
        //updateBoard();
    }
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
    //code needs refactoring because repeated code in here in the two for loops
private void highlightValidMoves(SquareGUI firstSquare) {
    Component[] components = boardPanel.getComponents();
        for (Component component : components) {
            boardPanel.remove(component);
        }
    SquareGUI[][] highlightedSquares = new SquareGUI[8][8];
    java.util.List<Square> validMoves = game.getAllValid(firstSquare.getSquare());

    for (Square move : validMoves) {
        int moveRow = move.getRow();
        int moveCol = move.getColumn().ordinal();
        SquareGUI highlightedSquare = new SquareGUI(move, Color.RED); //change color here
        highlightedSquare.setPreferredSize(new Dimension(80, 80));
        highlightedSquares[moveRow][moveCol] = highlightedSquare;
    }
    if(highlightedSquares.length != 0){
        //have issues here because it only highlights one row minus the current row making it one less row on the black side 
        // and one more row in the white side
        // Add the highlighted squares to the board panel
        if (isWhiteTurn) {
            for (int row = 7; row >= 0; row--) {
                for (int col = 0; col < 8; col++) {
                    if (highlightedSquares[row][col] != null) {
                boardPanel.add(highlightedSquares[row][col]);
            } else {
                boardPanel.add(boardSquares[row][col]);
            }
                }
            }
        }
        else {
            for (int row = 0; row < 8; row++) {
                for (int col = 7; col >= 0; col--) {
               if (highlightedSquares[row][col] != null) {
                boardPanel.add(highlightedSquares[row][col]);
            } else {
                boardPanel.add(boardSquares[row][col]);
            }
                }
            }
        }
        updateIcons();
        boardPanel.revalidate();
        boardPanel.repaint();
    }else{
        System.out.println("no moves available");
    }
}


    private void highlightKingInCheck(int row, int col) {
        //highlight the king square when check
    }

    private void updateBoard() {
        Component[] components = boardPanel.getComponents();
        for (Component component : components) {
            boardPanel.remove(component);
        }
        if (isWhiteTurn) {
            for (int row = 7; row >= 0; row--) {
                for (int col = 0; col < 8; col++) {
                    boardPanel.add(boardSquares[row][col]);
                }
            }
        }
        else {
            for (int row = 0; row < 8; row++) {
                for (int col = 7; col >= 0; col--) {
                    boardPanel.add(boardSquares[row][col]);
                }
            }
        }
        updateIcons();
        boardPanel.revalidate();
        boardPanel.repaint();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MainGuiChess());
    }
}
