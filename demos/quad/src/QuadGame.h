#ifndef SXE_DEMOS_QUADGAME_H
#define SXE_DEMOS_QUADGAME_H

#include <sxe/api.hpp>
#include <sxe/core/Game.hpp>
#include <sxe/core/input/KeyEvent.hpp>

#include <string>

namespace demos {
	class QuadGame : public sxe::core::Game
    {
      public:

        std::string getName() const override;
        bool start(sxe::core::GameEngine* engine) override;

      private:
        static std::string TAG;
        bool onKey(sxe::core::input::KeyEvent event);
    };
}

#endif
