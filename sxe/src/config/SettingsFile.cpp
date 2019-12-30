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

#include "sxe/config/SettingsFile.hpp"

#include <sxe/common/Utils.hpp>
#include <sxe/logging.hpp>
#include <sxe/stdheaders.hpp>

namespace sxe {  namespace config {

const SettingsFile::string_type SettingsFile::TAG = "SettingsFile";

SettingsFile::SettingsFile()
    : SettingsMap()
    , mPath()
{
}


SettingsFile::SettingsFile(const path_type& path)
    : SettingsMap()
    , mPath(path)
{
    Log::d(TAG, "Loading from " + mPath.string());
    std::ifstream input(mPath.string());
    parse(input);
}


SettingsFile::SettingsFile(std::istream& stream)
    : SettingsMap()
    , mPath()
{
    Log::d(TAG, "Loading from stream");
    parse(stream);
}


SettingsFile::~SettingsFile()
{
}


bool SettingsFile::save()
{
    if (mPath.empty()) {
        Log::e(TAG, "save(): no path to save to!");
        return false;
    }

    return save(mPath);
}


bool SettingsFile::save(const path_type& path)
{
    std::ofstream out(path.string());
    return save(out);
}


bool SettingsFile::save(std::ostream& stream)
{
    for (const string_type& key : keys()) {
        string_type value = getString(key);

        stream << key << '=' << value << std::endl;
    }

    return true;
}


void SettingsFile::parse(std::istream& stream)
{
    string_type line;
    size_t no = 0;

    while (stream) {
        std::getline(stream, line);
        no += 1;
        line = common::Utils::trim(line);
        Log::test(TAG, "parse(): path: " + mPath.string() + + ":" + std::to_string(no) + ": line: " + line);

        if (line.empty() || line.front() == '#' || line.front() == ';' ||
            line.find("//") == 0 || line.find("--") == 0)
            continue;

        auto equals = line.find('=');

        auto key = line.substr(0, equals);
        Log::test(TAG, "parse(): path: " + mPath.string() + + ":" + std::to_string(no) + ": parsed key: " + key);

        auto value = line.substr(equals + 1);
        Log::test(TAG, "parse(): path: " + mPath.string() + + ":" + std::to_string(no) + ": parsed value: " + value);

        setString(key, value);
    }
}

} }

