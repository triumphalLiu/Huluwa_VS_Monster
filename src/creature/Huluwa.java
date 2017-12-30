package creature;
import Field.*;
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

    public Huluwa(COLOR color, SENIORITY seiority) {
        this.color = color;
        this.seniority = seiority;
    }

    @Override
    public Image report() {
        switch (this.seniority){
            case 一: return new Image(this.getClass().getResourceAsStream("/Red.png"));
            case 二: return new Image(this.getClass().getResourceAsStream("/Orange.png"));
            case 三: return new Image(this.getClass().getResourceAsStream("/Yellow.png"));
            case 四: return new Image(this.getClass().getResourceAsStream("/Green.png"));
            case 五: return new Image(this.getClass().getResourceAsStream("/Cyan.png"));
            case 六: return new Image(this.getClass().getResourceAsStream("/Blue.png"));
            case 七: return new Image(this.getClass().getResourceAsStream("/Purple.png"));
            default://error
                break;
        }
        return null;
    }

    @Override
    public String toString(){
        return this.seniority.toString() + "(" + this.color.toString() + ")@" + this.position.getX() + ";";
    }

    public boolean biggerThan(Comparable brother){
        if (brother instanceof  Huluwa)
            return this.getSeniority().ordinal()> ((Huluwa) brother).getSeniority().ordinal();
        else
            return false;
    }
}
