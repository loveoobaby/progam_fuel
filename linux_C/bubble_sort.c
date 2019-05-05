//
// Compile: gcc bubble_sort.c
// Run: ./a.out
//

#include <stdio.h>
#include <time.h>
#include <stdlib.h>

void swap(int *a, int *b)
{
    int temp = *a;
    *a = *b;
    *b = temp;
}

void bubble_sort(int a[], int n)
{
    for(int i=n-1; i>=0;i--)
    {
        for(int j=0; j<i;j++)
        {
            if(a[j] > a[j+1])
            {
                swap(&a[j], &a[j+1]);
            }
        }
    }
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
    bubble_sort(a, n);
    int end_time = clock();

    for (int i = 0; i < number; ++i) {
        printf("%d, ", a[i]);
    }
    printf("\nrun time : %d \n", end_time - begin_time);
    return 0;
}