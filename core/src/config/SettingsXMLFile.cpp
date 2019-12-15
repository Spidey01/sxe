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

#include "sxe/core/config/SettingsXMLFile.hpp"

#include <boost/property_tree/xml_parser.hpp>
#include <sxe/logging.hpp>

namespace sxe { namespace core { namespace config {

const SettingsXMLFile::string_type SettingsXMLFile::TAG = "SettingsXMLFile";

SettingsXMLFile::SettingsXMLFile()
    : Settings()
    , mProps()
    , mPath()
{
}


SettingsXMLFile::SettingsXMLFile(const path_type& path)
    : Settings()
    , mProps()
    , mPath(path)
{
    Log::d(TAG, "Loading from " + mPath.string());
    boost::property_tree::read_xml(mPath.string(), mProps);
}


SettingsXMLFile::SettingsXMLFile(std::istream& stream)
    : Settings()
    , mProps()
    , mPath()
{
    Log::d(TAG, "Loading from stream");
    boost::property_tree::read_xml(stream, mProps);
}


SettingsXMLFile::~SettingsXMLFile()
{
}


SettingsXMLFile::KeyList SettingsXMLFile::keys() const
{
    KeyList r;

    for (auto& v : mProps) {
        r.push_back(v.first);
    }
    return r;
}


bool SettingsXMLFile::contains(const string_type& key) const
{
    return mProps.find(key) != mProps.not_found();
}


SettingsXMLFile::string_type SettingsXMLFile::getString(const string_type& key) const
{
    return mProps.get<string_type>(key, "");
}


SettingsXMLFile& SettingsXMLFile::setString(const string_type& key, const string_type& value)
{
    mProps.put(key, value);
    notifyListeners(key);
    return *this;
}


void SettingsXMLFile::clear()
{
    mProps.clear();
}


bool SettingsXMLFile::save()
{
    if (mPath.empty()) {
        Log::e(TAG, "save(): no path to save to!");
        return false;
    }

    return save(mPath);
}


bool SettingsXMLFile::save(const path_type& path)
{
    try {
        boost::property_tree::write_xml(path.string(), mProps);
    } catch(std::runtime_error& ex) {
        Log::e(TAG, "save(): failed writing to " + mPath.string(), ex);
        return false;
    }

    return true;
}


bool SettingsXMLFile::save(std::ostream& stream)
{
    try {
        boost::property_tree::write_xml(stream, mProps);
    } catch(std::runtime_error& ex) {
        Log::e(TAG, "save(): failed writing to stream.", ex);
        return false;
    }

    return true;
}

} } }

