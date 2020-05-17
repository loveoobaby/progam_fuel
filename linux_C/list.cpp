
#include <stdexcept>
#include <string>
#include <cstdlib>
#include <cstdio>
#include <iostream>
#include <ctime>
#include <algorithm>
#include <list>

using namespace std;

namespace jj02
{
    void test_list(long &counter)
    {
        cout << "test list " << endl;

        /* initialize random seed: */
        srand(time(NULL));

        list<string> c;
        char buf[10];

        clock_t timeStart = clock();
        for (size_t i = 0; i < counter; i++)
        {
            try
            {
                snprintf(buf, 10, "%d", rand()%32767);
                c.push_back(string(buf));
            }
            catch (exception &p)
            {
                cout << "i=" << i << " " << p.what() << endl;
                abort();
            }
        }

        cout << "milli - second " << (clock() - timeStart) << endl;
        cout << "list.front = " << c.front() << endl;
        cout << "list.max_size = " << c.max_size() << endl;
        cout << "list.size = " << c.size() << endl;

        string target = "16807";
        auto pIterm = ::find(c.begin(), c.end(), target);
        if (pIterm != c.end())
        {
            cout << "find() " << *pIterm << endl;
        }
    }
} // namespace jj02

int main()
{
    long value = 1000000;
    jj02::test_list(value);
}