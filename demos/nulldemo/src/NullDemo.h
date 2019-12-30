#ifndef SXE_DEMOS_NULLDEMO_H
#define SXE_DEMOS_NULLDEMO_H

#include <sxe/api.hpp>
#include <sxe/core/Game.hpp>

#include <string>

namespace sxe {
    namespace core {
        namespace input {
            class KeyEvent;
        }
    }
}

namespace demos {
	class NullDemo : public sxe::core::Game
    {
      public:

        std::string getName() const override;

        bool start(sxe::core::GameEngine* engine) override;
        void stop() override;
        void tick() override;

      private:
        static std::string TAG;
        bool onKey(sxe::core::input::KeyEvent event);
    };
}

#endif
