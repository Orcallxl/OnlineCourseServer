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
@Table(name = "user")
@EntityListeners(AuditingEntityListener.class)
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id", nullable = false)
    private Long id;

    @CreatedDate
    @Column(name = "create_time")
    private Timestamp createTime;

    @LastModifiedDate
    @Column(name = "modify_time")
    private Timestamp modifyTime;

    @Column(name = "sno")
    private Long sno;

    @Column(name = "type")
    private Integer  type;

    @Column(name = "user_name")
    private String userName;


    @Column(name = "password")
    private String password;

    @Column(name = "grade_spe")
    private String gradeSpe;

    @Column(name = "class_name")
    private String className;

    @Column(name = "last_login_date")
    private Timestamp lastLoginDate;

    @ManyToMany(fetch=FetchType.EAGER,cascade={CascadeType.PERSIST,CascadeType.MERGE})
    @JoinTable(name="user_course", joinColumns = @JoinColumn(name="user_id"), inverseJoinColumns = @JoinColumn(name="course_id"))
    @JsonIgnoreProperties({"users","passcode"})
    private Set<Course> course = new HashSet<>();

    @OneToMany(mappedBy = "user",cascade = {CascadeType.REMOVE,CascadeType.MERGE,CascadeType.PERSIST})
    @JsonIgnoreProperties({"user","parentSubject","subSubjects","content"})
    private Set<Subject> subjects = new HashSet<>();

    @OneToMany(mappedBy = "user",cascade = {CascadeType.REMOVE,CascadeType.MERGE,CascadeType.PERSIST})
    @JsonIgnoreProperties({"user","parentSubject","subSubjects","content"})
    private Set<Records> records = new HashSet<>();

    public Timestamp getCreateTime() {
        return createTime;
    }

    public Timestamp getModifyTime() {
        return modifyTime;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Set<Course> getCourse() {
        return course;
    }

    public void setCourse(Set<Course> course) {
        this.course = course;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getSno() {
        return sno;
    }

    public void setSno(Long sno) {
        this.sno = sno;
    }

    public Set<Subject> getSubjects() {
        return subjects;
    }

    public void setSubjects(Set<Subject> subjects) {
        this.subjects = subjects;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getGradeSpe() {
        return gradeSpe;
    }

    public void setGradeSpe(String gradeSpe) {
        this.gradeSpe = gradeSpe;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    public void setModifyTime(Timestamp modifyTime) {
        this.modifyTime = modifyTime;
    }

    public Timestamp getLastLoginDate() {
        return lastLoginDate;
    }

    public void setLastLoginDate(Timestamp lastLoginDate) {
        this.lastLoginDate = lastLoginDate;
    }

    public void addCourse(Course course) {
        if(!getCourse().contains(course))
        {
            getCourse().add(course);
        }
    }
}
