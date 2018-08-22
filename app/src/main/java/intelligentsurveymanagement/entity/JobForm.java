package intelligentsurveymanagement.entity;

import java.util.List;

public class JobForm {

    private Long id = null;

    private List<String> photoUrls = null;

    private String videoUrl = null;

    private Boolean haveWalked = null;

    private Boolean isLiveSystem = null;

    private Boolean isTrained = null;

    private Boolean isMSDSNecessary = null;

    private Boolean isAirMonitoringRequired = null;

    private Boolean isWorkPermitRequired = null;

    private String qrCode = null;

    private String barCode = null;

    private String customerName = null;

    private Integer raitingTrained = null;

    private Integer raitingProfessional = null;

    private Integer raitingSupervised = null;

    private Integer raitingSatisfied = null;

    private String comment = null;

    private String customerSignUrl = null;

    private Job job = null;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<String> getPhotoUrls() {
        return photoUrls;
    }

    public void setPhotoUrls(List<String> photoUrls) {
        this.photoUrls = photoUrls;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }

    public Boolean getHaveWalked() {
        return haveWalked;
    }

    public void setHaveWalked(Boolean haveWalked) {
        this.haveWalked = haveWalked;
    }

    public Boolean getLiveSystem() {
        return isLiveSystem;
    }

    public void setLiveSystem(Boolean liveSystem) {
        isLiveSystem = liveSystem;
    }

    public Boolean getTrained() {
        return isTrained;
    }

    public void setTrained(Boolean trained) {
        isTrained = trained;
    }

    public Boolean getMSDSNecessary() {
        return isMSDSNecessary;
    }

    public void setMSDSNecessary(Boolean MSDSNecessary) {
        isMSDSNecessary = MSDSNecessary;
    }

    public Boolean getAirMonitoringRequired() {
        return isAirMonitoringRequired;
    }

    public void setAirMonitoringRequired(Boolean airMonitoringRequired) {
        isAirMonitoringRequired = airMonitoringRequired;
    }

    public Boolean getWorkPermitRequired() {
        return isWorkPermitRequired;
    }

    public void setWorkPermitRequired(Boolean workPermitRequired) {
        isWorkPermitRequired = workPermitRequired;
    }

    public String getQrCode() {
        return qrCode;
    }

    public void setQrCode(String qrCode) {
        this.qrCode = qrCode;
    }

    public String getBarCode() {
        return barCode;
    }

    public void setBarCode(String barCode) {
        this.barCode = barCode;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public Integer getRaitingTrained() {
        return raitingTrained;
    }

    public void setRaitingTrained(Integer raitingTrained) {
        this.raitingTrained = raitingTrained;
    }

    public Integer getRaitingProfessional() {
        return raitingProfessional;
    }

    public void setRaitingProfessional(Integer raitingProfessional) {
        this.raitingProfessional = raitingProfessional;
    }

    public Integer getRaitingSupervised() {
        return raitingSupervised;
    }

    public void setRaitingSupervised(Integer raitingSupervised) {
        this.raitingSupervised = raitingSupervised;
    }

    public Integer getRaitingSatisfied() {
        return raitingSatisfied;
    }

    public void setRaitingSatisfied(Integer raitingSatisfied) {
        this.raitingSatisfied = raitingSatisfied;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getCustomerSignUrl() {
        return customerSignUrl;
    }

    public void setCustomerSignUrl(String customerSignUrl) {
        this.customerSignUrl = customerSignUrl;
    }

    public Job getJob() {
        return job;
    }

    public void setJob(Job job) {
        this.job = job;
    }
}
