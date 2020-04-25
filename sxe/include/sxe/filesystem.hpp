#ifndef SXE_FILESYSTEM__HPP
#define SXE_FILESYSTEM__HPP
/*-
 * Copyright (c) 2019-current, Terry Mathew Poulin <BigBoss1964@gmail.com>
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
#include <sxe/haveboost.hpp>

/** Used to disable things that made it into C++17 but not our release version of boost. */
#define SXE_BOOST_DOES_NOT_HAVE 0

namespace sxe {

    /** sxe::filesystem - std::filesystem drop in with a booster seat.
     *
     * Things listed here https://en.cppreference.com/w/cpp/header/filesystem
     * should be made available here using their boost equivalents.
     */
    namespace filesystem {

    #if SXE_HAVE_BOOST
        // ===== Classes

        using path = boost::filesystem::path;

        using filesystem_error = boost::filesystem::filesystem_error;

        using directory_entry = boost::filesystem::directory_entry;
        using directory_iterator = boost::filesystem::directory_iterator;
        using recursive_directory_iterator = boost::filesystem::recursive_directory_iterator;

        using file_status = boost::filesystem::file_status;
        using space_info = boost::filesystem::space_info;
        using file_type = boost::filesystem::file_type;

        using perms = boost::filesystem::perms;

        #if SXE_BOOST_DOES_NOT_HAVE
        using perm_options = boost::filesystem::perm_options;
        using copy_options = boost::filesystem::copy_options;
        #endif
        // Added in boost 1.72.
        using directory_options = boost::filesystem::directory_options;

        #if SXE_BOOST_DOES_NOT_HAVE
        using file_time_type = boost::filesystem::file_time_type;
        #endif

        // ===== Functions

        using boost::filesystem::absolute;
        using boost::filesystem::canonical;
        using boost::filesystem::weakly_canonical;
        using boost::filesystem::relative;
        #if SXE_BOOST_DOES_NOT_HAVE
        using boost::filesystem::proximate;
        #endif
        using boost::filesystem::copy;
        using boost::filesystem::copy_file;
        using boost::filesystem::copy_symlink;
        using boost::filesystem::create_directory;
        using boost::filesystem::create_directories;
        using boost::filesystem::current_path;
        using boost::filesystem::exists;
        using boost::filesystem::equivalent;
        using boost::filesystem::file_size;
        using boost::filesystem::hard_link_count;
        using boost::filesystem::last_write_time;
        using boost::filesystem::permissions;
        using boost::filesystem::read_symlink;
        using boost::filesystem::remove;
        using boost::filesystem::remove_all;
        using boost::filesystem::rename;
        using boost::filesystem::resize_file;
        using boost::filesystem::space;
        using boost::filesystem::status;
        #if SXE_BOOST_DOES_NOT_HAVE
        using boost::filesystem::symink_status;
        #endif
        using boost::filesystem::temp_directory_path;

        // ===== File types

        #if SXE_BOOST_DOES_NOT_HAVE
        using boost::filesystem::is_block_file;
        using boost::filesystem::is_character_file;
        #endif
        using boost::filesystem::is_directory;
        using boost::filesystem::is_empty;
        #if SXE_BOOST_DOES_NOT_HAVE
        using boost::filesystem::is_fifo;
        #endif
        using boost::filesystem::is_other;
        using boost::filesystem::is_regular_file;
        #if SXE_BOOST_DOES_NOT_HAVE
        using boost::filesystem::is_socket;
        #endif
        using boost::filesystem::is_symlink;
        using boost::filesystem::status_known;
    #else // SXE_HAVE_BOOST
        // ===== Classes

        using path = std::filesystem::path;

        using filesystem_error = std::filesystem::filesystem_error;

        using directory_entry = std::filesystem::directory_entry;
        using directory_iterator = std::filesystem::directory_iterator;
        using recursive_directory_iterator = std::filesystem::recursive_directory_iterator;

        using file_status = std::filesystem::file_status;
        using space_info = std::filesystem::space_info;
        using file_type = std::filesystem::file_type;

        using perms = std::filesystem::perms;

        #if SXE_BOOST_DOES_NOT_HAVE
        using perm_options = std::filesystem::perm_options;
        using copy_options = std::filesystem::copy_options;
        #endif
        // Added in boost 1.72.
        using directory_options = std::filesystem::directory_options;

        #if SXE_BOOST_DOES_NOT_HAVE
        using file_time_type = std::filesystem::file_time_type;
        #endif

        // ===== Functions

        using std::filesystem::absolute;
        using std::filesystem::canonical;
        using std::filesystem::weakly_canonical;
        using std::filesystem::relative;
        #if SXE_BOOST_DOES_NOT_HAVE
        using std::filesystem::proximate;
        #endif
        using std::filesystem::copy;
        using std::filesystem::copy_file;
        using std::filesystem::copy_symlink;
        using std::filesystem::create_directory;
        using std::filesystem::create_directories;
        using std::filesystem::current_path;
        using std::filesystem::exists;
        using std::filesystem::equivalent;
        using std::filesystem::file_size;
        using std::filesystem::hard_link_count;
        using std::filesystem::last_write_time;
        using std::filesystem::permissions;
        using std::filesystem::read_symlink;
        using std::filesystem::remove;
        using std::filesystem::remove_all;
        using std::filesystem::rename;
        using std::filesystem::resize_file;
        using std::filesystem::space;
        using std::filesystem::status;
        #if SXE_BOOST_DOES_NOT_HAVE
        using std::filesystem::symink_status;
        #endif
        using std::filesystem::temp_directory_path;

        // ===== File types

        #if SXE_BOOST_DOES_NOT_HAVE
        using std::filesystem::is_block_file;
        using std::filesystem::is_character_file;
        #endif
        using std::filesystem::is_directory;
        using std::filesystem::is_empty;
        #if SXE_BOOST_DOES_NOT_HAVE
        using std::filesystem::is_fifo;
        #endif
        using std::filesystem::is_other;
        using std::filesystem::is_regular_file;
        #if SXE_BOOST_DOES_NOT_HAVE
        using std::filesystem::is_socket;
        #endif
        using std::filesystem::is_symlink;
        using std::filesystem::status_known;
    #endif // SXE_HAVE_BOOST
    }
}

#undef SXE_BOOST_DOES_NOT_HAVE

#endif // SXE_FILESYSTEM__HPP
