#ifndef SXE_RESOURCE_ARCHIVE_STREAMBUF__HPP
#define SXE_RESOURCE_ARCHIVE_STREAMBUF__HPP
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

#include <archive.h>
#include <archive_entry.h>
#include <sxe/api.hpp>
#include <sxe/logging/Log.hpp>
#include <sxe/resource/basic_archive_typedefs.hpp>
#include <sxe/stdheaders.hpp>

namespace sxe { namespace resource {

    /** Implements streambuf for libarchive.
     */
    template<class CharT, class Traits=std::char_traits<CharT>>
    class basic_archive_streambuf
        : public std::basic_streambuf<CharT, Traits>
        , public basic_archive_typedefs<CharT, Traits>
    {
      public:

        using super_type_ = std::basic_streambuf<CharT, Traits>;
        /* Because templates are painful, and some compilers are stricter than others. */
        using typedefs_ = basic_archive_typedefs<CharT, Traits>;

        using traits_type = typename super_type_::traits_type;
        using int_type = typename super_type_::int_type;
        using char_type = typename super_type_::char_type;
        using string_type = typename typedefs_::string_type;
        using path_type = typename typedefs_::path_type;
        using openmode_type_ = typename typedefs_::openmode_type_;

        basic_archive_streambuf()
            : super_type_()
            , mArchive()
            , mFilename()
            , mMode(std::ios_base::in)
            , mHandle(nullptr)
            , mLog("basic_archive_streambuf<>")
        {
        }


        basic_archive_streambuf(const basic_archive_streambuf&) = delete;

        basic_archive_streambuf(basic_archive_streambuf&& other)
            : super_type_(std::move(other))
            , mArchive(std::move(other.mArchive))
            , mFilename(std::move(other.mFilename))
            , mMode(std::move(other.mMode))
            , mHandle(std::move(other.mHandle))
            , mLog(std::move(other.mLog))
        {
            other.mHandle = nullptr;
        }


        virtual ~basic_archive_streambuf()
        {
            close();
        }


        basic_archive_streambuf& operator=(const basic_archive_streambuf&) = delete;

        basic_archive_streambuf& operator=(basic_archive_streambuf&& other)
        {
            if (this == &other) return *this;

            super_type_::operator=(std::move(other));

            mArchive = std::move(other.mArchive);
            mFilename = std::move(other.mFilename);
            mMode = std::move(other.mMode);
            mHandle = std::move(other.mHandle); other.mHandle = nullptr;
            mLog = std::move(other.mLog);

            return *this;
        }


        void swap(basic_archive_streambuf& other)
        {
            super_type_::swap(other);
            std::swap(mArchive, other.mArchive);
            std::swap(mFilename, other.mFilename);
            std::swap(mMode, other.mMode);
            std::swap(mHandle, other.mHandle);
            std::swap(mLog, other.mLog);
        }


        bool is_open() const throw()
        {
            return mHandle != nullptr;
        }


        basic_archive_streambuf* open(const char* archive, const char* filename, openmode_type_ mode=std::ios_base::in)
        {
            mArchive = archive;
            mFilename = filename;
            mMode = mode;

            mHandle = open_archive(mArchive, mFilename, mMode);
            if (mHandle)
                return this;

            return nullptr;
        }


        basic_archive_streambuf* open(const string_type& archive, const string_type& filename, openmode_type_ mode=std::ios_base::in)
        {
            mArchive = archive;
            mFilename = filename;
            mMode = mode;

            mHandle = open_archive(mArchive, mFilename, mMode);
            if (mHandle)
                return this;

            return nullptr;
        }


        basic_archive_streambuf* open(const path_type& archive, const path_type& filename, openmode_type_ mode=std::ios_base::in)
        {
            mArchive = archive;
            mFilename = filename;
            mMode = mode;

            mHandle = open_archive(mArchive, mFilename, mMode);
            if (mHandle)
                return this;

            return nullptr;
        }


        basic_archive_streambuf* close()
        {
            if (mHandle == nullptr)
                return this;

            if (mMode & std::ios_base::in) {
                if (archive_read_close(mHandle) != ARCHIVE_OK) {
                    mLog.w("archive_read_close() failed for " + mArchive.string());
                    return nullptr;
                }

                if (archive_read_free(mHandle) != ARCHIVE_OK) {
                    mLog.w("archive_read_free() failed for " + mArchive.string());
                    return nullptr;
                }
            }

            mHandle = nullptr;
            return this;
        }

        /** Returns archive name associated. */
        string_type archive() const
        {
            return mArchive.string();
        }


        /** Returns file name associated. */
        string_type filename() const
        {
            return mFilename.string();
        }

      protected:

        /** General purpose read function.
         */
        std::streamsize xsgetn(char_type* s, std::streamsize count) override
        {
            mLog.test("xsgetn(): s: " + std::to_string((uintptr_t)s) + " count: " + std::to_string(count));
            return archive_read_data(mHandle, s, count);
        }


        int_type underflow() override
        {
            mLog.test("underflow()");

            char_type ch = (char_type)traits_type::eof();
            this->sgetn(&ch, 1);
            this->setg(&ch, &ch, &ch+1);
            return ch;
        }

      private:

        /** Path to the archive file.
         */
        path_type mArchive;

        /** Path in the archive file.
         */
        path_type mFilename;

        /** ios open mode.
         */
        openmode_type_ mMode;

        /** libarchive handle.
         */
        struct archive* mHandle;

        logging::Log mLog;

        /** Opens the specified archive.
         *
         * @param archive the file to open with libarchive.
         * @param filename the entry to find.
         * @param mode only std::ios_base::in is supported.
         *
         * @returns nullptr on failure, libarchive handle on success.
         */
        struct archive* open_archive(const path_type& archive, const path_type& filename, openmode_type_ mode)
        {
            mLog.xtrace("open_archive(): archive: " + archive.string() + " filename: " + filename.string() + " mode: " + std::to_string(mode));

            if (archive.empty()) {
                mLog.e("archive path cannot be empty.");
                return nullptr;
            }
            if (filename.empty()) {
                mLog.e("archive path cannot be empty.");
                return nullptr;
            }

            struct archive* handle = nullptr;

            if (mode & std::ios_base::in) {
                /* Handle opening the archive.
                 *
                 * formats depend on what libarchive found when it was compiled.
                 */
                handle = archive_read_new();
                if (handle == nullptr) {
                    mLog.e("archive_read_new() failed for " + archive.string());
                }

                archive_read_support_format_all(handle);
                archive_read_support_filter_all(handle);

                constexpr int BlockSize = 1024;
                if (archive_read_open_filename(handle, archive.string().c_str(), BlockSize) != ARCHIVE_OK) {
                    mLog.e("archive_read_open_filename() failed:"
                          + archive.string()
                          + ":" + filename.string()
                          + ": " + archive_error_string(handle));
                    return nullptr;
                }

                /* Find the entry for filename. */
                struct archive_entry* entry = nullptr;
                while (true) {
                    int rc = archive_read_next_header(handle, &entry);

                    if (rc == ARCHIVE_EOF) {
                        mLog.test("archive_read_next_header() returned EOF for " + archive.string());
                        break;
                    }

                    if (rc != ARCHIVE_OK) {
                        string_type diag = archive_error_string(handle);
                        mLog.e("archive_read_next_header() failed: " + diag);
                        break;
                    }
                    break;
                }
                if (entry == nullptr) {
                    mLog.e("Can't find " + filename.string() + " in archive " + archive.string());
                    return nullptr;
                }
            }

            return handle;
        }
    };

    /* The type you're looking for. */
    using archive_streambuf = basic_archive_streambuf<char>;

    /** Implements swap for archive_streambuf.
     *
     * Otherwise use basic_archive_streambuf<>::swap().
     */
    void swap(archive_streambuf& lhs, archive_streambuf& rhs)
    {
        lhs.swap(rhs);
    }

    /** And for wide characters.
     *
     * Generally 32-bit on Unicode unix systems, and for legacy reasons: 16-bit
     * on Windows NT/Unicode.
     */
    using warchive_streambuf = basic_archive_streambuf<wchar_t>;

    /** Implements swap for warchive_streambuf.
     *
     * Otherwise use basic_archive_streambuf<>::swap().
     */
    void swap(warchive_streambuf& lhs, warchive_streambuf& rhs)
    {
        lhs.swap(rhs);
    }

} }

 #endif // SXE_RESOURCE_ARCHIVE_STREAMBUF__HPP
