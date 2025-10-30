#define HIGHP
#define N_SAMPLES 10

uniform sampler2D u_texture;

uniform vec2 u_resolution;
uniform float u_intensity;

varying vec2 v_texCoords;

//https://www.shadertoy.com/view/XsfSDs
void main(){
    if(u_intensity < 0.001){
        gl_FragColor = texture2D(u_texture, v_texCoords.xy);
        return;
    }

    vec2 center = vec2(0.5);
    vec2 c = v_texCoords.xy - center;

    float precompute = u_intensity * (1.0 / float(N_SAMPLES - 1));
    vec4 color = vec4(0.0);
    for(int i = 0; i < N_SAMPLES; i++){
        float scale = 1.0 + (float(i - N_SAMPLES / 2) * precompute);
        color += texture2D(u_texture, c * scale + center);
    }

    color /= float(N_SAMPLES);

    gl_FragColor = color;
}