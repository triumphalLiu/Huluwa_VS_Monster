package creature;
import field.Position;
import javafx.scene.image.Image;

public class Huluwa implements Creature {

    public enum COLOR {
        赤, 橙, 黄, 绿, 青, 蓝, 紫
    }

    public enum SENIORITY {
        一, 二, 三, 四, 五, 六, 七
    }

    private COLOR color;
    private SENIORITY seniority;
    private Position position;
    private Boolean isDead;

    public Huluwa(COLOR color, SENIORITY seiority) {
        isDead = false;
        this.color = color;
        this.seniority = seiority;
    }

    @Override
    public void setDead(Boolean dead) {
        isDead = dead;
    }

    @Override
    public Boolean isDead(){
        return isDead;
    }

    public COLOR getColor() {
        return color;
    }

    public SENIORITY getSeniority() {
        return seniority;
    }

    @Override
    public Position getPosition() {
        return position;
    }

    @Override
    public void setPosition(Position position) {
        this.position = position;
        position.setHolder(this);
    }

    @Override
    public int getSide(){
        return 1;
    }

    @Override
    public Image report() {
        if(!isDead()) {
            switch (this.seniority) {
                case 一:
                    return new Image(this.getClass().getResourceAsStream("/Red.png"));
                case 二:
                    return new Image(this.getClass().getResourceAsStream("/Orange.png"));
                case 三:
                    return new Image(this.getClass().getResourceAsStream("/Yellow.png"));
                case 四:
                    return new Image(this.getClass().getResourceAsStream("/Green.png"));
                case 五:
                    return new Image(this.getClass().getResourceAsStream("/Cyan.png"));
                case 六:
                    return new Image(this.getClass().getResourceAsStream("/Blue.png"));
                case 七:
                    return new Image(this.getClass().getResourceAsStream("/Purple.png"));
                default://error
                    break;
            }
        }
        else{
            switch (this.seniority) {
                case 一:
                    return new Image(this.getClass().getResourceAsStream("/RedDead.png"));
                case 二:
                    return new Image(this.getClass().getResourceAsStream("/OrangeDead.png"));
                case 三:
                    return new Image(this.getClass().getResourceAsStream("/YellowDead.png"));
                case 四:
                    return new Image(this.getClass().getResourceAsStream("/GreenDead.png"));
                case 五:
                    return new Image(this.getClass().getResourceAsStream("/CyanDead.png"));
                case 六:
                    return new Image(this.getClass().getResourceAsStream("/BlueDead.png"));
                case 七:
                    return new Image(this.getClass().getResourceAsStream("/PurpleDead.png"));
                default://error
                    break;
            }
        }
        return null;
    }

    @Override
    public void run(){
        System.out.println(2);
    }
}
