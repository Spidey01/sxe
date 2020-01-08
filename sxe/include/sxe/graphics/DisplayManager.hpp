#ifndef SXE_GRAPHICS_DISPLAYMANAGER__HPP
#define SXE_GRAPHICS_DISPLAYMANAGER__HPP
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

#include <sxe/api.hpp>
#include <sxe/RateCounter.hpp>
#include <sxe/common/Subsystem.hpp>
#include <sxe/config/Settings.hpp>
#include <sxe/graphics/DisplayMode.hpp>

namespace sxe {  namespace graphics {

    /** Interface to the games Display.
     *
     * Every game must be rendered to a display for the user. Details are fairly
     * platform dependant but will generally be a matter of creating an OpenGL
     * context, updating it, and eventually destroying it.
     *
     *
     */
    class SXE_PUBLIC DisplayManager : public common::Subsystem
    {
      public:

        /** WRITE ME.
         *
         * @param name passed to Subsystem ctor.
         */
        DisplayManager(const string_type& name);

        virtual ~DisplayManager();

        bool initialize(GameEngine& engine) override;
        bool uninitialize() override;

        /** Create OpenGL context for the current Display.
         *
         * This must be called before rendering may be done.
         */
        virtual bool create() = 0;

        /** Destroy this OpenGL context.
        */
        virtual void destroy() = 0;

        /** Update the Display.
         *
         * This will render attached frame listeners, perform house keeping, etc.
         *
         * Calling update() before calling create() is undefined behaviour.
         * Implementations are entitled but not required to throw an
         * logic_error if the display has not been created; they could also
         * treat it as a noop.
         */
        void update() override;

        /** Indicates the user has tried to close the display.
         *
         * In PC space this may represent actions such as closing the game window,
         * the Alt+F4 trick, etc. Behaviour in relation to being terminated by
         * force (Windows taskmgr; unix kill) is undefined.
         *
         * On Mobile platforms, this will likely corrispond to whatever the
         * platforms norm is with regard to closing an application.
         */
        virtual bool isCloseRequested() const;

        /** Set the display mode.
         *
         * The exact mode syntax depends on the platform and display technology
         * involved. Not all Displays support setting the mode. A display mode is
         * information like horizontal and vertical resolution, color depth,
         * refresh rate, etc.
         *
         * This causs the virtual mode after creating a DisplayMode.
         *
         * @param mode passed to DisplayMode's ctor.
         * @param fs passed to DisplayMode's ctor.
         *
         * @return true if successful; false otherwise.
         */
        bool setMode(const string_type& mode, bool fs);

        /** Set the display mode.
         *
         * Default implementation records the mode and returns true.
         */
        virtual bool setMode(DisplayMode mode);

        const DisplayMode& getMode() const;

        /** Switch between fullscreen and windowed mode.
         *
         * @param fs if true: set mode to fullscreen.
         */
        virtual void setFullscreen(bool fs);

        bool isWindowed() const;
        bool isFullscreen() const;

        /** Settings::OnChangedListener for settings notification.
         *
         * Default implementation looks for .mode, .fps, and .fullscreen
         * settings prefixed by Game::getName(). When these settings are
         * changed, methods are triggered accordingly.
         *
         * Extend this method to add additional settings monitoring for your
         * platform implementation.
         */
        virtual void onSettingChanged(string_type key);

      protected:

      private:

        static const string_type TAG;
        sxe::RateCounter mFrameCounter;
        DisplayMode mDisplayMode;
        bool mFullscreen;
        config::Settings::SettingsManager::size_type mOnChangedListenerId;

        /* Key used for changing mDisplayMode. */
        string_type mModeSettingKey;

        /* Key used for toggling FPS display. */
        string_type mFpsSettingKey;

        /* Key used for toggling full screen / windowed mode. */
        string_type mFullscreenSettingKey;
    };

} }

#endif // SXE_GRAPHICS_DISPLAYMANAGER__HPP
#if 0 // not yet converted from Java.
import com.spidey01.sxe.core.gl.OpenGL;
import com.spidey01.sxe.core.graphics.FrameEndedListener;
import com.spidey01.sxe.core.graphics.FrameListener;
import com.spidey01.sxe.core.graphics.FrameStartedListener;

public interface Display extends Subsystem {

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
#endif
