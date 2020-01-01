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

#include "sxe/logging/LoggingManager.hpp"

#include <sxe/GameEngine.hpp>
#include <sxe/common/Utils.hpp>
#include <sxe/logging/LogSink.hpp>

using std::invalid_argument;
using std::make_shared;
using sxe::logging::LogSink;
using sxe::common::Utils::ends_with;
using sxe::common::Utils::split_str;
using sxe::common::Utils::starts_with;

namespace sxe { namespace logging {

const LoggingManager::string_type LoggingManager::TAG = "LoggingManager";

LoggingManager::LoggingManager()
    : Subsystem(TAG)
    , mOnChangedListenerId(SIZE_MAX)
{
}


LoggingManager::~LoggingManager()
{
}


bool LoggingManager::initialize(GameEngine& engine)
{
    Log::xtrace(TAG, "initialize()");

    auto cb = std::bind(&LoggingManager::onChanged, this, std::placeholders::_1);
    engine.getSettings().addChangeListener(cb);

    return Subsystem::initialize(engine);
}


bool LoggingManager::uninitialize()
{
    Log::xtrace(TAG, "uninitialize()");

    getSettings().removeChangeListener(mOnChangedListenerId );
    mOnChangedListenerId = SIZE_MAX;

    return Subsystem::uninitialize();
}


void LoggingManager::onChanged(string_type key)
{
    Log::xtrace(TAG, "onChanged(): key: " + key);

    constexpr auto npos = string_type::npos;

    auto i = key.rfind(".log_");
    if (i == npos)
        return;

    string_type name = key.substr(0, i);
    LogSink::shared_ptr sink = Log::getLogSink(name);

    if (sink == nullptr && key.rfind(".log_to") != npos) {
        sink = makeLogSink(name, getSettings().getString(key));

        /*
         * This is necessary so existing settings (e.g. .log_level) are
         * applied correctly to the now created log sink.
         */
        editLogSink(*sink);

        Log::add(sink);
    } else if (sink != nullptr) {
        /* Apply whatever settings are currently available. */
        editLogSink(*sink);
    } else {
        /* this log sink doesn't exist yet. Skip for now and edit later. */
        return;
    }
}


LogSink::shared_ptr LoggingManager::makeLogSink(const string_type& name, const string_type& to)
{
    Log::xtrace(TAG, "makeLogSink(): name: " + name + " to: " + to);

    int level = LogSink::DEFAULT_LOG_LEVEL;

    const string_type s = "LoggingManager::makeLogSink(): ";

    if (to.empty()) {
        // not a log spec'
        throw invalid_argument(s + "EMPTY LOG SPEC");
    }
    else if (to == "stdin") {
        throw invalid_argument(s + "Can't log to stdin; maybe you has typo?");
    }
    else if (to == "stdout" || to == "stderr") {
        return make_shared<LogSink>(name, level, (to == "stdout") ? std::cout : std::clog);
    }
    else {
        /* .log_to's value is a file name. */
        return make_shared<LogSink>(name, level, to);
    }
}


void LoggingManager::editLogSink(LogSink& sink)
{
    Log::xtrace(TAG, "editLogSink(): sink.getName(): " + sink.getName());

    const string_type name = sink.getName();
    const int level = getSettings().getInt(name + ".log_level");

    sink.setDefaultLevel(level);

    /*
     * Configure the log_tags for name.
     */

    string_type tags = getSettings().getString(name + ".log_tags");
    if (!tags.empty()) {
        /*
         * Must be done or it'll have the same default level for !log_tags.
         */

        Log::xtrace(TAG, "Setting log_tags");
        sink.setDefaultLevel(0);

        for (const string_type& t : split_str(tags, ',')) {
            Log::xtrace(TAG, "Setting " + name + " to level " + std::to_string(level) + " for tag " + t);
            sink.setLevel(t, level);
        }
    }

    string_type flags = getSettings().getString(name + ".log_flags");
    if (!flags.empty()) {
        Log::xtrace(TAG, "Setting log_flags");
        for (const string_type& f : split_str(flags, ',')) {
            Log::xtrace(TAG, "Setting " + name + " flag " + f);
            bool value = ends_with(f, "=true");
            if (starts_with(f, "DisplayThreadId")) {
                sink.setDisplayThreadId(value);
            } else if (starts_with(f, "DisplayDate")) {
                sink.setDisplayDate(value);
            }
        }
    }
}

} }

