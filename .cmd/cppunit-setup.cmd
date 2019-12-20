@ECHO OFF

REM Modern solution is under src\CppUnitLibraries2010.sln, with release|debug and win32|x64 setup to drop all the things in lib.
REM dll versions are _dll, debug versions are d[_dll].
REM
REM The solution is hard coded to copy $(TargetPath) ..\..\lib\$(TargetName).lib, etc. So better off using git clean -f than modifying properties.

PUSHD vendor\cppunit\

@REM these build both static and shared.

IF NOT EXIST %PROJECT_DISTDIR%\lib\cppunit.lib (
	@ECHO Building cppunit release x64
	msbuild src\CppUnitLibraries2010.sln /p:configuration=release /p:platform=x64 /p:PlatformToolset=v142 /verbosity:quiet /nologo

	XCOPY lib\cppunit_dll.dll %PROJECT_DISTDIR%\bin\ /Y
	XCOPY lib\cppunit_dll.lib %PROJECT_DISTDIR%\lib\ /Y
	XCOPY lib\cppunit.lib %PROJECT_DISTDIR%\lib\ /Y
	XCOPY lib\DllPlugInTester_dll.exe %PROJECT_DISTDIR%\bin\ /Y

	git clean -f lib src
)

IF NOT EXIST %PROJECT_DISTDIR%\lib\cppunitd.lib (
	@ECHO Building cppunit debug x64
	msbuild src\CppUnitLibraries2010.sln /p:configuration=debug /p:platform=x64 /p:PlatformToolset=v142 /verbosity:quiet /nologo

	XCOPY lib\cppunitd_dll.dll %PROJECT_DISTDIR%\bin\ /Y
	XCOPY lib\cppunitd_dll.lib %PROJECT_DISTDIR%\lib\ /Y
	XCOPY lib\cppunitd.lib %PROJECT_DISTDIR%\lib\ /Y
	XCOPY lib\DllPlugInTesterd_dll.exe %PROJECT_DISTDIR%\bin\ /Y

	git clean -f lib src
)

REM basically it's copy dir, kill config-auto.h, extensions\XmlInputHelper.h, and if this wasn't cmd.exe, also the Makefile.{am,in} files.
IF NOT EXIST %PROJECT_DISTDIR%\include\cppunit (
	XCOPY include\cppunit %PROJECT_DISTDIR%\include\cppunit /S /I
)


POPD
goto :eof

