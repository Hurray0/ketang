public class VoteClass {
	private String name;
	private int voteNum;

	public VoteClass(String name) {
		this.name = name;
		this.voteNum = 1;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getVoteNum() {
		return voteNum;
	}

	public void addVoteNum() {
		this.voteNum ++;
	}
}