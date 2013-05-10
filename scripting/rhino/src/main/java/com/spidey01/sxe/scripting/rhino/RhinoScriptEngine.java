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
import org.mozilla.javascript.ScriptableObject;

import com.spidey01.sxe.core.Log;
import com.spidey01.sxe.scripting.ScriptEngine;
import com.spidey01.sxe.scripting.Script;

import java.io.File;
import java.io.FileInputStream;
import java.io.Reader;
import java.io.InputStreamReader;
import java.io.IOException;

public class RhinoScriptEngine implements ScriptEngine {
    private final static String TAG = "RhinoScriptEngine";

    private boolean mIsInitialized = false;
    private Context mContext;
    private Scriptable mGlobalScope;

    public RhinoScriptEngine() {
        mContext = Context.enter();
        mGlobalScope = mContext.initStandardObjects();
        Context.exit();
    }

    public void initialize() {
        if (mIsInitialized) {
            return;
        }
    }

    public Object eval(Script scope, String sourceCode) {
        mContext = Context.enter();
        Object rv = null;
        Scriptable script = getScope(scope);
        try {
            rv = mContext.evaluateString(script, sourceCode, TAG, 1, null);
        } finally {
            Context.exit();
        }
        return rv;
    }

    public Object eval(Script scope, File sourceFile) {
        mContext = Context.enter();
        Scriptable script = getScope(scope);
        try {
            return mContext.evaluateReader(script,
                new InputStreamReader(new FileInputStream(sourceFile)),
                sourceFile.getPath(), 1, null);
        } catch (IOException e) {
            Log.e(TAG, "Failed cooking file.", e);
        } finally {
            Context.exit();
        }
        return null;
    }

    public Script createScript() {
        return new RhinoScript(newScope());
    }

    private Scriptable newScope() {
        Scriptable scope = mContext.newObject(mGlobalScope);
        scope.setPrototype(mGlobalScope);
        scope.setParentScope(null);
        return scope;
    }

    private Scriptable getScope(Script scope) {
        return ((RhinoScript)scope).getScriptable();
    }

    public Object get(Script scope, String variable) {
        Scriptable s = getScope(scope);
        Object x = s.get("x", s);
        if (x == Scriptable.NOT_FOUND) {
            System.out.println("x is not defined.");
            return null;
        } else {
            System.out.println("x = " + Context.toString(x));
        }
        return x;
    }
}

