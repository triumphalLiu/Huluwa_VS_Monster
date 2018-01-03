package creature;
import javafx.scene.image.Image;

public class Xiezijing extends Monster {

    @Override
    public Image report(){
        if(!isDead())
            return new Image(this.getClass().getResourceAsStream("/Xiezijing.png"));
        else
            return new Image(this.getClass().getResourceAsStream("/XiezijingDead.png"));
    }
}
