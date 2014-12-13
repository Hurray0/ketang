import VoteApp.*;
import org.omg.CosNaming.*;
import org.omg.CosNaming.NamingContextPackage.*;
import org.omg.CORBA.*;
import org.omg.PortableServer.*;
import org.omg.PortableServer.POA;
import java.util.Properties;

public class VoteServer {
	public static void main(String args[]) {

		String newargs[] = new String[4];
        // -ORBInitialHost 127.0.0.1 -ORBInitialPort 5858
		if(args.length != 4) {
			newargs[0] = "-ORBInitialHost";
			newargs[1] = "127.0.0.1";
			newargs[2] = "-ORBInitialPort";
			newargs[3] = "5858";
		} else {
			newargs = args;
		}

		try {
			ORB orb = ORB.init(newargs, null);
			POA rootpoa = (POA)orb.resolve_initial_references("RootPOA");
			rootpoa.the_POAManager().activate();

			VoteImpl voteImpl = new VoteImpl();
			voteImpl.setORB(orb);

			org.omg.CORBA.Object ref = rootpoa.servant_to_reference(voteImpl);
			Vote href = VoteHelper.narrow(ref);

			org.omg.CORBA.Object objRef = orb.resolve_initial_references("NameService");
			NamingContextExt ncRef = NamingContextExtHelper.narrow(objRef);

			String name = "Vote";
			NameComponent path[] = ncRef.to_name( name );
			ncRef.rebind(path, href);

			System.out.println("VoteServer ready and waiting ...");
			orb.run();
		}

		catch (Exception e) {
			System.err.println("ERROR: " + e);
			e.printStackTrace(System.out);
		}

		System.out.println("VoteServer Exiting ...");
	}
}