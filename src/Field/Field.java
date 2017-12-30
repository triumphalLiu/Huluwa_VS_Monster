package Field;

import creature.*;

import java.util.ArrayList;

public class Field {
    public int sizeX = 12;
    public int sizeY = 8;
    private Position[][] positions;
    private Creature[][] creatures;

    public Field() {    //创建出二维空间放置生物
        this.positions = new Position[sizeX][sizeY];
        this.creatures = new Creature[sizeX][sizeY];
        for(int i = 0; i < sizeX; ++i) {
            for (int j = 0; j < sizeY; ++j) {
                this.creatures[i][j] = new Space();
                this.positions[i][j] = new Position(i, j);
                this.creatures[i][j].setPosition(this.positions[i][j]);
            }
        }
    }

    public Position[] getPositions(int i) {
        return positions[i];
    }

    public Creature[][] getCreatures() {
        return creatures;
    }

    public <Template extends Creature> void Add(int x, int y, Template creature){
        this.creatures[x][y] = creature;
        this.positions[x][y].setHolder(creature);
    }

    public void getReady(){
        ArrayList<Huluwa> brothers = new ArrayList<Huluwa>();
        for (int i = 0; i < 7; i++) {
            brothers.add(new Huluwa(Huluwa.COLOR.values()[i], Huluwa.SENIORITY.values()[i]));
            Add(0,i,brothers.get(i));
        }
        Add(0,7,new Grandpa());
        Add(2,0,new Xiezijing());
        Add(3,0,new Xiaolouluo());
        Add(4,0,new Snake());
    }

    public void getHistoryField(String filename, int step){

    }
}