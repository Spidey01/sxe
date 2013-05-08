#
# This file is just for convenience of typing. In most cases you should run
# 'gradle' directly instead of adding a target here..
#

tags:
	gradle -PsxeTarget=complete tags

clean:
	gradle -PsxeTarget=complete clean

android:
	gradle -PsxeTarget=android build

complete:
	gradle -PsxeTarget=complete build

core:
	gradle -PsxeTarget=core build

pc:
	gradle -PsxeTarget=pc build
pc-demos:
	gradle -PsxeTarget=pc-demos build

scripting:
	gradle -PsxeTarget=scripting build

scripting-rhino:
	gradle -PsxeTarget=scripting :scripting:rhino:build

#
# helpers
# 
# XXX If I could remember whether or not nmake will handle ./unix/paths instead
#     of .\dos\paths, I'd make this more portable.
#
helloworld:
	gradle -PsxeTarget=pc-demos :demos:helloworld:pc:installApp
	./scripts/run.sh helloworld
pingpong:
	gradle -PsxeTarget=pc-demos :demos:pingpong:pc:installApp
	./scripts/run.sh pingpong
snake:
	gradle -PsxeTarget=pc-demos :demos:snake:pc:installApp
	./scripts/run.sh snake

.PHONY: tags clean android complete core pc pc-demos scripting scripting-rhino

