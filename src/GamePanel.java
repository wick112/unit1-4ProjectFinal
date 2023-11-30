import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.Random;

public class GamePanel extends JPanel implements ActionListener{

    int SCREEN_WIDTH = 800;
    int SCREEN_HEIGHT = 800;
    int UNIT_SIZE = 50;
    int GAME_UNITS = (SCREEN_WIDTH*SCREEN_HEIGHT)/(UNIT_SIZE*UNIT_SIZE);
    int delay = 170;


    //snake parts
    final int x[] = new int[GAME_UNITS];
    final int y[] = new int[GAME_UNITS];
    int bodyParts = 3;
    boolean running = false;

    //fruit parts
    int fruitsEaten, fruitsXCoord, fruitsYCoord;

    char direction = 'R';
    Timer timer;
    Random random;
    boolean playAgain = false;
    boolean gameOver = false;
    AdditionalGameFeatures obj = new AdditionalGameFeatures();
    private String name = obj.userName();
    GamePanel(){
        random = new Random();

        this.setPreferredSize(new Dimension(SCREEN_WIDTH,SCREEN_HEIGHT));
        this.setBackground(Color.black);
        this.setFocusable(true);
        this.addKeyListener(new MyKeyAdapter());
        startGame();
    }
    public void startGame() {
        newFruit();
        running = true;
        timer = new Timer(delay,this);
        timer.start();
    }
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g);
    }
    public void draw(Graphics g) {

        if(running) {
            for (int i = 0; i < SCREEN_HEIGHT / UNIT_SIZE; i++) {
                for (int j = 0; j < SCREEN_WIDTH / UNIT_SIZE; j++) {
                    g.drawLine(j * UNIT_SIZE, 0, j * UNIT_SIZE, SCREEN_HEIGHT);
                    g.drawLine(0, i * UNIT_SIZE, SCREEN_WIDTH, i * UNIT_SIZE);
                }
            }
            g.setColor(Color.red);
            g.fillOval(fruitsXCoord, fruitsYCoord, UNIT_SIZE, UNIT_SIZE);

            for(int i = 0; i < bodyParts;i++) {
                if(i == 0) {
                    g.setColor(Color.green);
                    g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
                }
                else {
                    g.setColor(new Color(((int)(Math.random() * 255) + 1),((int)(Math.random() * 255) + 1),((int)(Math.random() * 255) + 1)));
                    g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
                }
            }
            g.setColor(Color.white);
            g.setFont( new Font("Ink Free",Font.BOLD, 40));
            FontMetrics metrics = getFontMetrics(g.getFont());
            g.drawString(name + "'s Score: "+ fruitsEaten, (SCREEN_WIDTH - metrics.stringWidth(name + "'s Score: " + fruitsEaten))/2, g.getFont().getSize());

        }
        else if (!running) {
            gameOver = true;
            gameOver(g);
        }

    }
    public void newFruit(){
        fruitsXCoord = random.nextInt((int)(SCREEN_WIDTH/UNIT_SIZE))*UNIT_SIZE;
        fruitsYCoord = random.nextInt((int)(SCREEN_HEIGHT/UNIT_SIZE))*UNIT_SIZE;
    }
    public void moveSnake(){
        for(int i = bodyParts;i>0;i--) {
            x[i] = x[i-1];
            y[i] = y[i-1];
        }

        switch(direction) {
            case 'U':
                y[0] = y[0] - UNIT_SIZE;
                break;
            case 'D':
                y[0] = y[0] + UNIT_SIZE;
                break;
            case 'L':
                x[0] = x[0] - UNIT_SIZE;
                break;
            case 'R':
                x[0] = x[0] + UNIT_SIZE;
                break;
        }

    }
    public void checkFruit() {
        if((x[0] == fruitsXCoord) && (y[0] == fruitsYCoord)) {
            bodyParts++;
            fruitsEaten++;
            newFruit();
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
        if(x[0] > SCREEN_WIDTH) {
            running = false;
        }
        //check if head touches top border
        if(y[0] < 0) {
            running = false;
        }
        //check if head touches bottom border
        if(y[0] > SCREEN_HEIGHT) {
            running = false;
        }

        if(!running) {
            timer.stop();
        }
    }
    public void gameOver(Graphics g) {
        //Score

        g.setColor(Color.red);
        g.setFont( new Font("Onyx",Font.BOLD, 50));
        FontMetrics metrics1 = getFontMetrics(g.getFont());
        g.drawString(name + "'s Score: "+ fruitsEaten, (SCREEN_WIDTH - metrics1.stringWidth(name + "'s Score: "+ fruitsEaten))/2, g.getFont().getSize());
        //Game Over text
        g.setColor(Color.red);
        g.setFont( new Font("Bauhaus 93",Font.BOLD, 75));
        FontMetrics metrics2 = getFontMetrics(g.getFont());
        g.drawString("GAME  OVER", (SCREEN_WIDTH - metrics2.stringWidth("Game Over"))/2, SCREEN_HEIGHT/2);
        //Replay text
        g.setColor(Color.red);
        g.setFont( new Font("Bauhaus 93",Font.BOLD, 65));
        FontMetrics metrics3 = getFontMetrics(g.getFont());
        g.drawString("Press Enter to Play Again", (SCREEN_WIDTH - metrics3.stringWidth("Press Enter to Play Again"))/2, SCREEN_HEIGHT/3);

    }
    @Override
    public void actionPerformed(ActionEvent e) {

        if(running) {
            moveSnake();
            checkFruit();
            checkCollisions();
            if (delay > 100 && fruitsEaten > 0 && fruitsEaten % 7 == 0){
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
}


