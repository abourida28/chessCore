/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.chesscore;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 *
 * @author omara
 */
public class ChessCore {

    public static void main(String[] args) {
       chessGame game = new chessGame();

        try {
            // Read moves from ChessGame.txt
            Scanner scanner = new Scanner(new File("ChessGame.txt"));
            while (scanner.hasNextLine()) {
                String moveString = scanner.nextLine();
                String[] moveTokens = moveString.split(",");
                if (moveTokens.length == 2) {
                    game.move(moveTokens[0].trim(), moveTokens[1].trim(),"");
                    // Parse move coordinates
                    // Make the move
                }
                 else if(moveTokens.length == 3){
                    game.move(moveTokens[0],moveTokens[1],moveTokens[2]);
                 }
                else {
                    System.out.println("Invalid move format: " + moveString);
                }
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            System.out.println("File not found: ChessGame.txt");
        }
    }
}
