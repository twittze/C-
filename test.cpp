#include <iostream>
using namespace std;



int main(){
    int a =rand()%100+1;
    cout << "shuru:"<< endl;
    int b=0;
    cin >> b;   
    while (a>b)
    {
        cout <<"small,again"<<endl;
        cin >> b;
        break;
    }
    while (a<b)
    {
        cout <<"big,again"<<endl;
        cin >> b;
        break;
    }
    if (a=b)
    {
        cout <<"yes,a:"<< a<<endl;
    }
    
    
    system("pause");
    
    
    
    return 0;
}