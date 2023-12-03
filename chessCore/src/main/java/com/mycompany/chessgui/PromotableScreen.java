/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.chessgui;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JPanel;
import javax.swing.*;

/**
 *
 * @author Mashaal
 */
public class PromotableScreen extends JDialog{
      public PromotableScreen() {
        setTitle("Chess Promotion");
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setResizable(false);
        setSize(600, 150);
        setLocationRelativeTo(null);
        initScreen();
    }
      
     private void initScreen() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(1, 4));
        if(!MainGuiChess.getIsWhiteTurn()){
            addButton(panel, "R", MainGuiChess.pieceImagePaths[2]);
            addButton(panel, "K", MainGuiChess.pieceImagePaths[4]);
            addButton(panel, "B", MainGuiChess.pieceImagePaths[6]);
            addButton(panel, "Q", MainGuiChess.pieceImagePaths[8]);
        }else{
            addButton(panel, "R", MainGuiChess.pieceImagePaths[3]);
            addButton(panel, "K", MainGuiChess.pieceImagePaths[5]);
            addButton(panel, "B", MainGuiChess.pieceImagePaths[7]);
            addButton(panel, "Q", MainGuiChess.pieceImagePaths[9]);
        }
        add(panel);
    }
     
     private void addButton(JPanel panel, String buttonText, String iconPath) {
        JButton button = new JButton();
        ImageIcon icon = new ImageIcon(iconPath);
        Image before = icon.getImage();
        Image after = before.getScaledInstance(100, 100, java.awt.Image.SCALE_SMOOTH);
        icon = new ImageIcon(after);
        button.setIcon(icon);
        button.setBackground(Color.LIGHT_GRAY);
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent action) {
                MainGuiChess.promoteTo = buttonText;
                //JOptionPane.showMessageDialog(null, "Selected: " + buttonText);
                setVisible(false);
            }
        });
        panel.add(button);
    }
}
