package creature;

import field.Field;
import javafx.scene.image.Image;

public class Snake extends Monster {

    public Snake(Field f){
        super(f);
    }

    @Override
    public Image report(){
        if(!isDead())
            return new Image(this.getClass().getResourceAsStream("/Snake.png"));
        else
            return new Image(this.getClass().getResourceAsStream("/SnakeDead.png"));
    }
}
