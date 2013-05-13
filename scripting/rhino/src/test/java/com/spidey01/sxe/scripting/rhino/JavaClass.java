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

/* no package here: for extra seperation */
package com.spidey01.sxe.scripting.rhino;

import com.spidey01.sxe.core.UnitTest;
import com.spidey01.sxe.core.Log;

public class JavaClass {
    private static final String TAG = "JavaToRhino.JavaClass";


    public JavaClass() {
        Log.i(TAG, "Constructor fired.");
    }


    public boolean returnsTrue() {
        Log.i(TAG, "ReturnsTrue fired.");
        return true;
    }


    public int returnsSum(int a, int b) {
        Log.i(TAG, "ReturnsSum("+a+", "+b+") fired.");
        return a+b;
    }


    public String returnsStrCat(String a, String b) {
        Log.i(TAG, "ReturnsStrCat("+a+", "+b+") fired.");
        return a + b;
    }


}