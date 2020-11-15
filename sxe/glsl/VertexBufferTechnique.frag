#version 330
// but 300 for ES.

/* gl_FragColor deprecated since version 120, aka OpenGL 2.1. */
out vec4 FragColor;

void main()
{
    FragColor = vec4(0.5, 0.0, 0.5, 1.0);
}
