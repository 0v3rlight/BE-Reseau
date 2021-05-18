package Main;


//Java program to delete the first element of ArrayList 
import java.util.List; 
import java.util.ArrayList; 

public class test { 
 public static void main(String[] args) 
 { 
     List<Integer> al = new ArrayList<>(); 
     al.add(10); 
     al.add(20); 
     al.add(30); 
     al.add(1); 
     al.add(2); 

     // First element's index is always 0 
     int index = 0; 

     // Delete first element by passing index 
     System.out.println(al.remove(index)); 

     System.out.println("Modified ArrayList : " + al); 
     
     String s = "bonjour";
     String p = "traret, minabatur se discessurum: ut saltem id metuens perquisitor malivolus tandem desineret quieti coalitos homines in aperta pericula proiectare.\\r\\n\"";

     System.out.println(s.substring(0,3)); 
     System.out.println(s.substring(3));
     System.out.println(p.length());
 } 
} 
