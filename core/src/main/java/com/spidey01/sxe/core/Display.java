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

public interface Display {

    public boolean create();
    public void destroy();
    public void update();
    public boolean isCloseRequested();

    /** Get an Instance of OpenGl suitable for running GL commands.
     */
    public OpenGl getOpenGl();

    /** Add a FrameListener for frame events.
     *
     * This will register a FrameListener that will be notified on rendering evenets.
     */
    public void addFrameListener(FrameListener listener);
    public void addFrameStartedListener(FrameStartedListener listener);
    public void addFrameEndedListener(FrameEndedListener listener);

    /** remove a FrameListener for frame events.
     *
     * This will unregister a FrameListener that was previously registered.
     */
    public void removeFrameListener(FrameListener listener);
    public void removeFrameStartedListener(FrameStartedListener listener);
    public void removeFrameEndedListener(FrameEndedListener listener);
}

