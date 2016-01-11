/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
import java.util.Scanner;
/**
 *
 * @author Tani
 */
public class animal {
    int i,j=10,k=9;
    void run(){
        for(i=0;i<4;i++){
            System.out.println("taniay" +i);
        }
        while (j>0){
            System.out.println("Lanka" );
            j--;
        }
        do{
            System.out.println("Jayasekara");
            k--;
        }while(k>0);
        System.out.println("enter scan :");
        Scanner s = new Scanner(System.in);
        s.next();
        System.out.println(s);
        
    }

}
