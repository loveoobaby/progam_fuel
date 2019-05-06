//
// Created by yss on 2019/5/6.
//
#include <stdio.h>
#include <stdlib.h>
#include <time.h>

#define N 8

void insert_sort(int a[], int length)
{
    for (int i = 1; i < length; ++i) {
        int j = 0;
        while (a[j]<a[i]&& j<i)
        {
            j++;
        }
        if(i!=j)
        {
            int temp = a[i];
            for (int k = i; k > j; k--) {
                a[k] = a[k-1];
            }
            a[j] = temp;
        }
    }
}



int main(int argc, char *argv[])
{
    srand(time(NULL));
    int a[N];

    for (int i = 0; i < N; i++) {
        a[i] = rand() % 10000;      //生成一个小于10000的随机数
    }

    int begin_time = clock();
    insert_sort(a, N);
    int end_time = clock();

    for (int i = 0; i < N; ++i) {
        printf("%d, ", a[i]);
    }
    printf("\nrun time : %d \n", end_time - begin_time);

    return 0;
}