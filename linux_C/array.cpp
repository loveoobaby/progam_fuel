#include <array>
#include <iostream>
#include <ctime>
#include <cstdlib> // qsort bsearch NULL

using std::cout;
using std::endl;
using std::get;

#define ASIZE 500000

namespace jj01
{

    int compareLong(const void* a, const void* b){
        return *(long*)a - *(long*)b;
    }

    void test_array()
    {
        cout << "test array!" << endl;

        clock_t timeStart = clock();
        std::array<long, ASIZE> c;

        for (long i = 0; i < ASIZE; i++)
        {
            c[i] = rand();
        }

        cout << "milli - second " << (clock()-timeStart) << endl;
        cout << "array.front = " << c.front() << endl;
        cout << "array.data = " << c.data() << endl; // 返回数组的起始地址
        cout << "array.size = " << c.size() << endl;

        qsort(c.data(), ASIZE, sizeof(long), compareLong);

        // for (long i = 0; i < ASIZE; i++)
        // {
        //    cout << i <<" element in myarray: " << c.at(i) << "\n";
        // }
        

        
        

    }

} // namespace jj01


int main(){
    jj01::test_array();
}