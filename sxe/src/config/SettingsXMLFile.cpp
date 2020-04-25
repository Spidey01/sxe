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

#include "sxe/config/SettingsXMLFile.hpp"

#if SXE_HAVE_BOOST
#include <boost/property_tree/xml_parser.hpp>
#endif
#include <sxe/logging.hpp>

namespace sxe {  namespace config {

const SettingsXMLFile::string_type SettingsXMLFile::TAG = "SettingsXMLFile";

SettingsXMLFile::SettingsXMLFile()
    : Settings()
#if SXE_HAVE_BOOST
    , mProps()
#endif
    , mPath()
{
}


SettingsXMLFile::SettingsXMLFile(const path_type& path)
    : Settings()
#if SXE_HAVE_BOOST
    , mProps()
#endif
    , mPath(path)
{
    Log::d(TAG, "Loading from " + mPath.string());
#if SXE_HAVE_BOOST
    boost::property_tree::read_xml(mPath.string(), mProps);
#endif
}


SettingsXMLFile::SettingsXMLFile(std::istream& stream)
    : Settings()
#if SXE_HAVE_BOOST
    , mProps()
#endif
    , mPath()
{
    Log::d(TAG, "Loading from stream");
#if SXE_HAVE_BOOST
    boost::property_tree::read_xml(stream, mProps);
#endif
}


SettingsXMLFile::~SettingsXMLFile()
{
}


SettingsXMLFile::KeyList SettingsXMLFile::keys() const
{
    KeyList r;

#if SXE_HAVE_BOOST
    for (auto& v : mProps) {
        r.push_back(v.first);
    }
#endif

    return r;
}


bool SettingsXMLFile::contains(const string_type& key) const
{
#if SXE_HAVE_BOOST
    /*
     * Boost treats dots as nested fields instead of flat. ptree::find don't
     * automate the way the get/put do. Easier to just use get.
     */
    // return mProps.find(key) != mProps.not_found();
    return ! mProps.get<string_type>(key, "").empty();
#else
    return false;
#endif
}


SettingsXMLFile::string_type SettingsXMLFile::getString(const string_type& key) const
{
#if SXE_HAVE_BOOST
    return mProps.get<string_type>(key, "");
#else
    return false;
#endif
}


SettingsXMLFile& SettingsXMLFile::setString(const string_type& key, const string_type& value)
{
#if SXE_HAVE_BOOST
    mProps.put(key, value);
    notifyListeners(key);
#endif
    return *this;
}


void SettingsXMLFile::clear()
{
#if SXE_HAVE_BOOST
    mProps.clear();
#endif
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
#if SXE_HAVE_BOOST
    try {
        boost::property_tree::write_xml(path.string(), mProps);
    } catch(std::runtime_error& ex) {
        Log::e(TAG, "save(): failed writing to " + mPath.string(), ex);
        return false;
    }

    return true;
#else
    return false;
#endif
}


bool SettingsXMLFile::save(std::ostream& stream)
{
#if SXE_HAVE_BOOST
    try {
        boost::property_tree::write_xml(stream, mProps);
    } catch(std::runtime_error& ex) {
        Log::e(TAG, "save(): failed writing to stream.", ex);
        return false;
    }

    return true;
#else
    return false;
#endif
}

} }

