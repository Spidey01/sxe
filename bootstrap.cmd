@ECHO OFF

ECHO Bootstrapping SXE SDK.

:GIT_SUBMODULES
@REM Required because envsetup is a submodule.
ECHO Updating git submodules.
git submodule init
git submodule update

:CALL_ENVSETUP
ECHO Setting up environment.
IF NOT DEFINED PROJECT_ROOT ( CALL envsetup\envsetup.cmd )

SETLOCAL

:SETUP_VCPKG
WHERE /Q vcpkg
IF ERRORLEVEL 1 (
	robocopy "%PROJECT_ROOT%\vendor\vcpkg" "%VCPKG_ROOT%" /E "/LOG:%PROJECT_TMPDIR%\robocopy.log"
	PUSHD "%VCPKG_ROOT%"
	.\bootstrap-vcpkg.bat
	COPY /B "%VCPKG_ROOT%\vcpkg.exe" "%PROJECT_ROOT%\vcpkg.exe"
	POPD
)
WHERE vcpkg
IF ERRORLEVEL 1 GOTO :EOF

ECHO Installing dependencies with vcpkg.

vcpkg install --triplet %PROJECT_TARGET_TRIPLET% ^
nlohmann-json ^
zlib ^
libarchive ^
glfw3 ^
cppunit ^
boost-filesystem boost-optional boost-property-tree ^
vulkan vulkan-hpp

IF ERRORLEVEL 1 GOTO :EOF


ENDLOCAL

ECHO Running cmake
@ECHO ON
cmake -G Ninja -B "%PROJECT_BUILDDIR%" -S "%PROJECT_ROOT%" -DCMAKE_BUILD_TYPE=%PROJECT_BUILDTYPE% -DBUILD_SHARED_LIBS=ON -DBUILD_SXE_SDK=ON

