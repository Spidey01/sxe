@ECHO OFF

SETLOCAL

MKDIR %PROJECT_DISTDIR%
MKDIR %PROJECT_DISTDIR%\bin

CD %PROJECT_BUILDDIR%

SET DOT_VER=1.71.0
SET DASH_VER=1_71_0
SET BASE=boost_%DASH_VER%
SET ZIP_FILE=%BASE%.zip

SET ZIP_URL=https://dl.bintray.com/boostorg/release/%DOT_VER%/source/%ZIP_FILE%
SET CACHE=%PROJECT_ROOT%\tmp\%ZIP_FILE%

SET WHERE=%PROJECT_BUILDDIR%\%BASE%
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
	PUSHD %PROJECT_BUILDDIR%
	7z x %CACHE%
	@IF errorlevel 1 goto :NEED_ZIPPER
	POPD
)

IF NOT EXIST %B2% (
	ECHO Bootstrapping b2...
	PUSHD %WHERE%
	.\bootstrap.bat
	@IF errorlevel 1 goto :eof
	POPD
)

IF NOT EXIST %PROJECT_DISTDIR%\include\boost (
	ECHO Building boost...
	PUSHD %WHERE%
	@REM If you don't specify many things it is a PITA to build with --layout=system.
	.\b2 --prefix=%PROJECT_DISTDIR% %COMPONENTS% --layout=system --build-type=minimal toolset=msvc-%VisualStudioVersion% address-model=64 variant=release link=shared threading=multi runtime-link=shared  install
	@IF errorlevel 1 goto :eof
	POPD
	ECHO Copying dll's from dist\lib to dist\bin.
	FOR /F "delims=" %%i IN ("%PROJECT_DISTDIR%\lib\*.dll") DO ( COPY /B %%i %PROJECT_DISTDIR%\bin\ )
)

ECHO Your BOOST is %PROJECT_DISTDIR%
GOTO :eof


:NEED_ZIPPER
ECHO Please install 7z in path, or manually unzip/rename to %PROJECT_BUILDDIR%\boost
GOTO :eof

