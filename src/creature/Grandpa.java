package creature;
import Field.*;
import javafx.scene.image.Image;

public class Grandpa implements Creature {
    private Position position;
    private Boolean isDead;

    public Grandpa(){
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
        if(!isDead())
            return new Image(this.getClass().getResourceAsStream("/Grandpa.png"));
        else
            return new Image(this.getClass().getResourceAsStream("/GrandpaDead.png"));
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
        return 1;
    }

    @Override
    public void run(){

    }

}
