package biotech;

import arc.Core;
import arc.graphics.Camera;
import arc.graphics.Color;
import arc.graphics.g2d.Draw;
import arc.graphics.g2d.Fill;
import arc.math.Mathf;
import arc.scene.Element;
import arc.scene.Group;
import arc.util.Log;
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
    static int[] times = {4 * 60, 4 * 60, 4 * 60, 4 * 60, 7 * 60};

    public void build(Group parent) {
        parent.fill((x, y, width, height) -> render());
    }

    void render() {
        if (!enabled) return;

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
        if(waitTime < 400f){
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
                    Vars.control.resume();
                    Log.info("HUHHH");
                    enabled = false;
                } else {
                    Vars.control.pause();
                }
            }
        }

        Draw.color(Color.white);
        switch(drawIdx){
            case 0 -> drawLine("biotech-ima-cutscene-line-1");
            case 1 -> drawLine("biotech-ima-cutscene-line-2");
            case 2 -> drawLine("biotech-ima-cutscene-line-3");
            case 3 -> drawLine("biotech-ima-cutscene-line-4");
            case 4 -> drawLine("biotech-ima-cutscene-line-5");
        }
    }

    void drawLine(String textSprite) {
        Draw.alpha(Mathf.random());
        Draw.rect(Core.atlas.find("biotech-ima-cutscene-eye"), camera.position.x + Mathf.random(-1, 1), camera.position.y + 25 + Mathf.random(-1, 1), camera.height, camera.height);
        Draw.alpha(Mathf.random());
        Draw.rect(Core.atlas.find(textSprite), camera.position.x + Mathf.random(-1, 1), camera.position.y + Mathf.random(-1, 1), camera.width, camera.height);
    }

    void rect() {
        Fill.rect(camera.position.x, camera.position.y, camera.width, camera.height);
    }
}
