package creature;
import Field.*;
import javafx.scene.image.Image;

public class Snake extends Monster {
    private Position position;

    @Override
    public Image report(){
        return new Image(this.getClass().getResourceAsStream("/Snake.png"));
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
