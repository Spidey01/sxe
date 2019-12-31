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

#include "sxe/cmds/Command.hpp"

#include <sxe/logging.hpp>

namespace sxe { namespace cmds {

const Command::string_type Command::TAG = "Command";

Command::Command(const string_type& name)
    : mName(name)
{
    Log::test(TAG, "Created command " + name);
}


Command::string_type Command::getName() const
{
    return mName;
}


bool Command::operator() (const argv& args)
{
    using std::to_string;

    Log::xtrace(TAG, "operator() called on command: " + getName()
                + " args.size(): " + to_string(args.size()));

    for (argv::size_type i=0; i < args.size(); ++i)
        Log::v(TAG, "args[" + to_string(i) + "]: \"" + args[i] + "\"");

    return true;
}


bool Command::operator==(const Command& other) const
{
    return mName == other.mName;
}


bool Command::operator!=(const Command& other) const
{
    return mName != other.mName;
}


} }

