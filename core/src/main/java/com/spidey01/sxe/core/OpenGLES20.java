/*-
 * Copyright (c) 2013-current, Terry Mathew Poulin <BigBoss1964@gmail.com>
 *
 * This software is provided 'as-is', without any express or implied warranty.
 * In no event will the authors be held liable for any damages arising from the
 * use of this software.
 *
 * Permission is granted to anyone to use this software for any purpose,
 * including commercial applications, and to alter it and redistribute it
 * freely, subject to the following restrictions:
 *
 *	1. The origin of this software must not be misrepresented; you must
 *	   not claim that you wrote the original software. If you use this
 *	   software in a product, an acknowledgment in the product
 *	   documentation would be appreciated but is not required.
 *
 *	2. Altered source versions must be plainly marked as such, and must
 *	   not be misrepresented as being the original software.
 *
 *	3. This notice may not be removed or altered from any source
 *	   distribution.
 */

package com.spidey01.sxe.core;

import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.DoubleBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;

/** OpenGL Interface for Embedded Systems v2.0 */
public interface OpenGLES20 {

    /* these values from Linux /usr/include/GL/**.h on my linux box and
     * Android, which seems to be where they get their values from too.
     */

    static final int GL_ACTIVE_ATTRIBUTES = 0x8B89;
    static final int GL_ACTIVE_ATTRIBUTE_MAX_LENGTH = 0x8B8A;
    static final int GL_ACTIVE_TEXTURE = 0x84E0;
    static final int GL_ACTIVE_UNIFORMS = 0x8B86;
    static final int GL_ACTIVE_UNIFORM_MAX_LENGTH = 0x8B87;
    static final int GL_ALIASED_LINE_WIDTH_RANGE = 0x846E;
    static final int GL_ALIASED_POINT_SIZE_RANGE = 0x846D;
    static final int GL_ALPHA = 0x1906;
    static final int GL_ALPHA_BITS = 0x0D55;
    static final int GL_ALWAYS = 0x0207;
    static final int GL_ARRAY_BUFFER = 0x8892;
    static final int GL_ARRAY_BUFFER_BINDING = 0x8894;
    static final int GL_ATTACHED_SHADERS = 0x8B85;
    static final int GL_BACK = 0x0405;
    static final int GL_BLEND = 0x0BE2;
    static final int GL_BLEND_COLOR = 0x8005;
    static final int GL_BLEND_DST_ALPHA = 0x80CA;
    static final int GL_BLEND_DST_RGB = 0x80C8;
    static final int GL_BLEND_EQUATION = 0x8009;
    static final int GL_BLEND_EQUATION_ALPHA = 0x883D;
    static final int GL_BLEND_EQUATION_RGB = 0x8009;
    static final int GL_BLEND_SRC_ALPHA = 0x80CB;
    static final int GL_BLEND_SRC_RGB = 0x80C9;
    static final int GL_BLUE_BITS = 0x0D54;
    static final int GL_BOOL = 0x8B56;
    static final int GL_BOOL_VEC2 = 0x8B57;
    static final int GL_BOOL_VEC3 = 0x8B58;
    static final int GL_BOOL_VEC4 = 0x8B59;
    static final int GL_BUFFER_SIZE = 0x8764;
    static final int GL_BUFFER_USAGE = 0x8765;
    static final int GL_BYTE = 0x1400;
    static final int GL_CCW = 0x0901;
    static final int GL_CLAMP_TO_EDGE = 0x812F;
    static final int GL_COLOR_ATTACHMENT0 = 0x8CE0;
    static final int GL_COLOR_BUFFER_BIT = 0x00004000;
    static final int GL_COLOR_CLEAR_VALUE = 0x0C22;
    static final int GL_COLOR_WRITEMASK = 0x0C23;
    static final int GL_COMPILE_STATUS = 0x8B81;
    static final int GL_COMPRESSED_TEXTURE_FORMATS = 0x86A3;
    static final int GL_CONSTANT_ALPHA = 0x8003;
    static final int GL_CONSTANT_COLOR = 0x8001;
    static final int GL_CULL_FACE = 0x0B44;
    static final int GL_CULL_FACE_MODE = 0x0B45;
    static final int GL_CURRENT_PROGRAM = 0x8B8D;
    static final int GL_CURRENT_VERTEX_ATTRIB = 0x8626;
    static final int GL_CW = 0x0900;
    static final int GL_DECR = 0x1E03;
    static final int GL_DECR_WRAP = 0x8508;
    static final int GL_DELETE_STATUS = 0x8B80;
    static final int GL_DEPTH_ATTACHMENT = 0x8D00;
    static final int GL_DEPTH_BITS = 0x0D56;
    static final int GL_DEPTH_BUFFER_BIT = 0x00000100;
    static final int GL_DEPTH_CLEAR_VALUE = 0x0B73;
    static final int GL_DEPTH_COMPONENT = 0x1902;
    static final int GL_DEPTH_COMPONENT16 = 0x81A5;
    static final int GL_DEPTH_FUNC = 0x0B74;
    static final int GL_DEPTH_RANGE = 0x0B70;
    static final int GL_DEPTH_TEST = 0x0B71;
    static final int GL_DEPTH_WRITEMASK = 0x0B72;
    static final int GL_DITHER = 0x0BD0;
    static final int GL_DONT_CARE = 0x1100;
    static final int GL_DST_ALPHA = 0x0304;
    static final int GL_DST_COLOR = 0x0306;
    static final int GL_DYNAMIC_DRAW = 0x88E8;
    static final int GL_ELEMENT_ARRAY_BUFFER = 0x8893;
    static final int GL_ELEMENT_ARRAY_BUFFER_BINDING = 0x8895;
    static final int GL_EQUAL = 0x0202;
    static final int GL_EXTENSIONS = 0x1F03;
    static final int GL_FALSE = 0;
    static final int GL_FASTEST = 0x1101;
    static final int GL_FIXED = 0x140C;
    static final int GL_FLOAT = 0x1406;
    static final int GL_FLOAT_MAT2 = 0x8B5A;
    static final int GL_FLOAT_MAT3 = 0x8B5B;
    static final int GL_FLOAT_MAT4 = 0x8B5C;
    static final int GL_FLOAT_VEC2 = 0x8B50;
    static final int GL_FLOAT_VEC3 = 0x8B51;
    static final int GL_FLOAT_VEC4 = 0x8B52;
    static final int GL_FRAGMENT_SHADER = 0x8B30;
    static final int GL_FRAMEBUFFER = 0x8D40;
    static final int GL_FRAMEBUFFER_ATTACHMENT_OBJECT_NAME = 0x8CD1;
    static final int GL_FRAMEBUFFER_ATTACHMENT_OBJECT_TYPE = 0x8CD0;
    static final int GL_FRAMEBUFFER_ATTACHMENT_TEXTURE_CUBE_MAP_FACE = 0x8CD3;
    static final int GL_FRAMEBUFFER_ATTACHMENT_TEXTURE_LEVEL = 0x8CD2;
    static final int GL_FRAMEBUFFER_BINDING = 0x8CA6;
    static final int GL_FRAMEBUFFER_COMPLETE = 0x8CD5;
    static final int GL_FRAMEBUFFER_INCOMPLETE_ATTACHMENT = 0x8CD6;
    static final int GL_FRAMEBUFFER_INCOMPLETE_DIMENSIONS = 0x8CD9;
    static final int GL_FRAMEBUFFER_INCOMPLETE_MISSING_ATTACHMENT = 0x8CD7;
    static final int GL_FRAMEBUFFER_UNSUPPORTED = 0x8CDD;
    static final int GL_FRONT = 0x0404;
    static final int GL_FRONT_AND_BACK = 0x0408;
    static final int GL_FRONT_FACE = 0x0B46;
    static final int GL_FUNC_ADD = 0x8006;
    static final int GL_FUNC_REVERSE_SUBTRACT = 0x800B;
    static final int GL_FUNC_SUBTRACT = 0x800A;
    static final int GL_GENERATE_MIPMAP_HINT = 0x8192;
    static final int GL_GEQUAL = 0x0206;
    static final int GL_GREATER = 0x0204;
    static final int GL_GREEN_BITS = 0x0D53;
    static final int GL_HIGH_FLOAT = 0x8DF2;
    static final int GL_HIGH_INT = 0x8DF5;
    static final int GL_IMPLEMENTATION_COLOR_READ_FORMAT = 0x8B9B;
    static final int GL_IMPLEMENTATION_COLOR_READ_TYPE = 0x8B9A;
    static final int GL_INCR = 0x1E02;
    static final int GL_INCR_WRAP = 0x8507;
    static final int GL_INFO_LOG_LENGTH = 0x8B84;
    static final int GL_INT = 0x1404;
    static final int GL_INT_VEC2 = 0x8B53;
    static final int GL_INT_VEC3 = 0x8B54;
    static final int GL_INT_VEC4 = 0x8B55;
    static final int GL_INVALID_ENUM = 0x0500;
    static final int GL_INVALID_FRAMEBUFFER_OPERATION = 0x0506;
    static final int GL_INVALID_OPERATION = 0x0502;
    static final int GL_INVALID_VALUE = 0x0501;
    static final int GL_INVERT = 0x150A;
    static final int GL_KEEP = 0x1E00;
    static final int GL_LEQUAL = 0x0203;
    static final int GL_LESS = 0x0201;
    static final int GL_LINEAR = 0x2601;
    static final int GL_LINEAR_MIPMAP_LINEAR = 0x2703;
    static final int GL_LINEAR_MIPMAP_NEAREST = 0x2701;
    static final int GL_LINES = 0x0001;
    static final int GL_LINE_LOOP = 0x0002;
    static final int GL_LINE_STRIP = 0x0003;
    static final int GL_LINE_WIDTH = 0x0B21;
    static final int GL_LINK_STATUS = 0x8B82;
    static final int GL_LOW_FLOAT = 0x8DF0;
    static final int GL_LOW_INT = 0x8DF3;
    static final int GL_LUMINANCE = 0x1909;
    static final int GL_LUMINANCE_ALPHA = 0x190A;
    static final int GL_MAX_COMBINED_TEXTURE_IMAGE_UNITS = 0x8B4D;
    static final int GL_MAX_CUBE_MAP_TEXTURE_SIZE = 0x851C;
    static final int GL_MAX_FRAGMENT_UNIFORM_VECTORS = 0x8DFD;
    static final int GL_MAX_RENDERBUFFER_SIZE = 0x84E8;
    static final int GL_MAX_TEXTURE_IMAGE_UNITS = 0x8872;
    static final int GL_MAX_TEXTURE_SIZE = 0x0D33;
    static final int GL_MAX_VARYING_VECTORS = 0x8DFC;
    static final int GL_MAX_VERTEX_ATTRIBS = 0x8869;
    static final int GL_MAX_VERTEX_TEXTURE_IMAGE_UNITS = 0x8B4C;
    static final int GL_MAX_VERTEX_UNIFORM_VECTORS = 0x8DFB;
    static final int GL_MAX_VIEWPORT_DIMS = 0x0D3A;
    static final int GL_MEDIUM_FLOAT = 0x8DF1;
    static final int GL_MEDIUM_INT = 0x8DF4;
    static final int GL_MIRRORED_REPEAT = 0x8370;
    static final int GL_NEAREST = 0x2600;
    static final int GL_NEAREST_MIPMAP_LINEAR = 0x2702;
    static final int GL_NEAREST_MIPMAP_NEAREST = 0x2700;
    static final int GL_NEVER = 0x0200;
    static final int GL_NICEST = 0x1102;
    static final int GL_NONE = 0;
    static final int GL_NOTEQUAL = 0x0205;
    static final int GL_NO_ERROR = 0;
    static final int GL_NUM_COMPRESSED_TEXTURE_FORMATS = 0x86A2;
    static final int GL_NUM_SHADER_BINARY_FORMATS = 0x8DF9;
    static final int GL_ONE = 1;
    static final int GL_ONE_MINUS_CONSTANT_ALPHA = 0x8004;
    static final int GL_ONE_MINUS_CONSTANT_COLOR = 0x8002;
    static final int GL_ONE_MINUS_DST_ALPHA = 0x0305;
    static final int GL_ONE_MINUS_DST_COLOR = 0x0307;
    static final int GL_ONE_MINUS_SRC_ALPHA = 0x0303;
    static final int GL_ONE_MINUS_SRC_COLOR = 0x0301;
    static final int GL_OUT_OF_MEMORY = 0x0505;
    static final int GL_PACK_ALIGNMENT = 0x0D05;
    static final int GL_POINTS = 0x0000;
    static final int GL_POLYGON_OFFSET_FACTOR = 0x8038;
    static final int GL_POLYGON_OFFSET_FILL = 0x8037;
    static final int GL_POLYGON_OFFSET_UNITS = 0x2A00;
    static final int GL_RED_BITS = 0x0D52;
    static final int GL_RENDERBUFFER = 0x8D41;
    static final int GL_RENDERBUFFER_ALPHA_SIZE = 0x8D53;
    static final int GL_RENDERBUFFER_BINDING = 0x8CA7;
    static final int GL_RENDERBUFFER_BLUE_SIZE = 0x8D52;
    static final int GL_RENDERBUFFER_DEPTH_SIZE = 0x8D54;
    static final int GL_RENDERBUFFER_GREEN_SIZE = 0x8D51;
    static final int GL_RENDERBUFFER_HEIGHT = 0x8D43;
    static final int GL_RENDERBUFFER_INTERNAL_FORMAT = 0x8D44;
    static final int GL_RENDERBUFFER_RED_SIZE = 0x8D50;
    static final int GL_RENDERBUFFER_STENCIL_SIZE = 0x8D55;
    static final int GL_RENDERBUFFER_WIDTH = 0x8D42;
    static final int GL_RENDERER = 0x1F01;
    static final int GL_REPEAT = 0x2901;
    static final int GL_REPLACE = 0x1E01;
    static final int GL_RGB = 0x1907;
    static final int GL_RGB565 = 0x8D62;
    static final int GL_RGB5_A1 = 0x8057;
    static final int GL_RGBA = 0x1908;
    static final int GL_RGBA4 = 0x8056;
    static final int GL_SAMPLER_2D = 0x8B5E;
    static final int GL_SAMPLER_CUBE = 0x8B60;
    static final int GL_SAMPLES = 0x80A9;
    static final int GL_SAMPLE_ALPHA_TO_COVERAGE = 0x809E;
    static final int GL_SAMPLE_BUFFERS = 0x80A8;
    static final int GL_SAMPLE_COVERAGE = 0x80A0;
    static final int GL_SAMPLE_COVERAGE_INVERT = 0x80AB;
    static final int GL_SAMPLE_COVERAGE_VALUE = 0x80AA;
    static final int GL_SCISSOR_BOX = 0x0C10;
    static final int GL_SCISSOR_TEST = 0x0C11;
    static final int GL_SHADER_BINARY_FORMATS = 0x8DF8;
    static final int GL_SHADER_COMPILER = 0x8DFA;
    static final int GL_SHADER_SOURCE_LENGTH = 0x8B88;
    static final int GL_SHADER_TYPE = 0x8B4F;
    static final int GL_SHADING_LANGUAGE_VERSION = 0x8B8C;
    static final int GL_SHORT = 0x1402;
    static final int GL_SRC_ALPHA = 0x0302;
    static final int GL_SRC_ALPHA_SATURATE = 0x0308;
    static final int GL_SRC_COLOR = 0x0300;
    static final int GL_STATIC_DRAW = 0x88E4;
    static final int GL_STENCIL_ATTACHMENT = 0x8D20;
    static final int GL_STENCIL_BACK_FAIL = 0x8801;
    static final int GL_STENCIL_BACK_FUNC = 0x8800;
    static final int GL_STENCIL_BACK_PASS_DEPTH_FAIL = 0x8802;
    static final int GL_STENCIL_BACK_PASS_DEPTH_PASS = 0x8803;
    static final int GL_STENCIL_BACK_REF = 0x8CA3;
    static final int GL_STENCIL_BACK_VALUE_MASK = 0x8CA4;
    static final int GL_STENCIL_BACK_WRITEMASK = 0x8CA5;
    static final int GL_STENCIL_BITS = 0x0D57;
    static final int GL_STENCIL_BUFFER_BIT = 0x00000400;
    static final int GL_STENCIL_CLEAR_VALUE = 0x0B91;
    static final int GL_STENCIL_FAIL = 0x0B94;
    static final int GL_STENCIL_FUNC = 0x0B92;
    static final int GL_STENCIL_INDEX = 0x1901;
    static final int GL_STENCIL_INDEX8 = 0x8D48;
    static final int GL_STENCIL_PASS_DEPTH_FAIL = 0x0B95;
    static final int GL_STENCIL_PASS_DEPTH_PASS = 0x0B96;
    static final int GL_STENCIL_REF = 0x0B97;
    static final int GL_STENCIL_TEST = 0x0B90;
    static final int GL_STENCIL_VALUE_MASK = 0x0B93;
    static final int GL_STENCIL_WRITEMASK = 0x0B98;
    static final int GL_STREAM_DRAW = 0x88E0;
    static final int GL_SUBPIXEL_BITS = 0x0D50;
    static final int GL_TEXTURE = 0x1702;
    static final int GL_TEXTURE0 = 0x84C0;
    static final int GL_TEXTURE1 = 0x84C1;
    static final int GL_TEXTURE10 = 0x84CA;
    static final int GL_TEXTURE11 = 0x84CB;
    static final int GL_TEXTURE12 = 0x84CC;
    static final int GL_TEXTURE13 = 0x84CD;
    static final int GL_TEXTURE14 = 0x84CE;
    static final int GL_TEXTURE15 = 0x84CF;
    static final int GL_TEXTURE16 = 0x84D0;
    static final int GL_TEXTURE17 = 0x84D1;
    static final int GL_TEXTURE18 = 0x84D2;
    static final int GL_TEXTURE19 = 0x84D3;
    static final int GL_TEXTURE2 = 0x84C2;
    static final int GL_TEXTURE20 = 0x84D4;
    static final int GL_TEXTURE21 = 0x84D5;
    static final int GL_TEXTURE22 = 0x84D6;
    static final int GL_TEXTURE23 = 0x84D7;
    static final int GL_TEXTURE24 = 0x84D8;
    static final int GL_TEXTURE25 = 0x84D9;
    static final int GL_TEXTURE26 = 0x84DA;
    static final int GL_TEXTURE27 = 0x84DB;
    static final int GL_TEXTURE28 = 0x84DC;
    static final int GL_TEXTURE29 = 0x84DD;
    static final int GL_TEXTURE3 = 0x84C3;
    static final int GL_TEXTURE30 = 0x84DE;
    static final int GL_TEXTURE31 = 0x84DF;
    static final int GL_TEXTURE4 = 0x84C4;
    static final int GL_TEXTURE5 = 0x84C5;
    static final int GL_TEXTURE6 = 0x84C6;
    static final int GL_TEXTURE7 = 0x84C7;
    static final int GL_TEXTURE8 = 0x84C8;
    static final int GL_TEXTURE9 = 0x84C9;
    static final int GL_TEXTURE_2D = 0x0DE1;
    static final int GL_TEXTURE_BINDING_2D = 0x8069;
    static final int GL_TEXTURE_BINDING_CUBE_MAP = 0x8514;
    static final int GL_TEXTURE_CUBE_MAP = 0x8513;
    static final int GL_TEXTURE_CUBE_MAP_NEGATIVE_X = 0x8516;
    static final int GL_TEXTURE_CUBE_MAP_NEGATIVE_Y = 0x8518;
    static final int GL_TEXTURE_CUBE_MAP_NEGATIVE_Z = 0x851A;
    static final int GL_TEXTURE_CUBE_MAP_POSITIVE_X = 0x8515;
    static final int GL_TEXTURE_CUBE_MAP_POSITIVE_Y = 0x8517;
    static final int GL_TEXTURE_CUBE_MAP_POSITIVE_Z = 0x8519;
    static final int GL_TEXTURE_MAG_FILTER = 0x2800;
    static final int GL_TEXTURE_MIN_FILTER = 0x2801;
    static final int GL_TEXTURE_WRAP_S = 0x2802;
    static final int GL_TEXTURE_WRAP_T = 0x2803;
    static final int GL_TRIANGLES = 0x0004;
    static final int GL_TRIANGLE_FAN = 0x0006;
    static final int GL_TRIANGLE_STRIP = 0x0005;
    static final int GL_TRUE = 1;
    static final int GL_UNPACK_ALIGNMENT = 0x0CF5;
    static final int GL_UNSIGNED_BYTE = 0x1401;
    static final int GL_UNSIGNED_INT = 0x1405;
    static final int GL_UNSIGNED_SHORT = 0x1403;
    static final int GL_UNSIGNED_SHORT_4_4_4_4 = 0x8033;
    static final int GL_UNSIGNED_SHORT_5_5_5_1 = 0x8034;
    static final int GL_UNSIGNED_SHORT_5_6_5 = 0x8363;
    static final int GL_VALIDATE_STATUS = 0x8B83;
    static final int GL_VENDOR = 0x1F00;
    static final int GL_VERSION = 0x1F02;
    static final int GL_VERTEX_ATTRIB_ARRAY_BUFFER_BINDING = 0x889F;
    static final int GL_VERTEX_ATTRIB_ARRAY_ENABLED = 0x8622;
    static final int GL_VERTEX_ATTRIB_ARRAY_NORMALIZED = 0x886A;
    static final int GL_VERTEX_ATTRIB_ARRAY_POINTER = 0x8645;
    static final int GL_VERTEX_ATTRIB_ARRAY_SIZE = 0x8623;
    static final int GL_VERTEX_ATTRIB_ARRAY_STRIDE = 0x8624;
    static final int GL_VERTEX_ATTRIB_ARRAY_TYPE = 0x8625;
    static final int GL_VERTEX_SHADER = 0x8B31;
    static final int GL_VIEWPORT = 0x0BA2;
    static final int GL_ZERO = 0;


    /* OpenGL functions */

    void glAttachShader(GpuProgram p, Shader s);
    void glAttachShader(int program, int shader);

    void glBindBuffer(int target, int buffer);
    void glBindTexture(int target, int texture);
    void glBufferData(int target, ByteBuffer data, int usage);
    void glBufferData(int target, DoubleBuffer data, int usage);
    void glBufferData(int target, FloatBuffer data, int usage);
    void glBufferData(int target, IntBuffer data, int usage);

    void glClear(int mask);
    void glClearColor(float red, float green, float blue, float alpha);
    void glCompileShader(int shader);
    int glCreateProgram();
    int glCreateShader(int type);


    void glDeleteProgram(GpuProgram program);
    void glDeleteProgram(int program);
    void glDeleteShader(int shader);
    void glDetachShader(GpuProgram program, Shader shader);
    void glDetachShader(int program, int shader);
    void glDisable(int cap);
    void glDisableVertexAttribArray(int index);
    void glDrawArrays(int mode, int first, int count);

    void glEnable(int cap);
    void glEnableVertexAttribArray(int index);

    void glGenBuffers(IntBuffer buffers);
    void glGenTextures(ByteBuffer buffer);
    void glGenTextures(IntBuffer buffer);
    int glGetAttribLocation(int program, String name);
    String glGetProgramInfoLog(int program);
    int glGetProgramiv(int program, int pname);
    String glGetShaderInfoLog(int shader);
    int glGetShaderiv(int shader, int pname);
    int glGetUniformLocation(GpuProgram program, String name);
    int glGetUniformLocation(int program, String name);
    void glGetUniformfv(GpuProgram program, int location, FloatBuffer params);
    void glGetUniformfv(int program, int location, FloatBuffer params);
    void glGetUniformiv(GpuProgram program, int location, IntBuffer params);
    void glGetUniformiv(int program, int location, IntBuffer params);

    void glLineWidth(float width);
    void glLinkProgram(GpuProgram p);
    void glLinkProgram(int program);

    void glShaderSource(int shader, String source);
    void glTexImage2D(int target, int level, int internalFormat, int width, int height, int border, int format, int type, ByteBuffer pixels);

    void glUniform1f(int location, float v0);
    void glUniform1i(int location, int v0);
    void glUniform2f(int location, float v0, float v1);
    void glUniform2i(int location, int v0, int v1);
    void glUniform3f(int location, float v0, float v1, float v2);
    void glUniform3i(int location, int v0, int v1, int v2);
    void glUniform4f(int location, float v0, float v1, float v2, float v3);
    void glUniform4i(int location, int v0, int v1, int v2, int v3);
    void glUseProgram(GpuProgram program);
    void glUseProgram(int program);

    void glValidateProgram(GpuProgram p);
    void glValidateProgram(int program);
    void glVertex2d(double x, double y);
    void glVertex2f(float x, float y);
    void glVertex2i(int x, int y);
    void glVertex3d(double x, double y, double z);
    void glVertex3f(float x, float y, float z);
    void glVertex3i(int x, int y, int z);
    void glVertex4d(double x, double y, double z, double w);
    void glVertex4f(float x, float y, float z, float w);
    void glVertex4i(int x, int y, int z, int w);
    void glVertexAttribPointer(int indx, int size, int type, boolean normalized, int stride, int offset);
    void glViewport(int x, int y, int width, int height);

}

