/*-
 * Copyright (c) 2014-current, Terry Mathew Poulin <BigBoss1964@gmail.com>
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

#include "sxe/config/SettingsListener.hpp"

namespace sxe { namespace config {

const std::string SettingsListener::TAG = "SettingsListener";

SettingsListener::SettingsListener(Settings& settings, Settings::OnChangedListener listener)
    : SettingsListener(settings, listener, "")
{
}


SettingsListener::SettingsListener(Settings& settings, Settings::OnChangedListener listener, const Settings::string_type& prefix)
    : mSettings(settings)
    , mListener(listener)
    , mId(mSettings.addChangeListener(std::bind(&SettingsListener::onChangedFilter, this, std::placeholders::_1)))
    , mPrefix(prefix)
{
}


SettingsListener::~SettingsListener()
{
    if (mId != SIZE_MAX)
        mSettings.removeChangeListener(mId);
}


void SettingsListener::setFilter(const Settings::string_type& prefix)
{
    mPrefix = prefix;
}


void SettingsListener::clearFilter()
{
    mPrefix.clear();
}


Settings::string_type SettingsListener::getFilter() const
{
    return mPrefix;
}


void SettingsListener::onChangedFilter(Settings::string_type key)
{
    if (mPrefix.empty())
        mListener(key);
    else if (key.find(mPrefix) == 0)
        mListener(key);
}

} }

