package invaders.builder;

import invaders.logic.Damagable;

import invaders.physics.Vector2D;
import invaders.strategy.EnemyBullet;
import javafx.scene.image.Image;

import java.io.File;

public class AlienBuilder implements Builder {
    private Alien alien;
    private Image image;
    private Vector2D position;
    private int lives;
    private double width;
    private double height;
    private EnemyBullet bullet;
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
        this.bullet = bullet;
    }

//    @Override
//    public void changeImage() {
//        this.image = new Image(new File("src/main/resources/enemy.png").toURI().toString(), width, height, true, true);
//    }

    public Alien  getAlien(){
        alien =  new Alien(position, width, height, lives);
        alien.setBullet(bullet);
        return alien;
    }
}
