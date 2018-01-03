package stratagem;

import field.Field;
import creature.Creature;
import creature.Space;
import java.util.ArrayList;

public class HeyiStratagem implements Stratagem {

    @Override
    public <Template extends Creature> void generate(int startRow, int startCol, ArrayList<Template> creatures, Field space) {
        for(int i = 0; i < creatures.size()/2; ++i)
            space.Add(startRow+i, startCol+i, creatures.get(i));
        for(int i = creatures.size()/2; i < creatures.size(); ++i)
            space.Add(startRow+creatures.size()-i-1, startCol+i, creatures.get(i));

    }

    @Override
    public <Template extends Creature> void cancel(int startRow, int startCol, ArrayList<Template> creatures, Field space) {
        for(int i = 0; i < creatures.size()/2; ++i)
            space.Add(startRow+i, startCol+i, new Space());
        space.Add(startRow + creatures.size()/2, startCol + creatures.size()/2, new Space());
        for(int i = creatures.size()/2 + 1; i < creatures.size(); ++i)
            space.Add(startRow+creatures.size()-i-1, startCol+i, new Space());

    }
}
