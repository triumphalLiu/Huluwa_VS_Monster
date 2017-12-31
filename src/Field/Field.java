package Field;

import com.sun.org.apache.xpath.internal.operations.Bool;
import creature.*;
import stratagem.ChangsheStratagem;
import stratagem.HeyiStratagem;
import stratagem.YanxingStratagem;

import java.io.*;
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

    public Boolean getHistoryField(String filename, int step) { //读取文件加载历史
        Reader reader;
        try{
            reader = new FileReader(filename);
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
        long skip = (sizeX * sizeY + 3 /*##R*/) * step;
        try {
            reader.skip(skip);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        Boolean start = false;
        int cnt = -1; //第一个符号不计

        //清空战场
        for(int i = 0; i < sizeX; ++i) {
            for (int j = 0; j < sizeY; ++j) {
                this.creatures[i][j] = new Space();
                this.positions[i][j] = new Position(i, j);
                this.creatures[i][j].setPosition(this.positions[i][j]);
            }
        }

        while(true){
            try {
                char c = (char)(reader.read());
                if(start == false && c != '#')
                    return false;
                if(c == '#' && start == false)
                    start = true;
                else if(c == '#' && start == true)
                    break;
                else{
                    int posx = cnt % 12;
                    int posy = cnt / 12;
                    System.out.print(c);
                    switch (c){
                        case '1':Add(posx, posy, new Huluwa(Huluwa.COLOR.values()[0], Huluwa.SENIORITY.values()[0]));break;
                        case '2':Add(posx, posy, new Huluwa(Huluwa.COLOR.values()[1], Huluwa.SENIORITY.values()[1]));break;
                        case '3':Add(posx, posy, new Huluwa(Huluwa.COLOR.values()[2], Huluwa.SENIORITY.values()[2]));break;
                        case '4':Add(posx, posy, new Huluwa(Huluwa.COLOR.values()[3], Huluwa.SENIORITY.values()[3]));break;
                        case '5':Add(posx, posy, new Huluwa(Huluwa.COLOR.values()[4], Huluwa.SENIORITY.values()[4]));break;
                        case '6':Add(posx, posy, new Huluwa(Huluwa.COLOR.values()[5], Huluwa.SENIORITY.values()[5]));break;
                        case '7':Add(posx, posy, new Huluwa(Huluwa.COLOR.values()[6], Huluwa.SENIORITY.values()[6]));break;
                        case 'g':Add(posx, posy, new Grandpa());break;
                        case 's':Add(posx, posy, new Snake());break;
                        case 'l':Add(posx, posy, new Xiaolouluo());break;
                        case 'x':Add(posx, posy, new Xiezijing());break;
                        default:break;
                    }
                }
                cnt++;
            } catch (IOException e) {
                e.printStackTrace();
                return false;
            }
        }
        try {
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
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