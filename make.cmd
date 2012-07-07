@ECHO OFF

IF NOT "%1" == "" (SET thismvnprofile=%1) ELSE (SET thismvnprofile=pc)

SET thismvnflags=-P %thismvnprofile% -Dmaven.test.skip=true -q package

ECHO mvn %thismvnflags%
REM We're calling mvn by a batch file that will exit us :(
CALL mvn %thismvnflags%

MKDIR dist
IF %thismvnprofile% == "pc" (
	ECHO "Copying LWJGL files"
	COPY /Y %UserProfile%\.m2\repository\org\lwjgl\lwjgl\lwjgl\2.8.4\lwjgl-2.8.4.jar dist\
	COPY /Y %UserProfile%\.m2\repository\org\lwjgl\lwjgl\lwjgl_util\2.8.4\lwjgl_util-2.8.4.jar dist\
	COPY /Y %UserProfile%\.m2\repository\org\lwjgl\lwjgl\lwjgl-platform\2.8.4\lwjgl-platform-2.8.4-natives-windows.jar dist\
)

ECHO "Copying SXE files"
COPY /Y core\target\core-0.0.1-SNAPSHOT.jar dist\
COPY /Y pc\target\pc-0.0.1-SNAPSHOT.jar dist\
COPY /Y snakegame\target\snakegame-0.0.1-SNAPSHOT.jar dist\

