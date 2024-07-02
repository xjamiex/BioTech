package biotech.entities.part;

import arc.math.Interp;
import arc.math.Mathf;
import arc.util.Log;
import arc.util.Time;
import mindustry.entities.part.RegionPart;


public class BiologicalPart extends RegionPart {

    public int id;

    public BiologicalPart(String region) {
        super(region);
        id = Mathf.random(999999);
        progress = p -> Interp.exp5.apply(Mathf.sinDeg(Time.time + id * 5.2f)) * 0.4f;
        growProgress = p -> Interp.exp5.apply(Mathf.sinDeg(Time.time + id * 8f)) * 0.1f;
        Log.info(id);
    }

    @Override
    public void draw(PartParams params) {
        super.draw(params);
    }
}
