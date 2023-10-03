package invaders.builder;


import invaders.GameObject;
import invaders.factory.AlienBullet;
import invaders.factory.Bullet;
import invaders.factory.BulletCreator;
import invaders.logic.Damagable;
import invaders.physics.Moveable;
import invaders.physics.Vector2D;
import invaders.rendering.Renderable;
import invaders.strategy.EnemyBullet;
import invaders.strategy.FastBullet;
import invaders.strategy.SlowBullet;
import javafx.scene.image.Image;

import java.io.File;

//Alien Product Class
public class Alien implements Moveable, Renderable, GameObject, BulletCreator {
    private double speed;
    private boolean going_left;
    private Vector2D position;
    private int lives;
    private double width;
    private double height;
    private Image image;

    private AlienBullet bulletType;
    public Alien(Vector2D position, double width, double height, int lives) {
        this.image = new Image(new File("src/main/resources/white-enemy.png").toURI().toString(), width, height, true, true);
        this.width = width;
        this.height = height;
        this.lives = lives;
        this.position= position;
        going_left = true;
        this.lives=lives;
        speed = 1;
    }

    public void setSpeed(){
        speed= speed * 1.1;
    }

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
    public Image getImage() {
        return image;
    }

    public double getWidth() {
        return width;
    }

    @Override
    public void up() {
        return;
    }

    //Whenever the alien reaches the edge of the screen, it goes down
    @Override
    public void down() {this.position.setY(this.position.getY() + getHeight()/(1.5));}

    @Override
    public void left() {
        this.position.setX(this.position.getX() - speed);
    }

    @Override
    public void right() {
        this.position.setX(this.position.getX() + speed);
    }

    public void setBullet(EnemyBullet bullet){
        bulletType=  new AlienBullet();
        bulletType.setBullet(bullet);
    }

    public EnemyBullet firesBullet(){
        createBullet();
       return  bulletType.getBullet();
//        bullet.makeBullet(new Vector2D(x,y));
//
//        return bullet;
    }

    @Override
    public void start() {
        return;
    }

    public void isGoingLeft(boolean i){going_left= i;}

    public boolean getGoingLeft() {
        return going_left;
    }

    @Override
    public void update() {
        if (getGoingLeft()){
            left();
        }
        else{
            right();
        }
    }

    public void onLeftScreen(){
        isGoingLeft(false);
    }

    public void onRightScreen(){
        isGoingLeft(true);
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

    @Override
    public Bullet createBullet() {
        Double x = getPosition().getX();
        Double y = getPosition().getY();
        bulletType.setPosition(new Vector2D(x,y));
        bulletType.set_behaviour();
        return  bulletType;
    }
}