import javax.swing.JFrame;

public class GameFrame extends JFrame{

    GameFrame(int initialDelay) {
        GamePanel panel = new GamePanel(170);
        this.add(panel);
        this.setTitle("SNAKE GAME");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.pack();
        this.setVisible(true);
        this.setLocationRelativeTo(null);


    }
}