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
#include <sxe/sys/FileSystem.hpp>
#include <sxe/sys/Platform.hpp>

using std::getenv;
using sxe::sys::FileSystem::path;
using sxe::sys::FileSystem::string;
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

    p = getenv("XDG_DATA_DIRS");
    if (p != nullptr) {
        split<list, string>(XDG_DATA_DIRS, p, sep);
    } else {
        XDG_DATA_DIRS.push_back("/usr/local/share");
        XDG_DATA_DIRS.push_back("/usr/share");
    }

    p = getenv("XDG_CONFIG_HOME");
    if (p != nullptr) {
        XDG_CONFIG_HOME = p;
    } else {
        XDG_CONFIG_HOME /= USER_DIR / ".config";
    }

    p = getenv("XDG_CONFIG_DIRS");
    if (p != nullptr) {
        split<list, string>(XDG_CONFIG_DIRS, p, sep);
    } else {
        XDG_CONFIG_DIRS.push_back("/etc/xdg");
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


path Xdg::getDataHomeDir(const string& relative) const
{
    return XDG_DATA_HOME / relative;
}


path Xdg::getConfigHomeDir(const string& relative) const
{
    return XDG_CONFIG_HOME / relative;
}


path Xdg::getCacheDir(const string& relative) const
{
    return XDG_CACHE_HOME / relative;
}


path Xdg::getDataDir(const string& relative) const
{
    return FileSystem::find(XDG_DATA_DIRS.cbegin(), XDG_DATA_DIRS.cend(), relative);
}


path Xdg::getConfigDir(const string& relative) const
{
    return FileSystem::find(XDG_CONFIG_DIRS.cbegin(), XDG_CONFIG_DIRS.cend(), relative);
}


} } 

