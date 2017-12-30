package creature;

import Field.Position;
import javafx.scene.image.Image;

public class Space implements Creature {
    private Position position;

    @Override
    public Image report() {
        return new Image(this.getClass().getResourceAsStream("/Empty.png"));
    }

    @Override
    public void setPosition(Position position) {
        this.position = position;
        position.setHolder(this);
    }

    @Override
    public Position getPosition() {
        return position;
    }

    @Override
    public int getSide(){
        return 0;
    }
}
