package org.example;
import javax.swing.JFrame;
//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
    JFrame window = new JFrame("Chess");
    window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    window.setResizable(false);

    GamePanel gp = new GamePanel(); //Add it to the window
    window.add(gp);
    window.pack();

    window.setLocationRelativeTo(null);
    window.setVisible(true);
    gp.launchGame();
    }
}