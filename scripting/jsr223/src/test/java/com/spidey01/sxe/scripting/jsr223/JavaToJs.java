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

package com.spidey01.sxe.scripting.jsr223;

import org.junit.*;

import com.spidey01.sxe.core.Log;
import com.spidey01.sxe.core.UnitTest;
import com.spidey01.sxe.scripting.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

public class JavaToJs {
    private static final String TAG = "JavaToJs";

    private Jsr223ScriptEngine mJavaScript;
    private Jsr223Script mScript;
    private JavaClass mJavaClass;


    @BeforeClass
    public static void setupClass() {
        UnitTest.setup();
    }


    @Before
    public void setup() {
        mJavaScript = new Jsr223ScriptEngine();
        mScript = (Jsr223Script)mJavaScript.createScript();
        mJavaClass = new JavaClass();
    }


    @Test
    public void simple() throws IOException, FileNotFoundException {
        File script = new File("JavaWrapper.js");
        mJavaScript.eval(mScript, script);
    }


    @Test
    public void get() {
        int x = (Integer)mJavaScript.eval(mScript, "x = 7;");
        Assert.assertEquals(x, mJavaScript.get(mScript, "x"));
    }


    @Test
    public void put() {
        mJavaScript.put(mScript, "out", System.out);
        mJavaScript.eval(mScript, "out.println('fuck you!');");

        mJavaScript.put(mScript, "javaClass", mJavaClass);
        Assert.assertTrue((Boolean)mJavaScript.eval(mScript, "javaClass.returnsTrue();"));
        int sum = (Integer)mJavaScript.eval(mScript, "javaClass.returnsSum(2, 2);");
        Assert.assertEquals(4, sum);
        String foobar = String.valueOf(mJavaScript.eval(mScript, "javaClass.returnsStrCat('foo', 'bar');"));
        Assert.assertEquals("foobar", foobar);
    }
}

