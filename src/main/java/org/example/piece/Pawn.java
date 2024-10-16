package org.example.piece;

import org.example.GamePanel;

public class Pawn extends Piece {
    public Pawn(int color, int row, int col){
        super(color, row, col);

        if(color == GamePanel.WHITE) {
            image = getImage("/piece/w-pawn");
        } else {
            image = getImage("/piece/b-pawn");
        }
    }

}
