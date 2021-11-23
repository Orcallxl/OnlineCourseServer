package errortocorrect.dto;

public class ExpDto {
    private Long expId;

    private String expName;

    private Long courseId;

    private String courseName;

    public String getExpName() {
        return expName;
    }

    public void setExpName(String expName) {
        this.expName = expName;
    }

    public Long getCourseId() {
        return courseId;
    }

    public void setCourseId(Long courseId) {
        this.courseId = courseId;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public Long getExpId() {
        return expId;
    }

    public void setExpId(Long expId) {
        this.expId = expId;
    }
}
