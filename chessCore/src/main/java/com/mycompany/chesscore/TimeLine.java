/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.chesscore;
import java.util.Stack;
/**
 *
 * @author Mashaal
 */

public class TimeLine {
    private Stack<snapshot> snapshots = new Stack<>();

    public void pushSnapshot(snapshot snapshot) {
        snapshots.push(snapshot);
    }

    public snapshot popSnapshot() {
        if (!snapshots.isEmpty()) {
            return snapshots.pop();
        }
        return null; 
    }
    
    public boolean hasSnapshots() {
        return !snapshots.isEmpty();
    }

    public int getTimelineSize() {
        return snapshots.size();
    }
}
