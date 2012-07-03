
all:
	mvn -Dmaven.test.skip=true -q package

# engine 
dist/core-0.0.1-SNAPSHOT.jar: core/target/core-0.0.1-SNAPSHOT.jar
	cp "$<" "$@"
dist/pc-0.0.1-SNAPSHOT.jar: pc/target/pc-0.0.1-SNAPSHOT.jar
	cp "$<" "$@"

# demo
dist/snakegame-0.0.1-SNAPSHOT.jar: snakegame/target/snakegame-0.0.1-SNAPSHOT.jar
	cp "$<" "$@"

# lwjgl dependency
dist/lwjgl-2.8.4.jar: ${HOME}/.m2/repository/org/lwjgl/lwjgl/lwjgl/2.8.4/lwjgl-2.8.4.jar
	cp "$<" "$@"
dist/lwjgl_util-2.8.4.jar: ${HOME}/.m2/repository/org/lwjgl/lwjgl/lwjgl_util/2.8.4/lwjgl_util-2.8.4.jar
	cp "$<" "$@"
dist/lwjgl-platform-2.8.4-natives-linux.jar: ${HOME}/.m2/repository/org/lwjgl/lwjgl/lwjgl-platform/2.8.4/lwjgl-platform-2.8.4-natives-linux.jar
	cp "$<" "$@"

# the natives folder needs to be a part of dist, in the end.
run: dist/core-0.0.1-SNAPSHOT.jar dist/pc-0.0.1-SNAPSHOT.jar \
	dist/snakegame-0.0.1-SNAPSHOT.jar \
	dist/lwjgl-2.8.4.jar dist/lwjgl_util-2.8.4.jar dist/lwjgl-platform-2.8.4-natives-linux.jar
	(cd dist && java -Djava.library.path=../pc/target/natives -jar snakegame-0.0.1-SNAPSHOT.jar "640x480")
