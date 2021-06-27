/*-
 * Copyright (c) 2021-current, Terry Mathew Poulin <BigBoss1964@gmail.com>
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

#include <sxe/config/SettingsAdapter.hpp>
#include <sxe/logging.hpp>

namespace sxe {  namespace config {

const SettingsAdapter::string_type SettingsAdapter::TAG = "SettingsAdapter";

SettingsAdapter::SettingsAdapter(Settings& settings, const string_list& prefixes, const string_type& sep)
    : mSettings(settings)
    , mPrefixList(prefixes)
    , mSeperator(sep)
    , mKeysReturnsAllPossibleForms(false)
{
}


SettingsAdapter::SettingsAdapter(Settings& settings, const string_list& prefixes)
    : SettingsAdapter(settings, prefixes, ".")
{
}


SettingsAdapter::KeyList SettingsAdapter::keys() const
{
    KeyList r;

    for (const KeyList::value_type& k : mSettings.get().keys()) {
        if (mKeysReturnsAllPossibleForms) {
            for (const string_type& prefix : mPrefixList) {
                string_type pk = prefix + mSeperator + k;
                Log::test(TAG, "keys(): insert prefixed " + pk);
                r.push_back(pk);
            }
        }
        Log::test(TAG, "keys(): insert base " + k);
        r.push_back(k);
    }

    return r;
}

void SettingsAdapter::keysReturnsAllPossibleForms(bool yes)
{
    Log::xtrace(TAG, "keysReturnsAllPossibleForms(): yes: " + string_type(yes ? "true" : "false"));
    mKeysReturnsAllPossibleForms = yes;
}


bool SettingsAdapter::contains(const string_type& key) const
{
    for (const string_type& prefix : mPrefixList) {
        string_type p = prefix + mSeperator + key;
        Log::test(TAG, "contains(): try key: " + p + " for key: " + key);
        if (mSettings.get().contains(p)) {
            return true;
        }
    }

    return mSettings.get().contains(key);
}


SettingsAdapter::string_type SettingsAdapter::getString(const string_type& key) const
{
    string_type value = mSettings.get().getString(key);

    for (const string_type& prefix : mPrefixList) {
        string_type p = prefix + mSeperator + key;
        Log::test(TAG, "getString(): try key: " + p + " for key: " + key);
        if (mSettings.get().contains(p)) {
            value = mSettings.get().getString(p);
            break;
        }
    }

    Log::test(TAG, "getString(): key: " + key + " => value: " + value);
    return value;
}


SettingsAdapter& SettingsAdapter::setString(const string_type& key, const string_type& value)
{
    Log::test(TAG, "setString(): key: " + key + " => value: " + value);

    mSettings.get().setString(key, value);

    notifyListeners(key);

    return *this;
}

void SettingsAdapter::clear()
{
    Log::xtrace(TAG, "clear()");
    mSettings.get().clear();
}


bool SettingsAdapter::save()
{
    Log::d(TAG, "save()");
    return mSettings.get().save();
}

} }
