package cn.triumphal.field;

import cn.triumphal.creature.*;
import cn.triumphal.stratagem.ChangsheStratagem;
import cn.triumphal.stratagem.HeyiStratagem;

import java.io.*;
import java.util.ArrayList;

public class Field {
    final public int sizeX = 12;
    final public int sizeY = 8;
    private Position[][] positions;
    private Creature[][] creatures;
    private String fieldDocument; //保存到文件
    private boolean runAllThread; //线程控制
    private boolean isDisplaying;

    public Field() {    //创建出二维空间放置生物
        isDisplaying = false;
        fieldDocument = "";
        runAllThread = true;
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

    //添加creature
    public <Template extends Creature> void Add(int x, int y, Template creature){
        this.creatures[x][y] = creature;
        this.positions[x][y].setHolder(creature);
        creature.setPosition(this.positions[x][y]);
    }

    //删除creature
    public <Template extends Creature> void Delete(int x, int y){
        Creature p = new Space();
        this.creatures[x][y] = p;
        this.positions[x][y].setHolder(p);
        p.setPosition(this.positions[x][y]);
    }

    public void setIsDisplaying(boolean b){
        isDisplaying = b;
    }

    public boolean getIsDisplaying(){
        return isDisplaying;
    }

    public Position[][] getPositions() {
        return positions;
    }

    public Creature[][] getCreatures() {
        return creatures;
    }

    public void setFieldDocument(String str){
        fieldDocument = str;
    }

    public String getFieldDocument(){
        return fieldDocument;
    }

    public void setRunAllThread(boolean b){
        runAllThread = b;
    }

    public boolean getRunAllThread(){
        return runAllThread;
    }

    //同一行是否有敌人
    public boolean isRowHaveEnemy(Creature creature){
        int row = creature.getPosition().getY();
        for(int i = 0; i < sizeX; ++i){
            if(creatures[i][row].getSide() == -creature.getSide() && creatures[i][row].isDead() == false)
                return true;
        }
        return false;
    }

    //准备战斗 摆好阵形
    public void getReady(){
        ArrayList<Creature> goodGuys = new ArrayList<Creature>();
        ArrayList<Huluwa> brothers = new ArrayList<Huluwa>();
        for (int i = 0; i < 7; i++) {
            brothers.add(new Huluwa(Huluwa.COLOR.values()[i], Huluwa.SENIORITY.values()[i], this));
        }
        goodGuys.addAll(brothers);
        goodGuys.add(new Grandpa(this));
        new ChangsheStratagem().generate(0,0,goodGuys,this);

        ArrayList<Monster> badGuys = new ArrayList<Monster>();
        badGuys.add(new Xiaolouluo(this));
        badGuys.add(new Xiaolouluo(this));
        badGuys.add(new Xiaolouluo(this));
        badGuys.add(new Xiezijing(this));
        badGuys.add(new Snake(this));
        badGuys.add(new Xiaolouluo(this));
        badGuys.add(new Xiaolouluo(this));
        badGuys.add(new Xiaolouluo(this));
        new HeyiStratagem().generate(8,0,badGuys,this);
    }

    //清空战场
    public void CleanField(){
        for(int i = 0; i < sizeX; ++i) {
            for (int j = 0; j < sizeY; ++j) {
                this.creatures[i][j] = new Space();
                this.positions[i][j] = new Position(i, j);
                this.creatures[i][j].setPosition(this.positions[i][j]);
            }
        }
    }

    //从文件读取历史战斗
    public boolean getHistoryField(String filename, int step) {
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

        CleanField();

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
                    int posx = cnt / 8;
                    int posy = cnt % 8;
                    switch (c){
                        case 'I':Add(posx, posy, new Huluwa(Huluwa.COLOR.values()[0], Huluwa.SENIORITY.values()[0], this));creatures[posx][posy].setDead(true);break;
                        case 'O':Add(posx, posy, new Huluwa(Huluwa.COLOR.values()[1], Huluwa.SENIORITY.values()[1], this));creatures[posx][posy].setDead(true);break;
                        case 'P':Add(posx, posy, new Huluwa(Huluwa.COLOR.values()[2], Huluwa.SENIORITY.values()[2], this));creatures[posx][posy].setDead(true);break;
                        case 'A':Add(posx, posy, new Huluwa(Huluwa.COLOR.values()[3], Huluwa.SENIORITY.values()[3], this));creatures[posx][posy].setDead(true);break;
                        case 'S':Add(posx, posy, new Huluwa(Huluwa.COLOR.values()[4], Huluwa.SENIORITY.values()[4], this));creatures[posx][posy].setDead(true);break;
                        case 'D':Add(posx, posy, new Huluwa(Huluwa.COLOR.values()[5], Huluwa.SENIORITY.values()[5], this));creatures[posx][posy].setDead(true);break;
                        case 'F':Add(posx, posy, new Huluwa(Huluwa.COLOR.values()[6], Huluwa.SENIORITY.values()[6], this));creatures[posx][posy].setDead(true);break;
                        case 'H':Add(posx, posy, new Grandpa(this));creatures[posx][posy].setDead(true);break;
                        case 'J':Add(posx, posy, new Snake(this));creatures[posx][posy].setDead(true);break;
                        case 'L':Add(posx, posy, new Xiaolouluo(this));creatures[posx][posy].setDead(true);break;
                        case 'X':Add(posx, posy, new Xiezijing(this));creatures[posx][posy].setDead(true);break;
                        case 'Q':Add(posx, posy, new Huluwa(Huluwa.COLOR.values()[0], Huluwa.SENIORITY.values()[0], this));creatures[posx][posy].setDead(false);break;
                        case 'W':Add(posx, posy, new Huluwa(Huluwa.COLOR.values()[1], Huluwa.SENIORITY.values()[1], this));creatures[posx][posy].setDead(false);break;
                        case 'E':Add(posx, posy, new Huluwa(Huluwa.COLOR.values()[2], Huluwa.SENIORITY.values()[2], this));creatures[posx][posy].setDead(false);break;
                        case 'R':Add(posx, posy, new Huluwa(Huluwa.COLOR.values()[3], Huluwa.SENIORITY.values()[3], this));creatures[posx][posy].setDead(false);break;
                        case 'T':Add(posx, posy, new Huluwa(Huluwa.COLOR.values()[4], Huluwa.SENIORITY.values()[4], this));creatures[posx][posy].setDead(false);break;
                        case 'Y':Add(posx, posy, new Huluwa(Huluwa.COLOR.values()[5], Huluwa.SENIORITY.values()[5], this));creatures[posx][posy].setDead(false);break;
                        case 'U':Add(posx, posy, new Huluwa(Huluwa.COLOR.values()[6], Huluwa.SENIORITY.values()[6], this));creatures[posx][posy].setDead(false);break;
                        case 'G':Add(posx, posy, new Grandpa(this));creatures[posx][posy].setDead(false);break;
                        case 'K':Add(posx, posy, new Snake(this));creatures[posx][posy].setDead(false);break;
                        case 'Z':Add(posx, posy, new Xiaolouluo(this));creatures[posx][posy].setDead(false);break;
                        case 'C':Add(posx, posy, new Xiezijing(this));creatures[posx][posy].setDead(false);break;
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

    //创建线程并启动
    public void startRun() {
        for(int i = 0; i < this.sizeX; ++i) {
            for (int j = 0; j < this.sizeY; ++j) {
                if (!creatures[i][j].getClass().getSimpleName().equals("Space") && creatures[i][j].isDead() == false) {
                    Creature creature = creatures[i][j];
                    Thread current = new Thread(creature);
                    creature.setThread(current);
                    current.start();
                }
            }
        }
    }

    //保存当前的情况到字符串
    public void saveCurrentField(){
        fieldDocument += '#';
        for(int i = 0; i < sizeX; ++i) {
            for (int j = 0; j < sizeY; ++j) {
                String kind = creatures[i][j].getClass().getSimpleName();
                switch (kind){
                    case "Space":fieldDocument += '_'; continue;
                    case "Grandpa":fieldDocument += (creatures[i][j].isDead())?'H':'G';continue;
                    case "Huluwa":fieldDocument += ((Huluwa)creatures[i][j]).getNum();continue;
                    case "Snake":fieldDocument += (creatures[i][j].isDead())?'J':'K';continue;
                    case "Xiaolouluo":fieldDocument += (creatures[i][j].isDead())?'L':'Z';continue;
                    case "Xiezijing":fieldDocument += (creatures[i][j].isDead())?'X':'C';continue;
                    default:fieldDocument+='_';continue;
                }
            }
        }
        fieldDocument += '#';
        fieldDocument += '\n';
    }

    //游戏是否结束
    public int isGameOver(){
        boolean goodGuyAlive = false;
        boolean badGuyAlive = false;
        for(int i = 0; i < this.sizeX; ++i) {
            for(int j = 0; j < this.sizeY; ++j) {
                if(creatures[i][j].isDead() == false) {
                    if (creatures[i][j].getSide() == 1)
                        goodGuyAlive = true;
                    else if(creatures[i][j].getSide() == -1)
                        badGuyAlive = true;
                }
            }
        }
        if(goodGuyAlive && badGuyAlive)
            return 0;
        else if(goodGuyAlive)
            return 1;
        else if(badGuyAlive)
            return -1;
        else return 0x42;
    }

    public void showGoodGuyWin(){//好人胜利 清空进程 摆造型
        setRunAllThread(false);
        CleanField();
        for (int i = 0; i < 7; i++) {
            this.Add(2+i, 4, new Huluwa(Huluwa.COLOR.values()[i], Huluwa.SENIORITY.values()[i], this));
        }
        this.Add(9,4,new Grandpa(this));
    }

    public void showBadGuyWin(){
        //坏人胜利 清空进程 摆造型
        setRunAllThread(false);
        CleanField();
        for (int i = 0; i < 3; i++) {
            this.Add(2+i, 4, new Xiaolouluo(this));
        }
        this.Add(5,4,new Xiezijing(this));
        this.Add(6,4,new Snake(this));
        for (int i = 0; i < 3; i++) {
            this.Add(7+i, 4, new Xiaolouluo(this));
        }
    }
}