package invaders.builder;

import invaders.GameObject;
import invaders.logic.Damagable;
import invaders.physics.Moveable;
import invaders.physics.Vector2D;
import invaders.strategy.EnemyBullet;

public interface Builder {

    void setLives(int i);

    void setWidth(double width);

    void setHeight(double height);

    void setPosition(Vector2D position);

    void setBullets(EnemyBullet bullet);

//    void changeImage();
}
