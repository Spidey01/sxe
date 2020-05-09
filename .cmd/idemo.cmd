@ECHO OFF
IF NOT DEFINED PROJECT_ROOT (
	ECHO "PROJECT_ROOT not defined, please setup your environment"
	GOTO :eof
)
@ECHO ON

m sxe/install demos/%*/install
