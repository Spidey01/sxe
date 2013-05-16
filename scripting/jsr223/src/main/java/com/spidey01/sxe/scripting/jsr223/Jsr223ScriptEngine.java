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

import javax.script.Bindings;
import javax.script.ScriptContext;
// import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import javax.script.SimpleScriptContext;

import com.spidey01.sxe.core.Log;
import com.spidey01.sxe.core.Utils;
import com.spidey01.sxe.scripting.Script;
import com.spidey01.sxe.scripting.ScriptEngine;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;

public class Jsr223ScriptEngine implements ScriptEngine {
    private final static String TAG = "Jsr223ScriptEngine";

    private javax.script.ScriptEngine mScriptEngine;

    private enum GetBy {
        NAME, EXTENSION, MIME_TYPE
    }


    private Jsr223ScriptEngine(GetBy how, String value) {
        switch (how) {
            case NAME:
                mScriptEngine = (new ScriptEngineManager()).getEngineByName(value);
                break;
            case EXTENSION:
                mScriptEngine = (new ScriptEngineManager()).getEngineByExtension(value);
                break;
            case MIME_TYPE:
                mScriptEngine = (new ScriptEngineManager()).getEngineByMimeType(value);
                break;
        }
    }


    /** Create a ScriptEngine for "JavaScript". */
    public Jsr223ScriptEngine() {
        this(GetBy.NAME, "JavaScript");
    }


    public static Jsr223ScriptEngine getByName(String shortName) {
        return new Jsr223ScriptEngine(GetBy.NAME, shortName);
    }


    public static Jsr223ScriptEngine getByExtension(String extension) {
        return new Jsr223ScriptEngine(GetBy.EXTENSION, extension);
    }


    public static Jsr223ScriptEngine getByMimeType(String mimeType) {
        return new Jsr223ScriptEngine(GetBy.MIME_TYPE, mimeType);
    }


    public Object eval(Script scope, String sourceCode) {
        try {
            return mScriptEngine.eval(sourceCode, getScriptContext(scope));
        } catch(ScriptException e) {
            Log.e(TAG, "ScriptException wrapped.", e);
            throw new RuntimeException(e);
        }
    }


    public Object eval(Script scope, File sourceFile)
        throws IOException, FileNotFoundException
    {
        try {
            return mScriptEngine.eval(Utils.makeBufferedReader(sourceFile),
                                      getScriptContext(scope));
        } catch(ScriptException e) {
            Log.e(TAG, "ScriptException wrapped.", e);
            throw new RuntimeException(e);
        }
    }


    public Script createScript() {
        return new Jsr223Script(new SimpleScriptContext());
    }


    private ScriptContext getScriptContext(Script scope) {
        return ((Jsr223Script)scope).getScriptContext();
    }


    public Object get(Script scope, String variable) {
        return getScriptContext(scope).getBindings(ScriptContext.ENGINE_SCOPE).get(variable);
    }


    /** Export Java value to script language.
     */
    public Object put(Script scope, String variable, Object value) {
        getScriptContext(scope).getBindings(ScriptContext.ENGINE_SCOPE).put(variable, value);
        return null;
    }

}

