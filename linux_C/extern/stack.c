/* stack.c */
#include<stdio.h>

char stack[512];
int top = -1;

void push(char c)
{
	stack[++top] = c;
}