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

#include <sxe/logging/Log.hpp>
#include <sxe/logging/TextLogSink.hpp>
#include <sxe/stdheaders.hpp>
#include <sxe/sys/FileSystem.hpp>

#include <cppunit/BriefTestProgressListener.h>
#include <cppunit/CompilerOutputter.h>
#include <cppunit/TestResult.h>
#include <cppunit/TestResultCollector.h>
#include <cppunit/TestRunner.h>
#include <cppunit/extensions/TestFactoryRegistry.h>

using sxe::logging::TextLogSink;
using sxe::logging::Log;
using sxe::sys::FileSystem::path;
using std::vector;
using std::string;
using std::cout;
using std::clog;
using std::endl;
using std::exit;
using std::runtime_error;

static const char* TAG = "sxe/tests/main.cpp";

static void options(int argc, char* argv[], vector<string>& tests)
{
    auto suite = static_cast<CPPUNIT_NS::TestSuite*>(CPPUNIT_NS::TestFactoryRegistry::getRegistry().makeTest());
    if (!suite)
        throw runtime_error("Failed to get cppunit's test factory registry.");

    for (int i=1; i < argc; ++i) {
        const string a = argv[i];

        if (a.front() != '-') {
            tests.push_back(a);
            Log::i(TAG, "Adding test " + tests.back() + " to queue of tests to run.");
            continue;
        }

        if (a == "--help" || a == "-h" || a == "-help" || a == "/?") {
            cout
                << "usage: " << argv[0] << " [options] [test ...]" << endl
                << endl
                << "options:" << endl
                << '\t' << "-h, --help        This help." << endl
                << '\t' << "-o, --output FILE Creates a TextLogSink for FILE at level Log::TEST." << endl
                << '\t' << "-l, --list        List available test suites." << endl
                << endl
                ;
            exit(0);
        }

        if (a == "--output" || a == "-o" || a == "/output" || a == "/o") {
            i += 1;
            if (i > argc)
                throw runtime_error("--output/-o requires a value.");
            path file(argv[i]);

            Log::add(std::make_shared<TextLogSink>("output", Log::TEST, new std::ofstream(file.string()), true));
            Log::i(TAG, "--output " + file.string());

            continue;
        }

        if (a == "--list" || a == "-l" || a == "/list" || a == "/l") {
            Log::d(TAG, "--list");

            for (auto test : suite->getTests()) {
                cout << test->getName() << endl;
            }

            exit(0);
        }

        /*
         * Names of tests to create.
         */

        tests.push_back(a);
    }

    if (tests.empty()) {
        Log::i(TAG, "Adding all registered tests");

        for (auto available : suite->getTests()) {
            tests.push_back(available->getName());
        }
    }
}


static int run(const vector<string>& tests)
{
    CPPUNIT_NS::TestResult controller;

    CPPUNIT_NS::TestResultCollector result;
    controller.addListener(&result);

    CPPUNIT_NS::BriefTestProgressListener progress;
    controller.addListener(&progress);

    CPPUNIT_NS::TestRunner runner;

    for (const string& name : tests) {
        Log::d(TAG, "Attaching test " + name + " to runner");
        runner.addTest(CPPUNIT_NS::TestFactoryRegistry::getRegistry(name).makeTest());
    }

    Log::i(TAG, "Running tests.");
    runner.run(controller);

    Log::i(TAG, "Formatting output");
    CPPUNIT_NS::CompilerOutputter outputter(&result, std::cout);
    outputter.write();

    return result.wasSuccessful() ? 0 : 1;
}


int main(int argc, char* argv[])
{
    auto level = Log::ASSERT;
    auto sink = std::make_shared<TextLogSink>(string(TAG), level, std::cout);
    Log::add(sink);

    int rc = 0;
    vector<string> tests;

    try {
        options(argc, argv, tests);
        rc = run(tests);
    } catch (runtime_error& ex) {
        Log::wtf(TAG, argv[0], ex); // NORETURN
        return 1;
    }

    return rc;
}


