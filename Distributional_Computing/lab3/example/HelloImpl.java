// The servant -- object implementation -- for the Hello
// example.  Note that this is a subclass of HelloPOA, whose
// source file is generated from the compilation of
// Hello.idl using j2idl.

import HelloApp.*;
import org.omg.CosNaming.*;
import org.omg.CosNaming.NamingContextPackage.*;
import org.omg.CORBA.*;
import org.omg.PortableServer.*;
import org.omg.PortableServer.POA;

import java.util.Properties;

class HelloImpl extends HelloPOA {
  private ORB orb;

  public void setORB(ORB orb_val) {
    orb = orb_val; 
  }
  
  // implement sayHello() method
  public String sayHello()
  {
   // callobj.callback(msg);
   return "\nHello world !!\n";
 }
 
  // implement shutdown() method
 public void shutdown() {
  orb.shutdown(false);
}
} //end class
