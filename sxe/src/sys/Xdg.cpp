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
#include "sxe/sys/Xdg.hpp"

#include <sxe/common/Utils.hpp>
#include <sxe/logging.hpp>
#include <sxe/sys/FileSystem.hpp>
#include <sxe/sys/Platform.hpp>

using std::getenv;
using sxe::common::Utils::split;

namespace sxe {  namespace sys {

Xdg::Xdg()
    : HOME(FileSystem::getUserDir())
    , USER_DIR(FileSystem::getUserDir())
{
    char *p;

    p = getenv("APP_HOME");
    if (p != nullptr)
        APP_HOME = p;

    p = getenv("XDG_DATA_HOME");
    if (p != nullptr) {
        XDG_DATA_HOME = p;
    } else {
        XDG_DATA_HOME = USER_DIR / ".local" / "share";
    }

    /*
     * SxE 1.x always used :.
     * SxE 2.x switches to ; for Windows.
     */
    char sep = Platform().isWindows() ? ';' : ':';
    path defaultSdkRoot;
    if (Platform().isWindows()) {
        // CMake default install path.
        p = getenv("ProgramFiles(x86)");
        if (p != nullptr) {
            defaultSdkRoot = p;
        }
    }

    p = getenv("XDG_DATA_DIRS");
    if (p != nullptr) {
        Log::test("Xdg", "XDG_DATA_DIRS=" + string(p));
        split(std::back_inserter(XDG_DATA_DIRS), string(p), sep);
    } else {
        Log::test("Xdg", "XDG_DATA_DIRS=nullptr, defaulting");
        XDG_DATA_DIRS.push_back("/usr/local/share");
        XDG_DATA_DIRS.push_back("/usr/share");
        if (!defaultSdkRoot.empty())
            XDG_DATA_DIRS.push_back(defaultSdkRoot / "SXE_SDK" / "share");
    }

    p = getenv("XDG_CONFIG_HOME");
    if (p != nullptr) {
        XDG_CONFIG_HOME = p;
    } else {
        XDG_CONFIG_HOME /= USER_DIR / ".config";
    }

    p = getenv("XDG_CONFIG_DIRS");
    if (p != nullptr) {
        split<>(std::back_inserter(XDG_CONFIG_DIRS), string(p), sep);
    } else {
        XDG_CONFIG_DIRS.push_back("/etc/xdg");
        if (!defaultSdkRoot.empty())
            XDG_CONFIG_DIRS.push_back(defaultSdkRoot / "etc" / "xdg");
    }

    p = getenv("XDG_CACHE_HOME");
    if (p != nullptr)
        XDG_CACHE_HOME = p;
    else
        XDG_CACHE_HOME /= USER_DIR / ".cache";

    p = getenv("XDG_RUNTIME_DIR");
    if (p != nullptr)
        XDG_RUNTIME_DIR = p;
}


Xdg::path Xdg::getDataHomeDir(const string& relative) const
{
    return XDG_DATA_HOME / relative;
}


Xdg::path Xdg::getDataHomeDir(const path& relative) const
{
    return XDG_DATA_HOME / relative;
}


Xdg::path Xdg::getConfigHomeDir(const string& relative) const
{
    return XDG_CONFIG_HOME / relative;
}


Xdg::path Xdg::getConfigHomeDir(const path& relative) const
{
    return XDG_CONFIG_HOME / relative;
}


Xdg::path Xdg::getCacheDir(const string& relative) const
{
    return XDG_CACHE_HOME / relative;
}


Xdg::path Xdg::getCacheDir(const path& relative) const
{
    return XDG_CACHE_HOME / relative;
}


Xdg::path Xdg::getDataDir(const string& relative) const
{
    return FileSystem::find(XDG_DATA_DIRS.cbegin(), XDG_DATA_DIRS.cend(), relative);
}


Xdg::path Xdg::getDataDir(const path& relative) const
{
    return FileSystem::find(XDG_DATA_DIRS.cbegin(), XDG_DATA_DIRS.cend(), relative);
}


Xdg::path Xdg::getConfigDir(const string& relative) const
{
    return FileSystem::find(XDG_CONFIG_DIRS.cbegin(), XDG_CONFIG_DIRS.cend(), relative);
}


Xdg::path Xdg::getConfigDir(const path& relative) const
{
    return FileSystem::find(XDG_CONFIG_DIRS.cbegin(), XDG_CONFIG_DIRS.cend(), relative);
}


} } 

