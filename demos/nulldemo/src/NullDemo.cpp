
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

}


