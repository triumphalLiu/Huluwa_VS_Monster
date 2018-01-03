package creature;
import field.Field;
import field.Position;
import javafx.scene.image.Image;

public class Grandpa implements Creature {
    private Position position;
    private Boolean isDead;
    private Field field;
    private Thread thread = null;
    private Image image;
    private Image imageDied;

    public Grandpa(Field f){
        field = f;
        isDead = false;
        image = new Image(this.getClass().getResourceAsStream("/Grandpa.png"));
        imageDied = new Image(this.getClass().getResourceAsStream("/GrandpaDead.png"));
    }

    @Override
    public Thread getThread() {
        return thread;
    }

    @Override
    public void setThread(Thread t){
        thread = t;
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
            return image;
        else
            return imageDied;
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
    public void run() {
        while (field.getRunAllThread()) {
            //System.out.println("Grandpa Move");
            //找到最近的一个对手
            int len = 9999;
            int x = -1;
            int y = -1;
            for (int i = 0; i < field.sizeX; ++i) {
                for (int j = 0; j < field.sizeY; ++j) {
                    if (field.getCreatures()[i][j].getSide() == -this.getSide() && field.getCreatures()[i][j].isDead() == false) {
                        int distance = Math.abs(position.getX() - i) + Math.abs(position.getY() - j);
                        if (distance < len) {
                            len = distance;
                            x = i;
                            y = j;
                        }
                    }
                }
            }
            //距离小于1就打起来
            if (len == 1) {
                if (Math.random() > 0.4)
                    this.setDead(true);
                else
                    field.getCreatures()[x][y].setDead(true);
            }
            //否则看这一排还有没有对手
            else if (field.isRowHaveEnemy(this)) {
                field.Delete(position.getX(), position.getY());
                if (position.getX() > x) {
                    if (position.getX() - 1 < 0 || !field.getCreatures()[position.getX() - 1][position.getY()].getClass().getSimpleName().equals("Space"))
                        field.Add(position.getX(), position.getY(), this);
                    else
                        field.Add(position.getX() - 1, position.getY(), this);
                } else {
                    if (position.getX() + 1 >= field.sizeX || !field.getCreatures()[position.getX() + 1][position.getY()].getClass().getSimpleName().equals("Space"))
                        field.Add(position.getX(), position.getY(), this);
                    else
                        field.Add(position.getX() + 1, position.getY(), this);
                }
            }
            //没有就去其他排
            else {
                field.Delete(position.getX(), position.getY());
                if (position.getY() > y) {
                    if (position.getY() - 1 < 0 || !field.getCreatures()[position.getX()][position.getY() - 1].getClass().getSimpleName().equals("Space"))
                        field.Add(position.getX(), position.getY(), this);
                    else
                        field.Add(position.getX(), position.getY() - 1, this);
                } else {
                    if (position.getY() + 1 >= field.sizeY || !field.getCreatures()[position.getX()][position.getY() + 1].getClass().getSimpleName().equals("Space"))
                        field.Add(position.getX(), position.getY(), this);
                    else
                        field.Add(position.getX(), position.getY() + 1, this);
                }
            }
            if(this.isDead()){
                System.out.println("Grandpa Die");
                thread.interrupt();
                break;
            }
            try {
                Thread.sleep((int) (Math.random() * 1000) + 2000);
            } catch (Exception e) {

            }
        }
    }
}
