package invaders.builder;

import invaders.GameObject;
import invaders.State.ChangeGreen;
import invaders.State.ChangeRed;
import invaders.State.ChangeYellow;
import invaders.State.ColourChange;
import invaders.physics.Vector2D;
import invaders.rendering.Renderable;
import javafx.scene.image.Image;

import java.io.File;

public class Bunker implements Renderable, GameObject {
    private Vector2D position;
//    private boolean remove;
    private int lives;

    private double width;
    private double height;
    private Image image;

    private ColourChange chameleon= new ChangeGreen(this);

    public Bunker(Vector2D position,double width, double height, int lives){
        image = new Image(new File("src/main/resources/greenbunker.png").toURI().toString(), getWidth(), getHeight(), false, true);
        this.width = width;
        this.height = height;
        this.lives=lives;
        this.position = position;

//        remove = false;
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
    public int getLives() {
        return lives;
    }

    @Override
    public Layer getLayer() {
        return Layer.FOREGROUND;
    }

    @Override
    public void start() {
        return;
    }

    @Override
    public void update() {

    }

    public void changeImage(Image image){
        this.image = image;
    }

    public void changeState(ColourChange colour){
        chameleon = colour;
    }

    @Override
    public void takeDamage(double amount) {
        lives--;
        chameleon.changeColour();
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
        else{
            return true;
        }
    }
}
