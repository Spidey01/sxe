@ECHO OFF

PUSHD "%PROJECT_ROOT%"
@IF errorlevel 1 goto :NO_PROJECT_ROOT

SETLOCAL

SET DOT_VER=1.72.0
SET DASH_VER=1_72_0
SET DASH_VER_S=1_72
SET BASE=boost_%DASH_VER%
SET ZIP_FILE=%BASE%.zip

SET ZIP_URL=https://dl.bintray.com/boostorg/release/%DOT_VER%/source/%ZIP_FILE%
SET CACHE=%PROJECT_ROOT%\tmp\%ZIP_FILE%

SET WHERE=tmp\%BASE%
SET BOOTSTRAP=%WHERE%\bootstrap.bat
SET B2=%WHERE%\b2.exe

SET COMPONENTS=--with-headers --with-filesystem

IF EXIST %PROJECT_DISTDIR%\include\boost-%DASH_VER_S% GOTO :EOF

IF NOT EXIST %CACHE% (
	ECHO Downloading %ZIP_URL%
	ECHO This will take a very long time.
	Powershell Invoke-WebRequest "%ZIP_URL%" -OutFile "%CACHE%"
	@IF errorlevel 1 goto :eof
)

IF NOT EXIST %BOOTSTRAP% (
	ECHO Extracting...
	REM -oDIR resolves things like envsetup\..\build to be envsetup\build. Which is wrong.
	REM Screw it, just hard code tmp and use b2's --build-dir.
	7z x -otmp %CACHE%
	@IF errorlevel 1 goto :NEED_ZIPPER
)

IF NOT EXIST %B2% (
	ECHO Bootstrapping b2...
	PUSHD %WHERE%
	.\bootstrap.bat
	@IF errorlevel 1 goto :eof
	POPD
)

IF NOT EXIST %PROJECT_DISTDIR%\include\boost-%DASH_VER_S% (
	ECHO Building boost...
	PUSHD %WHERE%
	REM -j isn't in the --help but works ^_^.
	.\b2 ^
		--prefix=%PROJECT_DISTDIR% %COMPONENTS% ^
		--build-dir=%PROJECT_BUILDDIR%\vendor\boost ^
		--build-type=complete --layout=versioned ^
		-j12 ^
			address-model=64 ^
				install
	@IF errorlevel 1 goto :eof
	POPD
)

:FINISHED
ECHO Your BOOST is %PROJECT_DISTDIR%\include\boost-%DASH_VER_S%
GOTO :eof

:NEED_ZIPPER
ECHO Please install 7z in path, or manually unzip/rename to %PROJECT_BUILDDIR%\boost
GOTO :eof

:NO_PROJECT_ROOT
ECHO Please CALL envsetup.sh - PROJECT_ROOT has not been set
GOTO :eof
