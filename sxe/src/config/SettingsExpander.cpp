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

#include "sxe/config/SettingsExpander.hpp"

#include <sxe/config/Settings.hpp>
#include <sxe/logging.hpp>

namespace sxe { namespace config {

const SettingsExpander::string_type SettingsExpander::TAG = "SettingsExpander";

SettingsExpander::SettingsExpander(Settings& s)
    : mSettings(s)
    , mBuffer()
{
}


SettingsExpander::string_type SettingsExpander::expand(const string_type& input, char prefix, char openMarker, char closeMarker, char escape)
{
    using std::to_string;
    using std::stringstream;

    stringstream logMsg;
    logMsg << "expand():"
        << " input: " << input
        << " prefix: " << prefix
        << " openMarker: " << openMarker
        << " closeMarker: " << closeMarker
        << " escape: " << escape
        ;

    Log::test(TAG, logMsg.str());

    Log::test(TAG, "expand(): input => \"" + input + "\"");

    string_type buffer = input;
    string_type output;

    std::regex syntax("\\$\\{[a-zA-Z0-9]+\\}");

    auto begin = std::sregex_iterator(buffer.begin(), buffer.end(), syntax);
    auto end = std::sregex_iterator();

    for (std::sregex_iterator it=begin; it != end; ++it) {
        std::smatch match = *it;
        Log::test(TAG, "matched: " + match.str() + " position: " + to_string(match.position()));
        string_type var = match.str().substr(2);
        var.pop_back();

        Log::test(TAG, "gotta expand: " + var);
        string_type val = expand(mSettings.getString(var), prefix, openMarker, closeMarker, escape);

        output.clear();
        std::regex_replace(std::back_inserter(output),
                           buffer.begin(), std::next(buffer.begin(), match.position() + match.length()),
                           syntax, val);
        buffer = output;
        size_t pos = match.position() == 0 ? 0 : (match.position() - 1);
        buffer += input.substr(pos + match.length());
        Log::test(TAG, "buffer: " + buffer);
    }

    if (output.empty())
        output = input;
    Log::test(TAG, "returning: \"" + output + "\"");
    return output;
}


SettingsExpander::string_type SettingsExpander::expand(const string_type& key)
{
    return expand(mSettings.getString(key), '$', '{', '}', '\\');
}


} }

