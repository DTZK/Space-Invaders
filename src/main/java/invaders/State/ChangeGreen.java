package invaders.State;

import invaders.builder.Bunker;
import javafx.scene.image.Image;

import java.io.File;

public class ChangeGreen implements ColourChange{
    private Bunker bunker;

    public ChangeGreen(Bunker bunker){
        this.bunker = bunker;
        bunker.changeImage(new Image(new File("src/main/resources/greenbunker.png").toURI().toString(), bunker.getWidth(), bunker.getHeight(), false, false));
    }
    @Override
    public void changeColour() {
        bunker.changeState(new ChangeYellow(bunker));
    }
}
