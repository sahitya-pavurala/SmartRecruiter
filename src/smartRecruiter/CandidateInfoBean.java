package smartRecruiter;

public class CandidateInfoBean {

	private int id;
	private String firstName;
	private String lastName;
	private String linkToProfile;
	private String jobId;
	private String platform;
	private String interviewProfile;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getLinkToProfile() {
		return linkToProfile;
	}
	public void setLinkToProfile(String linkToProfile) {
		this.linkToProfile = linkToProfile;
	}
	public String getJobId() {
		return jobId;
	}
	public void setJobId(String jobId) {
		this.jobId = jobId;
	}
	public String getPlatform() {
		return platform;
	}
	public void setPlatform(String platform) {
		this.platform = platform;
	}
	public String getInterviewProfile() {
		return interviewProfile;
	}
	public void setInterviewProfile(String interviewProfile) {
		this.interviewProfile = interviewProfile;
	}
	
	
}
