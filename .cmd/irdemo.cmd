@ECHO OFF
CALL "%PROJECT_ROOT%\.cmd\idemo.cmd" %1
IF ERRORLEVEL 1 GOTO :eof
SETLOCAL
REM TODO: update these for 2.x.
REM SET "XDG_DATA_DIRS=%PROJECT_ROOT%\%1\pc\src\dist\share"
REM SET "XDG_CONFIG_DIRS=%PROJECT_ROOT%\demos\%1\pc\src\dist\share"
SHIFT
CALL %PROJECT_ROOT%\.cmd\rdemo.cmd %*
ENDLOCAL
