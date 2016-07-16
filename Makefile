TARGET = com.n1amr.tictactoe.Main
SRCDIR = src
OBJDIR = out

SRCS = \
com/n1amr/tictactoe/Main.java\
com/n1amr/tictactoe/AI.java\
com/n1amr/tictactoe/Board.java\
com/n1amr/tictactoe/GameForm.java\
com/n1amr/tictactoe/Game.java

COMPILER = javac
FLAGS = -g

SOURCES = $(addprefix $(SRCDIR)/, $(SRCS))
OBJECTS = $(addprefix $(OBJDIR)/, $(SRCS:.java=.class))

C_RESET = \033[00m
C_RED = \033[01;31m
C_DARK_GREEN = \033[00;32m
C_VIOLET = \033[01;35m

OK_STRING=$(C_DARK_GREEN)[OK]$(C_RESET)
ERROR_STRING=$(C_RED)[ERRORS]$(C_RESET)
WARN_STRING=$(C_VIOLET)[WARNINGS]$(C_RESET)

.PHONY: depend clean all

all:	$(TARGET)
	@echo "$(C_DARK_GREEN)Compiled successfully at \"$(TARGET)\".$(C_RESET)"

$(TARGET): $(OBJECTS)
	@echo

$(SRCDIR)/%.java:
	@echo -n "Compiling \"$<\"..."
	@$(COMPILER) $(FLAGS) $(INCLUDES) -cp $(SRCDIR) -d $(OBJDIR) $@  2> temp.log || touch temp.errors
	@if test -e temp.errors; then echo "$(ERROR_STRING)" && cat temp.log; elif test -s temp.log; then echo "$(WARN_STRING)" && cat temp.log; else echo "$(OK_STRING)"; fi;
	@$(RM) -f temp.errors temp.log

$(OBJDIR):
	mkdir $(OBJDIR)

$(OBJDIR)/%.class: $(SRCDIR)/%.java | $(OBJDIR)
	@echo -n "Compiling \"$<\"..."
	@$(COMPILER) $(FLAGS) $(INCLUDES) -cp $(SRCDIR) -d $(OBJDIR) $<  2> temp.log || touch temp.errors
	@if test -e temp.errors; then echo "$(ERROR_STRING)" && cat temp.log; elif test -s temp.log; then echo "$(WARN_STRING)" && cat temp.log; else echo "$(OK_STRING)"; fi;
	@$(RM) -f temp.errors temp.log

clean:
	$(RM) -rf $(OBJDIR)/*
	$(RM) -rf *.class *~ ./$(TARGET) $(OBJECTS)
	$(RM) -f temp.errors temp.log

run: all
	@java -classpath $(OBJDIR) $(TARGET);

depend: $(SOURCES)
	makedepend $(INCLUDES) $^
