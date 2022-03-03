package hotel.entity;

public class shareDynamic {
    private String shareno;
    private String nickname;
    private String sharedate;
    private String sharecontent;
    private String shareimage;

    @Override
    public String toString() {
        return "shareDynamic{" +
                "shareno='" + shareno + '\'' +
                ", nickname='" + nickname + '\'' +
                ", sharedate='" + sharedate + '\'' +
                ", sharecontent='" + sharecontent + '\'' +
                ", shareimage='" + shareimage + '\'' +
                '}';
    }

    public String getShareno() {
        return shareno;
    }

    public void setShareno(String shareno) {
        this.shareno = shareno;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getSharedate() {
        return sharedate;
    }

    public void setSharedate(String sharedate) {
        this.sharedate = sharedate;
    }

    public String getSharecontent() {
        return sharecontent;
    }

    public void setSharecontent(String sharecontent) {
        this.sharecontent = sharecontent;
    }

    public String getShareimage() {
        return shareimage;
    }

    public void setShareimage(String shareimage) {
        this.shareimage = shareimage;
    }
}
