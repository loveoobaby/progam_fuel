all: open_file inspect_open

open_file: open_file.c
	gcc -Wall -o open_file open_file.c

inspect_open: inspect_open.c
	gcc -shared -fPIC  inspect_open.c -o inspect_open.so -ldl

clean:
	rm -rf open_file inspect_open.so
