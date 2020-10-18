/*-
 * Copyright (c) 2020-current, Terry Mathew Poulin <BigBoss1964@gmail.com>
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

#include <sxe/config/SettingsEnvironment.hpp>
#include <sxe/logging.hpp>
#include <sxe/common/Utils.hpp>

/*
 * Not all platforms do this the same way.
 * 
 * std::getenv() is a classic C function that is pretty universal.
 * POSIX systems use setenv(name, value, 1)
 * WIN32 desktop uses _putenv_s(), etc.
 * 
 * Some platforms like UWP apps do not have environment variables.
 */

#if defined(__unix__) || defined(_GNU_SOURCE)
/* for "char **environ" */
#include <unistd.h>
#endif

namespace sxe {  namespace config {

const SettingsEnvironment::string_type SettingsEnvironment::TAG = "SettingsEnvironment";

SettingsEnvironment::SettingsEnvironment()
    : SettingsEnvironment(false)
{

}

SettingsEnvironment::SettingsEnvironment(bool cache)
    : mKeys()
{
    if (cache)
        cacheEnvironment();
}

void SettingsEnvironment::cacheEnvironment()
{
    all_env(*this);
}

SettingsEnvironment::KeyList SettingsEnvironment::keys() const
{
    KeyList r;

    for (const string_type& k : mKeys) {
        r.push_back(k);
    }

    return r;
}


bool SettingsEnvironment::contains(const string_type& key) const
{
    return !get_env(key).empty();
}


SettingsEnvironment::string_type SettingsEnvironment::getString(const string_type& key) const
{
    string_type value;

    if (contains(key))
        value = get_env(key);

    Log::test(TAG, "getString(): key: " + key + " => value: " + value);
    return value;
}


SettingsEnvironment& SettingsEnvironment::setString(const string_type& key, const string_type& value)
{
    Log::test(TAG, "setString(): key: " + key + " => value: " + value);

    if (!set_env(key, value)) {
        Log::w(TAG, "setString(): set_env() failed!");
        return *this;
    }

    if (value.empty()) {
        Log::test(TAG, "setString(): erase key");
        mKeys.erase(key);
    } else {
        Log::test(TAG, "setString(): insert key");
        mKeys.insert(key);
    }

    notifyListeners(key);
    return *this;
}


void SettingsEnvironment::clear()
{
    Log::xtrace(TAG, "clear():");

    for (const string_type& k : keys()) {
        setString(k, "");
    }
}


bool SettingsEnvironment::save()
{
    Log::d(TAG, "save(): no-op");
    return true;
}


SettingsEnvironment::string_type SettingsEnvironment::get_env(const string_type& var)
{
#if defined(WINAPI_FAMILY_PARTITION)

#if !WINAPI_FAMILY_PARTITION(WINAPI_PARTITION_DESKTOP)

    Log::w(TAG, "Windows only supports environment variables on the desktop API.");
    return "";

#else

    char* p = std::getenv(var.c_str());
    if (p == nullptr)
        return "";

    return string_type(p);

#endif

#else

    char* p = std::getenv(var.c_str());
    if (p == nullptr)
        return "";

    return string_type(p);

#endif
}


bool SettingsEnvironment::set_env(const string_type& var, const string_type& value) 
{

#if defined(__unix__) || defined(_GNU_SOURCE)

    int rc = setenv(var.c_str(), value.c_str(), 1);

    if (rc != 0) {
        Log::e(TAG, string_type("set_env(): setenv() failed: ") + strerror(errno));
    }

    return rc == 0;

#elif defined(WINAPI_FAMILY_PARTITION)

#if !WINAPI_FAMILY_PARTITION(WINAPI_PARTITION_DESKTOP)

    Log::w(TAG, "Windows only supports environment variables on the desktop API.");
    return false;

#else

    errno_t rc = _putenv_s(var.c_str(), value.c_str());

    if (rc != 0) {
        string_type err(std::strerror(rc));
        Log::e(TAG, "set_env() _putenv_s() failed: " + err);
    }

    return rc == 0;

#endif

#else

    Log::w(TAG, "No environment variables on this platform?");
    return false;

#endif
}

void SettingsEnvironment::all_env(SettingsEnvironment& self)
{
    // to environ.
    char** env = nullptr;

    /*
     * Non portable as hell if you have the typically key equal value (e.g. var=val) global environ pointer...
     */

#if defined(__unix__) || defined(_GNU_SOURCE)
    env = environ;
#elif defined(WINAPI_FAMILY_PARTITION) && WINAPI_FAMILY_PARTITION(WINAPI_PARTITION_DESKTOP)
    env = _environ;
#endif

    if (env == nullptr)
        return;


    char *p = nullptr;
    for (p=*env; *env != nullptr && p != nullptr; ++env, p=*env)
    {
        string_type kev = p;
        Log::test(TAG, "parse: " + kev);

        size_t equals = kev.find('=');

        if (equals == string_type::npos) {
            Log::w(TAG, "Cannot pre cache keys because the environ table is not in the common PC format.");
            self.mKeys.clear();
            break;
        }

        Log::test(TAG, "cache: " + kev.substr(0, equals));
        self.mKeys.insert(kev.substr(0, equals));
    }
}

} }
