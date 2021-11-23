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
@Table(name = "subject")
@EntityListeners(AuditingEntityListener.class)
public class Subject {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @CreatedDate
    @Column(name = "create_time")
    private Timestamp createTime;

    @LastModifiedDate
    @Column(name = "modify_time")
    private Timestamp modifyTime;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    @JsonIgnoreProperties({"parentSubject","subSubjects","user"})
    private Subject parentSubject;

    @OneToMany(mappedBy = "parentSubject",cascade = {CascadeType.REMOVE,CascadeType.MERGE,CascadeType.PERSIST})
    @JsonIgnoreProperties({"parentSubject"})
    private Set<Subject> subSubjects = new HashSet<>();


    @ManyToOne(fetch = FetchType.EAGER,cascade = {CascadeType.MERGE,CascadeType.PERSIST})
    @JoinColumn(name = "author_id")
    @JsonIgnoreProperties({"subjects","course","password"})
    private User user;

    @Column(name = "title")
    private String  title;

    @Column(name = "content")
    private String content;

    @Column(name = "thumb_num")
    private Integer thumbNum;

    @Column(name = "view_num")
    private Integer viewNum;

    @Column(name = "reply_num")
    private Integer replyNum;

    @Column(name = "is_top")
    private Integer topOrElse;

    @Column(name = "is_recommend")
    private Integer recommendOrElse;

    @Column(name = "is_hot")
    private Integer hotOrElse;

    @Column(name = "is_good")
    private Integer goodOrElse;

    public Integer getTopOrElse() {
        return topOrElse;
    }

    public void setTopOrElse(Integer topOrElse) {
        this.topOrElse = topOrElse;
    }

    public Integer getRecommendOrElse() {
        return recommendOrElse;
    }

    public void setRecommendOrElse(Integer recommendOrElse) {
        this.recommendOrElse = recommendOrElse;
    }

    public Integer getHotOrElse() {
        return hotOrElse;
    }

    public void setHotOrElse(Integer hotOrElse) {
        this.hotOrElse = hotOrElse;
    }

    public Integer getGoodOrElse() {
        return goodOrElse;
    }

    public void setGoodOrElse(Integer goodOrElse) {
        this.goodOrElse = goodOrElse;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Subject getParentSubject() {
        return parentSubject;
    }

    public void setParentSubject(Subject parentSubject) {
        this.parentSubject = parentSubject;
    }

    public Set<Subject> getSubSubjects() {
        return subSubjects;
    }

    public void setSubSubjects(Set<Subject> subSubjects) {
        this.subSubjects = subSubjects;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getThumbNum() {
        return thumbNum;
    }

    public void setThumbNum(Integer thumbNum) {
        this.thumbNum = thumbNum;
    }

    public Integer getViewNum() {
        return viewNum;
    }

    public void setViewNum(Integer viewNum) {
        this.viewNum = viewNum;
    }

    public Integer getReplyNum() {
        return replyNum;
    }

    public void setReplyNum(Integer replyNum) {
        this.replyNum = replyNum;
    }

    public void addSubSubjects(Subject sub) {
        if(!getSubSubjects().contains(sub))
        {
            getSubSubjects().add(sub);
        }
    }
}
