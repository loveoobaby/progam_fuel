//
// Created by yss on 2019/6/5.
//

#include <stdlib.h>
#include <stdio.h>

#define MAXSIZE 20

typedef int Status;
typedef int ElemType;

#define ERROR -1;
#define OK 0;

typedef struct {
    ElemType data[MAXSIZE];
    int top;
} Stack;

Status Push(Stack *S, ElemType e){
    if(S->top == MAXSIZE -1){ // 栈满
        return ERROR;
    }

    S->top++;
    S->data[S->top] = e;
}

Status InitStack(Stack *S){
    S->top = -1;
}

Status Pop(Stack *S, ElemType *e){
    if(S->top == -1){
        return ERROR;
    }

    *e = S->data[S->top];
    S->top--;
    return OK;
}

void printStack(Stack* S){
    for (int i = 0; i < S->top+1; ++i) {
        printf("%3d \n", S->data[i]);
    }
}

int main(int argc, char* argv[]){
    Stack L;
    InitStack(&L);
    Push(&L, 1);
    Push(&L, 2);
    Push(&L, 3);
    Push(&L, 4);
    
    ElemType pop;
    Pop(&L, &pop);

    printStack(&L);

}

