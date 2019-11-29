
#include "NullDemo.h"

#include <sxe/core/GameEngine.hpp>

namespace demos {

std::string NullDemo::sTAG = "NullDemo";

std::string NullDemo::getName() const
{
    return sTAG;
}

#if 0
bool NullDemo::start(sxe::core::GameEngine& engine)
{
    sxe::core::Game::start(engine);

    #if 0 // java:
    Log.v(TAG, "Null demo is starting.");

    mGameEngine.getInputManager().addKeyListener(InputCode.IC_Q, this);
    #endif

    return true;
}
#endif

}


