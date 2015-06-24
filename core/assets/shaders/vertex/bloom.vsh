#version 330

in vec4 a_color;
in vec3 a_position;
in vec2 a_texCoord0;

out vec4 v_color;
out vec2 v_texCoord0;

void main() {
    v_color = a_color;
    v_texCoord0 = a_texCoord0;

    gl_Position = vec4(a_position, 1.0);
}