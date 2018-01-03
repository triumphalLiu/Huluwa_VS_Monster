package stratagem;

import creature.Creature;
import creature.Space;
import field.Field;

import java.util.ArrayList;

public class ChangsheStratagem implements Stratagem {
    @Override
    public <Template extends Creature> void cancel(int startRow, int startCol, ArrayList<Template> creatures, Field space) {
        for(int i = 0; i < creatures.size(); ++i)
            space.Add(startRow, i + startCol, new Space());
    }

    @Override
    public <Template extends Creature> void generate(int startRow, int startCol, ArrayList<Template> creatures, Field space){
        for(int i = 0; i < creatures.size(); ++i)
            space.Add(startRow, i + startCol, creatures.get(i));
    }
}
