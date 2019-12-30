
#include "NullDemo.h"

#include <sxe/core/GameEngine.hpp>
#include <sxe/core/input/InputCode.hpp>
#include <sxe/core/input/InputManager.hpp>
#include <sxe/core/input/KeyEvent.hpp>
#include <sxe/core/input/KeyListener.hpp>
#include <sxe/logging.hpp>

#include <functional>

using sxe::core::input::InputCode;
using sxe::core::input::KeyEvent;
using sxe::core::input::KeyListener;

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

    KeyListener listener = std::bind(&NullDemo::onKey, this, std::placeholders::_1);
    engine->getInputManager().addKeyListener(InputCode::IC_Q, listener);

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


bool NullDemo::onKey(KeyEvent event)
{
    if (event.isKeyUp()) {
        Log::d(TAG, event.toString());

        if (event.getKeyCode() == InputCode::IC_Q) {
            Log::d(TAG, "Q key released");
            requestStop();
            return true;
        }
    }
    return false;
}

}

