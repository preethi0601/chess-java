package org.example.piece;

import org.example.GamePanel;

public class King extends Piece {
        public King(int color, int row, int col){
            super(color, row, col);

            if(color == GamePanel.WHITE) {
                image = getImage("/piece/w-king");
            } else {
                image = getImage("/piece/b-king");
            }
        }

        public boolean canMove(int targetCol, int targetRow) {
            if(isWithinBoard(targetCol, targetRow)) {
                if((Math.abs(targetCol-preCol) + Math.abs(targetRow-preRow) == 1) || (Math.abs(targetCol-preCol) * Math.abs(targetRow-preRow) == 1)) {
                    return true;
                }
            }
            return false;
        }

    }

