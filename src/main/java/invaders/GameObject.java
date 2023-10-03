package invaders;

import invaders.logic.Damagable;
import invaders.physics.Collider;

// contains basic methods that all GameObjects must implement
public interface GameObject extends Collider, Damagable {

    public void start();
    public void update();


}
