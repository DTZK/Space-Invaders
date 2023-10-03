package invaders.factory;

import invaders.physics.Vector2D;
import invaders.strategy.EnemyBullet;
import javafx.scene.image.Image;

import java.io.File;

public class AlienBullet implements Bullet{

    private Vector2D position;

    private Image image;
    private final double width = 15;
    private final double height = 15;

    private EnemyBullet bullet;

    private int lives;

    @Override
    public void start() {
        return;
    }

    @Override
    public void update() {
        down();
    }

    public AlienBullet() {
        //image = new Image(new File("src/main/resources/bunker.png").toURI().toString(), width, height, true, true);
        lives=1;
    }

    @Override
    public void up() {
        return;
    }

    @Override
    public void down() {

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

    public void setPosition(Vector2D position) {
        this.position = position;
    }

    public void createBullet() {
        bullet.makeBullet(position);
    }

    public EnemyBullet getBullet(){
        return bullet;
    }

    @Override
    public void takeDamage(double amount) {
        lives--;
    }

    @Override
    public double getHealth() {
        return 0;
    }

    @Override
    public boolean isAlive() {
        if (lives<=0){
            return false;
        }
        return true;
    }


    public void setBullet(EnemyBullet bullet){
        this.bullet = bullet;
    }

    @Override
    public void set_behaviour() {
        createBullet();
    }
}
