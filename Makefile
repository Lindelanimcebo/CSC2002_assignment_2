  
.SUFFIXES: .java .class
BINDIR=bin
SRCDIR=src
OUTDIR=output
DOCSDIR=docs
FILEIN="./data/med_in.txt"

$(BINDIR)/%.class:$(SRCDIR)/%.java
	javac -d $(BINDIR)/ -cp $(BINDIR) $<
	
CLASSES=Water.class \
		Terrain.class \
		FlowThread.class \
		FlowPanel.class \
		Flow.class
									

CLASS_FILES=$(CLASSES:%.class=$(BINDIR)/%.class)

default: $(CLASS_FILES)

clean:
	rm $(BINDIR)/*.class

docs:
	javadoc -d docs/ src/*.java

runM: 
	java -cp bin FlowSkeleton/Flow "./data/medsample_in.txt"

runL:
	java -cp bin FlowSkeleton/Flow "./data/largesample_in.txt"