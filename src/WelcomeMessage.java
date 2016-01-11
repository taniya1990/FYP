import java.lang.*;
class WelcomeMessage
{
  void printMessage()
  {
      System.out.println("Hello World");
  }

  int number(){
      int a = 4+7;
      return a;
  }
}
class Myclass
{
   public static void main(String  []args)
   {
      WelcomeMessage obj=new  WelcomeMessage ();
      obj.printMessage();
      obj.number();
      System.out.println(obj.number());
      animal ani = new animal();
      ani.run();
   }
}