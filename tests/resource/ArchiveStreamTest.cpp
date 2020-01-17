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

#include "./ArchiveStreamTest.hpp"

#include <sxe/logging.hpp>
#include <sxe/stdheaders.hpp>
#include <sxe/sys/Xdg.hpp>

using std::string;
using std::to_string;
using namespace sxe::resource;

static const string TAG = "ArchiveStreamTest";

static const string ARCHIVE = sxe::sys::Xdg().getDataDir("sxe/test.zip").string();
static const string FILENAME = "test.txt";

CPPUNIT_TEST_SUITE_NAMED_REGISTRATION(ArchiveStreamTest, TAG);
CPPUNIT_REGISTRY_ADD_TO_DEFAULT(TAG);

void ArchiveStreamTest::ctors()
{
    Log::xtrace(TAG, "ctors()");

    archive_istream default_ctor;
    CPPUNIT_ASSERT(default_ctor.is_open() == false);

    archive_istream cstr_ctor(ARCHIVE.c_str(), FILENAME.c_str());
    CPPUNIT_ASSERT(cstr_ctor.is_open() == true);

    archive_istream str_ctor(ARCHIVE, FILENAME);
    CPPUNIT_ASSERT(str_ctor.is_open() == true);

    sxe::filesystem::path archive = ARCHIVE;
    sxe::filesystem::path filename = FILENAME;

    archive_istream path_ctor(archive, filename);
    CPPUNIT_ASSERT(path_ctor.is_open() == true);
}


void ArchiveStreamTest::move_ctor()
{
    Log::xtrace(TAG, "move_ctor()");

    archive_istream first(ARCHIVE, FILENAME);
    CPPUNIT_ASSERT(first.rdbuf() != nullptr);

    Log::xtrace(TAG, "move construct");
    archive_istream other(std::move(first));

    CPPUNIT_ASSERT(first.rdbuf() != nullptr);
    CPPUNIT_ASSERT(other.rdbuf() != nullptr);
    CPPUNIT_ASSERT(other.tie() == 0);
    CPPUNIT_ASSERT(first.is_open() == false);
}


void ArchiveStreamTest::move_assign()
{
    Log::xtrace(TAG, "move_assign()");

    archive_istream first(ARCHIVE, FILENAME);
    CPPUNIT_ASSERT(first.rdbuf() != nullptr);

    archive_istream other;
    Log::xtrace(TAG, "move assign");
    other = std::move(first);

    CPPUNIT_ASSERT(first.rdbuf() != nullptr);
    CPPUNIT_ASSERT(other.rdbuf() != nullptr);
    CPPUNIT_ASSERT(other.tie() == 0);
    CPPUNIT_ASSERT(first.is_open() == false);
}


void ArchiveStreamTest::swap_method()
{
    Log::xtrace(TAG, "swap_method()");

    archive_istream first(ARCHIVE, FILENAME);
    archive_istream second;

    Log::xtrace(TAG, "second.swap(first)");
    second.swap(first);

    CPPUNIT_ASSERT(second.is_open() == true);
}


void ArchiveStreamTest::swap_function()
{
    Log::xtrace(TAG, "swap_function()");

    archive_istream first(ARCHIVE, FILENAME);
    archive_istream second;

    Log::xtrace(TAG, "swap(first, second)");
    sxe::resource::swap(first, second);

    CPPUNIT_ASSERT(second.is_open() == true);
}


void ArchiveStreamTest::read()
{
    Log::xtrace(TAG, "read()");

    // FIXME
    string txt = "C:/Users/Terry/sw/workspace/sxe/tests/test.txt";
    std::ifstream original(txt);
    CPPUNIT_ASSERT_MESSAGE("Need to open the original file at " + txt,
                           original);

    archive_istream archive(ARCHIVE, FILENAME);
    CPPUNIT_ASSERT_MESSAGE("Need to open the archive at " + ARCHIVE,
                           archive);

    constexpr size_t blocksize = 256;
    char o_buffer[blocksize];
    char a_buffer[blocksize];

    while (archive && original) {
        std::memset(o_buffer, '\0', sizeof(o_buffer));
        std::memset(a_buffer, '\0', sizeof(a_buffer));

        CPPUNIT_ASSERT_NO_THROW(original.read(o_buffer, blocksize));
        CPPUNIT_ASSERT_NO_THROW(archive.read(a_buffer, blocksize));

        Log::d(TAG, "o_buffer: \"" + string(o_buffer));
        Log::d(TAG, "a_buffer: \"" + string(a_buffer));

        CPPUNIT_ASSERT(original.gcount() == archive.gcount());
    }
}


void ArchiveStreamTest::getline()
{
    Log::xtrace(TAG, "getline()");

    // FIXME
    string txt = "C:/Users/Terry/sw/workspace/sxe/tests/test.txt";
    std::ifstream original(txt);
    CPPUNIT_ASSERT_MESSAGE("Need to open the original file at " + txt,
                           original);

    archive_istream archive(ARCHIVE, FILENAME);
    CPPUNIT_ASSERT_MESSAGE("Need to open the archive at " + ARCHIVE,
                           archive);

    string archive_line;
    string original_line;

    while (archive && original) {
        CPPUNIT_ASSERT_NO_THROW(std::getline(original, original_line));
        CPPUNIT_ASSERT_NO_THROW(std::getline(archive, archive_line));

        Log::d(TAG, "original_line: \"" + original_line + "\"");
        Log::d(TAG, "archive_line: \"" + archive_line + "\"");

        CPPUNIT_ASSERT_MESSAGE("expected line data: " + original_line + " actual line data: " + archive_line,
                               archive_line == original_line);
    }
}


