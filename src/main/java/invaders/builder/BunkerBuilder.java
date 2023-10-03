package invaders.builder;

import invaders.physics.Vector2D;
import invaders.strategy.EnemyBullet;
import javafx.scene.image.Image;

public class BunkerBuilder implements Builder{
    private Vector2D position;
    private Bunker bunker;
    private Image image;
    private int lives;

    private double width;
    private double height;

    @Override
    public void setLives(int i) {
        lives = i;
    }

    @Override
    public void setWidth(double width) {
        this.width = width;
    }

    @Override
    public void setHeight(double height) {
        this.height=height;
    }

    @Override
    public void setPosition(Vector2D position) {
        this.position = position;
    }

    @Override
    public void setBullets(EnemyBullet bullet) {

    }

//    @Override
//    public void changeImage() {
//        ColourChange colour = new BunkerColour(width,height);
//        Bunker bunker = new BunkerColour(width,height);
//        if (lives==2){
//            bunker.changeYellow(image);
//        } else if (lives==1) {
//            bunker.changeRed(image);
//        } else if (lives<=0) {
//            bunker.
//        }
//    }

    public Bunker getBunker(){
        return new Bunker(position, width, height, lives);
    }
}
