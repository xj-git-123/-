package hotel.entity;

public class study {
    private String studyno;
    private String studytime;
    private String nickname;

    public String getStudyno() {
        return studyno;
    }

    public void setStudyno(String studyno) {
        this.studyno = studyno;
    }

    public String getStudytime() {
        return studytime;
    }

    public void setStudytime(String studytime) {
        this.studytime = studytime;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    @Override
    public String toString() {
        return "study{" +
                "studyno='" + studyno + '\'' +
                ", studytime='" + studytime + '\'' +
                ", nickname='" + nickname + '\'' +
                '}';
    }
}
