package oops;

class ExampleOverloading
{
    public void disp(char c)
    {
         System.out.println(c);
    }
    public void disp(char c, int num)  
    {
         System.out.println(c + " "+num);
    }
}
public class DisplayOverloading 
{
   public static void main(String args[])
   {
	   ExampleOverloading obj = new ExampleOverloading();
       obj.disp('a');
       obj.disp('a',10);
   }
}

