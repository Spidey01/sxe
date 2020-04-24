@ECHO OFF

ECHO Bootstrapping SXE SDK.

:CALL_ENVSETUP
ECHO Setting up environment.
CALL envsetup\envsetup.cmd

:SETUP_DIST_TREE
@REM vendor\foo.ngen\foo-setup.cmd expect these to exist 'cuz written for ngen/ninja.
FOR %%d IN (lib include bin share) DO (
	IF NOT EXIST "%PROJECT_DISTDIR%\%%d" (
		MKDIR "%PROJECT_DISTDIR%\%%d"
	)
)

:LOCATE_CPPUNIT
@REM Best way to build upstream cppunit on NT is to use msbuild. Like our ngen setup script for it.
CALL "%PROJECT_ROOT%\vendor\cppunit.ngen\cppunit-setup.cmd"

:LOCATE_BOOST
@REM Boost is a monster.
CALL "%PROJECT_ROOT%\vendor\boost.ngen\boost-setup.cmd"

ECHO Set your SXE_SDK=%PROJECT_DISTDIR% or get smarter.

