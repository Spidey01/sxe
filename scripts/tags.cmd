@ECHO OFF
REM This is assumed to be run from the top level directory.

DIR /B /S /A-D . | findstr /E ".java" > tmp\sources.txt
ctags -L tmp\sources.txt
DEL tmp\sources.txt
