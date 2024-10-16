package org.example.piece;

import org.example.GamePanel;

public class Rook extends Piece {
    public Rook(int color, int row, int col){
        super(color, row, col);

        if(color == GamePanel.WHITE) {
            image = getImage("/piece/w-rook");
        } else {
            image = getImage("/piece/b-rook");
        }
    }
}
