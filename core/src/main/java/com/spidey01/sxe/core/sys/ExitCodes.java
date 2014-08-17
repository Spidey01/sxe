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

package com.spidey01.sxe.core.sys;

/** Various exit codes for Runtime.exit().
 *
 * Based on sysexits.h header file used by BSD Unix programmers.
 *
 */
public enum ExitCodes {

    EXIT_OK(0),

    /** Command line usage error.
     *
     * Examples: <ul>
     *  <li>Unknown argument.</li>
     *  <li>Wrong number of arguments.</li>
     *  <li>Bad value for an argument.</li>
     * </ul>
     */
    EXIT_USAGE(64),

    /** Data format error.
     *
     * The users data is incorrect in some way.
     */
    EXIT_DATAERR(65),

    /** Cannot open input */
    EXIT_NOINPUT(66),

    /** Specified user does not exist. */
    EXIT_NOUSER(67),

    /** Host name unknown. */
    EXIT_NOHOST(68),

    /** Service unavailable. */
    EXIT_UNAVAILABLE(69),

    /** Internal software error */
    EXIT_SOFTWARE(70),

    /** Operating system error.
     *
     * Stuff like couldn't create a thread, execute external program, etc.
     */
    EXIT_OSERR(71),

    /** Critical OS file is missing. */
    EXIT_OSFILE(72),

    /** Can't create output file. */
    EXIT_CANTCREAT(73),

    /** Input/output error. */
    EXIT_IOERR(74),

    /** Temporary failure. Feel free to retry the operation. */
    EXIT_TEMPFAIL(75),	/* temp failure; user is invited to retry */

    /** Network protocol format error. */
    EXIT_PROTOCOL(76),

    /** Insufficent permission to perform the operation. */
    EXIT_NOPERM(77),	/* permission denied */

    /** Configuratio error. */
    EXIT_CONFIG(78);

    private final int mExitCode;

    private ExitCodes(int code) {
        mExitCode = code;
    }
}

