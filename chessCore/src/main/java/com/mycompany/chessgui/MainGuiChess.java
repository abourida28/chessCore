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
import com.mycompany.chesscore.constants.GAME_STATUS;
import com.mycompany.chesscore.pieces.Bishop;
import com.mycompany.chesscore.pieces.King;
import com.mycompany.chesscore.pieces.Knight;
import com.mycompany.chesscore.pieces.Pawn;
import com.mycompany.chesscore.pieces.Piece;
import com.mycompany.chesscore.pieces.Queen;
import com.mycompany.chesscore.pieces.Rook;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

public class MainGuiChess extends JFrame {

    private SquareGUI[][] boardSquares;
    private static boolean isWhiteTurn = true;
    public static String promoteTo = "";
    chessGame game;
    JPanel boardPanel;
    java.util.ArrayList<SquareGUI> highlited = new ArrayList<SquareGUI>();

    public static final String[] pieceImagePaths = {
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
    
    public static boolean getIsWhiteTurn(){
        return isWhiteTurn;
    }

    public MainGuiChess() {
        initializeMenu();
        initializeBoard();
        updateIcons();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setLocationRelativeTo(null);
        setResizable(false);
        setTitle("Chess");
        setVisible(true);
        ImageIcon icon = new ImageIcon("resources/BlackKing.png");
        setIconImage(icon.getImage());
    }
    
    private void initializeMenu() {
        JMenuBar menuBar = new JMenuBar();
        setJMenuBar(menuBar);

        JMenu gameMenu = new JMenu("Game");
        menuBar.add(gameMenu);

        JMenuItem undoMenuItem = new JMenuItem("Undo");
        gameMenu.add(undoMenuItem);

        undoMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(game.getTimeLine().getTimelineSize() == 1){
                    System.out.println("No undo moves available");
                }else{
                game.unDo();
                isWhiteTurn = !isWhiteTurn;
                updateBoard();
                }
                
            }
        });
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
                SquareGUI square = new SquareGUI(game.getSquare(row+1, Letter.values()[col]), color);
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
                } else {
                    boardSquares[row][col].setPieceIcon(null);
                }
            }
        }
    }

    private void updateStatus()
    {
        GAME_STATUS status = game.getGameStatus();
        if (status == GAME_STATUS.GAME_IN_PROGRESS)
            return;
        if (status == GAME_STATUS.BLACK_IN_CHECK || status == GAME_STATUS.WHITE_IN_CHECK)
        {
            highlightKingInCheck();
        }
        else if (status == GAME_STATUS.BLACK_WON)
        {
            highlightKingInCheck();
            JOptionPane.showMessageDialog(null, "Game ended, BLACK WON!");
        }
        else if (status == GAME_STATUS.WHITE_WON)
        {
            highlightKingInCheck();
            JOptionPane.showMessageDialog(null, "Game ended, WHITE WON!");
        }
        else if (status == GAME_STATUS.INSUFFICIENT_MATERIAL)
        {
            JOptionPane.showMessageDialog(null, "Game ended as a draw due to insufficient material!");
        }
        else if (status == GAME_STATUS.STALEMATE)
        {
            JOptionPane.showMessageDialog(null, "Game ended as a draw due STALEMATE!");
        }
    }
    
    private class ChessButtonListener implements MouseListener {
        private final int row;
        private final int col;
        private static Square firstClick = null;

        public ChessButtonListener(int row, int col) {
            this.row = row;
            this.col = col;
        }

        @Override
        public void mouseClicked(MouseEvent action) {
             while (!highlited.isEmpty()) {
                    highlited.get(0).removeHighlight();
                    highlited.remove(0);
                }
            SquareGUI clickedSquare = (SquareGUI) action.getSource();
            if (firstClick == null || (clickedSquare.getSquare().getPiece() != null && clickedSquare.getSquare().getPiece().getColor() == game.getHasTurn())) {
                if (clickedSquare.getSquare().getPiece() != null) {
                    if (clickedSquare.getSquare().getPiece().getColor() == game.getHasTurn()) {
                        firstClick = game.getSquare(clickedSquare.getSquare().getRow(), clickedSquare.getSquare().getColumn());
                        highlightValidMoves(clickedSquare);
                    }
                }
            } else {
                if(firstClick.getPiece() instanceof Pawn && ((Pawn) firstClick.getPiece()).isPromotable(clickedSquare.getSquare())){
                    
                    System.out.println("promotion made");
       try{     
        String message = "Choose a chess piece to promote:";
        String title = "Piece Selection";
        Object[] options = {"Rook", "Bishop", "Queen", "Knight"};
        Object defaultOption = options[0];
        int result = JOptionPane.showOptionDialog(
                null,
                message,
                title,
                JOptionPane.YES_NO_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE,
                null,
                options,
                defaultOption
        );
        switch (result) {
            case 0:
                promoteTo = "R";
                break;
            case 1:
                promoteTo = "B";
                break;
            case 2:
                promoteTo = "Q";
                break;
            case 3:
                promoteTo = "K";
                break;
            default:
                promoteTo = "Q";
                break;
            
        }
          }catch(Exception e){}
                }
                boolean moved = game.move(firstClick, clickedSquare.getSquare(), promoteTo);
                promoteTo = "";
                if (moved) {
                    isWhiteTurn = !isWhiteTurn;
                    updateBoard();
                }
                firstClick = null;
                updateStatus();
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
        java.util.List<Square> validMoves = game.getAllValid(firstSquare.getSquare());

        for (Square move : validMoves) {
            highlited.add(boardSquares[move.getRow() - 1][move.getColumn().ordinal()]);
            boardSquares[move.getRow() - 1][move.getColumn().ordinal()].highlight();
        }
    }

    private void highlightKingInCheck() {
        //highlight the king square when check
        Square kingSquare = game.getKingSquare();
        highlited.add(boardSquares[kingSquare.getRow() - 1][kingSquare.getColumn().ordinal()]);
        boardSquares[kingSquare.getRow() - 1][kingSquare.getColumn().ordinal()].highlightInCheck();
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
        } else {
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
