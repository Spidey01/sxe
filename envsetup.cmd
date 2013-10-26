@ECHO OFF

SET SXE_ROOT=%~dp0

CALL "%SXE_ROOT%\.cmd\java-environment.cmd"
IF NOT DEFINED JAVA_HOME (
	ECHO JAVA_HOME is not set!
	GOTO :eof
)

CALL "%SXE_ROOT%\.cmd\android-environment.cmd"
IF NOT DEFINED ANDROID_HOME (
	ECHO ANDROID_HOME is not set!
	GOTO :eof
)

SET "XDG_CONFIG_HOME=%SXE_ROOT%\tmp"
SET "PATH=%SXE_ROOT%\.cmd;%PATH%"

IF EXIST "%SXE_ROOT%\envsetup.local.cmd" CALL "%SXE_ROOT%\envsetup.local.cmd"

