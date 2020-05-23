extern :main.o stack.o
	cc -o extern stack.o main.o

stack.o :stack.c
	cc -c stack.c

main.o :main.c
	cc -c main.c

clean :
	rm -rf *.o extern
