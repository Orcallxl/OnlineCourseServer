package errortocorrect.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.sql.Timestamp;


@Entity
@Table(name = "records")
@EntityListeners(AuditingEntityListener.class)
public class Records {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "record_id", nullable = false)
    private Integer id;


    @CreatedDate
    @Column(name = "create_time")
    private Timestamp createTime;

    @LastModifiedDate
    @Column(name = "modify_time")
    private Timestamp modifyTime;

    @Column(name = "commit_date")
    private Timestamp commitDate;

    @Column(name = "result")
    private String result;


    @Column(name = "time_consume")
    private Double timeConsume;

    @Column(name = "mem_consume")
    private Double memConsume;

    @Column(name = "max_time_consume")
    private Double maxTimeConsume;

    @Column(name = "max_mem_consume")
    private Double maxMemConsume;

    @Column(name = "lang")
    private String lang;

    @ManyToOne(fetch = FetchType.EAGER,cascade = {CascadeType.MERGE,CascadeType.PERSIST})
    @JoinColumn(name = "user_id")
    @JsonIgnoreProperties({"subjects","course","password"})
    private User user;

    @ManyToOne(fetch = FetchType.EAGER,cascade = {CascadeType.MERGE,CascadeType.PERSIST})
    @JoinColumn(name = "puzzle_id")
    @JsonIgnoreProperties({"user","records","testCases","exp","course"})

    private Puzzle puzzle;


    @Column(name = "puzzle_id",insertable=false,updatable=false)
    private Long puzzleId;

    @Column(name = "user_id",insertable=false,updatable=false)
    private Long userId;


    @Column(name = "code")
    private String code;

    @Column(name = "info")
    private String info;

    @Column(name = "score")
    private Integer score;

    @Column(name = "compiler")
    private String compiler;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getPuzzleId() {
        return puzzleId;
    }

    public void setPuzzleId(Long puzzleId) {
        this.puzzleId = puzzleId;
    }

    public Puzzle getPuzzle() {
        return puzzle;
    }

    public void setPuzzle(Puzzle puzzle) {
        this.puzzle = puzzle;
    }

    public Double getMaxTimeConsume() {
        return maxTimeConsume;
    }

    public void setMaxTimeConsume(Double maxTimeConsume) {
        this.maxTimeConsume = maxTimeConsume;
    }

    public Double getMaxMemConsume() {
        return maxMemConsume;
    }

    public void setMaxMemConsume(Double maxMemConsume) {
        this.maxMemConsume = maxMemConsume;
    }

    public String getCompiler() {
        return compiler;
    }

    public void setCompiler(String compiler) {
        this.compiler = compiler;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
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

    public Timestamp getCommitDate() {
        return commitDate;
    }

    public void setCommitDate(Timestamp commitDate) {
        this.commitDate = commitDate;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public Double getTimeConsume() {
        return timeConsume;
    }

    public void setTimeConsume(Double timeConsume) {
        this.timeConsume = timeConsume;
    }

    public Double getMemConsume() {
        return memConsume;
    }

    public void setMemConsume(Double memConsume) {
        this.memConsume = memConsume;
    }

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }



    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }



    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }
}
