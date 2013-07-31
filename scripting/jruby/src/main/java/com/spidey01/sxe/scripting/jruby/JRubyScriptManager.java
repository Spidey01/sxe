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

package com.spidey01.sxe.scripting.jruby;

import org.jruby.embed.ScriptingContainer;
import org.jruby.embed.LocalContextScope;
import org.jruby.embed.LocalVariableBehavior;

import com.spidey01.sxe.core.Log;
import com.spidey01.sxe.scripting.ScriptManager;
import com.spidey01.sxe.scripting.Script;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;


public class JRubyScriptManager implements ScriptManager {
    private final static String TAG = "JRubyScriptManager";


    public JRubyScriptManager() {
    }


    public Object eval(Script scope, String sourceCode) {
        ScriptingContainer c = getScope(scope);
        Object rv = c.runScriptlet(sourceCode);
        /*
        if (rv != null) {
            Log.xtrace(TAG, "rv class: "+rv.getClass().getName());
        }
        */
        return rv;
    }


    public Object eval(Script scope, File sourceFile)
        throws IOException, FileNotFoundException
    {
        ScriptingContainer c = getScope(scope);
        Object rv = c.runScriptlet(
                new InputStreamReader(
                    new FileInputStream(sourceFile)),
                sourceFile.getPath());
        /*
        if (rv != null) {
            Log.xtrace(TAG, "rv class: "+rv.getClass().getName());
        }
        */
        return rv;


    }


    public Script createScript() {
        return new JRubyScript(newContainer());
    }


    private ScriptingContainer newContainer() {
        return new ScriptingContainer(LocalContextScope.SINGLETHREAD, LocalVariableBehavior.PERSISTENT);
    }


    private ScriptingContainer getScope(Script scope) {
        return ((JRubyScript)scope).getContainer();
    }


    public Object get(Script scope, String variable) {
        ScriptingContainer c = getScope(scope);
        Object object = c.get(variable);
        // It's just null if it's not defined :-/
        /*
        if (object != null) {
            Log.xtrace(TAG, "object class: "+object.getClass().getName());
        }
        */
        return object;
    }


    /** Export Java value to script language.
     *
     * @param variable name used in scripting language.
     * @param value the java object referred to by variable.
     * @return ...
     */
    public Object put(Script scope, String variable, Object value) {
        ScriptingContainer c = getScope(scope);
        c.put(variable, value);
        // return value is old value, we probably want the new value.
        return get(scope, variable);
    }

}

