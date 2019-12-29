@ECHO OFF
CALL "%PROJECT_ROOT%\.cmd\itest.cmd" %1
IF ERRORLEVEL 1 GOTO :eof
CALL %PROJECT_ROOT%\.cmd\rtest.cmd %*
