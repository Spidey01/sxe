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

package com.spidey01.sxe.scripting.rhino;

import org.junit.*;

import com.spidey01.sxe.core.Log;
import com.spidey01.sxe.core.UnitTest;
import com.spidey01.sxe.scripting.*;

import java.io.File;

public class JavaToRhino {
    private static final String TAG = "JavaToRhino";

    private RhinoScriptEngine mRhino;
    private RhinoScript mScript;
    private JavaClass mJavaClass;


    @BeforeClass
    public static void setupClass() {
        UnitTest.setup();
    }


    @Before
    public void setup() {
        mRhino = new RhinoScriptEngine();
        mScript = (RhinoScript)mRhino.createScript();
        mJavaClass = new JavaClass();
    }


    @Test
    public void simple() {
        File script = new File("JavaToRhino.js");
        mRhino.eval(mScript, script);
    }


    @Test
    public void get() {
        int x = (Integer)mRhino.eval(mScript, "x = 7;");
        Assert.assertEquals(x, mRhino.get(mScript, "x"));
    }


        // rhino.put("javaClass", javaClass);
        // Log.d(TAG, "value: +"+ rhino.eval("javaClass.returnsTrue();").getClass());
        // System.out.println( "value: +"+ rhino.eval("javaClass.returnsTrue();").getClass());
        // String result = (String)rhino.eval("'Hello, Rhino!'");
        // Assert.assertEquals("Hello, Rhino!", result);
    @Test
    public void put() {
        mRhino.put(mScript, "out", System.out);
        mRhino.eval(mScript, "out.println('fuck you!');");

        mRhino.put(mScript, "javaClass", mJavaClass);
        Assert.assertTrue((Boolean)mRhino.eval(mScript, "javaClass.returnsTrue();"));
        int sum = (Integer)mRhino.eval(mScript, "javaClass.returnsSum(2, 2);");
        Assert.assertEquals(4, sum);
        String foobar = String.valueOf(mRhino.eval(mScript, "javaClass.returnsStrCat('foo', 'bar');"));
        Assert.assertEquals("foobar", foobar);
    }
}

