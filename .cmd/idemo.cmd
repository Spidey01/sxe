@ECHO OFF
IF NOT DEFINED PROJECT_ROOT (
	ECHO "PROJECT_ROOT not defined, please setup your environment"
	GOTO :eof
)

IF "%1" == "" (
	echo Select demo, e.g. .\%0 helloworld
	goto :eof
)
CALL "%PROJECT_ROOT%\.cmd\m.cmd" ":demos:%1:pc:installApp"

