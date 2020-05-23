/* main.c */
#include <stdio.h>

extern void push(char);


int main(void)
{
	push('a');
	push('b');
	push('c');

	return 0;
}