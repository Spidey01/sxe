@ECHO OFF

IF NOT "%1" == "" (SET thismvnprofile=%1) ELSE (SET thismvnprofile=pc)

SET thismvnflags=-P %thismvnprofile% -Dmaven.test.skip=true -q package

ECHO mvn %thismvnflags%
REM We're calling mvn by a batch file that will exit us :(
CALL mvn %thismvnflags%
IF ERRORLEVEL 1 GOTO :eof

MKDIR dist

IF "%thismvnprofile%" == "pc" (
	ECHO "Copying LWJGL files"
	FOR %%f IN (
		%UserProfile%\.m2\repository\org\lwjgl\lwjgl\lwjgl\2.8.4\lwjgl-2.8.4.jar
		%UserProfile%\.m2\repository\org\lwjgl\lwjgl\lwjgl_util\2.8.4\lwjgl_util-2.8.4.jar
		%UserProfile%\.m2\repository\org\lwjgl\lwjgl\lwjgl-platform\2.8.4\lwjgl-platform-2.8.4-natives-windows.jar
	) DO COPY /Y "%%f" dist\
)

ECHO "Copying SXE files"
FOR %%f IN (
	core\target\core-0.0.1-SNAPSHOT.jar
	pc\target\pc-0.0.1-SNAPSHOT.jar
	snakegame\target\snakegame-0.0.1-SNAPSHOT.jar
	snakegame\lib\target\snakegame-lib-0.0.1-SNAPSHOT.jar
	snakegame\pc\target\snakegame-pc-0.0.1-SNAPSHOT.jar
) DO COPY /Y "%%f" dist\
