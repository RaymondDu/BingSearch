JFLAGS = -cp commons-codec-1.7.jar
JC = javac
.SUFFIXES: .java .class
.java.class:
	$(JC) $(JFLAGS) $*.java

CLASSES = \
        BingTest.java

default: classes

classes: $(CLASSES:.java=.class)

clean:
	$(RM) *.class

run:
	java -cp "commons-codec-1.7.jar:." BingTest