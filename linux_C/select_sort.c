//
// Created by yss on 2019/5/6.
//


#include <stdio.h>
#include <stdlib.h>
#include <time.h>

#define N 10

void swap(int *a, int *b) {
    int temp = *a;
    *a = *b;
    *b = temp;
}

void select_sort(int a[], int length) {
    for (int i = 0; i < length; ++i) {
        int min = i;
        for (int j = i + 1; j < length; ++j) {
            if (a[j] < a[min]) {
                min = j;
            }
        }
        if (min != i) {
            swap(&a[i], &a[min]);
        }
    }

}

int main(int argc, char *argv[]) {
    srand(time(NULL));
    int a[N];

    for (int i = 0; i < N; i++) {
        a[i] = rand() % 10000;      //生成一个小于10000的随机数
    }

    int begin_time = clock();
    select_sort(a, N);
    int end_time = clock();

    for (int i = 0; i < N; ++i) {
        printf("%d, ", a[i]);
    }
    printf("\nrun time : %d \n", end_time - begin_time);

    return 0;
}