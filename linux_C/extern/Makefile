objects = main.o stack.o
cc = gcc -Wall

extern : $(objects)
	$(cc) -o extern $(objects)

stack.o : 
main.o :

.PHONY : clean
clean :
	rm -rf *.o extern
