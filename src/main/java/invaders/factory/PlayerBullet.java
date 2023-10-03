package invaders.factory;
import invaders.physics.Moveable;
import invaders.physics.Vector2D;
import invaders.rendering.Renderable;
import javafx.scene.image.Image;

import java.io.File;
//ConcreteProduct
/**
 * TODO: CREATE POSITION FOR BULLET
 * TODO: add the Renderable, Moveable interface into Bullet
 **/
public class PlayerBullet implements Bullet {
    private Image image;
    private int lives;
    private Vector2D position;
    private final double width = 10;
    private final double height = 10;

    public PlayerBullet(Vector2D position){
        this.position = position;
        image = new Image(new File("src/main/resources/player.png").toURI().toString(), width, height, true, true);
        lives=1;
    }

    @Override
    public void up() {
        position.setY(position.getY() - 1);
    }

    @Override
    public void down() {
        return;
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
    public void set_behaviour() {
        //image = new Image(new File("src/main/resources/enemy.png").toURI().toString(), width, height, true, true);
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
        return Layer.EFFECT;
    }

    @Override
    public void start() {
        return;
    }

    @Override
    public void update() {
        up();
        if (getPosition().getY()==0){
            takeDamage(-1);
        }
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
