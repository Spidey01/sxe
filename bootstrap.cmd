@ECHO OFF

ECHO Bootstrapping SXE SDK.

:CALL_ENVSETUP
ECHO Setting up environment.
CALL envsetup\envsetup.cmd

:SETUP_DIST_TREE
@REM vendor\foo.ngen\foo-setup.cmd expect these to exist 'cuz written for ngen/ninja.
FOR %%d IN (lib include bin share) DO (
	IF NOT EXIST "%PROJECT_DISTDIR%\%%d" (
		MKDIR "%PROJECT_DISTDIR%\%%d"
	)
)

:LOCATE_CPPUNIT
@REM Best way to build upstream cppunit on NT is to use msbuild. Like our ngen setup script for it.
CALL "%PROJECT_ROOT%\vendor\cppunit.ngen\cppunit-setup.cmd"
@IF errorlevel 1 goto :EOF

:LOCATE_BOOST
@REM Boost is a monster.
CALL "%PROJECT_ROOT%\vendor\boost.ngen\boost-setup.cmd"
@IF errorlevel 1 goto :EOF

:LOCATE_JSON
cmake -S "%PROJECT_ROOT%\vendor\json" -B "%PROJECT_TMPDIR%\json" -G Ninja "-DCMAKE_INSTALL_PREFIX=%PROJECT_DISTDIR%" -DCMAKE_BUILD_TYPE=Release
@IF errorlevel 1 goto :EOF
ninja -C "%PROJECT_TMPDIR%\json" install
@IF errorlevel 1 goto :EOF

:LOCATE_GLFW
cmake -S "%PROJECT_ROOT%\vendor\glfw" -B "%PROJECT_TMPDIR%\glfw" -G Ninja "-DCMAKE_INSTALL_PREFIX=%PROJECT_DISTDIR%" -DCMAKE_BUILD_TYPE=Release
@IF errorlevel 1 goto :EOF
ninja -C "%PROJECT_TMPDIR%\glfw" install
@IF errorlevel 1 goto :EOF

:LOCATE_ZLIB
@REM Can't build zlib from CMakeLists.txt, and also find_package() it without pain.
cmake -S "%PROJECT_ROOT%\vendor\zlib" -B "%PROJECT_TMPDIR%\zlib" -G Ninja "-DCMAKE_INSTALL_PREFIX=%PROJECT_DISTDIR%" -DCMAKE_BUILD_TYPE=Release
@IF errorlevel 1 goto :EOF
ninja -C "%PROJECT_TMPDIR%\zlib" install
@IF errorlevel 1 goto :EOF
@REM clean up
PUSHD vendor\zlib
@IF errorlevel 1 goto :EOF
git reset --hard HEAD
git clean -f
POPD

:LOCATE_LIBARCHIVE
cmake -S "%PROJECT_ROOT%\vendor\libarchive" -B "%PROJECT_TMPDIR%\libarchive" -G Ninja "-DCMAKE_INSTALL_PREFIX=%PROJECT_DISTDIR%" -DCMAKE_BUILD_TYPE=Release
@IF errorlevel 1 goto :EOF
ninja -C "%PROJECT_TMPDIR%\libarchive" install
@IF errorlevel 1 goto :EOF


ECHO Set your SXE_SDK=%PROJECT_DISTDIR% or get smarter.

