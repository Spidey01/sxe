#ifndef SXE_CORE_LOGGING_LOGSINK__HPP
#define SXE_CORE_LOGGING_LOGSINK__HPP
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

#include <sxe/stdheaders.hpp>
#include <boost/filesystem/path.hpp>

namespace sxe { namespace core { namespace logging {

    /** Sink for consuming statements from Log.
     *
     * The default implementation simply works with a std::ostream. Like std::cout or std::clog.
     */
    class LogSink {
      public:

        using shared_ptr = std::shared_ptr<LogSink>;
        using weak_ptr = std::weak_ptr<LogSink>;
        using unique_ptr = std::unique_ptr<LogSink>;

        /** Default log level set in ctors without a level param. */
        static const int DEFAULT_LOG_LEVEL;

        /** Default log name in ctors without a name param. */
        static const char* DEFAULT_LOG_NAME;

        virtual ~LogSink();

        /* Create a log sink from a filesystem path.
         *
         * @param name the base name of the log.
         * @param level the default level.
         * @param path the log file to create.
         */
        LogSink(const std::string& name, int level, const boost::filesystem::path& path);

        /* Create a log sink from an existing ostream.
         *
         * @param name the base name of the log.
         * @param level the default level.
         * @param stream the stream to write to.
         * @param deleteMe if true: delete stream on dtor.
         */
        LogSink(const std::string& name, int level, std::ostream* stream, bool deleteMe);

        /* LogSink(name, level, *stream, false);
         */
        LogSink(const std::string& name, int level, std::ostream& stream);

        /** Uses std::clog and the default constants.
         */
        LogSink();

        void log(int level, const std::string& tag, const std::string& message);

        /** Test if tag loggable.
         *
         * @returns true if messages for tag/level would be written.
         */
        bool isLoggable(const std::string& tag, int level) const;

        /* Returns log level for tag.
         */
        int getLevel(const std::string& tag) const;

        /* Sets log level for tag.
         */
        void setLevel(const std::string& tag, int level);

        int getDefaultLevel() const;

        /** Sets the default log level.
         *
         * This is comparable to setting the level in the constructor but does not
         * modify any levels already set.
         */
        void setDefaultLevel(int level);

        /** Returns the name of the log.
         *
         * This doesn't mean filename or pathname, it's just the 'name' param
         * from the ctor.
         */
        const std::string& getName() const;

        bool getDisplayThreadId() const;
        void setDisplayThreadId(bool x);

        bool getDisplayDate() const;
        void setDisplayDate(bool x);

        bool getDisplayTime() const;
        void setDisplayTime(bool x);

      protected:

        /** Default logging level for the sink. */
        int mDefaultLevel;

        /** Map of log tag -> log level.
         */
        using Filters = std::map<std::string, int>;
        Filters mFilters;

        bool mDisplayThreadId;
        bool mDisplayDate;
        bool mDisplayTime;

        /** Translates log level to word for header().
         */
        virtual std::string translate(int level) const;

        /** Writes the log header to the stream.
         *
         * Default implementation is suitable for plain text logging formats.
         *
         *  - "{translate(Level)}/{tag}( tid={thread id}, date={date}, time={time} ): "
         *
         *  Where the various key=value are determined by the associated
         *  methods, like getDisplayTime().
         */
        virtual void header(int level, const std::string& tag);

      private:

        std::string mName;
        std::ostream* mOutput;
        bool mDeleteMe;
    };

} } }

#endif // SXE_CORE_LOGGING_LOGSINK__HPP
