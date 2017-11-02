# The path to MiniSAT version 2
MINISAT2_PATH = ./minisat2-070721
# The path to MiniSAT version 2.2.0
MINISAT220_PATH = ./minisat
# The path to ZChaff
ZCHAFF_PATH = ./zchaff64



CFLAGS = -I.
CFLAGS += -g
#CFLAGS += -pg
CFLAGS += -O3
CFLAGS += -Wall
#CFLAGS += --pedantic
#CFLAGS += -DDEBUG

OBJS = defs.o bc.o gate.o gatehash.o handle.o
OBJS += parser.tab.o lexer.o
OBJS += parser11.tab.o lexer11.o
OBJS += timer.o heap.o

LINK = #-static
CC = g++


#LIB += /usr/lib/ccmalloc.o -ldl #-lfl



all: bc2cnf bc2edimacs edimacs2bc bc2iscas89


clean:
	rm -f bc2cnf bc2cnf.o
	rm -f bc2edimacs bc2edimacs.o
	rm -f edimacs2bc edimacs2bc.o
	rm -f bc2iscas89 bc2iscas89.o
	rm -f bczchaff bczchaff.o bczchaff_solve.o
	rm -f bcminisat.o bcminisat_solve.o
	rm -f bcminisat2core bcminisat2simp
	rm -f bcminisat220core bcminisat220simp
	rm -f $(OBJS)
	rm -f parser.tab.c parser.tab.h parser.output lexer.c
	rm -f parser11.tab.c parser11.tab.h parser11.output lexer11.c
	rm -f *~

.cc.o:
	$(CC) $(CFLAGS) -c $<

.c.o:
	$(CC) $(CFLAGS) -c $<


lexer.c: lexer.lex parser.tab.h
	flex -L -Pbcp_ -olexer.c lexer.lex;

parser.tab.c parser.tab.h: parser.y
	bison -b parser -p bcp_ -d parser.y;

lexer11.c: lexer11.lex parser11.tab.h
	flex -L -Pbcp11_ -olexer11.c lexer11.lex;

parser11.tab.c parser11.tab.h: parser11.y
	bison -b parser11 -p bcp11_ -d parser11.y;

bc2cnf: $(OBJS) bc2cnf.o
	$(CC) $(CFLAGS) -o $@ $(OBJS) bc2cnf.o $(LIB) $(LINK) 

bc2edimacs:  defs.o bc.o gate.o gatehash.o lexer.o parser.tab.o handle.o bc2edimacs.o
	$(CC) $(CFLAGS) -o $@ $(OBJS) bc2edimacs.o $(LIB) $(LINK) 

edimacs2bc:  defs.o bc.o gate.o gatehash.o lexer.o parser.tab.o handle.o edimacs2bc.o
	$(CC) $(CFLAGS) -o $@ $(OBJS) edimacs2bc.o $(LIB) $(LINK) 

bc2iscas89: $(OBJS) bc2iscas89.o
	$(CC) $(CFLAGS) -o $@ $(OBJS) bc2iscas89.o $(LIB) $(LINK) 






#
# bczchaff
#
.PHONY : bczchaff
bczchaff: $(OBJS) bczchaff.o 
	cd $(ZCHAFF_PATH) && make
	$(CC) $(CFLAGS) -I$(ZCHAFF_PATH) -DBC_HAS_ZCHAFF -c bczchaff_solve.cc
	$(CC) $(CFLAGS) -o bczchaff $(OBJS) bczchaff_solve.o bczchaff.o -L$(ZCHAFF_PATH) -lsat $(LIB) $(LINK) 



#
# bcminisat
#
# MiniSat 2 core version
.PHONY : bcminisat2core
bcminisat2core: $(OBJS) bcminisat.o
	cd $(MINISAT2_PATH)/core && make lib
	$(CC) $(CFLAGS) -I$(MINISAT2_PATH) -I$(MINISAT2_PATH)/core -I$(MINISAT2_PATH)/mtl -DBC_HAS_MINISAT -DMINISAT2CORE -c bcminisat_solve.cc
	$(CC) $(CFLAGS) -o bcminisat2core $(OBJS) bcminisat_solve.o bcminisat.o $(LIB) -L$(MINISAT2_PATH)/core -lminisat $(LINK) 
# MiniSat 2 with simplifier
.PHONY : bcminisat2simp
bcminisat2simp: $(OBJS) bcminisat.o
	cd $(MINISAT2_PATH)/simp && make lib
	$(CC) $(CFLAGS) -I$(MINISAT2_PATH) -I$(MINISAT2_PATH)/core -I$(MINISAT2_PATH)/simp -I$(MINISAT2_PATH)/mtl -DBC_HAS_MINISAT -DMINISAT2SIMP -c bcminisat_solve.cc
	$(CC) $(CFLAGS) -o bcminisat2simp $(OBJS) bcminisat_solve.o bcminisat.o $(LIB) -L$(MINISAT2_PATH)/simp -lminisat $(LINK) 
# MiniSat 2.2.0 core version
.PHONY : bcminisat220core
bcminisat220core: $(OBJS) bcminisat.o
	export MROOT=.. && cd $(MINISAT220_PATH)/core && make libr
	$(CC) $(CFLAGS) -I$(MINISAT220_PATH) -I$(MINISAT220_PATH)/core -I$(MINISAT220_PATH)/mtl -DBC_HAS_MINISAT -DMINISAT220CORE -c bcminisat220_solve.cc
	$(CC) $(CFLAGS) -o bcminisat220core $(OBJS) bcminisat220_solve.o bcminisat.o $(LIB) -L$(MINISAT220_PATH)/core -l_release $(LINK) 
# MiniSat 2.2.0 with simplifier
.PHONY : bcminisat220simp
bcminisat220simp: $(OBJS) bcminisat.o
	export MROOT=.. && cd $(MINISAT220_PATH)/simp && make libr
	$(CC) $(CFLAGS) -I$(MINISAT220_PATH) -I$(MINISAT220_PATH)/core -I$(MINISAT220_PATH)/simp -I$(MINISAT220_PATH)/mtl -DBC_HAS_MINISAT -DMINISAT220SIMP -c bcminisat220_solve.cc
	$(CC) $(CFLAGS) -o bcminisat220simp $(OBJS) bcminisat220_solve.o bcminisat.o $(LIB) -L$(MINISAT220_PATH)/simp -l_release $(LINK) 




# DO NOT DELETE

