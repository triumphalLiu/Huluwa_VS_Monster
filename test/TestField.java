import creature.*;
import field.Field;
import org.junit.Test;

public class TestField {
    Field field;

    @Test
    public void testAddCreature(){
        field = new Field();
        Creature creature = new Xiaolouluo(field);
        field.Add(5,5, creature);
        assert(field.getCreatures()[5][5] instanceof Xiaolouluo);
    }

    @Test
    public void testDeleteCreature(){
        field = new Field();
        field.Add(5,5,new Xiaolouluo(field));
        assert(field.getCreatures()[5][5] instanceof Xiaolouluo);
        field.Delete(5,5);
        assert(field.getCreatures()[5][5] instanceof Space);
    }

    @Test
    public void testIsRowHaveEnemy(){
        field = new Field();
        field.Add(5,5,new Xiaolouluo(field));
        field.Add(5,6,new Grandpa(field));
        field.Add(6,5,new Grandpa(field));
        assert(field.isRowHaveEnemy(field.getCreatures()[5][6]) == false);
        assert(field.isRowHaveEnemy(field.getCreatures()[6][5]) == true);
    }

    @Test
    public void testIsGameOver(){
        field = new Field();
        field.Add(1,1,new Grandpa(field));
        field.Add(0,0,new Xiaolouluo(field));
        field.getCreatures()[1][1].setDead(true);
        assert(field.isGameOver() == -1);//妖精胜利

        field = new Field();
        field.Add(0,0,new Xiaolouluo(field));
        field.Add(5,5,new Huluwa(Huluwa.COLOR.values()[0], Huluwa.SENIORITY.values()[0], field));
        field.getCreatures()[0][0].setDead(true);
        assert(field.isGameOver() == 1);//葫芦娃胜利

        field = new Field();
        field.Add(5,5,new Huluwa(Huluwa.COLOR.values()[0], Huluwa.SENIORITY.values()[0], field));
        field.Add(6,6,new Snake(field));
        assert(field.isGameOver() == 0);//战斗持续
    }
}
