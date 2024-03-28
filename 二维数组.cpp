#include <iostream>
using namespace std;
#include <string>

int main(){

    int arr[3][3]=
    {
        {100,100,100},
        {60,80,90},
        {70,40,10}
    };
    string name[3]={"张三","李四","王五"};

    int score=0;
    
    for (int i = 0; i < 3; i++)
    {
        for (int j = 0; i < 3; i++)
        {
            score += arr[i][j];
            cout << name[i]<<"："<<arr[i][j] <<endl;
            
        
        }
        
        
        
    }
    


    system("pause");
    return 0;
}