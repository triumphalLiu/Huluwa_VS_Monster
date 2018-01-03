package creature;

import field.Field;
import javafx.scene.image.Image;

public class Snake extends Monster {

    public Snake(Field f){
        super(f);
        image = new Image(this.getClass().getResourceAsStream("/Snake.png"));
        imageDied = new Image(this.getClass().getResourceAsStream("/SnakeDead.png"));
    }

    @Override
    public Image report(){
        if(!isDead())
            return image;
        else
            return imageDied;
    }
}
