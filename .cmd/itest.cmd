@ECHO OFF
IF NOT DEFINED PROJECT_ROOT (
	ECHO "PROJECT_ROOT not defined, please setup your environment"
	GOTO :eof
)
@ECHO ON

ngen
@IF errorlevel 1 GOTO :eof
ninja tests
@IF errorlevel 1 GOTO :eof