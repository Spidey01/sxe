#ifndef SXE_LOGGING_LOG__HPP
#define SXE_LOGGING_LOG__HPP
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

#include <sxe/api.hpp>
#include <sxe/logging/LogSink.hpp>

namespace sxe { namespace logging {

    /** Logging support for SxE.
     *
     * The goal here is to generally do the right thing.
     *
     * Logging is a simple Android inspired API based on log levels. Each log level
     * has a set of related methods.  This is backwards compatable with the Android
     * API, while allowing good freedom for a more specialized logging API.
     *
     * In SxE the destination of log messages can be customized. Everything is
     * logged to a collection LogSink's according to the log levels and tags
     * supplied with the message.  Logging is controlled by a per 'tag' log level.
     * So it is entirely possible to send log data to multiple sources and to
     * configure each source based on tag/level.
     *
     * By default there are no log sinks, which causes all log data to be silenced.
     *
     * There are two ways to use Log:
     *
     *   1. Invoke static methods.
     *     - These are the primary interface to make the magic happen.
     *
     *   2. Create a Log instance.
     *     - Provides a way to do mLog.foo(...).
     *     - Calls the static methods with data filled in from ctor.
     *
     */
    class SXE_PUBLIC Log
        : public common::stdtypedefs<Log>
    {
      public:

        // Android mimicery: least to most: ERROR, WARN, INFO, DEBUG, VERBOSE.
        enum {
            ASSERT  = 0,
            ERROR   = 1,
            WARN    = 2,
            INFO    = 3,
            DEBUG   = 4,
            VERBOSE = 5,
            TRACE   = 10,
            TEST    = 999,
        };

        /** Returns enum value as string.
         *
         * Unknown values are mapped to "".
         */
        static std::string levelToString(int level);

        /** Returns enum value for string.
         *
         * For each enum value, its string form (e.g. "ERROR") and its base-10
         * form (e.g. 1) are supported as input. If the former fails,
         * std::stoi() will be tried before declaring it an unknown value.
         *
         * Unknown values are mapped to Log::ASSERT.
         */
        static int stringToLevel(const std::string& level);

        /** Send an ASSERT message.
         *
         * Report a condition that should never happen. A false assertion will also
         * be triggered if assertions are enabled.  Android and the assertion
         * method calls this "What a Terrible Failure" but you may call it by other
         * names if you wish ;).
         */
        static void wtf(const std::string& tag, const std::string& message);
        static void wtf(const std::string& tag, const std::string& message, const std::exception& error);

        /** Send an ERROR message. */
        static void e(const std::string& tag, const std::string& message);
        static void e(const std::string& tag, const std::string& message, const std::exception& error);

        /** Send an WARN message. */
        static void w(const std::string& tag, const std::string& message);
        static void w(const std::string& tag, const std::string& message, const std::exception& error);

        /** Send an INFO message. */
        static void i(const std::string& tag, const std::string& message);
        static void i(const std::string& tag, const std::string& message, const std::exception& error);

        /** Send an DEBUG message. */
        static void d(const std::string& tag, const std::string& message);
        static void d(const std::string& tag, const std::string& message, const std::exception& error);

        /** Send an VERBOSE message. */
        static void v(const std::string& tag, const std::string& message);
        static void v(const std::string& tag, const std::string& message, const std::exception& error);

        /** Send an TRACE message. */
        static void xtrace(const std::string& tag, const std::string& message);
        static void xtrace(const std::string& tag, const std::string& message, const std::exception& error);

        /** Send an TEST message. */
        static void test(const std::string& tag, const std::string& message);
        static void test(const std::string& tag, const std::string& message, const std::exception& error);

        /** Send a message to all sinks.
        */
        static void log(int level, const std::string& tag, const std::string& message);

        /** Adds exception to log message.
         */
        static void log(int level, const std::string& tag, const std::string& message, const std::exception& error);

        /** Add log sink.
         */
        static void add(LogSink::shared_ptr sink);

        /** Remove log sink.
         */
        static void remove(LogSink::shared_ptr sink);

        using LogSinkList = std::list<LogSink::weak_ptr>;

        static LogSinkList getSinks();

        static LogSink::shared_ptr getLogSink(const std::string& name);

        /** Return if tag is loggable at level.
         */
        static bool isLoggable(const std::string& tag, int level);

        /** Convenience method that sets the level of tag for every sink. */
        static void setLevel(const std::string& tag, int level);

        /** Creates a log instance for tag.
         *
         * Use this if you want to do something like mLog.xtrace(msg, ex) instead of Log::xtrace(TAG, msg, ex).
         *
         * Use this if you want to pass an object that other code can log without having to know the tag.
         *
         * You could also inherit this.
         */
        Log(const std::string& tag);

        /** Change the logging tag for this instance.
         *
         * @param tag sets mTag.
         */
        void setInstanceTag(const std::string& tag);

        /** Returns the tag for this instance.
         */
        std::string getInstanceTag() const;

        /** Calls the static version. */
        void log(int level, const std::string& message);

        /** Calls the static version. */
        void log(int level, const std::string& message, const std::exception& error);

        void wtf(const std::string& message);
        void wtf(const std::string& message, const std::exception& error);
        void e(const std::string& message);
        void e(const std::string& message, const std::exception& error);
        void w(const std::string& message);
        void w(const std::string& message, const std::exception& error);
        void i(const std::string& message);
        void i(const std::string& message, const std::exception& error);
        void d(const std::string& message);
        void d(const std::string& message, const std::exception& error);
        void v(const std::string& message);
        void v(const std::string& message, const std::exception& error);
        void xtrace(const std::string& message);
        void xtrace(const std::string& message, const std::exception& error);
        void test(const std::string& message);
        void test(const std::string& message, const std::exception& error);


      private:
        using lock_guard = std::lock_guard<std::recursive_mutex>;

        /* Used for synchronizing access to log sinks.
         */
        static lock_guard::mutex_type sMutex;

        /** Instance tag.
         */
        std::string mTag;
    };


} }

#endif // SXE_LOGGING_LOG__HPP
