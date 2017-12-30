package creature;

import Field.Position;
import javafx.scene.image.Image;

public interface Creature {
    public Image report();
    public void setPosition(Position position);
    public Position getPosition();
    public int getSide();
}
