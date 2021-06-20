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

#include "sxe/sys/Platform.hpp"

using std::string;
using std::to_string;
using std::quoted;

namespace sxe {  namespace sys {

const string Platform::TAG = "Platform";
const string Platform::ANDROID = "Android";
const string Platform::LINUX = "Linux";
const string Platform::MAC_OS = "Mac OS";
const string Platform::WINDOWS = "Windows";

Platform::Platform()
    : mName(guess())
{
}


Platform::Platform(const std::string& name)
    : mName(name)
{
}


string Platform::name() const
{
    return mName;
}


string Platform::arch() const
{
    return "TODO:implement Platform::version()";
}


string Platform::version() const
{
    return "TODO:implement Platform::version()";
}


bool Platform::isAndroid() const
{
    return mName.find(ANDROID) == 0;
}


bool Platform::isMacOS() const
{
    return mName.find(MAC_OS) == 0 || mName.find("OS X") != string::npos;
}


bool Platform::isUnix() const
{
    /*
     * Because this is probably more DWIM to most game developers than just
     * return !isWindows.
     */
    return (isAndroid() || isMacOS() || isWindows()) ?  false : true;
}


bool Platform::isWindows() const
{
    return mName.find(WINDOWS) == 0;
}


string Platform::toString() const
{
    std::ostringstream ss;

    ss << "platform:"
        << " name=" << quoted(name())
        << " version=" << quoted(version())
        << " arch=" << quoted(arch())
        ;

    return ss.str();
}


string Platform::guess()
{
    /*
     * In Java, we did this by inspecting os.name, {file,line,path}.separator,
     * and the java.vm.{vendor,name} system properties. In C++, we can just
     * compile time macro it.
     */

    /* TODO: Android support. */
    /* TODO: macOS support. */
#if defined(__linux__)
    return LINUX;
#elif defined(WINDOWS) || defined(_WIN32) || defined(_WIN64)
    return WINDOWS;
#else
    throw std::runtime_error("Unknown OS");
#endif

}


} }

