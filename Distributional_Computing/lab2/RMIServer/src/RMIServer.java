
/**
 * @Author hurray
 * @Part
 * @Note
 * @Encoding UTF-8
 * @Date 2014-11-22 03:55:33
 * @Copyright Hurray@BUPT
 * @MainPage http://www.ihurray.com
 *
 */
import function.*;
import java.io.*;
import java.net.*;
import java.rmi.*;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import resource.*;

/**
 * @Author hurray
 * @Class RMIServer
 */
public class RMIServer {

    public HashMap<String, String> user_map = new HashMap<String, String>();
    public List meetingList = new ArrayList();

    public RMIServer() {
        String portNum, registryURL;

        try {
            int RMIPortNum = R.PORT;
            startRegistry(RMIPortNum);
            Impl exportedObj = new Impl(user_map, meetingList);
            registryURL = "rmi://localhost:" + R.PORT + "/meeting";
            Naming.rebind(registryURL, exportedObj);
            System.out.println("Server registered.  Registry currently contains:");
            listRegistry(registryURL);
            System.out.println("Hello Server ready.");
        } catch (Exception re) {
            System.out.println("Exception in HelloServer.main: " + re);
        }
    }

    public static void main(String args[]) {
        new RMIServer();
    }

    private static void startRegistry(int RMIPortNum)
            throws RemoteException {
        try {
            Registry registry = LocateRegistry.getRegistry(RMIPortNum);
            registry.list();
        } catch (RemoteException e) {

            System.out.println("RMI registry cannot be located at port "
                    + RMIPortNum);
            Registry registry
                    = LocateRegistry.createRegistry(RMIPortNum);
            System.out.println(
                    "RMI registry created at port " + RMIPortNum);
        }
    }

    // This method lists the names registered with a Registry object
    private static void listRegistry(String registryURL)
            throws RemoteException, MalformedURLException {
        System.out.println("Registry " + registryURL + " contains: ");
        String[] names = Naming.list(registryURL);
        for (int i = 0; i < names.length; i++) {
            System.out.println(names[i]);
        }
    }

}
