package creature;
import Field.Position;
import javafx.scene.image.Image;

public class Monster implements Creature{
    private Position position;
    private Boolean isDead;

    public Monster(){
        isDead = false;
    }

    @Override
    public void setDead(Boolean dead) {
        isDead = dead;
    }

    @Override
    public Boolean isDead(){
        return isDead;
    }

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

    @Override
    public int getSide(){
        return -1;
    }

    @Override
    public void run(){
        System.out.println(3);
    }
}
