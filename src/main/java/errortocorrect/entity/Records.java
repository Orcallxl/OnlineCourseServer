package errortocorrect.entity;

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
    private Integer result;


    @Column(name = "time_consume")
    private Double timeConsume;

    @Column(name = "mem_consume")
    private Double memConsume;

    @Column(name = "lang")
    private String lang;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "puzzle_id")
    private Long puzzleId;

    @Column(name = "user_name")
    private String userName;

    @Column(name = "puzzle_name")
    private String puzzleName;

    @Column(name = "code")
    private String code;

    @Column(name = "info")
    private String info;

    @Column(name = "score")
    private String score;

    @Column(name = "compiler")
    private String compiler;

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

    public Integer getResult() {
        return result;
    }

    public void setResult(Integer result) {
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

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPuzzleName() {
        return puzzleName;
    }

    public void setPuzzleName(String puzzleName) {
        this.puzzleName = puzzleName;
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

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }
}
