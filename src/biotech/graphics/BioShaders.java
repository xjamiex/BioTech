package biotech.graphics;

import arc.Core;
import arc.graphics.gl.Shader;
import mindustry.Vars;

import static arc.Core.files;
import static mindustry.Vars.renderer;

public class BioShaders {
    public static ShockwaveShader shockwaveShader;

    public static void init(){
        shockwaveShader = new ShockwaveShader();
    }

    public static class ShockwaveShader extends BioLoadShader{
        public float radius;
        public float intensity;

        public ShockwaveShader(){
            super("screenspace", "vineboom");
        }

        @Override
        public void apply(){
            setUniformf("u_resolution", Core.graphics.getWidth(), Core.graphics.getHeight());
            setUniformf("u_radius", radius * renderer.getDisplayScale());
            setUniformf("u_intensity", intensity * renderer.getDisplayScale());
        }
    }

    public static class BioLoadShader extends Shader {
        public BioLoadShader(String vert, String frag){
            super(
                    files.internal("shaders/" + vert + ".vert"),
                    Vars.tree.get("shaders/" + frag + ".frag")
            );
        }

        public BioLoadShader(String frag){
            this("default", frag);
        }
    }
}
