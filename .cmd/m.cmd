@ECHO OFF
IF NOT DEFINED SXE_ROOT (
	ECHO "SXE_ROOT not defined, please setup your environment"
	GOTO :eof
)

@CALL "%SXE_ROOT%\gradlew.bat" --daemon %* 
@REM @CALL "%SXE_ROOT%\gradlew.bat" --daemon %* 1> .\tmp\gradlew.log 2>&1

