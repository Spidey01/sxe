@ECHO OFF

:DETECT_ARCH
	@REM only supporting native 64-bit and 32-bit builds.
	IF %PROCESSOR_ARCHITECTURE% == AMD64 (
		SET PROJECT_TARGET_ARCH=x64
		SET PROJECT_HOST_ARCH=x64
	) ELSE (
		SET PROJECT_TARGET_ARCH=x86
		SET PROJECT_HOST_ARCH=x86
	)

:GIT_SUBMODULES
	ECHO Updating git submodules.
	git submodule init
	git submodule update

:LOCATE_COMPILER
	@REM Should exist if >= VS2017 installed, and not old.
	@REM But requires IDE installed, command line tools not enough.
	SET "SXE_VSWHERE=%ProgramFiles(x86)%\Microsoft Visual Studio\Installer\vswhere.exe"

	REM compiling with MSC, proffered on Windows.
	IF NOT DEFINED VisualStudioVersion (
		ECHO Locating compiler.

		FOR /f "usebackq delims=" %%i IN (`"%SXE_VSWHERE%" -prerelease -latest -property installationPath`) DO (
			IF EXIST "%%i\Common7\Tools\vsdevcmd.bat" (
				CALL "%%i\Common7\Tools\vsdevcmd.bat" -arch=%PROJECT_TARGET_ARCH% -host_arch=%PROJECT_HOST_ARCH%
			)
		)

		@REM Fall back to what I use.
		IF NOT DEFINED VisualStudioVersion (
			ECHO Falling back to VS2019.
			CALL "%ProgramFiles(x86)%\Microsoft Visual Studio\2019\BuildTools\Common7\Tools\VsDevCmd.bat" ^
				-arch=%PROJECT_TARGET_ARCH% ^
				-host_arch=%PROJECT_HOST_ARCH%
		)

		IF NOT DEFINED VisualStudioVersion (
			ECHO FAILED locating compiler.
			GOTO :EOF
		)
	)
	ECHO Using Visual Studio %VisualStudioVersion%
	SET PROJECT_TOOLCHAIN=msc%VisualStudioVersion%

:LOCATE_VULKAN
	@REM The Vulkan SDK from LunarG defines VK_SDK_PATH and VULKAN_SDK as the root of the files.
	@REM It also adds its bin directory to Path.
	IF NOT DEFINED VULKAN_SDK ( GOTO :LIVE_LONG_AND_PROPOSER ) ELSE ( GOTO :DONE_VULKAN )

:LIVE_LONG_AND_PROPOSER
	ECHO VULKAN_SDK is not defined. SxE SDK is normally built with Vulkan support.
	ECHO Please install https://vulkan.lunarg.com/sdk/home#sdk/downloadConfirm/latest/windows/vulkan-sdk.exe and try again in a new %ComSpec%.
	SET /P SXE_SDK_WITHOUT_VULKAN_SDK="Continue without Vulkan SDK? (Y/N)? "
	ECHO SXE_SDK_WITHOUT_VULKAN_SDK = '%SXE_SDK_WITHOUT_VULKAN_SDK%'
	IF /I "%SXE_SDK_WITHOUT_VULKAN_SDK%" == "Y" ( GOTO :DONE_VULKAN ) ELSE ( GOTO :EOF )

:DONE_VULKAN
	SET SXE_SDK_WITHOUT_VULKAN_SDK=

:SET_VARS
	REM MS and *nix land differ on the arch names, and toolchain != vendor, but
	REM same idea as triplets like x864_64-linux-gnu. In this case we're using
	REM vcpkg triplet, which is more of a pair..
	SET PROJECT_TARGET_TRIPLET=%PROJECT_TARGET_ARCH%-windows

	REM Where to build stuff in cmake. - default
	REM SET PROJECT_BUILDDIR=%PROJECT_ROOT%\tmp\%PROJECT_TARGET_TRIPLET%
	SET PROJECT_BUILDDIR=%PROJECT_ROOT%\build

	REM Where to install stuff for redist. - default.
	SET PROJECT_DISTDIR=%ProgramFiles(x86)%\SXE_SDK

	REM Where to put temp files, like logs from sxe-test-runner.
	SET PROJECT_TMPDIR=%PROJECT_ROOT%\tmp

	REM Preferred value for cmake -G.
	SET PROJECT_BUILDSYSTEM=Ninja

	REM Preferred CMAKE_BUILD_TYPE.
	SET PROJECT_BUILDTYPE=RelWithDebInfo

	REM Where vcpkg should be rooted.
	SET "VCPKG_ROOT=%PROJECT_TMPDIR%\vcpkg"

:FINISH_SETUP
	SET PROJECT_
	SET XDG_
	SET VCPKG_ROOT

	TITLE SxE Development Command Prompt

