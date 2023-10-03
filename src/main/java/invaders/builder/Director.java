package invaders.builder;

import invaders.physics.Vector2D;
import invaders.strategy.EnemyBullet;

public class Director {
    private Vector2D position;
    private EnemyBullet bullet;

    private double width;
    private double height;

    public void setWidth(double width){
        this.width = width;
    }

    public void setHeight(double height){
        this.height = height;
    }

    public void setBullet(EnemyBullet bullet){
        this.bullet = bullet;
    }

    public void setPosition(Vector2D position){
        this.position=position;
    }

    public void constructAlienBuilder(Builder alienBuild){
        alienBuild.setLives(1);
        alienBuild.setWidth(25);
        alienBuild.setHeight(25);
        alienBuild.setPosition(position);
        alienBuild.setBullets(bullet);
//        return alienBuild.getAlien();
    }

    public void constructBunkerBuilder(Builder bunkerBuild){
        bunkerBuild.setLives(3);
        bunkerBuild.setPosition(position);
        bunkerBuild.setWidth(width);
        bunkerBuild.setHeight(height);
//        return bunkerBuild.getBunker();
    }

}
