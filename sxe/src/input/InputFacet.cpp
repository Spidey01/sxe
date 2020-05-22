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

#include "sxe/input/InputFacet.hpp"

#include <sxe/logging.hpp>

using std::to_string;

namespace sxe { namespace input {

const std::string InputFacet::TAG = "InputManager";

InputFacet::InputFacet(InputManager& inputManager)
    : mInputManager(inputManager)
    , mIdMap()
    , mMutex()
{
}


InputFacet::InputFacet(InputManager& inputManager, KeyListener action)
    : InputFacet(inputManager, InputCode::IC_ANYKEY, action)
{
}


InputFacet::InputFacet(InputManager& inputManager, InputCode code, KeyListener action)
    : InputFacet(inputManager)
{
    addKeyListener(code, action);
}


InputFacet::~InputFacet()
{
    // lock_guard g(mMutex);

    Log::xtrace(TAG, "~InputFacet(): clearing mIdMap");

    for (auto& pair : mIdMap) {
        int code = pair.first;
        IdList& list = pair.second;

        for (auto id : list) {
            mInputManager.removeKeyListener(code, id);
        }
    }

    mIdMap.clear();

}


bool InputFacet::addKeyListener(InputCode code, KeyListener action)
{
    lock_guard g(mMutex);

    IdList::value_type id = mInputManager.addKeyListener(code, action);

    if (id == SIZE_MAX)
        return false;

    mIdMap[code.code()].push_back(id);
    Log::test(TAG, "addKeyListener(): bound KeyListener for " + code.dump() + " as id " + to_string(id));

    return true;
}


void InputFacet::removeKeyListener(InputCode code)
{
    lock_guard g(mMutex);

    IdList& list = mIdMap[code.code()];
    for (IdList::value_type id : list) {
        Log::test(TAG, "removeKeyListener(): unbind KeyListener for " + code.dump() + " using id " + to_string(id));
        mInputManager.removeKeyListener(code, id);
    }

    list.clear();
}


InputManager& InputFacet::manager() const
{
    return mInputManager;
}

} }
