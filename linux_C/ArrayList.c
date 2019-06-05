//
// Created by yss on 2019/6/5.
//
#include <stdio.h>
#include <stdlib.h>

#define MAX_SIZE  20
#define OK 1
#define ERROR -1
#define TRUE 1
#define FALSE 0

typedef int Status;
typedef int ElemType;

typedef struct {
    ElemType data[MAX_SIZE];
    int length;
} SqList;


Status GetElem(SqList L, int i, ElemType *e) {
    if (L.length == 0 || i < 0 || i > L.length) {
        return ERROR;
    }

    *e = L.data[i];
    return OK;
}


Status InsertElem(SqList *L, int i, ElemType e) {
    if (L->length > MAX_SIZE) {
        return ERROR;
    }

    if (i < 0 || i > L->length) {
        return ERROR;
    }

    for (int k = L->length; k > i; --k) {
        L->data[k + 1] = L->data[k];
    }

    L->data[i] = e;
    L->length++;
    return OK;
}

Status CreateList(SqList *L) {
    L = (SqList *) malloc(sizeof(SqList));
    L->length = 0;
}

void printList(SqList *L) {
    for (int i = 0; i < L->length; ++i) {
        printf(" %d ", L->data[i]);
    }
}

Status ListDel(SqList *L, int i) {
    if (L->length == 0) {
        return ERROR;
    }

    if (i < 0 || i > L->length) {
        return ERROR;
    }

    for (int k = i; k < L->length; ++k) {
        L->data[k] = L->data[k + 1];
    }
    L->length--;
    return OK;
}


int main(int argc, char *argv[]) {
    SqList L;
    CreateList(&L);
    InsertElem(&L, 0, 1);
    InsertElem(&L, 1, 2);

    ListDel(&L, 1);

    printList(&L);

}

