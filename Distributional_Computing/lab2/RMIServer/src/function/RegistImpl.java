/**
 * @Author hurray
 * @Part
 * @Note
 * @Encoding UTF-8
 * @Date 2014-11-22 03:59:14
 * @Copyright Hurray@BUPT
 * @MainPage http://www.ihurray.com
 *
 */
package function;

import java.rmi.*;
import java.rmi.server.*;

/**
 * @Author hurray
 * @Class Regist
 */
public class RegistImpl extends UnicastRemoteObject
        implements RegistInterface {

    public RegistImpl() throws RemoteException {
        super();
    }

    public String sayHello(String name) throws RemoteException {
        return "Hello, World!" + name;
    }
}
