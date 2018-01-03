package creature;
import field.Field;
import javafx.scene.image.Image;

public class Xiezijing extends Monster {

    public Xiezijing(Field f){
        super(f);
        image = new Image(this.getClass().getResourceAsStream("/Xiezijing.png"));
        imageDied = new Image(this.getClass().getResourceAsStream("/XiezijingDead.png"));
    }

    @Override
    public Image report(){
        if(!isDead())
            return image;
        else
            return imageDied;
    }
}
