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

#include "sxe/cmds/SetCommand.hpp"

#include <sxe/config/Settings.hpp>
#include <sxe/logging.hpp>

namespace sxe { namespace cmds {

const SetCommand::string_type SetCommand::TAG = "SetCommand";

SetCommand::SetCommand(config::Settings& settings)
    : Command("set")
    , mSettings(settings)
{
}


bool SetCommand::operator() (const argv& args)
{
    for (const string_type& word : args) {
        auto equals = word.find('=');

        string_type key = word.substr(0, equals);
        Log::test(TAG, "parsed key: \"" + key + "\"");

        string_type value = word.substr(equals + 1);
        Log::test(TAG, "parsed value: \"" + value + "\"");

        mSettings.setString(key, value);
    }

    return true;
}

} }

