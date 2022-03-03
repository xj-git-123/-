package hotel.entity;

public class searchDynamic {
    private String searchno;
    private String nickname;
    private String searchdate;
    private String searchcontent;
    private String searchimage;

    public String getSearchno() {
        return searchno;
    }

    public void setSearchno(String searchno) {
        this.searchno = searchno;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getSearchdate() {
        return searchdate;
    }

    public void setSearchdate(String searchdate) {
        this.searchdate = searchdate;
    }

    public String getSearchcontent() {
        return searchcontent;
    }

    public void setSearchcontent(String searchcontent) {
        this.searchcontent = searchcontent;
    }

    public String getSearchimage() {
        return searchimage;
    }

    public void setSearchimage(String searchimage) {
        this.searchimage = searchimage;
    }

    @Override
    public String toString() {
        return "searchDynamic{" +
                "searchno='" + searchno + '\'' +
                ", nickname='" + nickname + '\'' +
                ", searchdate='" + searchdate + '\'' +
                ", searchcontent='" + searchcontent + '\'' +
                ", searchimage='" + searchimage + '\'' +
                '}';
    }
}
