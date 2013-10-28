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

package com.spidey01.sxe.core.gl;

import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.DoubleBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;

/** OpenGL Interface for Embedded Systems v3.0 */
public interface OpenGLES30 extends OpenGLES20 {

    static final int GL_ACTIVE_UNIFORM_BLOCKS = 0x8A36;
    static final int GL_ACTIVE_UNIFORM_BLOCK_MAX_NAME_LENGTH = 0x8A35;
    static final int GL_ALREADY_SIGNALED = 0x911A;
    static final int GL_ANY_SAMPLES_PASSED = 0x8C2F;
    static final int GL_ANY_SAMPLES_PASSED_CONSERVATIVE = 0x8D6A;
    static final int GL_BLUE = 0x1905;
    static final int GL_BUFFER_ACCESS_FLAGS = 0x911F;
    static final int GL_BUFFER_MAPPED = 0x88BC;
    static final int GL_BUFFER_MAP_LENGTH = 0x9120;
    static final int GL_BUFFER_MAP_OFFSET = 0x9121;
    static final int GL_BUFFER_MAP_POINTER = 0x88BD;
    static final int GL_COLOR = 0x1800;
    static final int GL_COLOR_ATTACHMENT1 = 0x8CE1;
    static final int GL_COLOR_ATTACHMENT10 = 0x8CEA;
    static final int GL_COLOR_ATTACHMENT11 = 0x8CEB;
    static final int GL_COLOR_ATTACHMENT12 = 0x8CEC;
    static final int GL_COLOR_ATTACHMENT13 = 0x8CED;
    static final int GL_COLOR_ATTACHMENT14 = 0x8CEE;
    static final int GL_COLOR_ATTACHMENT15 = 0x8CEF;
    static final int GL_COLOR_ATTACHMENT2 = 0x8CE2;
    static final int GL_COLOR_ATTACHMENT3 = 0x8CE3;
    static final int GL_COLOR_ATTACHMENT4 = 0x8CE4;
    static final int GL_COLOR_ATTACHMENT5 = 0x8CE5;
    static final int GL_COLOR_ATTACHMENT6 = 0x8CE6;
    static final int GL_COLOR_ATTACHMENT7 = 0x8CE7;
    static final int GL_COLOR_ATTACHMENT8 = 0x8CE8;
    static final int GL_COLOR_ATTACHMENT9 = 0x8CE9;
    static final int GL_COMPARE_REF_TO_TEXTURE = 0x884E;
    static final int GL_COMPRESSED_R11_EAC = 0x9270;
    static final int GL_COMPRESSED_RG11_EAC = 0x9272;
    static final int GL_COMPRESSED_RGB8_ETC2 = 0x9274;
    static final int GL_COMPRESSED_RGB8_PUNCHTHROUGH_ALPHA1_ETC2 = 0x9276;
    static final int GL_COMPRESSED_RGBA8_ETC2_EAC = 0x9278;
    static final int GL_COMPRESSED_SIGNED_R11_EAC = 0x9271;
    static final int GL_COMPRESSED_SIGNED_RG11_EAC = 0x9273;
    static final int GL_COMPRESSED_SRGB8_ALPHA8_ETC2_EAC = 0x9279;
    static final int GL_COMPRESSED_SRGB8_ETC2 = 0x9275;
    static final int GL_COMPRESSED_SRGB8_PUNCHTHROUGH_ALPHA1_ETC2 = 0x9277;
    static final int GL_CONDITION_SATISFIED = 0x911C;
    static final int GL_COPY_READ_BUFFER = 0x8F36;
    static final int GL_COPY_READ_BUFFER_BINDING = GL_COPY_READ_BUFFER;
    static final int GL_COPY_WRITE_BUFFER = 0x8F37;
    static final int GL_COPY_WRITE_BUFFER_BINDING = GL_COPY_WRITE_BUFFER;
    static final int GL_CURRENT_QUERY = 0x8865;
    static final int GL_DEPTH = 0x1801;
    static final int GL_DEPTH24_STENCIL8 = 0x88F0;
    static final int GL_DEPTH32F_STENCIL8 = 0x8CAD;
    static final int GL_DEPTH_COMPONENT24 = 0x81A6;
    static final int GL_DEPTH_COMPONENT32F = 0x8CAC;
    static final int GL_DEPTH_STENCIL = 0x84F9;
    static final int GL_DEPTH_STENCIL_ATTACHMENT = 0x821A;
    static final int GL_DRAW_BUFFER0 = 0x8825;
    static final int GL_DRAW_BUFFER1 = 0x8826;
    static final int GL_DRAW_BUFFER10 = 0x882F;
    static final int GL_DRAW_BUFFER11 = 0x8830;
    static final int GL_DRAW_BUFFER12 = 0x8831;
    static final int GL_DRAW_BUFFER13 = 0x8832;
    static final int GL_DRAW_BUFFER14 = 0x8833;
    static final int GL_DRAW_BUFFER15 = 0x8834;
    static final int GL_DRAW_BUFFER2 = 0x8827;
    static final int GL_DRAW_BUFFER3 = 0x8828;
    static final int GL_DRAW_BUFFER4 = 0x8829;
    static final int GL_DRAW_BUFFER5 = 0x882A;
    static final int GL_DRAW_BUFFER6 = 0x882B;
    static final int GL_DRAW_BUFFER7 = 0x882C;
    static final int GL_DRAW_BUFFER8 = 0x882D;
    static final int GL_DRAW_BUFFER9 = 0x882E;
    static final int GL_DRAW_FRAMEBUFFER = 0x8CA9;
    static final int GL_DRAW_FRAMEBUFFER_BINDING = GL_FRAMEBUFFER_BINDING;
    static final int GL_DYNAMIC_COPY = 0x88EA;
    static final int GL_DYNAMIC_READ = 0x88E9;
    static final int GL_FLOAT_32_UNSIGNED_INT_24_8_REV = 0x8DAD;
    static final int GL_FLOAT_MAT2x3 = 0x8B65;
    static final int GL_FLOAT_MAT2x4 = 0x8B66;
    static final int GL_FLOAT_MAT3x2 = 0x8B67;
    static final int GL_FLOAT_MAT3x4 = 0x8B68;
    static final int GL_FLOAT_MAT4x2 = 0x8B69;
    static final int GL_FLOAT_MAT4x3 = 0x8B6A;
    static final int GL_FRAGMENT_SHADER_DERIVATIVE_HINT = 0x8B8B;
    static final int GL_FRAMEBUFFER_ATTACHMENT_ALPHA_SIZE = 0x8215;
    static final int GL_FRAMEBUFFER_ATTACHMENT_BLUE_SIZE = 0x8214;
    static final int GL_FRAMEBUFFER_ATTACHMENT_COLOR_ENCODING = 0x8210;
    static final int GL_FRAMEBUFFER_ATTACHMENT_COMPONENT_TYPE = 0x8211;
    static final int GL_FRAMEBUFFER_ATTACHMENT_DEPTH_SIZE = 0x8216;
    static final int GL_FRAMEBUFFER_ATTACHMENT_GREEN_SIZE = 0x8213;
    static final int GL_FRAMEBUFFER_ATTACHMENT_RED_SIZE = 0x8212;
    static final int GL_FRAMEBUFFER_ATTACHMENT_STENCIL_SIZE = 0x8217;
    static final int GL_FRAMEBUFFER_ATTACHMENT_TEXTURE_LAYER = 0x8CD4;
    static final int GL_FRAMEBUFFER_DEFAULT = 0x8218;
    static final int GL_FRAMEBUFFER_INCOMPLETE_MULTISAMPLE = 0x8D56;
    static final int GL_FRAMEBUFFER_UNDEFINED = 0x8219;
    static final int GL_GREEN = 0x1904;
    static final int GL_HALF_FLOAT = 0x140B;
    static final int GL_INTERLEAVED_ATTRIBS = 0x8C8C;
    static final int GL_INT_2_10_10_10_REV = 0x8D9F;
    static final int GL_INT_SAMPLER_2D = 0x8DCA;
    static final int GL_INT_SAMPLER_2D_ARRAY = 0x8DCF;
    static final int GL_INT_SAMPLER_3D = 0x8DCB;
    static final int GL_INT_SAMPLER_CUBE = 0x8DCC;
    static final int GL_INVALID_INDEX = -1;
    static final int GL_MAJOR_VERSION = 0x821B;
    static final int GL_MAP_FLUSH_EXPLICIT_BIT = 0x0010;
    static final int GL_MAP_INVALIDATE_BUFFER_BIT = 0x0008;
    static final int GL_MAP_INVALIDATE_RANGE_BIT = 0x0004;
    static final int GL_MAP_READ_BIT = 0x0001;
    static final int GL_MAP_UNSYNCHRONIZED_BIT = 0x0020;
    static final int GL_MAP_WRITE_BIT = 0x0002;
    static final int GL_MAX = 0x8008;
    static final int GL_MAX_3D_TEXTURE_SIZE = 0x8073;
    static final int GL_MAX_ARRAY_TEXTURE_LAYERS = 0x88FF;
    static final int GL_MAX_COLOR_ATTACHMENTS = 0x8CDF;
    static final int GL_MAX_COMBINED_FRAGMENT_UNIFORM_COMPONENTS = 0x8A33;
    static final int GL_MAX_COMBINED_UNIFORM_BLOCKS = 0x8A2E;
    static final int GL_MAX_COMBINED_VERTEX_UNIFORM_COMPONENTS = 0x8A31;
    static final int GL_MAX_DRAW_BUFFERS = 0x8824;
    static final int GL_MAX_ELEMENTS_INDICES = 0x80E9;
    static final int GL_MAX_ELEMENTS_VERTICES = 0x80E8;
    static final int GL_MAX_ELEMENT_INDEX = 0x8D6B;
    static final int GL_MAX_FRAGMENT_INPUT_COMPONENTS = 0x9125;
    static final int GL_MAX_FRAGMENT_UNIFORM_BLOCKS = 0x8A2D;
    static final int GL_MAX_FRAGMENT_UNIFORM_COMPONENTS = 0x8B49;
    static final int GL_MAX_PROGRAM_TEXEL_OFFSET = 0x8905;
    static final int GL_MAX_SAMPLES = 0x8D57;
    static final int GL_MAX_SERVER_WAIT_TIMEOUT = 0x9111;
    static final int GL_MAX_TEXTURE_LOD_BIAS = 0x84FD;
    static final int GL_MAX_TRANSFORM_FEEDBACK_INTERLEAVED_COMPONENTS = 0x8C8A;
    static final int GL_MAX_TRANSFORM_FEEDBACK_SEPARATE_ATTRIBS = 0x8C8B;
    static final int GL_MAX_TRANSFORM_FEEDBACK_SEPARATE_COMPONENTS = 0x8C80;
    static final int GL_MAX_UNIFORM_BLOCK_SIZE = 0x8A30;
    static final int GL_MAX_UNIFORM_BUFFER_BINDINGS = 0x8A2F;
    static final int GL_MAX_VARYING_COMPONENTS = 0x8B4B;
    static final int GL_MAX_VERTEX_OUTPUT_COMPONENTS = 0x9122;
    static final int GL_MAX_VERTEX_UNIFORM_BLOCKS = 0x8A2B;
    static final int GL_MAX_VERTEX_UNIFORM_COMPONENTS = 0x8B4A;
    static final int GL_MIN = 0x8007;
    static final int GL_MINOR_VERSION = 0x821C;
    static final int GL_MIN_PROGRAM_TEXEL_OFFSET = 0x8904;
    static final int GL_NUM_EXTENSIONS = 0x821D;
    static final int GL_NUM_PROGRAM_BINARY_FORMATS = 0x87FE;
    static final int GL_NUM_SAMPLE_COUNTS = 0x9380;
    static final int GL_OBJECT_TYPE = 0x9112;
    static final int GL_PACK_ROW_LENGTH = 0x0D02;
    static final int GL_PACK_SKIP_PIXELS = 0x0D04;
    static final int GL_PACK_SKIP_ROWS = 0x0D03;
    static final int GL_PIXEL_PACK_BUFFER = 0x88EB;
    static final int GL_PIXEL_PACK_BUFFER_BINDING = 0x88ED;
    static final int GL_PIXEL_UNPACK_BUFFER = 0x88EC;
    static final int GL_PIXEL_UNPACK_BUFFER_BINDING = 0x88EF;
    static final int GL_PRIMITIVE_RESTART_FIXED_INDEX = 0x8D69;
    static final int GL_PROGRAM_BINARY_FORMATS = 0x87FF;
    static final int GL_PROGRAM_BINARY_LENGTH = 0x8741;
    static final int GL_PROGRAM_BINARY_RETRIEVABLE_HINT = 0x8257;
    static final int GL_QUERY_RESULT = 0x8866;
    static final int GL_QUERY_RESULT_AVAILABLE = 0x8867;
    static final int GL_R11F_G11F_B10F = 0x8C3A;
    static final int GL_R16F = 0x822D;
    static final int GL_R16I = 0x8233;
    static final int GL_R16UI = 0x8234;
    static final int GL_R32F = 0x822E;
    static final int GL_R32I = 0x8235;
    static final int GL_R32UI = 0x8236;
    static final int GL_R8 = 0x8229;
    static final int GL_R8I = 0x8231;
    static final int GL_R8UI = 0x8232;
    static final int GL_R8_SNORM = 0x8F94;
    static final int GL_RASTERIZER_DISCARD = 0x8C89;
    static final int GL_READ_BUFFER = 0x0C02;
    static final int GL_READ_FRAMEBUFFER = 0x8CA8;
    static final int GL_READ_FRAMEBUFFER_BINDING = 0x8CAA;
    static final int GL_RED = 0x1903;
    static final int GL_RED_INTEGER = 0x8D94;
    static final int GL_RENDERBUFFER_SAMPLES = 0x8CAB;
    static final int GL_RG = 0x8227;
    static final int GL_RG16F = 0x822F;
    static final int GL_RG16I = 0x8239;
    static final int GL_RG16UI = 0x823A;
    static final int GL_RG32F = 0x8230;
    static final int GL_RG32I = 0x823B;
    static final int GL_RG32UI = 0x823C;
    static final int GL_RG8 = 0x822B;
    static final int GL_RG8I = 0x8237;
    static final int GL_RG8UI = 0x8238;
    static final int GL_RG8_SNORM = 0x8F95;
    static final int GL_RGB10_A2 = 0x8059;
    static final int GL_RGB10_A2UI = 0x906F;
    static final int GL_RGB16F = 0x881B;
    static final int GL_RGB16I = 0x8D89;
    static final int GL_RGB16UI = 0x8D77;
    static final int GL_RGB32F = 0x8815;
    static final int GL_RGB32I = 0x8D83;
    static final int GL_RGB32UI = 0x8D71;
    static final int GL_RGB8 = 0x8051;
    static final int GL_RGB8I = 0x8D8F;
    static final int GL_RGB8UI = 0x8D7D;
    static final int GL_RGB8_SNORM = 0x8F96;
    static final int GL_RGB9_E5 = 0x8C3D;
    static final int GL_RGBA16F = 0x881A;
    static final int GL_RGBA16I = 0x8D88;
    static final int GL_RGBA16UI = 0x8D76;
    static final int GL_RGBA32F = 0x8814;
    static final int GL_RGBA32I = 0x8D82;
    static final int GL_RGBA32UI = 0x8D70;
    static final int GL_RGBA8 = 0x8058;
    static final int GL_RGBA8I = 0x8D8E;
    static final int GL_RGBA8UI = 0x8D7C;
    static final int GL_RGBA8_SNORM = 0x8F97;
    static final int GL_RGBA_INTEGER = 0x8D99;
    static final int GL_RGB_INTEGER = 0x8D98;
    static final int GL_RG_INTEGER = 0x8228;
    static final int GL_SAMPLER_2D_ARRAY = 0x8DC1;
    static final int GL_SAMPLER_2D_ARRAY_SHADOW = 0x8DC4;
    static final int GL_SAMPLER_2D_SHADOW = 0x8B62;
    static final int GL_SAMPLER_3D = 0x8B5F;
    static final int GL_SAMPLER_BINDING = 0x8919;
    static final int GL_SAMPLER_CUBE_SHADOW = 0x8DC5;
    static final int GL_SEPARATE_ATTRIBS = 0x8C8D;
    static final int GL_SIGNALED = 0x9119;
    static final int GL_SIGNED_NORMALIZED = 0x8F9C;
    static final int GL_SRGB = 0x8C40;
    static final int GL_SRGB8 = 0x8C41;
    static final int GL_SRGB8_ALPHA8 = 0x8C43;
    static final int GL_STATIC_COPY = 0x88E6;
    static final int GL_STATIC_READ = 0x88E5;
    static final int GL_STENCIL = 0x1802;
    static final int GL_STREAM_COPY = 0x88E2;
    static final int GL_STREAM_READ = 0x88E1;
    static final int GL_SYNC_CONDITION = 0x9113;
    static final int GL_SYNC_FENCE = 0x9116;
    static final int GL_SYNC_FLAGS = 0x9115;
    static final int GL_SYNC_FLUSH_COMMANDS_BIT = 0x00000001;
    static final int GL_SYNC_GPU_COMMANDS_COMPLETE = 0x9117;
    static final int GL_SYNC_STATUS = 0x9114;
    static final int GL_TEXTURE_2D_ARRAY = 0x8C1A;
    static final int GL_TEXTURE_3D = 0x806F;
    static final int GL_TEXTURE_BASE_LEVEL = 0x813C;
    static final int GL_TEXTURE_BINDING_2D_ARRAY = 0x8C1D;
    static final int GL_TEXTURE_BINDING_3D = 0x806A;
    static final int GL_TEXTURE_COMPARE_FUNC = 0x884D;
    static final int GL_TEXTURE_COMPARE_MODE = 0x884C;
    static final int GL_TEXTURE_IMMUTABLE_FORMAT = 0x912F;
    static final int GL_TEXTURE_IMMUTABLE_LEVELS = 0x82DF;
    static final int GL_TEXTURE_MAX_LEVEL = 0x813D;
    static final int GL_TEXTURE_MAX_LOD = 0x813B;
    static final int GL_TEXTURE_MIN_LOD = 0x813A;
    static final int GL_TEXTURE_SWIZZLE_A = 0x8E45;
    static final int GL_TEXTURE_SWIZZLE_B = 0x8E44;
    static final int GL_TEXTURE_SWIZZLE_G = 0x8E43;
    static final int GL_TEXTURE_SWIZZLE_R = 0x8E42;
    static final int GL_TEXTURE_WRAP_R = 0x8072;
    static final int GL_TIMEOUT_EXPIRED = 0x911B;
    static final int GL_TRANSFORM_FEEDBACK = 0x8E22;
    static final int GL_TRANSFORM_FEEDBACK_ACTIVE = 0x8E24;
    static final int GL_TRANSFORM_FEEDBACK_BINDING = 0x8E25;
    static final int GL_TRANSFORM_FEEDBACK_BUFFER = 0x8C8E;
    static final int GL_TRANSFORM_FEEDBACK_BUFFER_BINDING = 0x8C8F;
    static final int GL_TRANSFORM_FEEDBACK_BUFFER_MODE = 0x8C7F;
    static final int GL_TRANSFORM_FEEDBACK_BUFFER_SIZE = 0x8C85;
    static final int GL_TRANSFORM_FEEDBACK_BUFFER_START = 0x8C84;
    static final int GL_TRANSFORM_FEEDBACK_PAUSED = 0x8E23;
    static final int GL_TRANSFORM_FEEDBACK_PRIMITIVES_WRITTEN = 0x8C88;
    static final int GL_TRANSFORM_FEEDBACK_VARYINGS = 0x8C83;
    static final int GL_TRANSFORM_FEEDBACK_VARYING_MAX_LENGTH = 0x8C76;
    static final int GL_UNIFORM_ARRAY_STRIDE = 0x8A3C;
    static final int GL_UNIFORM_BLOCK_ACTIVE_UNIFORMS = 0x8A42;
    static final int GL_UNIFORM_BLOCK_ACTIVE_UNIFORM_INDICES = 0x8A43;
    static final int GL_UNIFORM_BLOCK_BINDING = 0x8A3F;
    static final int GL_UNIFORM_BLOCK_DATA_SIZE = 0x8A40;
    static final int GL_UNIFORM_BLOCK_INDEX = 0x8A3A;
    static final int GL_UNIFORM_BLOCK_NAME_LENGTH = 0x8A41;
    static final int GL_UNIFORM_BLOCK_REFERENCED_BY_FRAGMENT_SHADER = 0x8A46;
    static final int GL_UNIFORM_BLOCK_REFERENCED_BY_VERTEX_SHADER = 0x8A44;
    static final int GL_UNIFORM_BUFFER = 0x8A11;
    static final int GL_UNIFORM_BUFFER_BINDING = 0x8A28;
    static final int GL_UNIFORM_BUFFER_OFFSET_ALIGNMENT = 0x8A34;
    static final int GL_UNIFORM_BUFFER_SIZE = 0x8A2A;
    static final int GL_UNIFORM_BUFFER_START = 0x8A29;
    static final int GL_UNIFORM_IS_ROW_MAJOR = 0x8A3E;
    static final int GL_UNIFORM_MATRIX_STRIDE = 0x8A3D;
    static final int GL_UNIFORM_NAME_LENGTH = 0x8A39;
    static final int GL_UNIFORM_OFFSET = 0x8A3B;
    static final int GL_UNIFORM_SIZE = 0x8A38;
    static final int GL_UNIFORM_TYPE = 0x8A37;
    static final int GL_UNPACK_IMAGE_HEIGHT = 0x806E;
    static final int GL_UNPACK_ROW_LENGTH = 0x0CF2;
    static final int GL_UNPACK_SKIP_IMAGES = 0x806D;
    static final int GL_UNPACK_SKIP_PIXELS = 0x0CF4;
    static final int GL_UNPACK_SKIP_ROWS = 0x0CF3;
    static final int GL_UNSIGNALED = 0x9118;
    static final int GL_UNSIGNED_INT_10F_11F_11F_REV = 0x8C3B;
    static final int GL_UNSIGNED_INT_24_8 = 0x84FA;
    static final int GL_UNSIGNED_INT_2_10_10_10_REV = 0x8368;
    static final int GL_UNSIGNED_INT_5_9_9_9_REV = 0x8C3E;
    static final int GL_UNSIGNED_INT_SAMPLER_2D = 0x8DD2;
    static final int GL_UNSIGNED_INT_SAMPLER_2D_ARRAY = 0x8DD7;
    static final int GL_UNSIGNED_INT_SAMPLER_3D = 0x8DD3;
    static final int GL_UNSIGNED_INT_SAMPLER_CUBE = 0x8DD4;
    static final int GL_UNSIGNED_INT_VEC2 = 0x8DC6;
    static final int GL_UNSIGNED_INT_VEC3 = 0x8DC7;
    static final int GL_UNSIGNED_INT_VEC4 = 0x8DC8;
    static final int GL_UNSIGNED_NORMALIZED = 0x8C17;
    static final int GL_VERTEX_ARRAY_BINDING = 0x85B5;
    static final int GL_VERTEX_ATTRIB_ARRAY_DIVISOR = 0x88FE;
    static final int GL_VERTEX_ATTRIB_ARRAY_INTEGER = 0x88FD;
    static final int GL_WAIT_FAILED = 0x911D;
    static final long GL_TIMEOUT_IGNORED = -1;

}
