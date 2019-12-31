#ifndef SXE_CMDS_COMMAND__HPP
#define SXE_CMDS_COMMAND__HPP
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

namespace sxe { namespace cmds {

    /** Generic command execution.
     *
     * This is used by the Console to implement console commands. A Command is a
     * Callable that includes extra data useful to Commands.
     */
    class SXE_PUBLIC Command
    {
      public:

        using string_type = std::string;
        using argv = std::vector<string_type>;
        using unique_ptr = std::unique_ptr<Command>;
        using shared_ptr = std::shared_ptr<Command>;
        using weak_ptr = std::weak_ptr<Command>;

        Command(const string_type& name);

        string_type getName() const;

        /** Invoke this command with provided arguments.
         */
        virtual bool operator() (const argv& args);

        /** Compares based on getName().
         */
        bool operator==(const Command& other) const;

        bool operator!=(const Command& other) const;

      private:

        static const string_type TAG;
        string_type mName;
    };

} }

#endif // SXE_CMDS_COMMAND__HPP
