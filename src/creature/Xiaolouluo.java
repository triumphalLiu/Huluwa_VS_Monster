package creature;
import Field.Position;
import javafx.scene.image.Image;

public class Xiaolouluo extends Monster {

   @Override
    public Image report(){
       if(!isDead())
            return new Image(this.getClass().getResourceAsStream("/LittleMonster.png"));
       else
           return new Image(this.getClass().getResourceAsStream("/LittleMonsterDead.png"));
    }
}
