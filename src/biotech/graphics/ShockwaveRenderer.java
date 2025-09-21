package biotech.graphics;

import arc.Core;
import arc.Events;
import arc.graphics.g2d.Draw;
import arc.graphics.g2d.TextureRegion;
import arc.graphics.gl.FrameBuffer;
import arc.math.Mathf;
import arc.util.Time;
import mindustry.Vars;
import mindustry.game.EventType;
import mindustry.graphics.Layer;

import static arc.Core.*;

public class ShockwaveRenderer {
    private float boomIntensity,
            boomReduction,
            boomTime;
    private float rockAlpha,
            rockReduction;
    private final float rockBorder = 32;
    private final FrameBuffer buffer;

    public ShockwaveRenderer(){
        if(Vars.headless){
            buffer = null;
            return;
        }

        BioShaders.init();
        buffer = new FrameBuffer();

        Events.run(EventType.Trigger.update, this::update);
        Events.run(EventType.Trigger.draw, this::draw);
    }

    public void boom(float intensity, float duration){
        boomIntensity = Math.max(boomIntensity, Mathf.clamp(intensity, 0, 1));
        boomTime = Math.max(boomTime, duration);
        boomReduction = boomIntensity / boomTime;
        rockAlpha = 0.25f;
        rockReduction = rockAlpha / boomTime;
    }

    private void update(){
        if(!Vars.state.isPaused() && boomTime > 0){
            boomIntensity -= boomReduction * Time.delta;
            rockAlpha -= rockReduction * Time.delta;
            boomTime -= Time.delta;
            boomIntensity = Mathf.clamp(boomIntensity, 0f, 1f);
            rockAlpha = Mathf.clamp(rockAlpha, 0f, 1f);
        }
    }

    private void draw(){
        if(Vars.headless || boomIntensity < 0.001f) return;

        Draw.draw(Layer.background - 0.1f, () -> {
            if(!blur()) return;
            buffer.resize(graphics.getWidth(), graphics.getHeight());
            buffer.begin();
        });

        Draw.draw(Layer.max - 6, () -> {
            if(blur()){
                buffer.end();
                BioShaders.shockwaveShader.intensity = boomIntensity * intensity();
                buffer.blit(BioShaders.shockwaveShader);
            }

            Draw.alpha(rockAlpha);
            TextureRegion region = atlas.find("circle");
            float rWidth = region.width, rHeight = region.height;
            float sWidth = graphics.getWidth() - 2 * rockBorder,
                    sHeight = graphics.getHeight() - 2 * rockBorder;
            float targetRatio = sHeight / sWidth;
            float sourceRatio = rHeight / rWidth;
            float scale = targetRatio > sourceRatio ? sWidth / rWidth : sHeight / rHeight;
            rWidth *= scale / Vars.renderer.getDisplayScale();
            rHeight *= scale / Vars.renderer.getDisplayScale();
            Draw.rect(region, camera.position.x, camera.position.y, rWidth, rHeight);
            Draw.color();
        });
    }

    public boolean blur(){
        return settings.getInt("vine-boom-intensity", 4) > 0;
    }

    public float intensity(){
        return settings.getInt("vine-boom-intensity", 4) / 4f;
    }
}
