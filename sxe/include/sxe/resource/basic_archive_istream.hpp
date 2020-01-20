#ifndef SXE_RESOURCE_ARCHIVEISTREAM__HPP
#define SXE_RESOURCE_ARCHIVEISTREAM__HPP
/*-
 * Copyright (c) 2020-current, Terry Mathew Poulin <BigBoss1964@gmail.com>
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
#include <sxe/logging/Log.hpp>
#include <sxe/resource/basic_archive_streambuf.hpp>
#include <sxe/resource/basic_archive_typedefs.hpp>
#include <sxe/stdheaders.hpp>

namespace sxe { namespace resource {

    /** Implements std::basic_istream<> for libarchive targets.
     *
     * Gemerally this works like the standard istreams. Except operations like
     * open take two paraemters: one to specify the archive to open, and one to
     * specify the entry in to take input from.
     */
    template<class CharT, class Traits=std::char_traits<CharT>>
    class basic_archive_istream
        : public std::basic_istream<CharT, Traits>
        , public basic_archive_typedefs<CharT, Traits>
    {
      public:

        using typedefs_ = basic_archive_typedefs<CharT, Traits>;
        using istream_type_ = std::basic_istream<CharT, Traits>;
        using traits_type = typename istream_type_::traits_type;
        using int_type = typename istream_type_::int_type;
        using char_type = typename istream_type_::char_type;
        using string_type = typename typedefs_::string_type;
        using path_type = typename typedefs_::path_type;
        using openmode_type_ = typename typedefs_::openmode_type_;
        using streambuf_type_ = basic_archive_streambuf<char_type, traits_type>;


        basic_archive_istream()
            : istream_type_(nullptr)
            , mStreamBuf()
            , mLog("basic_archive_istream<>")
        {
            /* Single most important line, ever. */
            istream_type_::set_rdbuf(&mStreamBuf);
        }


        virtual ~basic_archive_istream()
        {
            close();
        }


        /** Input from specified archive and filename.
         *
         * @param archive the archive to open.
         * @param filename the entry to get input from.
         * @param mode same as iostreams.
         */
        explicit basic_archive_istream(const char* archive, const char* filename,
                                       openmode_type_ mode=std::ios_base::in)
            : basic_archive_istream()
        {
            open(archive, filename, mode);
        }


        /** Input from specified archive and filename.
         *
         * @param archive the archive to open.
         * @param filename the entry to get input from.
         * @param mode same as iostreams.
         */
        explicit basic_archive_istream(const string_type& archive, const string_type& filename,
                                       openmode_type_ mode=std::ios_base::in)
            : basic_archive_istream()
        {
            open(archive, filename, mode);
        }


        /** Input from specified archive and filename.
         *
         * @param archive the archive to open.
         * @param filename the entry to get input from.
         * @param mode same as iostreams.
         */
        explicit basic_archive_istream(const path_type& archive, const path_type& filename,
                                       openmode_type_ mode=std::ios_base::in)
            : basic_archive_istream()
        {
            open(archive, filename, mode);
        }


        basic_archive_istream(const basic_archive_istream& other) = delete;


        basic_archive_istream(basic_archive_istream&& other)
            : istream_type_(std::move(other))
            , mStreamBuf(std::move(other.mStreamBuf))
            , mLog(std::move(other.mLog))
        {
            istream_type_::set_rdbuf(&mStreamBuf);
        }



        basic_archive_istream& operator=(const basic_archive_istream&) = delete;


        basic_archive_istream& operator=(basic_archive_istream&& other)
        {
            if (this == &other) return *this;

            istream_type_::operator=(std::move(other));
            mStreamBuf = std::move(other.mStreamBuf);
            mLog = std::move(other.mLog);

            return *this;
        }

        void swap(basic_archive_istream& other)
        {
            istream_type_::swap(other);
            mStreamBuf.swap(other.mStreamBuf);
            std::swap(mLog, other.mLog);
        }


        bool is_open() const
        {
            return mStreamBuf.is_open();
        }


        void open(const char* archive, const char* filename, openmode_type_ mode=std::ios_base::in)
        {
            if (!mStreamBuf.open(archive, filename, mode | std::ios_base::in)) {
                mLog.e("open():" + string_type(archive) + ":" + string_type(filename) + ": setting failbit.");
                this->setstate(std::ios_base::failbit);
            } else {
                mLog.test("open():" + string_type(archive) + ":" + string_type(filename) + ": success.");
                this->clear();
            }
        }


        void open(const string_type& archive, const string_type& filename, openmode_type_ mode=std::ios_base::in)
        {
            if (!mStreamBuf.open(archive, filename, mode | std::ios_base::in)) {
                mLog.e("open():" + archive + ":" + filename + ": setting failbit.");
                this->setstate(std::ios_base::failbit);
            } else {
                mLog.test("open():" + archive + ":" + filename + ": success.");
                this->clear();
            }
        }


        void open(const path_type& archive, const path_type& filename, openmode_type_ mode=std::ios_base::in)
        {
            if (!mStreamBuf.open(archive, filename, mode | std::ios_base::in)) {
                mLog.e("open():" + archive.string() + ":" + filename.string() + ": setting failbit.");
                this->setstate(std::ios_base::failbit);
            } else {
                mLog.test("open():" + archive.string() + ":" + filename.string() + ": success.");
                this->clear();
            }
        }


        void close()
        {
            mLog.test("close(): mStreamBuf.archive(): " + mStreamBuf.archive() + " mStreamBuf.filename():" + mStreamBuf.filename());

            if (!mStreamBuf.close()) {
                mLog.e("close(): setting failbit.");
                this->setstate(std::ios_base::failbit);
            }
        }

      protected:

      private:

        streambuf_type_ mStreamBuf;

        logging::Log mLog;
    };


    /* The type you're looking for. */
    using archive_istream = basic_archive_istream<char>;

    /** Implements swap for archive_stream.
     *
     * Otherwise use basic_archive_istream<>::swap().
     */
    void swap(archive_istream& lhs, archive_istream& rhs)
    {
        lhs.swap(rhs);
    }

    /** And for wide characters.
     *
     * Generally 32-bit on Unicode unix systems, and for legacy reasons: 16-bit
     * on Windows NT/Unicode.
     */
    using warchive_istream = basic_archive_istream<wchar_t>;

    /** Implements swap for warchive_stream.
     *
     * Otherwise use basic_archive_istream<>::swap().
     */
    void swap(warchive_istream& lhs, warchive_istream& rhs)
    {
        lhs.swap(rhs);
    }

} }

#endif // SXE_RESOURCE_ARCHIVEISTREAM__HPP
