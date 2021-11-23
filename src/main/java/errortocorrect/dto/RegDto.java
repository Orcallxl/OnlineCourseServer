package errortocorrect.dto;

public class RegDto {

    private String userName;
    private String sno;
    private String passWord;
    private String checkPass;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getSno() {
        return sno;
    }

    public void setSno(String sno) {
        this.sno = sno;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    public String getCheckPass() {
        return checkPass;
    }

    public void setCheckPass(String checkPass) {
        this.checkPass = checkPass;
    }
}
