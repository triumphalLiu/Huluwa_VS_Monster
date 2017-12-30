package creature;
import Field.Position;
import javafx.scene.image.Image;

public class Monster implements Creature{
    private Position position;
    @Override
    public Image report(){
        return null;
    }

    @Override
    public void setPosition(Position position){
        this.position = position;
        position.setHolder(this);
    }

    @Override
    public Position getPosition(){
        return position;
    }
}
