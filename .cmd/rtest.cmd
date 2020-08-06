@ECHO OFF
IF NOT DEFINED PROJECT_ROOT (
	ECHO "PROJECT_ROOT not defined, please setup your environment"
	GOTO :eof
)

SETLOCAL
SET XDG_DATA_DIRS=%PROJECT_DISTDIR%\share
SET XDG_CONFIG_DIRS=%PROJECT_DISTDIR%\etc\xdg
SET XDG_DATA_HOME=%PROJECT_ROOT%\tmp\share
SET XDG_CONFIG_HOME=%PROJECT_ROOT%\tmp\config
SET XDG_CACHE_HOME=%PROJECT_ROOT%\tmp\cache
@REM Using debug symbols will want these too.
SET PATH=%Path%;%PROJECT_DISTDIR%\debug\bin

@ECHO ON
"%PROJECT_DISTDIR%\bin\sxe-test-runner" -o "%PROJECT_TMPDIR%\sxe-test-runner.log" %*

@ENDLOCAL
