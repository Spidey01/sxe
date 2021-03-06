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

#include <sxe/config/SettingsMap.hpp>
#include <sxe/logging.hpp>

namespace sxe {  namespace config {

const SettingsMap::string_type SettingsMap::TAG = "SettingsMap";

SettingsMap::SettingsMap()
    : mMap()
{
}


SettingsMap::SettingsMap(int count, char** args)
    : SettingsMap()
{
    Log::test(TAG, "start parsing count/args; count = " + std::to_string(count));
    for (int i=0; i < count; ++i) {
        Log::test(TAG, std::to_string(i));
        string_type arg = args[i];
        Log::test(TAG, "args[" + std::to_string(i) + "]: " + arg);

        auto sep = arg.find_last_of("=");

        string_type key = arg.substr(0, sep);
        Log::test(TAG, "key: " + key);

        string_type value = arg.substr(sep + 1);
        Log::test(TAG, "value: " + value);

        mMap[key] = value;
    }
    Log::test(TAG, "done parsing count/args");
}


SettingsMap::KeyList SettingsMap::keys() const
{
    KeyList r;

    for (auto pair : mMap) {
        r.push_back(pair.first);
    }

    return r;
}


bool SettingsMap::contains(const string_type& key) const
{
    return mMap.find(key) != mMap.cend();
}


SettingsMap::string_type SettingsMap::getString(const string_type& key) const
{
    string_type value;

    if (contains(key))
        value = mMap.at(key);

    Log::test(TAG, "getString(): key: " + key + " => value: " + value);
    return value;
}


SettingsMap& SettingsMap::setString(const string_type& key, const string_type& value)
{
    Log::test(TAG, "setString(): key: " + key + " => value: " + value);
    mMap[key] = value;

    notifyListeners(key);
    return *this;
}


void SettingsMap::clear()
{
    Log::xtrace(TAG, "clear()");
    mMap.clear();
}


bool SettingsMap::save()
{
    Log::d(TAG, "save(): no-op");
    return true;
}


} }

