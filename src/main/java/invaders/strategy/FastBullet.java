package invaders.strategy;

import invaders.physics.Vector2D;
import javafx.scene.image.Image;

import java.io.File;
//This is my Concrete Strategy
//Alien will be my Context
public class FastBullet implements EnemyBullet{
    private Vector2D position;

    private Image image;
    private final double width = 15;
    private final double height = 15;

    private int lives;

    @Override
    public void start() {
        return;
    }

    @Override
    public void update() {
        down();
    }

    public FastBullet() {
        image = new Image(new File("src/main/resources/bunker.png").toURI().toString(), width, height, true, true);
        lives=1;
    }

    @Override
    public void up() {
        return;
    }

    @Override
    public void down() {
        position.setY(position.getY() + 2);
    }

    @Override
    public void left() {
        return;
    }

    @Override
    public void right() {
        return;
    }

    @Override
    public Image getImage() {
        return image;
    }

    @Override
    public double getWidth() {
        return width;
    }

    @Override
    public double getHeight() {
        return height;
    }

    @Override
    public Vector2D getPosition() {
        return position;
    }

    @Override
    public Layer getLayer() {
        return Layer.FOREGROUND;
    }

    @Override
    public void makeBullet(Vector2D position) {
        this.position = position;
    }

    @Override
    public void takeDamage(double amount) {
        lives--;
    }

    @Override
    public double getHealth() {
        return lives;
    }

    @Override
    public boolean isAlive() {
        if (lives<=0){
            return false;
        }
        return true;
    }
}
