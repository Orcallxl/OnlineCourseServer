package errortocorrect.dto;

public class LoginRetDto {
    private String uid;
    private String sno;
    private String type;
    private String userName;
    private String gradSpe;
    private String className;

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getSno() {
        return sno;
    }

    public void setSno(String sno) {
        this.sno = sno;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getGradSpe() {
        return gradSpe;
    }

    public void setGradSpe(String gradSpe) {
        this.gradSpe = gradSpe;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }
}
