package invaders.State;

import invaders.builder.Bunker;
import javafx.scene.image.Image;

import java.io.File;

public class ChangeRed implements ColourChange{
    private Bunker bunker;
    public ChangeRed(Bunker bunker){
        this.bunker = bunker;
        bunker.changeImage(new Image(new File("src/main/resources/bunker.png").toURI().toString(), bunker.getWidth(), bunker.getHeight(), false, true));
    }

    @Override
    public void changeColour() {
        bunker.changeState(new ChangeRed(bunker));
    }
}
