package cn.triumphal.creature;

import cn.triumphal.field.Position;
import javafx.scene.image.Image;

public interface Creature extends Runnable{
    Image report();
    void setPosition(Position position);
    Position getPosition();
    int getSide();
    Boolean isDead();
    void setDead(Boolean dead);
    Thread getThread();
    void setThread(Thread t);
}
