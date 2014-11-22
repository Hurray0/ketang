/**
 * @Author hurray
 * @Part 
 * @Note 
 * @Encoding UTF-8 
 * @Date 2014-11-22 04:12:55
 * @Copyright Hurray@BUPT
 * @MainPage http://www.ihurray.com
 * 
 */
import java.io.*;
import java.rmi.*;
import function.*;
import resource.R;
/**
 * @Author hurray
 * @Class RMIClient
 */
public class RMIClient {
    public static void main(String args[]) {
      try {
         int RMIPort;         
         String hostName;
         InputStreamReader is = new InputStreamReader(System.in);
         BufferedReader br = new BufferedReader(is);
         hostName = R.HOSTNAME;
         RMIPort = R.PORT;
         String registryURL = 
            "rmi://" + hostName+ ":" + RMIPort + "/hello";  
         // find the remote object and cast it to an interface object
         RegistInterface h =
           (RegistInterface)Naming.lookup(registryURL);
         System.out.println("Lookup completed " );
         // invoke the remote method
         String message = h.sayHello("Donald Duck");
         System.out.println("HelloClient: " + message);
      } // end try 
      catch (Exception e) {
         System.out.println("Exception in HelloClient: " + e);
      } 
   } //end main
}