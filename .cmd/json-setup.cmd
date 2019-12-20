@ECHO OFF
IF NOT DEFINED PROJECT_DISTDIR (
	ECHO "PROJECT_DISTDIR not defined, please setup your environment"
	GOTO :eof
)

IF NOT EXIST "%PROJECT_DISTDIR%\include\nlohmann\json.hpp" (
    ROBOCOPY vendor\json\include\nlohmann "%PROJECT_DISTDIR%\include\nlohmann" /MIR
)

ECHO Your json is %PROJECT_DISTDIR%
