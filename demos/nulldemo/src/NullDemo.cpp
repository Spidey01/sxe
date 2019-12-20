
#include "NullDemo.h"

#include <sxe/core/GameEngine.hpp>
#include <sxe/logging.hpp>

namespace demos {

std::string NullDemo::TAG = "NullDemo";

std::string NullDemo::getName() const
{
    return TAG;
}

bool NullDemo::start(sxe::core::GameEngine* engine)
{
    sxe::core::Game::start(engine);

    Log::v(TAG, "Null demo is starting.");

    #if 0 // java:
    mGameEngine.getInputManager().addKeyListener(InputCode.IC_Q, this);
    #endif

    return true;
}


void NullDemo::stop()
{
    sxe::core::Game::stop();

    Log::v(TAG, "Null demo is stopping");
}


void NullDemo::tick()
{
    Log::xtrace(TAG, "tick()");
}

#if 0 // java
    @Override
    public boolean onKey(KeyEvent event) {
        if (event.isKeyUp()) {
            if (event.getKeyCode().equals(InputCode.IC_Q)) {
                Log.d(TAG, "Q key released");
                requestStop();
                return true;
            }
        }
        return false;
    }
#endif

}


