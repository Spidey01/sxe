/*-
 * Copyright (c) 2012-current, Terry Mathew Poulin <BigBoss1964@gmail.com>
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

package com.spidey01.sxe.core;

/** Base class for representing Client and Server Variables.
 *
 * Variables have a name and a value.
 *
 * This is not a generic (templated) implementation because of type erasure.
 * All the fun stuff of using a class for this is pretty much erased in favour
 * of typing <Type> in a few places, litterally.
 *
 * Internally it is implemented as a String. Get/set operations are provided in
 * a manor much like a type safe union. Ain't Java so grand?
 *
 * These are the data types you can get in and out:
 *
 *      - String
 *      - boolean
 *      - int
 *      - float
 */
public class Variable {

    private String mName;
    private String mValue;
    private static final String TAG = "Variable";

    public Variable(String name) {
        this(name, "");
    }

    public Variable(String name, String value) {
        mName = name;
        mValue = value;
    }

    public Variable(String name, boolean value) {
        mName = name;
        mValue = String.valueOf(value);
    }

    public Variable(String name, int value) {
        mName = name;
        mValue = String.valueOf(value);
    }

    public Variable(String name, float value) {
        mName = name;
        mValue = String.valueOf(value);
    }

    public void set(String value) {
        mValue = value;
    }

    public void set(boolean value) {
        mValue = String.valueOf(value);
    }

    public void set(int value) {
        mValue = String.valueOf(value);
    }

    public void set(float value) {
        mValue = String.valueOf(value);
    }

    public String get() {
        return mValue;
    }

    public String toString() {
        return mValue;
    }

    public boolean booleanValue() {
        return Boolean.parseBoolean(mValue);
    }

    public int intValue() {
        return Integer.parseInt(mValue);
    }

    public float floatValue() {
        return Float.parseFloat(mValue);
    }
}

