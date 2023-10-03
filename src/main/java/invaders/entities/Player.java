package invaders.entities;

import invaders.GameObject;
import invaders.factory.Bullet;
import invaders.factory.BulletCreator;
import invaders.factory.PlayerBullet;
import invaders.logic.Damagable;
import invaders.physics.Collider;
import invaders.physics.Moveable;
import invaders.physics.Vector2D;
import invaders.rendering.Animator;
import invaders.rendering.Renderable;

import javafx.scene.image.Image;

import java.io.File;

public class Player implements Moveable, Damagable, Renderable, BulletCreator, Collider {
    private final Vector2D position;
    private final Animator anim = null;
    private double health = 3;
    private boolean shoot;
    private final double width = 25;
    private final double height = 30;

    private double speed;
    private final Image image;

    public Player(Vector2D position){
        this.image = new Image(new File("src/main/resources/player.png").toURI().toString(), width, height, true, true);
        this.position = position;
        shoot = false;
    }

    @Override
    public void takeDamage(double amount) {
        health--;
    }

    @Override
    public double getHealth() {
        return this.health;
    }

    @Override
    public boolean isAlive() {
        return this.health > 0;
    }

    @Override
    public void up() {
        return;
    }

    @Override
    public void down() {
        return;
    }

    @Override
    public void left() {
        this.position.setX(this.position.getX() - 1);
    }

    @Override
    public void right() {
        this.position.setX(this.position.getX() + 1);
    }

    public void shoot(){
        // todo

    }

    public void setShoot(boolean x){ shoot = x;}

    public boolean getShoot(){
        return shoot;
    }

    @Override
    public Image getImage() {
        return this.image;
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
    public Bullet createBullet() {
        Vector2D position = new Vector2D(getPosition().getX(), getPosition().getY()-10);
       Bullet playerBullet = new PlayerBullet(position);
       return playerBullet;
    }

}
