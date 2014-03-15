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

package com.spidey01.sxe.core.cmds;

import com.spidey01.sxe.core.logging.Log;
import com.spidey01.sxe.core.config.Settings;
import com.spidey01.sxe.core.config.SettingsMap;

/** Command that sets runtime Settings.
 *
 * Example: <samp>~console~ set debug=true debug.log_level=10</samp>
 */
public class SetCommand extends Command {
    private static final String TAG = "SetCommand";

    private Settings mSettings;

    public SetCommand(Settings settings) {
        super("set");
        mSettings = settings;
    }


    public void run() {
        mSettings.merge(new SettingsMap(getArgs()));
    }
}

