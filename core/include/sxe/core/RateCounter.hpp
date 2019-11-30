#ifndef SXE_CORE_RATECOUNTER__HPP
#define SXE_CORE_RATECOUNTER__HPP
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

#include <sxe/stdheaders.hpp>

namespace sxe { namespace core {

    class RateCounter
    {
      public:

        RateCounter(const std::string& name, bool debug);
        RateCounter(const std::string& name);

        std::string getName() const;

        void disableDebugging();
        void enableDebugging();
        bool isDebuggingEnabled();

        void update();

      private:

        static const std::string TAG;

        std::string mName;
        bool mDebuggingEnabled;
        std::chrono::steady_clock::time_point mLast;
        size_t mPerSecond;

    };


} }

#endif // SXE_CORE_RATECOUNTER__HPP
