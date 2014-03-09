/*-
 * Copyright (c) 2014-current, Terry Mathew Poulin <BigBoss1964@gmail.com>
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
package com.spidey01.sxe.core.config;

import com.spidey01.sxe.core.logging.Log;

import java.util.StringTokenizer;


/** Helper class for expanding settings.
 *
 * The value of a setting can be interoplated into value over another setting
 * by enclosing it within curly braces. Like so:
 *
 * <pre>
 *      key     = stored value  = expanded value
 *      ham     = foo           = foo
 *      spam    = bar           = bar
 *      eggs    = {ham}.{bar}   = foo.bar
 * </pre>
 */
public class SettingsExpander {
    private static final String TAG = "SettingsExpander";

    private Settings mSettings;
    private StringBuilder mBuffer = new StringBuilder(32);

    /**
     *
     * @param s source settings to be expanded.
     */
    public SettingsExpander(Settings s) {
        mSettings = s;
        Log.test(TAG, "ctor mSettings =>", mSettings);
    }


    /** Expand for a given key. */
    public String wtf_expand(String key) {
        Log.test(TAG, "key =>", key);
        Log.test(TAG, "expd mSettings =>", mSettings);
        // String value = mSettings.get(key, false);
        String value = mSettings.getString(key);
        if (value == null) {
            Log.test(TAG, "Input was null.");
            return null;
        }
        Log.test(TAG, "value.isEmpty() =>", value.isEmpty());

        StringTokenizer tokenizer = new StringTokenizer(value, "{}", true);
        Log.test(TAG, "countTokens =>", tokenizer.countTokens());
        if (tokenizer.countTokens() == 0) {
            Log.test(TAG, "No expansion to be done: key =>", key, "return =>", value);
            return value;
        }

        boolean expanding = false;

        while (tokenizer.hasMoreTokens()) {
            String token = tokenizer.nextToken();
            if (token.equals("{")) {
                Log.test(TAG, "Start of expansion at =>", token);
                expanding = true;
            }
            else if (token.equals("}")) {
                Log.test(TAG, "end of expansion at =>", token);
                expanding = false;
            }
            else {
                if (expanding) {
                    Log.test(TAG, "The content of the variable =>", token);
                    if (token.equals(key)) {
                        Log.test("XXX", "token =>", token, "value =>", value, "mBuffer =>", mBuffer);
                        return value;
                    }
                    mBuffer.append(mSettings.getString(token));
                } else {
                    /* the non variable part. */
                    Log.test(TAG, "Non variable piece =>", token);
                    mBuffer.append(token);
                }
            }
        }
        value = mBuffer.toString();
        Log.test(TAG, "returning expanded value =>", value);
        // mBuffer.setLength(0);
        return value;
    }


    public String expand(String key) {
        Log.test(TAG, "do the getString");
        String value = mSettings.getString(key);
        StringTokenizer tokenizer = new StringTokenizer(value, "{}", true);

        Log.test(TAG, "value == null =>", value==null);
        Log.test(TAG, "value.isEmpty() =>", value.isEmpty());
        Log.test(TAG, "value =>", value);
        Log.test(TAG, "tokenizer.countTokens() => ", tokenizer.countTokens());
        if (tokenizer.countTokens() == 1) {
            // no expansion to be done.
            Log.test(TAG, "No expansion to be done: key =>", key, "return =>", value);
            return value;
        }

        boolean expanding = false;

        while (tokenizer.hasMoreTokens()) {
            String token = tokenizer.nextToken();
            if (token.equals("{")) {
                Log.test(TAG, "Start of expansion at =>", token);
                expanding = true;
            }
            else if (token.equals("}")) {
                Log.test(TAG, "end of expansion at =>", token);
                expanding = false;
            }
            else {
                if (expanding) {
                    /* the content of the variable */
                    Log.test(TAG, "The content of the variable =>", token);
                    if (token.equals(key)) {
                        Log.test("XXX", "token =>", token, "value =>", value, "mBuffer =>", mBuffer);
                        return value;
                    }
                    mBuffer.append(mSettings.getString(token));
                } else {
                    /* the non variable part. */
                    Log.test(TAG, "Non variable piece =>", token);
                    mBuffer.append(token);
                }
            }
        }
        value = mBuffer.toString();
        mBuffer.setLength(0);
        Log.test(TAG, "returning expanded value =>", value);
        return value;
    }
}

