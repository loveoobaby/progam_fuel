#include <vector>
#include <stdexcept>
#include <string>
#include <cstdlib>
#include <cstdio>
#include <iostream>
#include <ctime>
#include <algorithm>

using namespace std;

#define ASIZE 500

namespace jj02
{
    void test_vector(long &counter)
    {
        cout << "test vector " << endl;

        vector<string> c;
        char buf[10];

        clock_t timeStart = clock();
        for (long i = 0; i < counter; i++)
        {
            try
            {
                snprintf(buf, 10, "%d", rand());
                c.push_back(string(buf));
            }
            catch (exception &e)
            {
                cout << e.what() << endl;
                abort();
            }
        }

        cout << "milli seconds = " << (clock() - timeStart) << endl;
        cout << "vector size = " << c.size() << endl;
        cout << "vector front = " << c.front() << endl;
        cout << "vector data = " << c.data() << endl;
        
    }
} // namespace jj02

int main()
{
    long value = 100;
    jj02::test_vector(value);
}