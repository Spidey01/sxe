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

#include "sxe/common/Utils.hpp"


namespace sxe {  namespace common { namespace Utils {

bool SXE_PUBLIC notSpace(int ch)
{
    return !std::isspace(ch);
}


std::string SXE_PUBLIC trim(const std::string& input)
{
    std::string copy = input;

    copy.erase(copy.begin(),
               std::find_if(copy.begin(), copy.end(), &notSpace));
    copy.erase(std::find_if(copy.rbegin(), copy.rend(), &notSpace).base(),
               copy.end());

    return copy;
}


bool starts_with(const std::string& str, const std::string& prefix)
{
    return str.size() >= prefix.size() && str.compare(0, prefix.size(), prefix) == 0;
}


bool ends_with(const std::string& str, const std::string& suffix)
{
    return str.size() >= suffix.size() && str.compare(str.size() - suffix.size(), std::string::npos, suffix) == 0;
}

} } }

