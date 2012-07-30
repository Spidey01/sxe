
goals ?= package
DESTDIR ?= $(CURDIR)/dist
thismvnflags = -Dmaven.test.skip=true -q 

sxedeps = $(DESTDIR)/core-0.0.1-SNAPSHOT.jar
pcdeps = $(sxedeps) \
		 $(DESTDIR)/lwjgl-2.8.4.jar \
		 $(DESTDIR)/lwjgl_util-2.8.4.jar \
		 $(DESTDIR)/lwjgl-platform-2.8.4-natives-linux.jar \
		 $(DESTDIR)/pc-0.0.1-SNAPSHOT.jar
snakegamedeps = $(DESTDIR)/snakegame-lib-0.0.1-SNAPSHOT.jar \
				$(DESTDIR)/snakegame-pc-0.0.1-SNAPSHOT.jar

pc:
	mvn -P $@ $(thismvnflags) $(goals)
	
dist-pc: dist $(pcdeps)

clean-pc:
	mvn -P pc $(thismvnflags) clean

dist-snakegame-pc: dist-pc $(snakegamedeps)


# The natives folder needs to be a part of dist-pc. This is done because I
# don't want the extra disk writes of copying the extra files to $DESTDIR on
# every test run.
run-snakegame-pc: dist-snakegame-pc
	(cd "$(DESTDIR)" && java -Djava.library.path="$(CURDIR)/pc/target/natives" -jar snakegame-pc-0.0.1-SNAPSHOT.jar "640x480")

android:
	mvn -P $@ $(thismvnflags) $(goals)

dist-snakegame-android: dist
	cp snakegame/android/target/snakegame-android-0.0.1-SNAPSHOT.apk "$(DESTDIR)/"

dropbox-dist-snamegame-android: dist-snakegame-android
	cp "$(DESTDIR)/snakegame-android-0.0.1-SNAPSHOT.apk" ~/Dropbox/snakegame-android-0.0.1-SNAPSHOT.apk	

clean-android:
	mvn -P android $(thismvnflags) clean

# N.B. doesn't work when you're doing the build on the device itself, instead
# of using PC->USB.
run-snakegame-android: android
	adb install snakegame/android/target/snakegame-android-0.0.1-SNAPSHOT.apk

# Use tee here because we rarely care about the exit status of this target so
# much as the corpious output.
docs:
	mvn -P $@ $(thismvnflags) javadoc:aggregate | tee mvn.log

clean-docs:
	rm -rf target/site/apidocs

tags:
	find . -name \*.java | xargs ctags

dist: $(DESTDIR)

clean: clean-pc clean-android clean-docs

distclean:
	rm -rf "$(DESTDIR)"/*

# destructively make this a pristine environment
pristine: clean distclean
	-rmdir "$(DESTDIR)"
	-rm -rf target
	[ -f .git ] && git reset --hard HEAD

all: pc android docs


###                                                      ###
### Targets below here are either "Concrete" or "Magic". ###
###                                                      ###

$(DESTDIR):
	[ ! -d "$(DESTDIR)" ] && mkdir "$(DESTDIR)"

# engine 
$(DESTDIR)/core-0.0.1-SNAPSHOT.jar: core/target/core-0.0.1-SNAPSHOT.jar dist
	cp "$<" "$@"
$(DESTDIR)/pc-0.0.1-SNAPSHOT.jar: pc/target/pc-0.0.1-SNAPSHOT.jar dist
	cp "$<" "$@"

# demo
$(DESTDIR)/snakegame-lib-0.0.1-SNAPSHOT.jar: snakegame/lib/target/snakegame-lib-0.0.1-SNAPSHOT.jar dist
	cp "$<" "$@"
$(DESTDIR)/snakegame-pc-0.0.1-SNAPSHOT.jar: snakegame/pc/target/snakegame-pc-0.0.1-SNAPSHOT.jar dist
	cp "$<" "$@"

# lwjgl dependency
$(DESTDIR)/lwjgl-2.8.4.jar: ${HOME}/.m2/repository/org/lwjgl/lwjgl/lwjgl/2.8.4/lwjgl-2.8.4.jar dist
	cp "$<" "$@"
$(DESTDIR)/lwjgl_util-2.8.4.jar: ${HOME}/.m2/repository/org/lwjgl/lwjgl/lwjgl_util/2.8.4/lwjgl_util-2.8.4.jar dist
	cp "$<" "$@"
$(DESTDIR)/lwjgl-platform-2.8.4-natives-linux.jar: ${HOME}/.m2/repository/org/lwjgl/lwjgl/lwjgl-platform/2.8.4/lwjgl-platform-2.8.4-natives-linux.jar dist
	cp "$<" "$@"

.PHONY: all pc android tags docs clean clean clean-pc clean-android clean-docs distclean pristine
