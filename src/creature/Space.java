package creature;

import field.Position;
import javafx.scene.image.Image;

public class Space implements Creature {
    private Position position;
    private Image image;

    public Space(){
        image = new Image(this.getClass().getResourceAsStream("/Empty.png"));
    }

    @Override
    public Image report() {
        return image;
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

    @Override
    public Boolean isDead() {
        return true;
    }

    @Override
    public void setDead(Boolean dead) {}

    @Override
    public Thread getThread() {
        return null;
    }

    @Override
    public void setThread(Thread t) {}

    @Override
    public void run(){}
}
