import VoteApp.*;
import org.omg.CosNaming.*;
import org.omg.CosNaming.NamingContextPackage.*;
import org.omg.CORBA.*;
import org.omg.PortableServer.*;
import org.omg.PortableServer.POA;
import java.util.List;
import java.util.ArrayList;

import java.util.Properties;

class VoteImpl extends VotePOA {
	private ORB orb;
	private List<VoteClass> voterList;

	public void setORB(ORB orb) {
		this.orb = orb;
		voterList = new ArrayList<VoteClass>();
	}

	public String getList() {
		String returnMsg;
		returnMsg = "Name\tVoteNum\n";
		for(int i = 0; i < voterList.size(); i++) {
			returnMsg += voterList.get(i).getName() + "\t" +
				voterList.get(i).getVoteNum() + "\n";
		}
		return returnMsg;
	}

	public void castVote(String name) {
		for(int i = 0; i < voterList.size(); i++) {
			if(voterList.get(i).getName().equals(name)) {
				voterList.get(i).addVoteNum();
				System.out.println("第" + i + "位候选人 " + name + " 被投了一票！");
				return;
			}
		}
		voterList.add(new VoteClass(name));
		System.out.println("新候选人" + name + "获得了一票！");
	}

	// public void shutdown() {
	// 	orb.shutdown(false);
	// }
}