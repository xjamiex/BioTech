package biotech;

import arc.graphics.Camera;
import arc.graphics.Color;
import arc.graphics.g2d.Draw;
import arc.graphics.g2d.Fill;
import arc.scene.Element;
import arc.scene.Group;
import arc.util.Time;
import mindustry.Vars;
import mindustry.logic.LExecutor;

import static arc.Core.graphics;
import static arc.graphics.g2d.Draw.rect;

public class ImaFragment extends Element {

    private final Camera camera = new Camera();

    public boolean enabled = false;
    float waitTime = 0f;
    float frameTime = 0f;
    int frames = 3;
    float alpha = 0;

    static int timeOffset = 0;
    static int drawIdx = 0;
    static int[] times = {6 * 60, 3 * 60, 10 * 60, 3 * 60, 15 * 60, 4 * 60, 17 * 60, 28 * 60, 10};

    public void build(Group parent) {
        parent.fill((x, y, width, height) -> render());
    }

    void render() {
        if (!enabled) return;
        Vars.control.pause();

        int sw = graphics.getWidth();
        int sh = graphics.getHeight();
        float max = (float)Math.max(sw, sh);

        float width = (sw / max) * 1000f;
        float height = (sh / max) * 1000f;

        camera.position.set(width / 2f, height / 2f);
        camera.resize(width, height);

        //Draw.flush();
        Draw.proj(camera);

        drawMain();
    }

    void drawMain(){
        Draw.z(0f);
        Draw.color(Color.black);
        Draw.alpha(alpha);
        rect();

        waitTime += Time.delta;
        if(waitTime < 640f){
            alpha += 0.005f;
            return;
        }

        frameTime += Time.delta;
        if(frameTime > 1f){
            frames++;
            frameTime -= 1f;
        }

        if(drawIdx < times.length){
            if((frames - timeOffset) >= times[drawIdx]){
                drawIdx++;
                timeOffset = frames;
                if(drawIdx >= times.length){
                    enabled = false;
                }
            }
        }

        Draw.color(Color.white);
        switch(drawIdx){
            case 0 -> tidesOfAffection();
            case 1 -> placeholder();
            case 2 -> reset();
        }
    }

    void tidesOfAffection() {
        Draw.color(Color.darkGray);
        rect();
    }

    void placeholder() {
        Draw.color(Color.gray);
        rect();
    }

    void reset() {
        enabled = false;
        Vars.control.resume();
    }

    void rect() {
        Fill.rect(camera.position.x, camera.position.y, camera.width, camera.height);
    }
}
