package invaders.factory;

import invaders.GameObject;
import invaders.physics.Moveable;
import invaders.rendering.Renderable;

//Product in factory method
public interface Bullet extends Moveable, Renderable, GameObject {
    void set_behaviour();

}
