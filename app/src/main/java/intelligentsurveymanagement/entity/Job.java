package intelligentsurveymanagement.entity;

public class Job {

    private Long id = null;

    private String jobTitle = null;

    private String jobDescription = null;

    private String jobStartTimestamp = null;

    private String jobEndTimestamp = null;

    private String jobLocation = null;

    private String clientName = null;

    private String inspector = null;

    private String status = null;

    private User user = null;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public String getJobDescription() {
        return jobDescription;
    }

    public void setJobDescription(String jobDescription) {
        this.jobDescription = jobDescription;
    }

    public String getJobStartTimestamp() {
        return jobStartTimestamp;
    }

    public void setJobStartTimestamp(String jobStartTimestamp) {
        this.jobStartTimestamp = jobStartTimestamp;
    }

    public String getJobEndTimestamp() {
        return jobEndTimestamp;
    }

    public void setJobEndTimestamp(String jobEndTimestamp) {
        this.jobEndTimestamp = jobEndTimestamp;
    }

    public String getJobLocation() {
        return jobLocation;
    }

    public void setJobLocation(String jobLocation) {
        this.jobLocation = jobLocation;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public String getInspector() {
        return inspector;
    }

    public void setInspector(String inspector) {
        this.inspector = inspector;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
