@ECHO OFF
REM We're calling mvn by a batch file that will exit us :(
CALL mvn -Dmaven.test.skip=true -q package

MKDIR dist
REM copy LWJGL files
COPY %UserProfile%\.m2\repository\org\lwjgl\lwjgl\lwjgl\2.8.4\lwjgl-2.8.4.jar dist\
COPY %UserProfile%\.m2\repository\org\lwjgl\lwjgl\lwjgl_util\2.8.4\lwjgl_util-2.8.4.jar dist\
COPY %UserProfile%\.m2\repository\org\lwjgl\lwjgl\lwjgl-platform\2.8.4\lwjgl-platform-2.8.4-natives-windows.jar dist\

REM copy our files
COPY core\target\core-0.0.1-SNAPSHOT.jar dist\
COPY pc\target\pc-0.0.1-SNAPSHOT.jar dist\
COPY snakegame\target\snakegame-0.0.1-SNAPSHOT.jar dist\
