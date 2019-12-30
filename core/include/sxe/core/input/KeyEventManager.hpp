#ifndef SXE_CORE_INPUT_KEYEVENTMANAGER__HPP
#define SXE_CORE_INPUT_KEYEVENTMANAGER__HPP
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

#include <sxe/api.hpp>
#include <sxe/core/input/KeyEvent.hpp>
#include <sxe/core/input/KeyListener.hpp>
#include <sxe/core/input/InputCode.hpp>
#include <sxe/core/common/NotificationManager.hpp>

namespace sxe { namespace core { namespace input {

    class SXE_PUBLIC KeyEventManager : public common::NotificationManager<KeyListener, KeyEvent>
    {
      public:

        using Id = size_type;

        Id addKeyListener(KeyListener listener)
        {
            return subscribe(listener);
        }


        Id addKeyListener(InputCode inputCode, KeyListener listener)
        {
            return subscribe(listener, std::to_string(inputCode.code()));
        }


        void removeKeyListener(Id id)
        {
            unsubscribe(id);
        }


        void removeKeyListener(InputCode inputCode, Id id)
        {
            unsubscribe(std::to_string(inputCode.code()), id);
        }

    };

} } }

#endif // SXE_CORE_INPUT_KEYEVENTMANAGER__HPP
