package creature;
import field.Field;
import javafx.scene.image.Image;

public class Xiaolouluo extends Monster {

    public Xiaolouluo(Field f){
        super(f);
    }

   @Override
    public Image report(){
       if(!isDead())
            return new Image(this.getClass().getResourceAsStream("/LittleMonster.png"));
       else
           return new Image(this.getClass().getResourceAsStream("/LittleMonsterDead.png"));
    }
}
