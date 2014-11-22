/**
 * @Author hurray
 * @Part RMI
 * @Note RMI的Interface
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
public interface Interface extends Remote {

    public boolean regist(String username, String passwd) throws java.rmi.RemoteException;

    public boolean landed(String username, String passwd) throws java.rmi.RemoteException;

    public String addMeeting(String user1, String user2, long startTime, long endTime, String label) throws java.rmi.RemoteException;

    public String queryMeeting(String user1, long startTime, long endTime) throws java.rmi.RemoteException;

    public String delMeeting(String user1, int id) throws java.rmi.RemoteException;

    public String clearMeeting(String user1) throws java.rmi.RemoteException;
}
