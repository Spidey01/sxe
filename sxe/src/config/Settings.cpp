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

#include <sxe/config/Settings.hpp>
#include <sxe/logging.hpp>

namespace sxe {  namespace config {

const Settings::string_type Settings::TAG = "Settings";

Settings::SettingsManager::size_type Settings::addChangeListener(OnChangedListener callback, const string_type& key)
{
    Log::xtrace(TAG, "addChangeListener(): key: " + key);
    return mSettingsManager.subscribe(callback, key);
}


Settings::SettingsManager::size_type Settings::addChangeListener(OnChangedListener callback)
{
    Log::xtrace(TAG, "addChangeListener():");
    return mSettingsManager.subscribe(callback);
}


void Settings::removeChangeListener(const string_type& key, SettingsManager::size_type id)
{
    Log::xtrace(TAG, "removeChangeListener(): key: " + key + " id: " + std::to_string(id));
    mSettingsManager.unsubscribe(key, id);
}


void Settings::removeChangeListener(SettingsManager::size_type id)
{
    Log::xtrace(TAG, "removeChangeListener(): id: " + std::to_string(id));
    mSettingsManager.unsubscribe(id);
}


bool Settings::getBool(const string_type& key) const
{
    string_type value = getString(key);
    bool v = false;

    if (value.empty())
        v = false;
    else if (value == "true")
        v = true;
    else if (value == "false")
        v = false;
    else
        Log::w(TAG, "getBool(): key: " + key + " value: " + value + " - not true|false!");

    Log::test(TAG, "getBool(): key: " + key + " value: " + string_type(v ? "true" : "false"));
    return v;
}


float Settings::getFloat(const string_type& key) const
{
    float value = 0;

    if (contains(key))
        value = std::stof(getString(key));

    Log::test(TAG, "getFloat(): key: " + key + " value: " + std::to_string(value));
    return value;
}


int Settings::getInt(const string_type& key) const
{
    int value = 0;

    if (contains(key))
        value = std::stoi(getString(key));

    Log::test(TAG, "getInt(): key: " + key + " value: " + std::to_string(value));
    return value;
}


long Settings::getLong(const string_type& key) const
{
    long value = 0;

    if (contains(key))
        value = std::stol(getString(key));

    Log::test(TAG, "getLong(): key: " + key + " value: " + std::to_string(value));
    return value;
}


Settings& Settings::setBool(const string_type& key, bool value)
{
    setString(key, value ? "true" : "false");
    return *this;
}


Settings& Settings::setFloat(const string_type& key, float value)
{
    setString(key, std::to_string(value));
    return *this;
}


Settings& Settings::setInt(const string_type& key, int value)
{
    setString(key, std::to_string(value));
    return *this;
}


Settings& Settings::setLong(const string_type& key, long value)
{
    setString(key, std::to_string(value));
    return *this;
}


void Settings::merge(const Settings& other)
{
    for (string_type key : other.keys()) {
        setString(key, other.getString(key));
    }
}


void Settings::notifyListeners(const string_type& key)
{
    Log::xtrace(TAG, "notifyListeners(): key: " + key);
    mSettingsManager.notifyListeners(key);
}


} }

