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
@Table(name = "exp")
@EntityListeners(AuditingEntityListener.class)
public class Exp {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "exp_id", nullable = false)
    private Long expId;


    @CreatedDate
    @Column(name = "create_time")
    private Timestamp createTime;

    @LastModifiedDate
    @Column(name = "modify_time")
    private Timestamp modifyTime;

    @Column(name = "exp_name")
    private String expName;

    @Column(name = "description")
    private String description;

    @Column(name = "course_name")
    private String courseName;

    @Column(name = "course_id",insertable = false, updatable = false)
    private Long courseId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "course_id")
    private Course course;

    @OneToMany(mappedBy = "exp",cascade = {CascadeType.REMOVE,CascadeType.MERGE,CascadeType.PERSIST})
    @JsonIgnoreProperties({"course","exp"})
    private Set<Puzzle> puzzles = new HashSet<>();

    public Set<Puzzle> getPuzzles() {
        return puzzles;
    }

    public void setPuzzles(Set<Puzzle> puzzles) {
        this.puzzles = puzzles;
    }

        public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public Long getCourseId() {
        return courseId;
    }

    public void setCourseId(Long courseId) {
        this.courseId = courseId;
    }

    public Long getExpId() {
        return expId;
    }

    public void setExpId(Long expId) {
        this.expId = expId;
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

    public String getExpName() {
        return expName;
    }

    public void setExpName(String expName) {
        this.expName = expName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public void addPuzzle(Puzzle puzzle) {
        if(!getPuzzles().contains(puzzle))
        {
            getPuzzles().add(puzzle);
        }
    }

//    public Long getCourseId() {
//        return courseId;
//    }
//
//    public void setCourseId(Long courseId) {
//        this.courseId = courseId;
//    }
}
