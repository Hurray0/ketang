import VoteApp.*;
import org.omg.CosNaming.*;
import org.omg.CosNaming.NamingContextPackage.*;
import org.omg.CORBA.*;
import java.io.BufferedReader;
import java.io.InputStreamReader;

public class VoteClient {

    static Vote voteImpl;
    static BufferedReader br =new BufferedReader(new InputStreamReader(System.in));

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
            org.omg.CORBA.Object objRef = orb.resolve_initial_references("NameService");
            NamingContextExt ncRef = NamingContextExtHelper.narrow(objRef);
            String name = "Vote";
            voteImpl = VoteHelper.narrow(ncRef.resolve_str(name));
                // System.out.println("Obtained a handle on server object: " + voteImpl);
            while(true) {
                System.out.println("请输入您要进行的操作");
                System.out.println("1.列出所有投票结果");
                System.out.println("2.投票");
                System.out.println("3.退出");
                String voteChoose=br.readLine();
                switch (voteChoose) {
                    case "1":
                    {
                        System.out.println("所有投票结果如下(姓名，票数)");
                        System.out.println(voteImpl.getList());
                        // String voteString=voteImpl.getList();
                        // String[] voteList=voteString.split("#");
                        // for (int i=0;i<voteList.length;i++) {
                        //     System.out.println(voteList[i]);
                        // }
                        break;
                    }
                    case "2":
                    {
                        System.out.println("请输入候选人名字");
                        String candidateName=br.readLine();
                        voteImpl.castVote(candidateName);
                        break;
                    }
                    case "3":
                    {
                        br.close();
                        System.exit(0);
                    }
                    default:
                    {
                        break;
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("ERROR : " + e);
            e.printStackTrace(System.out);
        }

    }
}
