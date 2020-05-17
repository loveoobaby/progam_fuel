#include <iostream>


using namespace std;

class A
{
public: 
	int i;
	void f();
};

void A::f()
{
	i = 20;
	cout << i << endl;
}


struct B
{
	int i;
};

void ff(struct B* p){
	p->i = 30;
	cout << p->i << endl;
}

int main()
{
	A a;
	a.f();
	struct B p;
	ff(&p);
	return 0;
}