@ECHO OFF
IF "%1" == "" (
	echo Select demo, e.g. .\%0 helloworld
	goto :eof
)

IF "%XDG_CONFIG_HOME%" == "" (SET XDG_CONFIG_HOME=.\tmp)

.\gradlew.bat ":demos:%1:pc:installApp"
IF ERRORLEVEL 0 GOTO :eof
demos\%1\pc\build\install\%1-pc\bin\%1-pc.bat %2 %3 %4 %5 %6 %7 %8 %9
