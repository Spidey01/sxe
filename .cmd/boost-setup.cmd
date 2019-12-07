@ECHO OFF

SETLOCAL

SET DOT_VER=1.71.0
SET DASH_VER=1_71_0
SET BASE=boost_%DASH_VER%
SET ZIP_FILE=%BASE%.zip

SET ZIP_URL=https://dl.bintray.com/boostorg/release/%DOT_VER%/source/%ZIP_FILE%
SET CACHE=%PROJECT_ROOT%\tmp\%ZIP_FILE%

SET WHERE=tmp\%BASE%
SET BOOTSTRAP=%WHERE%\bootstrap.bat
SET B2=%WHERE%\b2.exe

SET COMPONENTS=--with-headers --with-filesystem

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

IF NOT EXIST %PROJECT_DISTDIR%\include\boost-1_71 (
	ECHO Building boost...
	PUSHD %WHERE%
	REM -j isn't in the --help but works ^_^.
	.\b2 ^
		--prefix=%PROJECT_DISTDIR% %COMPONENTS% ^
		--build-dir=%PROJECT_BUILDDIR%\vendor\boost ^
		--build-type=complete ^
		-j12 ^
			address-model=64 ^
				install
	@IF errorlevel 1 goto :eof
	POPD
)

ECHO Your BOOST is %PROJECT_DISTDIR%
GOTO :eof

:NEED_ZIPPER
ECHO Please install 7z in path, or manually unzip/rename to %PROJECT_BUILDDIR%\boost
GOTO :eof

