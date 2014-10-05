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

package com.spidey01.sxe.core.graphics;

import com.spidey01.sxe.core.common.Subsystem;
import com.spidey01.sxe.core.gl.OpenGL;
import com.spidey01.sxe.core.graphics.FrameEndedListener;
import com.spidey01.sxe.core.graphics.FrameListener;
import com.spidey01.sxe.core.graphics.FrameStartedListener;

/** Interface to the games Display.
 *
 * Every game must be rendered to a display for the user. Details are fairly
 * platform dependant but will generally be a matter of creating an OpenGL
 * context, updating it, and eventually destroying it.
 *
 *
 */
public interface Display extends Subsystem {

    /** Create OpenGL context for the current Display.
     *
     * This must be called before rendering may be done.
     */
    boolean create();


    /** Destroy this OpenGL context.
     */
    void destroy();


    /** Update the Display.
     *
     * This will render attached frame listeners, perform house keeping, etc.
     *
     * Calling update() before calling create() is undefined behaviour.
     * Implementations are entitled but not required to throw an
     * IllegalStateException if the display has not been created; they could
     * also treat it as a noop.
     */
    void update();


    /** Indicates the user has tried to close the display.
     *
     * In PC space this may represent actions such as closing the game window,
     * the Alt+F4 trick, etc. Behaviour in relation to being terminated by
     * force (Windows taskmgr; unix kill) is undefined.
     *
     * On Mobile platforms, this will likely corrispond to whatever the
     * platforms norm is with regard to closing an application.
     */
    boolean isCloseRequested();


    /** Set the display mode.
     *
     * The exact mode syntax depends on the platform and display technology
     * involved. Not all Displays support setting the mode. A display mode is
     * information like horizontal and vertical resolution, color depth,
     * refresh rate, etc.
     *
     * @return true if successful; false otherwise.
     */
    boolean setMode(String mode);


    /** Get an Instance of OpenGL suitable for running GL commands.
     *
     * This shouldn't be necessary in most cases but is made for
     * convenience. It may be depreciated in the future.
     *
     * In most cases, you just want to implement some portion of FrameListener
     * and be added to the display. 
     */
    OpenGL getOpenGL();


    /** Add a FrameListener for frame events.
     *
     * This will register a FrameListener that will be notified on rendering evenets.
     */
    void addFrameListener(FrameListener listener);
    void addFrameStartedListener(FrameStartedListener listener);
    void addFrameEndedListener(FrameEndedListener listener);


    /** remove a FrameListener for frame events.
     *
     * This will unregister a FrameListener that was previously registered.
     */
    void removeFrameListener(FrameListener listener);
    void removeFrameStartedListener(FrameStartedListener listener);
    void removeFrameEndedListener(FrameEndedListener listener);


    /** Get the {@link GraphicsTechnique} for this {@link RenderableObject}.
     */
    GraphicsTechnique getTechnique(RenderData object);
}

