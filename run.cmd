@ECHO OFF
PUSHD dist
REM the natives folder needs to be a part of dist, in the end.
java -Djava.library.path=..\pc\target\natives -jar snakegame-pc-0.0.1-SNAPSHOT.jar "640x480"
POPD
