#ifndef SXE_INPUT_CONSOLEINPUTMANAGER__HPP
#define SXE_INPUT_CONSOLEINPUTMANAGER__HPP
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

#include <sxe/api.hpp>
#include <sxe/input/InputManager.hpp>

namespace sxe { namespace input {

    class SXE_PUBLIC ConsoleInputManager : public InputManager
    {
      public:

        /** Default I/O streams and prompt.
         */
        ConsoleInputManager();

        /** Default I/O streams and specified prompt.
         *
         * Equal to ConsoleInputManager(std::cin, std::cout, promp).
         */
        ConsoleInputManager(const string_type& prompt);

        /** The most control without POSIX terminal APIs.
         *
         * @param input to read from this console.
         * @param output to write to this console.
         * @param prompt prefix for prompting the user.
         */
        ConsoleInputManager(std::istream& input, std::ostream& output, const string_type& prompt);

        virtual ~ConsoleInputManager() = default;

        string_type getPrompt() const;
        void setPrompt(const string_type& prompt);

        /** Set an override on EOF.
         *
         * When reading from the console returns EOF, this string will be
         * inject()'d in it's place. You can set this to e.g. "quit" to map EOF
         * to the command quit, for example.
         */
        void setEOFOverride(const string_type& str);

        /** Poll console for new input and notify listeners.
         *
         * This is a line based poll using std::getline(). It will inject() key
         * events batched by line. Someday, perhaps this will use a readline library.
         */
        void poll() override;


      private:
        static const string_type TAG;

        std::istream& mInput;
        std::ostream& mOutput;
        string_type mPrompt;
        string_type mEOFOverride;
    };

} }

#endif // SXE_INPUT_CONSOLEINPUTMANAGER__HPP
