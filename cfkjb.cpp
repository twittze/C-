#include <iostream>
using namespace std;

int main(){

    int a=0;

    for (int i = 1; i < 10; i++)
    {
        
        for (int j = 1; j <= i; j++)
        {
           a=i*j;
           cout << j<< "*" << i <<"="<<a <<"  ";
        }
        cout<<endl;
        
    }
    


    system("pause");
    return 0;
}