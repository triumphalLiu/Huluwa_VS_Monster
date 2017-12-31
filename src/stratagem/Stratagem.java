package stratagem;

import creature.Creature;
import Field.Field;
import java.util.ArrayList;

public interface Stratagem {
    public <Template extends Creature> void generate(int startRow, int startCol, ArrayList<Template> creatures, Field space);
    public <Template extends Creature> void cancel(int startRow, int startCol, ArrayList<Template> creatures, Field space);
}
