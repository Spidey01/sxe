#
# This file is just for convenience of typing. In most cases you should run
# 'gradle' directly instead of adding a target here..
#
all:
	@echo pick a target

tags:
	gradle -PsxeTarget=complete tags

clean:
	gradle -PsxeTarget=complete clean

android:
	gradle -PsxeTarget=android build
test-android:
	gradle -PsxeTarget=android test

complete:
	gradle -PsxeTarget=complete build
test-complete:
	gradle -PsxeTarget=complete test

core:
	gradle -PsxeTarget=core build
test-core:
	gradle -PsxeTarget=core test

pc:
	gradle -PsxeTarget=pc build
test-pc:
	gradle -PsxeTarget=pc test
pc-demos:
	gradle -PsxeTarget=pc-demos build
test-pc-demos:
	gradle -PsxeTarget=pc-demos test

scripting:
	gradle -PsxeTarget=scripting build
test-scripting:
	gradle -PsxeTarget=scripting test

scripting-rhino:
	gradle -PsxeTarget=scripting :scripting:rhino:build
test-scripting-rhino:
	gradle -PsxeTarget=scripting :scripting:rhino:test

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

