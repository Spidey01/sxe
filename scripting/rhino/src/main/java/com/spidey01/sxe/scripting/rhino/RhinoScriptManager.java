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
import com.spidey01.sxe.scripting.ScriptManager;
import com.spidey01.sxe.scripting.Script;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;

public class RhinoScriptManager implements ScriptManager {
    private final static String TAG = "RhinoScriptManager";

    private Context mContext;
    private Scriptable mGlobalScope;


    public RhinoScriptManager() {
        mContext = Context.enter();
        mGlobalScope = mContext.initStandardObjects();
        Context.exit();
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


    public Object eval(Script scope, File sourceFile)
        throws IOException, FileNotFoundException
    {
        mContext = Context.enter();
        Scriptable script = getScope(scope);
        try {
            return mContext.evaluateReader(script,
                new InputStreamReader(new FileInputStream(sourceFile)),
                sourceFile.getPath(), 1, null);
        } finally {
            Context.exit();
        }
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
        Object object = s.get(variable, s);
        if (object == Scriptable.NOT_FOUND) {
            Log.v(TAG, variable+" is not defined.");
            return null;
        }
        Log.v(TAG, "object class: "+object.getClass().getName());
        return object;
    }


    /** Export Java value to script language.
     *
     * If value is an instance of String, Number, or Boolean: it will be
     * converted to the corresponding JavaScript types of string, number, and
     * boolean. Instances of Character will be converted to a JavaScript string
     * containing the character.
     *
     * Rhino relies on Number.doubeValue() when performing arithmetic in
     * JavaScript on Number instances. Loss of precision is possible if the
     * number won't fit in a double.
     *
     * @param variable name used in scripting language.
     * @param value the java object referred to by variable.
     * @return ...
     */
    public Object put(Script scope, String variable, Object value) {
        Scriptable s = getScope(scope);
        Object wrapped = null;
        Context.enter();
        try {
            wrapped = Context.javaToJS(value, s);
        } finally {
            Context.exit();
        }
        ScriptableObject.putProperty(s, variable, wrapped);
        return wrapped;
    }

}

