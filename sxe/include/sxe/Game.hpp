#ifndef SXE_GAME__HPP
#define SXE_GAME__HPP
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

namespace sxe {

    class GameEngine;

    /** Your games base class.
    */
    class SXE_PUBLIC Game
    {
      public:
        using unique_ptr = std::unique_ptr<Game>;
        using shared_ptr = std::shared_ptr<Game>;
        using weak_ptr = std::weak_ptr<Game>;

        /** Enumerated game state.
         *
         * We're either starting the game, running, or stopping the game. This can
         * be used to adjust code to the current state of the game. Such as loading
         * resources during STARTING and unloading them during STOPPING.
         */
        enum class State {
            STARTING,
            RUNNING,
            STOPPING,
        };

        Game();

        virtual ~Game();

        /** Implement to return the name of your game.
         */
        virtual std::string getName() const = 0;

        virtual std::string getPublisher() const;

        /** Starts the game running.
         *
         * @param engine a GameEngine to execute the game within.
         */
        virtual bool start(GameEngine* engine);

        /** Stops the game running.
         *
         */
        virtual void stop();

        bool isStopRequested() const;

        bool isStopped() const;

        /** Request that the game be stoped.
         *
         * @see #stop
         */
        void requestStop();

        /** Maximum Frames Per Second rate.  */
        int getMaxFpsRate() const;

        /** Maximum tick rate.  */
        int getMaxTickRate() const;

        virtual int getTickRate() const;

        /** Tick the game implementation.
         *
         * By default this:
         *   - Forwards to updateMainThread() if called from main main.
         *   - Forwards to updateGameThread() if called from game thread.
         *   - Throws called from unknown thread.
         *
         * @throws logic_error if called from unknown thread.
         */
        virtual void tick();

        /** Returns the GameEngine.
         *
         * @throws runtime_error if start() has not been called yet.
         */
        GameEngine& getGameEngine() const;

      protected:

        /** Our GameEngine.
         *
         * @see #start
         */
        GameEngine* mGameEngine;

        State mState;

        /** Called by tick from the main thread.
         *
         * Use this to update the game implementation from the engine's main
         * application thread. Unless access to main is required, you should
         * use updateGameThread to avoid blocking the GameEngine's main loop.
         */
        virtual void updateMainThread();

        /** Called by tick from the game thread.
         *
         * Use this to update the game implementation from the game's dedicated
         * background thread.
         */
        virtual void updateGameThread();

      private:

        static const std::string TAG;
        static const size_t mMaxTickRate;

        std::atomic_bool mStopRequested;
        std::atomic_bool mStopDone;
        std::thread mThread;
        std::thread::id mMainThreadId;
        std::thread::id mGameThreadId;

        RateCounter mTickCounter;

        /** Entry point for mThread.
         */
        void runGameThread();
    };

}

#endif // SXE_GAME__HPP
