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

import java.util.Map;

/** Access to persistent settings.
 *
 * @see PcSettings
 * @see AndroidSettings
 */
public interface Settings {

    /** Get key as a boolean value.
     *
     * @return the value of key or false if not set.
     */
    boolean getBoolean(String key);

    /** Get key as a float value.
     *
     * @return the value of key or 0 if not set.
     */
    float getFloat(String key);

    /** Get key as a int value.
     *
     * @return the value of key or 0 if not set.
     */
    int getInt(String key);

    /** Get key as a long value.
     *
     * @return the value of key or 0 if not set.
     */
    long getLong(String key);

    /** Get key as a String value.
     *
     * @return the value of key or "" if not set.
     */
    String getString(String key);

    /** Set key as a boolean value.  */
    Settings setBoolean(String key, boolean value);

    /** Set key as a float value.  */
    Settings setFloat(String key, float value);

    /** Set key as a int value.  */
    Settings setInt(String key, int value);

    /** Set key as a long value.  */
    Settings setLong(String key, long value);

    /** Set key as a String value.  */
    Settings setString(String key, String value);

    boolean save();
}

