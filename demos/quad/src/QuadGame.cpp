
#include "QuadGame.h"

#include <sxe/core/GameEngine.hpp>

namespace demos {

std::string QuadGame::sTAG = "QuadGame";

std::string QuadGame::getName() const
{
    return sTAG;
}

#if 0
bool QuadGame::start(sxe::core::GameEngine& engine)
{
    sxe::core::Game::start(engine);

    #if 0 // java:
    mQuad = new  Quad(mGameEngine);

    /* Add our demo Quad to the scene. */
    mGameEngine.getSceneManager().add(mQuad.getGraphicsFacet());

    /* Bind ourself to handle the 'Q' key press. */
    mGameEngine.getInputManager().addKeyListener(InputCode.IC_Q, this);
    #endif

    return true;
}
#endif

}

