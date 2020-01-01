#ifndef SXE_LOGGING_LOGGINGMANAGER__HPP
#define SXE_LOGGING_LOGGINGMANAGER__HPP
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

#include <sxe/api.hpp>
#include <sxe/common/NotificationManager.hpp>
#include <sxe/common/Subsystem.hpp>
#include <sxe/config/Settings.hpp>
#include <sxe/logging/LogSink.hpp>

namespace sxe { namespace logging {

    /** Subsystem to manage logging.
     *
     * This subscribes to settings changes: creating and editing log sinks as settings are changed.
     *
     * For any top level setting, "foo", the following ".properties" are reacted to:
     *
     *   - log_to:
     *     + stdout = standard output stream.
     *     + stderr = standard error stream.
     *     + x = file named x, relative to current working directory.
     *   - log_level:
     *     + Integer passed to LogSink for logging level.
     *   - log_tags:
     *     + Comma separated list of tags.
     *     + Default log level will be set to 0.
     *     + Log level for each tag will be set to log_level.
     *   - log_flags:
     *     + Comma separated list of flag=value.
     *     + Calls set{Flag}({value}.
     *     + E.g. DisplayThreadId=true calls LogSink::setDisplayThreadId(true).
     */
    class SXE_PUBLIC LoggingManager : public common::Subsystem
    {
      public:
        LoggingManager();
        virtual ~LoggingManager();

        bool initialize(GameEngine& engine) override;
        bool uninitialize() override;

      private:
        static const string_type TAG;
        config::Settings::SettingsManager::size_type mOnChangedListenerId;

        void onChanged(string_type key);
        LogSink::shared_ptr makeLogSink(const string_type& name, const string_type& to);
        void editLogSink(LogSink& sink);
    };

} }

#endif // SXE_LOGGING_LOGGINGMANAGER__HPP
