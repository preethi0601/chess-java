package org.example;
import org.example.piece.*;

import javax.swing.JPanel;
import java.awt.*;
import java.util.ArrayList;

public class GamePanel extends JPanel implements Runnable {

    public static final int width = 1100;
    public static final int height = 800;
    final int FPS = 60;
    Thread gameThread;
    Board board = new Board();
    Mouse mouse = new Mouse();

    public static ArrayList<Piece>pieces = new ArrayList<>();
    public static ArrayList<Piece> simPieces = new ArrayList<>();
    Piece activePiece;

    public static final int WHITE = 0;
    public static final int BLACK = 1;
    int currentColor = WHITE;

    boolean canMove;
    boolean validSquare;

    public GamePanel(){
        setPreferredSize(new Dimension(width, height));
        setBackground(Color.black);
        addMouseMotionListener(mouse);
        addMouseListener(mouse);

        setPieces();
        copyPieces(pieces, simPieces);
    }

    public void launchGame() {
        gameThread = new Thread(this);
        gameThread.start();
    }


    public void setPieces() {
        // Team WHITE
        pieces.add(new Pawn(WHITE,6,0));
        pieces.add(new Pawn(WHITE, 6, 1));
        pieces.add(new Pawn(WHITE, 6, 2));
        pieces.add(new Pawn(WHITE, 6, 3));
        pieces.add(new Pawn(WHITE, 6, 4));
        pieces.add(new Pawn(WHITE, 6, 5));
        pieces.add(new Pawn(WHITE, 6, 6));
        pieces.add(new Pawn(WHITE, 6, 7));
        pieces.add(new Rook(WHITE, 7, 0));
        pieces.add(new Rook(WHITE, 7, 7));
        pieces.add(new Knight(WHITE, 7,1));
        pieces.add(new Knight(WHITE, 7, 6));
        pieces.add(new Bishop(WHITE, 7, 2));
        pieces.add(new Bishop(WHITE, 7, 5));
        pieces.add(new Queen(WHITE, 7, 3));
        pieces.add(new King(WHITE, 7, 4));

        // Team BLACK
        pieces.add(new Pawn(BLACK,1,0));
        pieces.add(new Pawn(BLACK, 1, 1));
        pieces.add(new Pawn(BLACK, 1, 2));
        pieces.add(new Pawn(BLACK, 1,3));
        pieces.add(new Pawn(BLACK, 1, 4));
        pieces.add(new Pawn(BLACK, 1, 5));
        pieces.add(new Pawn(BLACK, 1, 6));
        pieces.add(new Pawn(BLACK, 1, 7));
        pieces.add(new Rook(BLACK, 0, 0));
        pieces.add(new Rook(BLACK, 0, 7));
        pieces.add(new Knight(BLACK, 0,1));
        pieces.add(new Knight(BLACK, 0, 6));
        pieces.add(new Bishop(BLACK, 0, 5));
        pieces.add(new Bishop(BLACK, 0, 2));
        pieces.add(new Queen(BLACK, 0, 3));
        pieces.add(new King(BLACK, 0, 4));

    }

    private void copyPieces(ArrayList<Piece> source, ArrayList<Piece> target) {
        target.clear();
        target.addAll(source);
    }
    public void run() {
        double drawInterval = (double) 1000000000 /FPS;
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;

        while(gameThread!=null) {
            currentTime = System.nanoTime();
            delta += (currentTime - lastTime) / drawInterval;
            lastTime = currentTime;

            if(delta >= 1) {
                update();
                repaint();
                delta--;
            }
        }
    }

    private void update() {
        if(mouse.pressed) {
            if(activePiece == null) {
                for(Piece piece : simPieces) {
                    if(piece.color == currentColor &&
                    piece.col == mouse.x/Board.SQUARE_SIZE &&
                    piece.row == mouse.y/Board.SQUARE_SIZE) {
                        activePiece = piece;
                    }
                }
            } else {
                simulate();
            }
        }
        if(!mouse.pressed) {
            if(activePiece != null) {
                if(validSquare) {
                    activePiece.updatePosition();
                }else {
                    activePiece.resetPosition();
                    activePiece = null;
                }
            }
        }
    }

    private void simulate() {

        canMove = false;
        validSquare = false;

        activePiece.x = mouse.x - Board.HALF_SQUARE_SIZE;
        activePiece.y = mouse.y - Board.HALF_SQUARE_SIZE;
        activePiece.col = activePiece.getCol(activePiece.x);
        activePiece.row = activePiece.getRow(activePiece.y);

        if(activePiece.canMove(activePiece.col, activePiece.row)) {
            canMove = true;
            validSquare = true;
        }
    }
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g;
        board.draw(g2);

        for(Piece p : simPieces) {
            p.draw(g2);
        }
        if(activePiece != null) {
            if(canMove) {
                g2.setColor(Color.white);
                g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.7f));
                g2.fillRect(activePiece.col * Board.SQUARE_SIZE, activePiece.row * Board.SQUARE_SIZE, Board.SQUARE_SIZE, Board.SQUARE_SIZE);
                g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));


            }
            activePiece.draw(g2);
        }
    }
}
