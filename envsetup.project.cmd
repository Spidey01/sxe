@ECHO OFF

REM compiling with MSC, preffered on Windows.
IF DEFINED VisualStudioVersion (

	REM VS2019 defines vars about this but older ones like VS2015 don't.
	REM
	REM Assuming x86 is a good guess if you use Developer Command Prompt
	REM shortcut instead of [arch] Native ...
	REM
	IF NOT DEFINED VSCMD_ARG_TGT_ARCH (
		SET PROJECT_TARGET_ARCH=x86
	) ELSE (
		SET PROJECT_TARGET_ARCH=%VSCMD_ARG_TGT_ARCH%
	)
	SET PROJECT_TOOLCHAIN=msc%VisualStudioVersion%
)
REM MS and *nix land differ on the arch names, and toolchain != vendor, but
REM same idea as triplets like x864_64-linux-gnu
SET PROJECT_TARGET_TRIPLET=%PROJECT_TARGET_ARCH%-%OS%-%PROJECT_TOOLCHAIN%

REM Where to build stuff in cmake. - default
SET PROJECT_BUILDDIR=%PROJECT_ROOT%\tmp\%PROJECT_TARGET_TRIPLET%

REM Where to install stuff for redist. - default.
SET PROJECT_DISTDIR=%PROJECT_ROOT%\dist

ECHO PROJECT_TARGET_ARCH is %PROJECT_TARGET_ARCH%
ECHO PROJECT_TOOLCHAIN is %PROJECT_TOOLCHAIN%
ECHO PROJECT_TARGET_TRIPLET is %PROJECT_TARGET_TRIPLET%
ECHO PROJECT_BUILDDIR is %PROJECT_BUILDDIR%
ECHO PROJECT_DISTDIR is %PROJECT_DISTDIR%


IF NOT DEFINED JAVA_HOME (
	CALL "%ENVSETUP_DIR%\.cmd\java-environment.cmd"
	IF NOT DEFINED JAVA_HOME (
		ECHO JAVA_HOME is not set!
		GOTO :eof
	)
)
ECHO Your JAVA_HOME is "%JAVA_HOME%"

IF NOT DEFINED ANDROID_HOME (
	CALL "%ENVSETUP_DIR%\.cmd\android-environment.cmd"
	IF NOT DEFINED ANDROID_HOME (
		ECHO ANDROID_HOME is not set!
		GOTO :eof
	)
)
ECHO Your ANDROID_HOME is "%ANDROID_HOME%"

ECHO Your XDG_DATA_HOME is "%XDG_DATA_HOME%"
ECHO Your XDG_CONFIG_HOME is "%XDG_CONFIG_HOME%"
ECHO Your XDG_CACHE_HOME is "%XDG_CACHE_HOME%"


.cmd\boost-setup.cmd

