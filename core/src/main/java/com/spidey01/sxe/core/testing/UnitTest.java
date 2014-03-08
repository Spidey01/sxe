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

package com.spidey01.sxe.core.testing;

import com.spidey01.sxe.core.logging.Log;
import com.spidey01.sxe.core.logging.LogSink;

/** Base class for unit tests.
 *
 * This class is configured as a way of providing unit testing support for unit
 * tests that can be shared with other modules. It needs to have access to
 * core, so it cannot be a separate project. I don't care to add junit as a
 * baked in dependency of core either.
 */
public class UnitTest {

    private static boolean sIsLoggingReady = false;

    /** Initialize logging for unit tests.
     *
     * stdout will get all messages.
     * stderr will get up to warning messages.
     */
    public static void logging() {
        if (sIsLoggingReady) {
            return;
        }
        // gradle report will have all log statements sunk to 'Standard Output'
        Log.add(new LogSink(System.out, Integer.MAX_VALUE));

        // gradle output will have these sunk to 'Standard Error'
        Log.add(new LogSink(System.err, Log.WARN));

        sIsLoggingReady = true;
    }

    /** Perform any standard setup for unit tests.
     *
     * This is meant to be used in a @BeforeClass method.
     *
     * Equal to calling:<ul>
     *  <li>logging()</li>
     * </ul>
     */
    public static void setup() {
        UnitTest.logging();
    }
}

