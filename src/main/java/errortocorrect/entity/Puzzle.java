package errortocorrect.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "puzzle")
@EntityListeners(AuditingEntityListener.class)
public class Puzzle {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "puzzle_id", nullable = false)
    private Long id;


    @CreatedDate
    @Column(name = "create_time")
    private Timestamp createTime;

    @LastModifiedDate
    @Column(name = "modify_time")
    private Timestamp modifyTime;

    @Column(name = "puzzle_name")
    private String puzzleName;

    @Column(name = "description")
    private String description;

    @Column(name = "point")
    private Integer point;

    @Column(name = "time_limit")
    private Double timeLimit;

    @Column(name = "mem_limit")
    private Double memLimit;

    @Column(name = "exp_name")
    private String expName;

    @Column(name = "exp_id",insertable = false, updatable = false)
    private Long expId;

    @Column(name = "course_name")
    private String courseName;

    @Column(name = "course_id",insertable = false, updatable = false)
    private Long courseId;

    @Column(name = "pass_rate")
    private Double passRate;

    @Column(name = "tags")
    private String tags;

    @Column(name = "solution_link")
    private String solutionLink;

    @Column(name = "input_descrip")
    private String inputDescrip;

    @Column(name = "output_descrip")
    private String outputDescrip;

    @Column(name = "teacher_name")
    private String teacherName;

    @Column(name = "code_template")
    private String codeTemplate;

    @Column(name = "difficulty")
    private String difficulty;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "exp_id")
    @JsonIgnoreProperties({"puzzles"})
    private Exp exp;
    public Exp getExp() {
        return exp;
    }

    public void setExp(Exp exp) {
        this.exp = exp;
    }

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "course_id")
    private Course course;

    @OneToMany(mappedBy = "puzzle",cascade = {CascadeType.REMOVE,CascadeType.MERGE,CascadeType.PERSIST})
    @JsonIgnoreProperties({"puzzle"})
    private Set<TestCase> testCases = new HashSet<>();

    @OneToMany(mappedBy = "puzzle",cascade = {CascadeType.REMOVE,CascadeType.MERGE,CascadeType.PERSIST})
    @JsonIgnoreProperties({"puzzle"})
    private Set<Records> records = new HashSet<>();


    public Set<TestCase> getTestCases() {
        return testCases;
    }

    public void setTestCases(Set<TestCase> testCases) {
        this.testCases = testCases;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public Long getId() {
        return id;
    }

    public String getCodeTemplate() {
        return codeTemplate;
    }

    public void setCodeTemplate(String codeTemplate) {
        this.codeTemplate = codeTemplate;
    }

    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    public Timestamp getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(Timestamp modifyTime) {
        this.modifyTime = modifyTime;
    }

    public String getPuzzleName() {
        return puzzleName;
    }

    public void setPuzzleName(String puzzleName) {
        this.puzzleName = puzzleName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getPoint() {
        return point;
    }

    public void setPoint(Integer point) {
        this.point = point;
    }

    public Double getTimeLimit() {
        return timeLimit;
    }

    public void setTimeLimit(Double timeLimit) {
        this.timeLimit = timeLimit;
    }

    public Double getMemLimit() {
        return memLimit;
    }

    public void setMemLimit(Double memLimit) {
        this.memLimit = memLimit;
    }

    public String getExpName() {
        return expName;
    }

    public void setExpName(String expName) {
        this.expName = expName;
    }

    public Long getExpId() {
        return expId;
    }

    public void setExpId(Long expId) {
        this.expId = expId;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public Long getCourseId() {
        return courseId;
    }

    public void setCourseId(Long courseId) {
        this.courseId = courseId;
    }

    public Double getPassRate() {
        return passRate;
    }

    public void setPassRate(Double passRate) {
        this.passRate = passRate;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public String getSolutionLink() {
        return solutionLink;
    }

    public void setSolutionLink(String solutionLink) {
        this.solutionLink = solutionLink;
    }

    public String getInputDescrip() {
        return inputDescrip;
    }

    public void setInputDescrip(String inputDescrip) {
        this.inputDescrip = inputDescrip;
    }

    public String getOutputDescrip() {
        return outputDescrip;
    }

    public void setOutputDescrip(String outputDescrip) {
        this.outputDescrip = outputDescrip;
    }

    public String getTeacherName() {
        return teacherName;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }


}
