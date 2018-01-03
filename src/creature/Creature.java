package creature;

import Field.Position;
import javafx.scene.image.Image;

public interface Creature extends Runnable{
    public Image report();
    public void setPosition(Position position);
    public Position getPosition();
    public int getSide();
    public Boolean isDead();
    public void setDead(Boolean dead);
}
