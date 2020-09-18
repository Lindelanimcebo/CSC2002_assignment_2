  
.SUFFIXES: .java .class
BINDIR=bin
SRCDIR=src
OUTDIR=output
DOCSDIR=docs
FILEIN="./data/med_in.txt"

$(BINDIR)/%.class:$(SRCDIR)/%.java
	javac -d $(BINDIR)/ -cp $(BINDIR) $<
	
CLASSES=Terrain.class \
		FlowPanel.class \
		Flow.class
									

CLASS_FILES=$(CLASSES:%.class=$(BINDIR)/%.class)

default: $(CLASS_FILES)

clean:
	rm $(BINDIR)/*.class

docs:
	rm $(DOCSDIR)/*
	javadoc -d $(DOCSDIR)/ $(SRCDIR)/*.java $<

run: 
	java -cp bin FlowSkeleton/Flow "./data/medsample_in.txt"
