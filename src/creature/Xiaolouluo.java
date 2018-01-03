package creature;
import field.Field;
import javafx.scene.image.Image;

public class Xiaolouluo extends Monster {

    public Xiaolouluo(Field f){
        super(f);
        image = new Image(this.getClass().getResourceAsStream("/LittleMonster.png"));
        imageDied = new Image(this.getClass().getResourceAsStream("/LittleMonsterDead.png"));
    }

   @Override
    public Image report(){
       if(!isDead())
            return image;
       else
            return imageDied;
    }
}
