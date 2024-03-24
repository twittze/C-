#include <iostream>
using namespace std;

int main(){
    int i=0;
    
    for ( i = 1; i < 101; i++)
    {
        if (i%7==0)
        {
            cout << i <<endl;
        }
        else if(i%10==7)
        {
            cout << i <<endl;

        }
        else if(i/10==7)
        {
            cout <<i<<endl;
        }
        
    }
    



    system("puase");
    return 0;
}