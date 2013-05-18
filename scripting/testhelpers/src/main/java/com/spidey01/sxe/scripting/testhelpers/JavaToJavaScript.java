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

package com.spidey01.sxe.scripting.testhelpers;

import org.junit.*;

import com.spidey01.sxe.core.Log;

import com.spidey01.sxe.scripting.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

public class JavaToJavaScript {
    private static final String TAG = "JavaToJavaScript";


    public static void simple(ScriptManager manager) throws IOException, FileNotFoundException {
        manager.eval(manager.createScript(), new File("JavaWrapper.js"));
    }


    public static void get(ScriptManager manager) {
        Script script = manager.createScript();
        int x = (Integer)manager.eval(script, "x = 7;");
        Assert.assertEquals(x, manager.get(script, "x"));
    }


    public static void put(ScriptManager manager) {
        Script script = manager.createScript();
        JavaClass javaClass = new JavaClass();

        manager.put(script, "out", System.out);
        manager.eval(script, "out.println('fuck you!');");

        manager.put(script, "javaClass", javaClass);
        Assert.assertTrue((Boolean)manager.eval(script, "javaClass.returnsTrue();"));
        int sum = (Integer)manager.eval(script, "javaClass.returnsSum(2, 2);");
        Assert.assertEquals(4, sum);
        String foobar = String.valueOf(manager.eval(script, "javaClass.returnsStrCat('foo', 'bar');"));
        Assert.assertEquals("foobar", foobar);
    }
}

