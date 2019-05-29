/**
 * 函数指针demo
 * 当函数名出现在表达式中，如 void (*fun_ptr)(int) = fun；
 * 它就会"蜕变"成一个指针，即隐式取出其地址，这点类似于数组名的行为。
 * 故上述代码等价于：void (*fun_ptr)(int) = &fun；
 */

#include <stdio.h>

void fun(int a)
{
    printf("Value a = %d\n", a);
}

int main(int argc, char* argv[])
{
    void (*fun_ptr)(int) = fun;
    (*fun_ptr)(10);
    printf("%p\n", &fun);
    printf("%p\n", *fun_ptr);

}
