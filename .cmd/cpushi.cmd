@ECHO OFF
IF NOT DEFINED PROJECT_DISTDIR (
	ECHO "PROJECT_DISTDIR not defined, please setup your environment"
	GOTO :eof
)

PUSHD "%PROJECT_DISTDIR%"
