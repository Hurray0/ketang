/**
 * @Author hurray
 * @Part 
 * @Note 
 * @Encoding UTF-8 
 * @Date 2014-11-22 04:01:07
 * @Copyright Hurray@BUPT
 * @MainPage http://www.ihurray.com
 * 
 */

package function;
import java.rmi.*;
/**
 * @Author hurray
 * @Class RegistInterface
 */
public interface RegistInterface extends Remote {
    public String sayHello(String name) throws java.rmi.RemoteException;
}