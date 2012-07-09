
thismvnflags = -Dmaven.test.skip=true -q 
sxedeps = dist/core-0.0.1-SNAPSHOT.jar
pcdeps = $(sxedeps) \
		 dist/lwjgl-2.8.4.jar \
		 dist/lwjgl_util-2.8.4.jar \
		 dist/lwjgl-platform-2.8.4-natives-linux.jar \
		 dist/pc-0.0.1-SNAPSHOT.jar
snakegamedeps = dist/snakegame-lib-0.0.1-SNAPSHOT.jar \
				dist/snakegame-pc-0.0.1-SNAPSHOT.jar

pc: $(pcdeps)
	mvn -P $@ $(thismvnflags) package
	
# the natives folder needs to be a part of dist, in the end.
run-snakegame-pc: dist $(sxedeps) $(pcdeps) $(snakegamedeps)
	(cd dist && java -Djava.library.path=../pc/target/natives -jar snakegame-pc-0.0.1-SNAPSHOT.jar "640x480")

android:
	mvn -P $@ $(thismvnflags) package
	cp snakegame/android/target/snakegame-android-0.0.1-SNAPSHOT.apk dist/
	cp dist/snakegame-android-0.0.1-SNAPSHOT.apk ~/Dropbox/snakegame-android-0.0.1-SNAPSHOT.apk	

all: pc android

dist:
	-mkdir dist

# engine 
dist/core-0.0.1-SNAPSHOT.jar: core/target/core-0.0.1-SNAPSHOT.jar
	cp "$<" "$@"
dist/pc-0.0.1-SNAPSHOT.jar: pc/target/pc-0.0.1-SNAPSHOT.jar
	cp "$<" "$@"

# demo
dist/snakegame-lib-0.0.1-SNAPSHOT.jar: snakegame/lib/target/snakegame-lib-0.0.1-SNAPSHOT.jar
	cp "$<" "$@"
dist/snakegame-pc-0.0.1-SNAPSHOT.jar: snakegame/pc/target/snakegame-pc-0.0.1-SNAPSHOT.jar
	cp "$<" "$@"

# lwjgl dependency
dist/lwjgl-2.8.4.jar: ${HOME}/.m2/repository/org/lwjgl/lwjgl/lwjgl/2.8.4/lwjgl-2.8.4.jar
	cp "$<" "$@"
dist/lwjgl_util-2.8.4.jar: ${HOME}/.m2/repository/org/lwjgl/lwjgl/lwjgl_util/2.8.4/lwjgl_util-2.8.4.jar
	cp "$<" "$@"
dist/lwjgl-platform-2.8.4-natives-linux.jar: ${HOME}/.m2/repository/org/lwjgl/lwjgl/lwjgl-platform/2.8.4/lwjgl-platform-2.8.4-natives-linux.jar
	cp "$<" "$@"


.PHONY: dist pc android all
