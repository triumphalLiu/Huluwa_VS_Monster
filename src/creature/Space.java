package creature;

import Field.Position;
import javafx.scene.image.Image;

public class Space implements Creature {
    private Position position;

    @Override
    public Image report() {
        return null;
    }

    @Override
    public void setPosition(Position position) {
        this.position = position;
        position.setHolder(this);
    }

    @Override
    public Position getPosition() {
        return null;
    }
}
