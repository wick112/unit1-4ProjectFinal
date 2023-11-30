import java.util.Random;
import java.awt.*;

public class Fruit {
    private int xCoord, yCoord, unitSize;
    private int screenWidth, screenHeight;
    private Random random;

    public Fruit(int screenWidth, int screenHeight, int unitSize) {
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;
        this.unitSize = unitSize;
        this.random = new Random();
        spawnFruit();
    }

    public void spawnFruit() {
        xCoord = random.nextInt((int) (screenWidth / unitSize)) * unitSize;
        yCoord = random.nextInt((int) (screenHeight / unitSize)) * unitSize;
    }

    public int getXCoord() {
        return xCoord;
    }

    public int getYCoord() {
        return yCoord;
    }

    public void draw(Graphics g) {
        g.setColor(Color.red);
        g.fillOval(getXCoord(), getYCoord(), unitSize, unitSize);
    }

    public boolean checkFruitCollision(int[] snakeX, int[] snakeY, int bodyParts) {
        for (int i = bodyParts; i > 0; i--) {
            if ((snakeX[0] == getXCoord()) && (snakeY[0] == getYCoord())) {
                spawnFruit();
                return true;
            }
        }
        return false;
    }
}
