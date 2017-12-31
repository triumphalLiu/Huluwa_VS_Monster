package Field;

import creature.*;
import stratagem.ChangsheStratagem;
import stratagem.HeyiStratagem;
import stratagem.YanxingStratagem;

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

    public <Template extends Creature> void Delete(int x, int y){
        Creature p = new Space();
        this.creatures[x][y] = p;
        this.positions[x][y].setHolder(p);
    }

    public void getReady(){ //准备战斗
        ArrayList<Creature> goodGuys = new ArrayList<Creature>();
        ArrayList<Huluwa> brothers = new ArrayList<Huluwa>();
        for (int i = 0; i < 7; i++) {
            brothers.add(new Huluwa(Huluwa.COLOR.values()[i], Huluwa.SENIORITY.values()[i]));
        }
        goodGuys.addAll(brothers);
        goodGuys.add(new Grandpa());
        new ChangsheStratagem().generate(0,0,goodGuys,this);

        ArrayList<Monster> badGuys = new ArrayList<Monster>();

        badGuys.add(new Xiaolouluo());
        badGuys.add(new Xiaolouluo());
        badGuys.add(new Xiaolouluo());
        badGuys.add(new Xiezijing());
        badGuys.add(new Snake());
        badGuys.add(new Xiaolouluo());
        badGuys.add(new Xiaolouluo());
        badGuys.add(new Xiaolouluo());
        new HeyiStratagem().generate(8,0,badGuys,this);
    }

    public void getHistoryField(String filename, int step){ //读取文件加载历史

    }

    public void move(){ //移动
        for(int j = 0; j < this.sizeY; ++j){
            if(creatures[0][j].getSide() == 1){
                Creature creature = creatures[0][j];
                Delete(0,j);
                Add(1,j,creature);
            }
        }
    }
}