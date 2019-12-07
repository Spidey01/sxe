#ifndef SXE_DEMOS_NULLDEMO_H
#define SXE_DEMOS_NULLDEMO_H

#include <sxe/api.hpp>
#include <sxe/core/Game.hpp>

#include <string>

namespace demos {
	class NullDemo : public sxe::core::Game
    {
      public:

        std::string getName() const override;

        bool start(sxe::core::GameEngine* engine) override;

      private:
        static std::string TAG;
    };
}

#endif
