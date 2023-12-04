import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.Random;

public class GamePanel extends JPanel implements ActionListener{
    int screenWidth = 800;
    int screenHeight = 800;
    int unitSize = 50;
    int gameUnits = (screenWidth * screenHeight)/(unitSize * unitSize);
    int delay;

    //snake parts
    final int[] x = new int[gameUnits];
    final int[] y = new int[gameUnits];
    int bodyParts = 3;
    boolean running = false;

    //fruit parts
    int fruitsEaten;

    char direction = 'R';
    Timer timer;
    Random random;
    boolean playAgain = false;
    boolean gameOver = false;
    //Accesses objects from other 2 classes
    AdditionalGameFeatures obj = new AdditionalGameFeatures();
    private String name = obj.userName();
    private String restOfName = name.substring(name.length() - (name.length() - 1));
    private String firstIn = obj.getFirstInitial(name);
    Fruit fruit = new Fruit(800, 800, 50, 50, 50);

    GamePanel(int delay){
        this.delay = delay;
        random = new Random();
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.black);
        this.setFocusable(true);
        this.addKeyListener(new MyKeyAdapter());
        startGame();
    }
    public void startGame() {
        running = true;
        timer = new Timer(delay,this);
        timer.start();
        fruit.spawnFruit();
    }
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g);
    }
    public void draw(Graphics g) {
        if(running) {
            //Draws grid lines onto the screen
            for (int i = 0; i < screenHeight / unitSize; i++) {
                for (int j = 0; j < screenWidth / unitSize; j++) {
                    g.drawLine(j * unitSize, 0, j * unitSize, screenHeight);
                    g.drawLine(0, i * unitSize, screenWidth, i * unitSize);
                }
            }

            g.setColor(Color.white);
            fruit.draw(g);
            //Sets the head of the snake as green and randomizes the color of each body part every second
            for(int i = 0; i < bodyParts;i++) {
                if(i == 0) {
                    g.setColor(Color.green);
                    g.fillRect(x[i], y[i], unitSize, unitSize);
                }
                else {
                    g.setColor(new Color(((int)(Math.random() * 255) + 1),((int)(Math.random() * 255) + 1),((int)(Math.random() * 255) + 1)));
                    g.fillRect(x[i], y[i], unitSize, unitSize);
                }
            }
            g.setColor(Color.white);
            //Displays user name and score onto the playing screen
            g.setFont( new Font("Ink Free",Font.BOLD, 40));
            FontMetrics metrics = getFontMetrics(g.getFont());
            g.drawString(firstIn + restOfName + "'s Score: " + "\n" + getFruitsEaten(), (screenWidth - metrics.stringWidth(firstIn + restOfName + "'s Score: " + getFruitsEaten()))/2, g.getFont().getSize());

        }
        else if (!running) {
            gameOver = true;
            gameOver(g);
        }

    }

    public void moveSnake(){
        int i = bodyParts;
        while (i > 0) {
            x[i] = x[i - 1];
            y[i] = y[i - 1];
            i--;
        }

        switch(direction) {
            case 'U':
                y[0] = y[0] - unitSize;
                break;
            case 'D':
                y[0] = y[0] + unitSize;
                break;
            case 'L':
                x[0] = x[0] - unitSize;
                break;
            case 'R':
                x[0] = x[0] + unitSize;
                break;
        }
    }
    public void checkFruit() {
        boolean z = fruit.checkFruitCollision(x, y, bodyParts);
        if (z == true){
            bodyParts ++;
            fruitsEaten++;
        }
    }
    public void checkCollisions() {
        //checks if head collides with body
        for(int i = bodyParts; i>0; i--) {
            if((x[0] == x[i])&& (y[0] == y[i])) {
                running = false;
            }
        }
        //check if head touches left border
        if(x[0] < 0) {
            running = false;
        }
        //check if head touches right border
        if(x[0] > screenWidth) {
            running = false;
        }
        //check if head touches top border
        if(y[0] < 0) {
            running = false;
        }
        //check if head touches bottom border
        if(y[0] > screenHeight) {
            running = false;
        }

        if(!running) {
            timer.stop();
        }
    }
    private void gameOver(Graphics g) {
        //Score
        g.setColor(Color.red);
        g.setFont( new Font("Onyx",Font.BOLD, 50));
        FontMetrics metrics1 = getFontMetrics(g.getFont());
        g.drawString(firstIn + restOfName + "'s Score: "+ getFruitsEaten(), (screenWidth - metrics1.stringWidth(firstIn + restOfName + "'s Score: "+ getFruitsEaten()))/2, g.getFont().getSize());
        //Game Over text
        g.setColor(Color.red);
        g.setFont( new Font("Bauhaus 93",Font.BOLD, 75));
        FontMetrics metrics2 = getFontMetrics(g.getFont());
        g.drawString("GAME  OVER", (screenWidth - metrics2.stringWidth("Game Over"))/2, screenHeight /2);
        //Replay text
        g.setColor(Color.red);
        g.setFont( new Font("Bauhaus 93",Font.BOLD, 65));
        FontMetrics metrics3 = getFontMetrics(g.getFont());
        g.drawString("Thanks for Playing", (screenWidth - metrics3.stringWidth("Thanks for Playing"))/2, screenHeight /3);

    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if(running) {
            moveSnake();
            checkFruit();
            checkCollisions();
            if (delay > 100 && getFruitsEaten() > 0 && getFruitsEaten() % 7 == 0){
                delay -= 3;
                timer.stop();
                timer = new Timer(delay,this);
                timer.start();
            }
        }
        repaint();
    }

    public class MyKeyAdapter extends KeyAdapter{
        @Override
        //Checks which WASD Key is pressed; only one key is pressed at a time to limit snake turning to 90 degrees
        public void keyPressed(KeyEvent e) {
            switch(e.getKeyCode()) {
                case KeyEvent.VK_A:
                    if(direction != 'R') {
                        direction = 'L';
                    }
                    break;
                case KeyEvent.VK_D:
                    if(direction != 'L') {
                        direction = 'R';
                    }
                    break;
                case KeyEvent.VK_W:
                    if(direction != 'D') {
                        direction = 'U';
                    }
                    break;
                case KeyEvent.VK_S:
                    if(direction != 'U') {
                        direction = 'D';
                    }
                    break;
            }
        }
    }
    //private helper method
    private int getFruitsEaten(){
        return fruitsEaten;
    }
}