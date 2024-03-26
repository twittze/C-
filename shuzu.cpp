#include <iostream>
using namespace std;

int main(){
    //小猪称重
   /*int arr[5]={300,350,200,400,250};
    int max=0;
    max=arr[0];  //将数组首部赋值给max
    for (int i = 0; i < 5; i++)
    {
        if (arr[i] > max)
        max =arr[i];  //判断数组中的每一个数是否大于第一个数
    }
    cout <<max<<endl;*/




    int a[5]={1,2,3,4,5};
    int b[5];
    for (int j = 0; j < 5; j++)
    {
       b[4-j]=a[j];
    }
    for (int k = 0; k < 5; k++)
    {
    cout <<b[k]<<endl;
    }
    


    system("pause");
    return 0;
}