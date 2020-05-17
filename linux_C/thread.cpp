#include <iostream>
#include <thread>

using namespace std;

void function_1(){
    cout << " thread work" << endl;
}

int main(){
    thread t(function_1);

    // t.detach(); 
    if(t.joinable()){
        t.join();
    }


    return 0;
}