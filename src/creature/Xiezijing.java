package creature;
import Field.Position;
import javafx.scene.image.Image;

public class Xiezijing extends Monster {
    private Position position;
    @Override
    public Image report(){
        return new Image(this.getClass().getResourceAsStream("/Xiezijing.png"));
    }
}
