
goals ?= package
thismvnflags = -Dmaven.test.skip=true -q 

sxedeps = dist/core-0.0.1-SNAPSHOT.jar
pcdeps = $(sxedeps) \
		 dist/lwjgl-2.8.4.jar \
		 dist/lwjgl_util-2.8.4.jar \
		 dist/lwjgl-platform-2.8.4-natives-linux.jar \
		 dist/pc-0.0.1-SNAPSHOT.jar
snakegamedeps = dist/snakegame-lib-0.0.1-SNAPSHOT.jar \
				dist/snakegame-pc-0.0.1-SNAPSHOT.jar

pc:
	mvn -P $@ $(thismvnflags) $(goals)
	
dist-pc: dist $(pcdeps)

dist-snakegame-pc: dist-pc $(snakegamedeps)

# the natives folder needs to be a part of dist-pc, in the end.
run-snakegame-pc: dist-pc
	(cd dist && java -Djava.library.path=../pc/target/natives -jar snakegame-pc-0.0.1-SNAPSHOT.jar "640x480")

android:
	mvn -P $@ $(thismvnflags) $(goals)

dist-snakegame-android: dist
	cp snakegame/android/target/snakegame-android-0.0.1-SNAPSHOT.apk dist/

dropbox-dist-snamegame-android: dist-snakegame-android
	cp dist/snakegame-android-0.0.1-SNAPSHOT.apk ~/Dropbox/snakegame-android-0.0.1-SNAPSHOT.apk	

# N.B. doesn't work when you're doing the build on the device itself.
run-snakegame-android: android
	adb install snakegame/android/target/snakegame-android-0.0.1-SNAPSHOT.apk

# Use tee here because we rarely care about the exit status of this target so
# much as the corpious output.
docs:
	mvn -P $@ $(thismvnflags) javadoc:aggregate | tee mvn.log

all: pc android docs

dist:
	-mkdir dist

# engine 
dist/core-0.0.1-SNAPSHOT.jar: core/target/core-0.0.1-SNAPSHOT.jar dist
	cp "$<" "$@"
dist/pc-0.0.1-SNAPSHOT.jar: pc/target/pc-0.0.1-SNAPSHOT.jar dist
	cp "$<" "$@"

# demo
dist/snakegame-lib-0.0.1-SNAPSHOT.jar: snakegame/lib/target/snakegame-lib-0.0.1-SNAPSHOT.jar dist
	cp "$<" "$@"
dist/snakegame-pc-0.0.1-SNAPSHOT.jar: snakegame/pc/target/snakegame-pc-0.0.1-SNAPSHOT.jar dist
	cp "$<" "$@"

# lwjgl dependency
dist/lwjgl-2.8.4.jar: ${HOME}/.m2/repository/org/lwjgl/lwjgl/lwjgl/2.8.4/lwjgl-2.8.4.jar dist
	cp "$<" "$@"
dist/lwjgl_util-2.8.4.jar: ${HOME}/.m2/repository/org/lwjgl/lwjgl/lwjgl_util/2.8.4/lwjgl_util-2.8.4.jar dist
	cp "$<" "$@"
dist/lwjgl-platform-2.8.4-natives-linux.jar: ${HOME}/.m2/repository/org/lwjgl/lwjgl/lwjgl-platform/2.8.4/lwjgl-platform-2.8.4-natives-linux.jar dist
	cp "$<" "$@"

tags:
	find . -name \*.java | xargs ctags

.PHONY: dist pc android all tags docs
