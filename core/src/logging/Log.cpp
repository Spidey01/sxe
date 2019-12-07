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

#include "sxe/core/logging/Log.hpp"

using std::string;

namespace sxe { namespace core { namespace logging {

using LogSinkListPrivate = std::list<LogSink::shared_ptr>;
static LogSinkListPrivate sSinks;

Log::lock_guard::mutex_type Log::sMutex;

void Log::wtf(const std::string& tag, const std::string& message)
{
    log(ASSERT, tag, message);
    static const char* msg = "What a Terrible Failure Report we has here";
    assert(msg == nullptr);
}


void Log::e(const std::string& tag, const std::string& message)
{
    log(ERROR, tag, message);
}


void Log::w(const std::string& tag, const std::string& message)
{
    log(WARN, tag, message);
}


void Log::i(const std::string& tag, const std::string& message)
{
    log(INFO, tag, message);
}


void Log::d(const std::string& tag, const std::string& message)
{
    log(DEBUG, tag, message);
}


void Log::v(const std::string& tag, const std::string& message)
{
    log(VERBOSE, tag, message);
}


void Log::xtrace(const std::string& tag, const std::string& message)
{
    log(TRACE, tag, message);
}


void Log::test(const std::string& tag, const std::string& message)
{
    log(TEST, tag, message);
}


void Log::log(int level, const std::string& tag, const std::string& message)
{
    lock_guard synchronized(sMutex);

    for (auto sink : sSinks) {
        sink->log(level, tag, message);
    }
}


void Log::add(LogSink::shared_ptr sink)
{
    if (!sink) return;

    lock_guard synchronized(sMutex);

    sSinks.push_back(sink);
}


void Log::remove(LogSink::shared_ptr sink)
{
    lock_guard synchronized(sMutex);

    sSinks.remove(sink);
}


Log::LogSinkList Log::getSinks()
{
    lock_guard synchronized(sMutex);
    LogSinkList list;

    for (auto shared : sSinks) {
        list.push_back(shared);
    }

    return list;
}


bool Log::isLoggable(const string& tag, int level)
{
    lock_guard synchronized(sMutex);

    for (auto sink : sSinks) {
        if (sink->isLoggable(tag, level)) {
            return true;
        }
    }

    return false;
}


void Log::setLevel(const string& tag, int level)
{
    lock_guard synchronized(sMutex);

    for (auto sink : sSinks) {
        sink->setLevel(tag, level);
    }
}


} } }
