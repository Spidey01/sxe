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
package com.spidey01.sxe.core.logging;

import com.spidey01.sxe.core.GameEngine;
import com.spidey01.sxe.core.common.Subsystem;

/** Subsystem to manage logging.
 *
 * Presently this class only serves to provide an easy way to hook up the
 * {@link LogSettingsManager} to the {@link GameEngine} for managing
 * {@link LogSink} configuratiion.
 */
public class Logging
    implements Subsystem
{
    private static final String TAG = "Logging";

    private LogSettingsManager mSettingsManager;

    @Override
    public String name() { return TAG; }


    @Override
    public void initialize(GameEngine engine) {
        Log.d(TAG, "initialize(", engine, ")");

        mSettingsManager = new LogSettingsManager(engine);
    }


    @Override
    public void reinitialize(GameEngine engine) {
        uninitialize();
        initialize(engine);
    }


    @Override
    public void uninitialize() {
        Log.d(TAG, "uninitialize()");

        mSettingsManager.clear();
        mSettingsManager = null;
    }

}

