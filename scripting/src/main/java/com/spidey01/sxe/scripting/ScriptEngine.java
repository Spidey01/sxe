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

package com.spidey01.sxe.scripting;

import com.spidey01.sxe.core.Log;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

public interface ScriptEngine {

    Script createScript();

    Object eval(Script scope, String sourceCode);

    Object eval(Script scope, File sourceFile) throws IOException, FileNotFoundException;

    Object get(Script scope, String variable);

    /** Export Java value to script language.
     *
     * Types will be mapped as expected. Things like Strings , booleans, etc.
     * Exact representation of numeric data on the other hand is implementation
     * defined. Please see the corrisponding documentation for the
     * implementation.
     *
     * @param variable name used in scripting language.
     * @param value the java object referred to by variable.
     * @return ...
     */
    Object put(Script scope, String variable, Object value);
}

