#version 330
// but 300 for ES.
// this or version 430 if you want layout on uniforms.
#extension GL_ARB_explicit_uniform_location : warn

layout(location = 0) in vec4 sxe_vertex_position;

uniform mat4 sxe_transform;

void main()
{
    gl_Position = sxe_transform * sxe_vertex_position;
}
