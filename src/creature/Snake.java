package creature;

import javafx.scene.image.Image;

public class Snake extends Monster {

    @Override
    public Image report(){
        if(!isDead())
            return new Image(this.getClass().getResourceAsStream("/Snake.png"));
        else
            return new Image(this.getClass().getResourceAsStream("/SnakeDead.png"));
    }
}
