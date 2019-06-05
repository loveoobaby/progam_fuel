//
// Created by yss on 2019/6/5.
//


//
// Created by yss on 2019/6/5.
//
#include <stdio.h>
#include <stdlib.h>

#define OK 1
#define ERROR -1
#define TRUE 1
#define FALSE 0

typedef int Status;
typedef int ElemType;

typedef struct Node{
    ElemType data;
    struct Node* next;
} LinkedList;


Status GetElem(LinkedList *L, int i, ElemType *e) {

    LinkedList* p = L;
    int j = 0;
    while (p && j<i){
        p = p->next;
        ++j;
    }

    if(!p && j>i){
        return ERROR;
    }
    *e = p->data;
    return OK;
}


Status InsertElem(LinkedList *L, int i, ElemType e) {
    LinkedList* p=L;
    LinkedList* s;

    int j =1;
    while (p && j<i){
        p = p->next;
        j++;
    }

    if(!p || j>i){
        return ERROR;
    }


    s = (LinkedList*)malloc(sizeof(LinkedList));
    s->data = e;
    s->next = p->next;
    p->next = s;
    return OK;
}

Status InitHead(LinkedList *L) {
    L = (LinkedList *) malloc(sizeof(LinkedList));
    L->data = NULL;
}

void printList(LinkedList *L) {
    LinkedList *p = L->next;
    while (p){
        printf(" %3d ", p->data);
        p = p->next;
    }
}



int main(int argc, char *argv[]) {
    LinkedList L;
    InitHead(&L);
    InsertElem(&L, 1, 1);
    InsertElem(&L, 1, 2);
    InsertElem(&L, 1, 3);
    InsertElem(&L, 1, 4);
    InsertElem(&L, 1, 5);

    printList(&L);

}


