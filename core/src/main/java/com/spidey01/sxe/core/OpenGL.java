/*-
 * Copyright (c) 2012-current, Terry Mathew Poulin <BigBoss1964@gmail.com>
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

/** Working set of OpenGL functions.
 *
 * This should provide access to the fixed and programmable pipelines where
 * available. Implementations may throw an unchecked exception if OpenGL legacy
 * / depreciated functionality is unavailable.
 *
 * Attempting to intermix pipelines in a bad way may but is not required to
 * throw an IllegalStateException.
 *
 * In most cases unless a method is intended to be free to use anything it
 * wants (such as for ease of testing), you should probably write methods to
 * use the appropriate type.
 */
public interface OpenGL extends OpenGLES11, OpenGLES20, OpenGLES30 {
}

