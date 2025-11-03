package biotech.entities.part;

import mindustry.*;

public class BioPartProgParams{
    public static final BioPartParams bioParams = new BioPartParams();

    public static class BioPartParams{
        public int id;

        public BioPartParams set(int id){
            this.id = id;
            return this;
        }
    }

    public interface BioPartProgress{
        BioPartProgress
            id = p -> bioParams.id
        ;

        float get(BioPartProgress p);
    }


}
