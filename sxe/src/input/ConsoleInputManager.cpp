/*-
 * Copyright (c) 2013-current, Terry Mathew Poulin <BigBoss1964@gmail.com>
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

#include "sxe/input/ConsoleInputManager.hpp"

#include <sxe/logging.hpp>

namespace sxe {  namespace input {

const ConsoleInputManager::string_type ConsoleInputManager::TAG = "ConsoleInputManager";

ConsoleInputManager::ConsoleInputManager()
    : ConsoleInputManager("SxE->")
{
}


ConsoleInputManager::ConsoleInputManager(const string_type& prompt)
    : ConsoleInputManager(std::cin, std::cout, prompt)
{
}


ConsoleInputManager::ConsoleInputManager(std::istream& input, std::ostream& output, const string_type& prompt)
    : InputManager(TAG)
    , mInput(input)
    , mOutput(output)
    , mPrompt(prompt)
    , mEOFOverride()
{
}


ConsoleInputManager::string_type ConsoleInputManager::getPrompt() const
{
    return mPrompt;
}


void ConsoleInputManager::setPrompt(const string_type& prompt)
{
    Log::xtrace(TAG, "setPrompt(): old: " + mPrompt + " -> new: " + prompt);
    mPrompt = prompt;
}


void ConsoleInputManager::setEOFOverride(const string_type& str)
{
    Log::xtrace(TAG, "setEOFOverride(): old: " + mEOFOverride + " -> new: " + str);
    mEOFOverride = str;
}


void ConsoleInputManager::poll()
{
    if (!mInput || !mOutput)
        return;

    mOutput << mPrompt << ' ';

    string_type line;
    std::getline(mInput, line);

    if (mInput.eof())
        inject(mEOFOverride);
    else
        inject(line);

    mOutput << std::endl;
}

} }

