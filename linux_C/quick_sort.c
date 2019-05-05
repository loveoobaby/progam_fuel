//
// Compile: gcc quick_sort.c
// Run: ./a.out
//

#include <stdio.h>
#include <time.h>
#include <stdlib.h>

void swap(int *a, int *b) {
    int temp = *a;
    *a = *b;
    *b = temp;
}

int partition(int a[], int low, int height) {
    int base = a[low];//分区基准


    while (low < height) {
        // 从右向左扫描，找到第一个小于base的值
        while (a[height] >= base && height > low) {
            height--;
        }
        if (height > low) {
            a[low] = a[height];
        }

        while (a[low] <= base && height > low) {
            low++;
        }
        if (low < height) {
            a[height] = a[low];
        }
    }

    a[low] = base;
    return low;
}


void quick_sort(int a[], int low, int height) {
    if (low >= height) {
        return;
    }
    int pi = partition(a, low, height);
    quick_sort(a, low, pi - 1);
    quick_sort(a, pi + 1, height);
}


int main(int argc, char *argv[]) {

    int number = 10000;
    srand( time(NULL) );
    int a[number];

    for(int i=0;i<number;i++)
    {
        a[i]=rand()%10000;      //生成一个小于1000的随机数
    }

    int begin_time = clock();
    quick_sort(a, 0, number - 1);
    int end_time = clock();

    for (int i = 0; i < number; ++i) {
        printf("%d, ", a[i]);
    }
    printf("\nrun time : %d \n", end_time - begin_time);
    return 0;
}