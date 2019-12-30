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

#include "sxe/logging/LogSink.hpp"
#include "sxe/logging/Log.hpp"

using std::string;

namespace sxe {  namespace logging {

const int LogSink::DEFAULT_LOG_LEVEL = Log::INFO;
const char* LogSink::DEFAULT_LOG_NAME = "debug";

LogSink::~LogSink()
{
    if (mDeleteMe && mOutput != nullptr)
        delete mOutput;
}


LogSink::LogSink(const string& name, int level, const boost::filesystem::path& path)
    : LogSink(name, level, new std::fstream(path.string(), std::ios::app), true)
{
}


LogSink::LogSink(const string& name, int level, std::ostream* stream, bool deleteMe)
    : mDefaultLevel(level)
    , mFilters()
    , mDisplayThreadId(true)
    , mDisplayDate(true)
    , mDisplayTime(true)
    , mName(name)
    , mOutput(stream)
    , mDeleteMe(deleteMe)
{
}


LogSink::LogSink(const string& name, int level, std::ostream& stream)
    : LogSink(name, level, &stream, false)
{
}


LogSink::LogSink()
    : LogSink(DEFAULT_LOG_NAME, DEFAULT_LOG_LEVEL, std::clog)
{
}


void LogSink::log(int level, const std::string& tag, const std::string& message)
{
    if (message.empty())
        return;

    if (level > getLevel(tag))
        return;

    header(level, tag);

    *mOutput << message << std::endl;
}


bool LogSink::isLoggable(const string& tag, int level) const
{
    return level == getLevel(tag);
}


int LogSink::getLevel(const string& tag) const
{
    auto pair = mFilters.find(tag);

    return (pair == mFilters.cend() ? mDefaultLevel : pair->second);
}


void LogSink::setLevel(const string& tag, int level)
{
    mFilters[tag] = level;
}


int LogSink::getDefaultLevel() const
{
    return mDefaultLevel;
}


void LogSink::setDefaultLevel(int level)
{
    mDefaultLevel = level;
}


const string& LogSink::getName() const
{
    return mName;
}


bool LogSink::getDisplayThreadId() const
{
    return mDisplayThreadId;
}


void LogSink::setDisplayThreadId(bool x)
{
    mDisplayThreadId = x;
}


bool LogSink::getDisplayDate() const
{
    return mDisplayDate;
}


void LogSink::setDisplayDate(bool x)
{
    mDisplayDate = x;
}


bool LogSink::getDisplayTime() const
{
    return mDisplayTime;
}


void LogSink::setDisplayTime(bool x)
{
    mDisplayTime = x;
}


string LogSink::translate(int level) const
{
    switch (level) {
        case Log::ASSERT: return "ASSERT";
        case Log::ERROR: return "e";
        case Log::WARN: return "w";
        case Log::INFO: return "i";
        case Log::DEBUG: return "d";
        case Log::TRACE: return "x";
        case Log::TEST: return "TEST";

        default: return "WTF";
    }
}


void LogSink::header(int level, const string& tag)
{
    *mOutput << translate(level) << '/' << tag;
    *mOutput << '(';

    bool prefixComma = false;

    if (mDisplayThreadId) {
        *mOutput << " tid=" << std::this_thread::get_id();
        prefixComma = true;
    }

    auto t_now = std::time(nullptr);
    std::tm now = *std::gmtime(&t_now);

    if (mDisplayDate) {
        if (prefixComma)
            *mOutput << ',';
        else
            prefixComma = true;

        static const char* format = "%Y-%m-%d";
        *mOutput << " date=" << std::put_time(&now, format);
    }

    if (mDisplayTime) {
        if (prefixComma)
            *mOutput << ',';
        else
            prefixComma = true;

        static const char* format = "%H:%M:%S";
        *mOutput << " time=" << std::put_time(&now, format);
    }

    *mOutput << " ): ";
}


} }

