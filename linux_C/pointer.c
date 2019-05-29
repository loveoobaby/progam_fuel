
#include <stdio.h>

int main(int argc, char* argv[])
{
    /**
     *  获取变量的地址使用&
     *  运算符*有两种用法： 1. 定义指针变量 如int *p; 2. 获取地址保存的变量值
     * %p可以打印地址
     */
    int x;
    printf("%p", &x);

    int var = 10;
    int *ptr = &var;

    printf("Value of var is %d\n", var);
    printf("Address of Var = %p \n", ptr);

    *ptr = 20;
    printf("After doing *ptr = 20, *ptr is %d\n", *ptr);

    /**
     * 数组与指针
     *
     * 指针可以进行运算，但只有与数组结合起来才有实际意义
     *
     * 数组名可以直接作为指针使用
     *
     */

    int v[3] = {10, 100, 200};
    int *ptr_arr = v;
    for (int i = 0; i < 3; i++)
    {
        printf("Value of *ptr = %d\n", *ptr_arr);
        printf("Value of *(v+%d) = %d\n", i, *(v+i));
        printf("Value of ptr = %p\n\n", ptr_arr);
        ptr_arr++;
    }







}
























