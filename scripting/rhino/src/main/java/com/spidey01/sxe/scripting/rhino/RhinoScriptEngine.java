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

import org.mozilla.javascript.Context;
import org.mozilla.javascript.Scriptable;

import com.spidey01.sxe.core.Log;
import com.spidey01.sxe.scripting.ScriptEngine;

import java.io.File;
import java.io.FileInputStream;
import java.io.Reader;
import java.io.InputStreamReader;
import java.io.IOException;

public class RhinoScriptEngine extends ScriptEngine {
    private final static String TAG = "RhinoScriptEngine";

    private boolean mIsInitialized = false;
    private Context mContext;

    public RhinoScriptEngine() {
        mContext = Context.enter();
    }

    public void initialize() {
        if (mIsInitialized) {
            return;
        }
    }

    public Object eval(String sourceCode) {
        Scriptable scope = mContext.initStandardObjects();
        return mContext.evaluateString(scope, sourceCode, TAG, 1, null);
    }

    public Object eval(File sourceFile) {
        Scriptable scope = mContext.initStandardObjects();
        try {
            return mContext.evaluateReader(scope,
                new InputStreamReader(new FileInputStream(sourceFile)),
                sourceFile.getPath(), 1, null);
        } catch (IOException e) {
            Log.e(TAG, "Failed cooking file.", e);
        }
        return null;
    }
}

