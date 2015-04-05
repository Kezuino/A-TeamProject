varying vec4 v_color;
varying vec2 v_texCoord0;

uniform sampler2D u_sampler2D;

vec4 toGrayscale(in vec4 color) {
    float average = (color.r + color.g + color.b) / 3.0;
    return vec4(vec3(average), 1.0);
}

void main() {
    gl_FragColor = texture2D(u_sampler2D, v_texCoord0) * toGrayscale(v_color);
}