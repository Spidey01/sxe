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

/** OpenGL Interface for Embedded Systems v1.1 */
public interface OpenGLES11 {

    static final int GL_ACTIVE_TEXTURE = 0x84E0;
    static final int GL_ADD = 0x0104;
    static final int GL_ADD_SIGNED = 0x8574;
    static final int GL_ALIASED_LINE_WIDTH_RANGE = 0x846E;
    static final int GL_ALIASED_POINT_SIZE_RANGE = 0x846D;
    static final int GL_ALPHA = 0x1906;
    static final int GL_ALPHA_BITS = 0x0D55;
    static final int GL_ALPHA_SCALE = 0x0D1C;
    static final int GL_ALPHA_TEST = 0x0BC0;
    static final int GL_ALPHA_TEST_FUNC = 0x0BC1;
    static final int GL_ALPHA_TEST_REF = 0x0BC2;
    static final int GL_ALWAYS = 0x0207;
    static final int GL_AMBIENT = 0x1200;
    static final int GL_AMBIENT_AND_DIFFUSE = 0x1602;
    static final int GL_AND = 0x1501;
    static final int GL_AND_INVERTED = 0x1504;
    static final int GL_AND_REVERSE = 0x1502;
    static final int GL_ARRAY_BUFFER = 0x8892;
    static final int GL_ARRAY_BUFFER_BINDING = 0x8894;
    static final int GL_BACK = 0x0405;
    static final int GL_BLEND = 0x0BE2;
    static final int GL_BLEND_DST = 0x0BE0;
    static final int GL_BLEND_SRC = 0x0BE1;
    static final int GL_BLUE_BITS = 0x0D54;
    static final int GL_BUFFER_ACCESS = 0x88BB;
    static final int GL_BUFFER_SIZE = 0x8764;
    static final int GL_BUFFER_USAGE = 0x8765;
    static final int GL_BYTE = 0x1400;
    static final int GL_CCW = 0x0901;
    static final int GL_CLAMP_TO_EDGE = 0x812F;
    static final int GL_CLEAR = 0x1500;
    static final int GL_CLIENT_ACTIVE_TEXTURE = 0x84E1;
    static final int GL_CLIP_PLANE0 = 0x3000;
    static final int GL_CLIP_PLANE1 = 0x3001;
    static final int GL_CLIP_PLANE2 = 0x3002;
    static final int GL_CLIP_PLANE3 = 0x3003;
    static final int GL_CLIP_PLANE4 = 0x3004;
    static final int GL_CLIP_PLANE5 = 0x3005;
    static final int GL_COLOR_ARRAY = 0x8076;
    static final int GL_COLOR_ARRAY_BUFFER_BINDING = 0x8898;
    static final int GL_COLOR_ARRAY_POINTER = 0x8090;
    static final int GL_COLOR_ARRAY_SIZE = 0x8081;
    static final int GL_COLOR_ARRAY_STRIDE = 0x8083;
    static final int GL_COLOR_ARRAY_TYPE = 0x8082;
    static final int GL_COLOR_BUFFER_BIT = 0x4000;
    static final int GL_COLOR_CLEAR_VALUE = 0x0C22;
    static final int GL_COLOR_LOGIC_OP = 0x0BF2;
    static final int GL_COLOR_MATERIAL = 0x0B57;
    static final int GL_COLOR_WRITEMASK = 0x0C23;
    static final int GL_COMBINE = 0x8570;
    static final int GL_COMBINE_ALPHA = 0x8572;
    static final int GL_COMBINE_RGB = 0x8571;
    static final int GL_COMPRESSED_TEXTURE_FORMATS = 0x86A3;
    static final int GL_CONSTANT = 0x8576;
    static final int GL_CONSTANT_ATTENUATION = 0x1207;
    static final int GL_COORD_REPLACE_OES = 0x8862;
    static final int GL_COPY = 0x1503;
    static final int GL_COPY_INVERTED = 0x150C;
    static final int GL_CULL_FACE = 0x0B44;
    static final int GL_CULL_FACE_MODE = 0x0B45;
    static final int GL_CURRENT_COLOR = 0x0B00;
    static final int GL_CURRENT_NORMAL = 0x0B02;
    static final int GL_CURRENT_TEXTURE_COORDS = 0x0B03;
    static final int GL_CW = 0x0900;
    static final int GL_DECAL = 0x2101;
    static final int GL_DECR = 0x1E03;
    static final int GL_DEPTH_BITS = 0x0D56;
    static final int GL_DEPTH_BUFFER_BIT = 0x0100;
    static final int GL_DEPTH_CLEAR_VALUE = 0x0B73;
    static final int GL_DEPTH_FUNC = 0x0B74;
    static final int GL_DEPTH_RANGE = 0x0B70;
    static final int GL_DEPTH_TEST = 0x0B71;
    static final int GL_DEPTH_WRITEMASK = 0x0B72;
    static final int GL_DIFFUSE = 0x1201;
    static final int GL_DITHER = 0x0BD0;
    static final int GL_DONT_CARE = 0x1100;
    static final int GL_DOT3_RGB = 0x86AE;
    static final int GL_DOT3_RGBA = 0x86AF;
    static final int GL_DST_ALPHA = 0x0304;
    static final int GL_DST_COLOR = 0x0306;
    static final int GL_DYNAMIC_DRAW = 0x88E8;
    static final int GL_ELEMENT_ARRAY_BUFFER = 0x8893;
    static final int GL_ELEMENT_ARRAY_BUFFER_BINDING = 0x8895;
    static final int GL_EMISSION = 0x1600;
    static final int GL_EQUAL = 0x0202;
    static final int GL_EQUIV = 0x1509;
    static final int GL_EXP = 0x0800;
    static final int GL_EXP2 = 0x0801;
    static final int GL_EXTENSIONS = 0x1F03;
    static final int GL_FALSE = 0;
    static final int GL_FASTEST = 0x1101;
    static final int GL_FIXED = 0x140C;
    static final int GL_FLAT = 0x1D00;
    static final int GL_FLOAT = 0x1406;
    static final int GL_FOG = 0x0B60;
    static final int GL_FOG_COLOR = 0x0B66;
    static final int GL_FOG_DENSITY = 0x0B62;
    static final int GL_FOG_END = 0x0B64;
    static final int GL_FOG_HINT = 0x0C54;
    static final int GL_FOG_MODE = 0x0B65;
    static final int GL_FOG_START = 0x0B63;
    static final int GL_FRONT = 0x0404;
    static final int GL_FRONT_AND_BACK = 0x0408;
    static final int GL_FRONT_FACE = 0x0B46;
    static final int GL_GENERATE_MIPMAP = 0x8191;
    static final int GL_GENERATE_MIPMAP_HINT = 0x8192;
    static final int GL_GEQUAL = 0x0206;
    static final int GL_GREATER = 0x0204;
    static final int GL_GREEN_BITS = 0x0D53;
    static final int GL_IMPLEMENTATION_COLOR_READ_FORMAT_OES = 0x8B9B;
    static final int GL_IMPLEMENTATION_COLOR_READ_TYPE_OES = 0x8B9A;
    static final int GL_INCR = 0x1E02;
    static final int GL_INTERPOLATE = 0x8575;
    static final int GL_INVALID_ENUM = 0x0500;
    static final int GL_INVALID_OPERATION = 0x0502;
    static final int GL_INVALID_VALUE = 0x0501;
    static final int GL_INVERT = 0x150A;
    static final int GL_KEEP = 0x1E00;
    static final int GL_LEQUAL = 0x0203;
    static final int GL_LESS = 0x0201;
    static final int GL_LIGHT0 = 0x4000;
    static final int GL_LIGHT1 = 0x4001;
    static final int GL_LIGHT2 = 0x4002;
    static final int GL_LIGHT3 = 0x4003;
    static final int GL_LIGHT4 = 0x4004;
    static final int GL_LIGHT5 = 0x4005;
    static final int GL_LIGHT6 = 0x4006;
    static final int GL_LIGHT7 = 0x4007;
    static final int GL_LIGHTING = 0x0B50;
    static final int GL_LIGHT_MODEL_AMBIENT = 0x0B53;
    static final int GL_LIGHT_MODEL_TWO_SIDE = 0x0B52;
    static final int GL_LINEAR = 0x2601;
    static final int GL_LINEAR_ATTENUATION = 0x1208;
    static final int GL_LINEAR_MIPMAP_LINEAR = 0x2703;
    static final int GL_LINEAR_MIPMAP_NEAREST = 0x2701;
    static final int GL_LINES = 0x0001;
    static final int GL_LINE_LOOP = 0x0002;
    static final int GL_LINE_SMOOTH = 0x0B20;
    static final int GL_LINE_SMOOTH_HINT = 0x0C52;
    static final int GL_LINE_STRIP = 0x0003;
    static final int GL_LINE_WIDTH = 0x0B21;
    static final int GL_LOGIC_OP_MODE = 0x0BF0;
    static final int GL_LUMINANCE = 0x1909;
    static final int GL_LUMINANCE_ALPHA = 0x190A;
    static final int GL_MATRIX_MODE = 0x0BA0;
    static final int GL_MAX_CLIP_PLANES = 0x0D32;
    static final int GL_MAX_ELEMENTS_INDICES = 0x80E9;
    static final int GL_MAX_ELEMENTS_VERTICES = 0x80E8;
    static final int GL_MAX_LIGHTS = 0x0D31;
    static final int GL_MAX_MODELVIEW_STACK_DEPTH = 0x0D36;
    static final int GL_MAX_PROJECTION_STACK_DEPTH = 0x0D38;
    static final int GL_MAX_TEXTURE_SIZE = 0x0D33;
    static final int GL_MAX_TEXTURE_STACK_DEPTH = 0x0D39;
    static final int GL_MAX_TEXTURE_UNITS = 0x84E2;
    static final int GL_MAX_VIEWPORT_DIMS = 0x0D3A;
    static final int GL_MODELVIEW = 0x1700;
    static final int GL_MODELVIEW_MATRIX = 0x0BA6;
    static final int GL_MODELVIEW_MATRIX_FLOAT_AS_INT_BITS_OES = 0x898D;
    static final int GL_MODELVIEW_STACK_DEPTH = 0x0BA3;
    static final int GL_MODULATE = 0x2100;
    static final int GL_MULTISAMPLE = 0x809D;
    static final int GL_NAND = 0x150E;
    static final int GL_NEAREST = 0x2600;
    static final int GL_NEAREST_MIPMAP_LINEAR = 0x2702;
    static final int GL_NEAREST_MIPMAP_NEAREST = 0x2700;
    static final int GL_NEVER = 0x0200;
    static final int GL_NICEST = 0x1102;
    static final int GL_NOOP = 0x1505;
    static final int GL_NOR = 0x1508;
    static final int GL_NORMALIZE = 0x0BA1;
    static final int GL_NORMAL_ARRAY = 0x8075;
    static final int GL_NORMAL_ARRAY_BUFFER_BINDING = 0x8897;
    static final int GL_NORMAL_ARRAY_POINTER = 0x808F;
    static final int GL_NORMAL_ARRAY_STRIDE = 0x807F;
    static final int GL_NORMAL_ARRAY_TYPE = 0x807E;
    static final int GL_NOTEQUAL = 0x0205;
    static final int GL_NO_ERROR = 0;
    static final int GL_NUM_COMPRESSED_TEXTURE_FORMATS = 0x86A2;
    static final int GL_ONE = 1;
    static final int GL_ONE_MINUS_DST_ALPHA = 0x0305;
    static final int GL_ONE_MINUS_DST_COLOR = 0x0307;
    static final int GL_ONE_MINUS_SRC_ALPHA = 0x0303;
    static final int GL_ONE_MINUS_SRC_COLOR = 0x0301;
    static final int GL_OPERAND0_ALPHA = 0x8598;
    static final int GL_OPERAND0_RGB = 0x8590;
    static final int GL_OPERAND1_ALPHA = 0x8599;
    static final int GL_OPERAND1_RGB = 0x8591;
    static final int GL_OPERAND2_ALPHA = 0x859A;
    static final int GL_OPERAND2_RGB = 0x8592;
    static final int GL_OR = 0x1507;
    static final int GL_OR_INVERTED = 0x150D;
    static final int GL_OR_REVERSE = 0x150B;
    static final int GL_OUT_OF_MEMORY = 0x0505;
    static final int GL_PACK_ALIGNMENT = 0x0D05;
    static final int GL_PALETTE4_R5_G6_B5_OES = 0x8B92;
    static final int GL_PALETTE4_RGB5_A1_OES = 0x8B94;
    static final int GL_PALETTE4_RGB8_OES = 0x8B90;
    static final int GL_PALETTE4_RGBA4_OES = 0x8B93;
    static final int GL_PALETTE4_RGBA8_OES = 0x8B91;
    static final int GL_PALETTE8_R5_G6_B5_OES = 0x8B97;
    static final int GL_PALETTE8_RGB5_A1_OES = 0x8B99;
    static final int GL_PALETTE8_RGB8_OES = 0x8B95;
    static final int GL_PALETTE8_RGBA4_OES = 0x8B98;
    static final int GL_PALETTE8_RGBA8_OES = 0x8B96;
    static final int GL_PERSPECTIVE_CORRECTION_HINT = 0x0C50;
    static final int GL_POINTS = 0x0000;
    static final int GL_POINT_DISTANCE_ATTENUATION = 0x8129;
    static final int GL_POINT_FADE_THRESHOLD_SIZE = 0x8128;
    static final int GL_POINT_SIZE = 0x0B11;
    static final int GL_POINT_SIZE_ARRAY_BUFFER_BINDING_OES = 0x8B9F;
    static final int GL_POINT_SIZE_ARRAY_OES = 0x8B9C;
    static final int GL_POINT_SIZE_ARRAY_POINTER_OES = 0x898C;
    static final int GL_POINT_SIZE_ARRAY_STRIDE_OES = 0x898B;
    static final int GL_POINT_SIZE_ARRAY_TYPE_OES = 0x898A;
    static final int GL_POINT_SIZE_MAX = 0x8127;
    static final int GL_POINT_SIZE_MIN = 0x8126;
    static final int GL_POINT_SMOOTH = 0x0B10;
    static final int GL_POINT_SMOOTH_HINT = 0x0C51;
    static final int GL_POINT_SPRITE_OES = 0x8861;
    static final int GL_POLYGON_OFFSET_FACTOR = 0x8038;
    static final int GL_POLYGON_OFFSET_FILL = 0x8037;
    static final int GL_POLYGON_OFFSET_UNITS = 0x2A00;
    static final int GL_POLYGON_SMOOTH_HINT = 0x0C53;
    static final int GL_POSITION = 0x1203;
    static final int GL_PREVIOUS = 0x8578;
    static final int GL_PRIMARY_COLOR = 0x8577;
    static final int GL_PROJECTION = 0x1701;
    static final int GL_PROJECTION_MATRIX = 0x0BA7;
    static final int GL_PROJECTION_MATRIX_FLOAT_AS_INT_BITS_OES = 0x898E;
    static final int GL_PROJECTION_STACK_DEPTH = 0x0BA4;
    static final int GL_QUADRATIC_ATTENUATION = 0x1209;
    static final int GL_RED_BITS = 0x0D52;
    static final int GL_RENDERER = 0x1F01;
    static final int GL_REPEAT = 0x2901;
    static final int GL_REPLACE = 0x1E01;
    static final int GL_RESCALE_NORMAL = 0x803A;
    static final int GL_RGB = 0x1907;
    static final int GL_RGBA = 0x1908;
    static final int GL_RGB_SCALE = 0x8573;
    static final int GL_SAMPLES = 0x80A9;
    static final int GL_SAMPLE_ALPHA_TO_COVERAGE = 0x809E;
    static final int GL_SAMPLE_ALPHA_TO_ONE = 0x809F;
    static final int GL_SAMPLE_BUFFERS = 0x80A8;
    static final int GL_SAMPLE_COVERAGE = 0x80A0;
    static final int GL_SAMPLE_COVERAGE_INVERT = 0x80AB;
    static final int GL_SAMPLE_COVERAGE_VALUE = 0x80AA;
    static final int GL_SCISSOR_BOX = 0x0C10;
    static final int GL_SCISSOR_TEST = 0x0C11;
    static final int GL_SET = 0x150F;
    static final int GL_SHADE_MODEL = 0x0B54;
    static final int GL_SHININESS = 0x1601;
    static final int GL_SHORT = 0x1402;
    static final int GL_SMOOTH = 0x1D01;
    static final int GL_SMOOTH_LINE_WIDTH_RANGE = 0x0B22;
    static final int GL_SMOOTH_POINT_SIZE_RANGE = 0x0B12;
    static final int GL_SPECULAR = 0x1202;
    static final int GL_SPOT_CUTOFF = 0x1206;
    static final int GL_SPOT_DIRECTION = 0x1204;
    static final int GL_SPOT_EXPONENT = 0x1205;
    static final int GL_SRC0_ALPHA = 0x8588;
    static final int GL_SRC0_RGB = 0x8580;
    static final int GL_SRC1_ALPHA = 0x8589;
    static final int GL_SRC1_RGB = 0x8581;
    static final int GL_SRC2_ALPHA = 0x858A;
    static final int GL_SRC2_RGB = 0x8582;
    static final int GL_SRC_ALPHA = 0x0302;
    static final int GL_SRC_ALPHA_SATURATE = 0x0308;
    static final int GL_SRC_COLOR = 0x0300;
    static final int GL_STACK_OVERFLOW = 0x0503;
    static final int GL_STACK_UNDERFLOW = 0x0504;
    static final int GL_STATIC_DRAW = 0x88E4;
    static final int GL_STENCIL_BITS = 0x0D57;
    static final int GL_STENCIL_BUFFER_BIT = 0x0400;
    static final int GL_STENCIL_CLEAR_VALUE = 0x0B91;
    static final int GL_STENCIL_FAIL = 0x0B94;
    static final int GL_STENCIL_FUNC = 0x0B92;
    static final int GL_STENCIL_PASS_DEPTH_FAIL = 0x0B95;
    static final int GL_STENCIL_PASS_DEPTH_PASS = 0x0B96;
    static final int GL_STENCIL_REF = 0x0B97;
    static final int GL_STENCIL_TEST = 0x0B90;
    static final int GL_STENCIL_VALUE_MASK = 0x0B93;
    static final int GL_STENCIL_WRITEMASK = 0x0B98;
    static final int GL_SUBPIXEL_BITS = 0x0D50;
    static final int GL_SUBTRACT = 0x84E7;
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
    static final int GL_TEXTURE_COORD_ARRAY = 0x8078;
    static final int GL_TEXTURE_COORD_ARRAY_BUFFER_BINDING = 0x889A;
    static final int GL_TEXTURE_COORD_ARRAY_POINTER = 0x8092;
    static final int GL_TEXTURE_COORD_ARRAY_SIZE = 0x8088;
    static final int GL_TEXTURE_COORD_ARRAY_STRIDE = 0x808A;
    static final int GL_TEXTURE_COORD_ARRAY_TYPE = 0x8089;
    static final int GL_TEXTURE_ENV = 0x2300;
    static final int GL_TEXTURE_ENV_COLOR = 0x2201;
    static final int GL_TEXTURE_ENV_MODE = 0x2200;
    static final int GL_TEXTURE_MAG_FILTER = 0x2800;
    static final int GL_TEXTURE_MATRIX = 0x0BA8;
    static final int GL_TEXTURE_MATRIX_FLOAT_AS_INT_BITS_OES = 0x898F;
    static final int GL_TEXTURE_MIN_FILTER = 0x2801;
    static final int GL_TEXTURE_STACK_DEPTH = 0x0BA5;
    static final int GL_TEXTURE_WRAP_S = 0x2802;
    static final int GL_TEXTURE_WRAP_T = 0x2803;
    static final int GL_TRIANGLES = 0x0004;
    static final int GL_TRIANGLE_FAN = 0x0006;
    static final int GL_TRIANGLE_STRIP = 0x0005;
    static final int GL_TRUE = 1;
    static final int GL_UNPACK_ALIGNMENT = 0x0CF5;
    static final int GL_UNSIGNED_BYTE = 0x1401;
    static final int GL_UNSIGNED_SHORT = 0x1403;
    static final int GL_UNSIGNED_SHORT_4_4_4_4 = 0x8033;
    static final int GL_UNSIGNED_SHORT_5_5_5_1 = 0x8034;
    static final int GL_UNSIGNED_SHORT_5_6_5 = 0x8363;
    static final int GL_VENDOR = 0x1F00;
    static final int GL_VERSION = 0x1F02;
    static final int GL_VERTEX_ARRAY = 0x8074;
    static final int GL_VERTEX_ARRAY_BUFFER_BINDING = 0x8896;
    static final int GL_VERTEX_ARRAY_POINTER = 0x808E;
    static final int GL_VERTEX_ARRAY_SIZE = 0x807A;
    static final int GL_VERTEX_ARRAY_STRIDE = 0x807C;
    static final int GL_VERTEX_ARRAY_TYPE = 0x807B;
    static final int GL_VIEWPORT = 0x0BA2;
    static final int GL_WRITE_ONLY = 0x88B9;
    static final int GL_XOR = 0x1506;
    static final int GL_ZERO = 0;


    void glBindBuffer(int target, int buffer);
    void glBufferData(int target, ByteBuffer data, int usage);
    void glBufferData(int target, DoubleBuffer data, int usage);
    void glBufferData(int target, FloatBuffer data, int usage);
    void glBufferData(int target, IntBuffer data, int usage);
    void glBufferSubData(int target, int offset, int size, IntBuffer data);
    void glBufferSubData(int target, int offset, int size, ByteBuffer data);
    void glBufferSubData(int target, int offset, int size, DoubleBuffer data);
    void glBufferSubData(int target, int offset, int size, FloatBuffer data);

    void glClear(int mask);
    void glClearColor(float red, float green, float blue, float alpha);

    void glDisable(int cap);
    void glDrawElements(int mode, int count, int type, ByteBuffer indices);
    void glDrawElements(int mode, int count, int type, int offset);
    void glDrawArrays(int mode, int first, int count);

    void glEnable(int cap);

    void glGenBuffers(IntBuffer buffers);

    void glLineWidth(float width);

}

