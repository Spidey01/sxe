#
# This file is just for convenience of typing. In most cases you should run
# 'gradle' directly instead of adding a target here..
#
all:
	@echo pick a target

tags:
	gradle tags

clean:
	gradle clean

android:
	gradle :android:build
test-android:
	gradle :android:test

complete:
	gradle build
test-complete:
	gradle test

core:
	gradle :core:build
test-core:
	gradle :core:test

pc:
	gradle :pc:build
test-pc:
	gradle :pc:test
pc-demos:
	gradle pc-demos

scripting:
	gradle scripting:build
test-scripting:
	gradle scripting:test

scripting-rhino:
	gradle :scripting:rhino:build
test-scripting-rhino:
	gradle :scripting:rhino:test

scripting-jsr223:
	gradle :scripting:jsr223:build
test-scripting-jsr223:
	gradle :scripting:jsr223:test

#
# helpers
# 
# XXX If I could remember whether or not nmake will handle ./unix/paths instead
#     of .\dos\paths, I'd make this more portable.
#
helloworld:
	gradle :demos:helloworld:pc:installApp
	./scripts/run.sh helloworld
pingpong:
	gradle :demos:pingpong:pc:installApp
	./scripts/run.sh pingpong
snake:
	gradle :demos:snake:pc:installApp
	./scripts/run.sh snake

.PHONY: tags clean android complete core pc pc-demos scripting scripting-rhino

