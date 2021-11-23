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
@Table(name = "course")
@EntityListeners(AuditingEntityListener.class)
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "course_id", nullable = false)
    private Long courseId;


    @CreatedDate
    @Column(name = "create_time")
    private Timestamp createTime;

    @LastModifiedDate
    @Column(name = "modify_time")
    private Timestamp modifyTime;

    @Column(name = "course_name")
    private String courseName ;

    @Column(name = "pass_code")
    private String passCode ;

    @Column(name = "status")
    private Integer status;

    @Column(name = "teacher_id")
    private Long teacherId ;

    @Column(name = "teacher_name")
    private String teacherName ;

    @Column(name = "description")
    private String description ;

    @Column(name = "grade_spe")
    private String gradeSpe ;

    @Column(name = "expire_date")
    private Timestamp expire_date ;

    @ManyToMany(cascade={CascadeType.MERGE,CascadeType.PERSIST}, mappedBy = "course")

    @JsonIgnoreProperties({"course","password","subjects"})
    private Set<User> users = new HashSet<>();

    @OneToMany(mappedBy = "course",cascade = {CascadeType.REMOVE,CascadeType.MERGE,CascadeType.PERSIST})
    @JsonIgnoreProperties({"course"})
    private Set<Exp> exps = new HashSet<>();

    public Set<Exp> getExps() {
        return exps;
    }

    public void setExps(Set<Exp> exps) {
        this.exps = exps;
    }

    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }

    public Long getCourseId() {
        return courseId;
    }

    public void setCourseId(Long courseId) {
        this.courseId = courseId;
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

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getPassCode() {
        return passCode;
    }

    public void setPassCode(String passCode) {
        this.passCode = passCode;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Long getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(Long teacherId) {
        this.teacherId = teacherId;
    }

    public String getTeacherName() {
        return teacherName;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getGradeSpe() {
        return gradeSpe;
    }

    public void setGradeSpe(String gradeSpe) {
        this.gradeSpe = gradeSpe;
    }

    public Timestamp getExpire_date() {
        return expire_date;
    }

    public void setExpire_date(Timestamp expire_date) {
        this.expire_date = expire_date;
    }

    public void addExp(Exp exp) {
        if(!getExps().contains(exp))
        {
            getExps().add(exp);
        }
    }

    public void addUser(User user){
        if(!getUsers().contains(user)){
            getUsers().add(user);
        }
    }
}
