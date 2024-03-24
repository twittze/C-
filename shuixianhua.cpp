#include <iostream>
using namespace std;

int main(){

int t=100;
do{

   int a=0;
   int b=0;
   int c=0;
   a=t%10;
   b=t/10%10;
   c=t/100;
   if (a*a*a+b*b*b+c*c*c==t)
   {
      cout << t <<endl;
   }
   t++;
} while (t <1000);


system("pause");

    return 0;
}