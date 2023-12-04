import javax.swing.JFrame;

public class GameFrame extends JFrame {
    private GamePanel gamePanel; // New private instance variable

    public GameFrame(int initialDelay) {
        gamePanel = new GamePanel(170);
        this.add(gamePanel);
        this.setTitle("SNAKE GAME");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.pack();
        this.setVisible(true);
        this.setLocationRelativeTo(null);
    }

}