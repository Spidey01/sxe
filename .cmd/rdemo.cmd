@ECHO OFF
IF NOT DEFINED PROJECT_ROOT (
	ECHO "PROJECT_ROOT not defined, please setup your environment"
	GOTO :eof
)

IF "%1" == "" (
	echo Select demo, e.g. %0 helloworld
	goto :eof
)

SETLOCAL
SET XDG_DATA_DIRS=%PROJECT_DISTDIR%\share
SET XDG_CONFIG_DIRS=%PROJECT_DISTDIR%\etc\xdg
SET XDG_DATA_HOME=%PROJECT_ROOT%\tmp\share
SET XDG_CONFIG_HOME=%PROJECT_ROOT%\tmp\config
SET XDG_CACHE_HOME=%PROJECT_ROOT%\tmp\cache

@ECHO ON
PUSHD %PROJECT_DISTDIR%\bin
%*
POPD

ENDLOCAL

