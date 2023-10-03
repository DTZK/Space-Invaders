package invaders.strategy;

import invaders.GameObject;
import invaders.physics.Moveable;
import invaders.physics.Vector2D;
import invaders.rendering.Renderable;

public interface EnemyBullet extends Moveable, Renderable, GameObject {
    void makeBullet(Vector2D position);

}
