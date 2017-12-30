package creature;
import Field.*;
import javafx.scene.image.Image;

public class Grandpa implements Creature {
    private Position position;

    @Override
    public Image report(){
        return new Image(this.getClass().getResourceAsStream("/Grandpa.png"));
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
