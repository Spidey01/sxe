#ifndef SXE_COMMON_INITIALIZABLE__HPP
#define SXE_COMMON_INITIALIZABLE__HPP
/*-
 * Copyright (c) 2014-current, Terry Mathew Poulin <BigBoss1964@gmail.com>
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

namespace sxe { namespace common {

    template <class E>
    class Initializable
    {
      public:

        bool isInitialized() const
        {
            return mIsInitialized;
        }


        virtual bool initialize(E& data)
        {
            (void)data;
            mIsInitialized = true;
            return true;
        }


        virtual bool reinitialize(E& data)
        {
            (void)data;
            if (!uninitialize())
                return false;
            if (!initialize(data))
                return false;
            return true;
        }


        virtual bool uninitialize()
        {
            mIsInitialized = false;
            return true;
        }

      protected:

        bool mIsInitialized;

      private:
    };

} }

#endif // SXE_COMMON_INITIALIZABLE__HPP
