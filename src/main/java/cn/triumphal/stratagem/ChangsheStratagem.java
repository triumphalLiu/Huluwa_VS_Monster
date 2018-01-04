package cn.triumphal.stratagem;

import cn.triumphal.creature.Creature;
import cn.triumphal.field.Field;

import java.util.ArrayList;

public class ChangsheStratagem implements Stratagem {
    @Override
    public <Template extends Creature> void generate(int startRow, int startCol, ArrayList<Template> creatures, Field space){
        for(int i = 0; i < creatures.size(); ++i)
            space.Add(startRow, i + startCol, creatures.get(i));
    }
}
