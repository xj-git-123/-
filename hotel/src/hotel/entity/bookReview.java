package hotel.entity;

public class bookReview {
    private String reviewno;
    private String title;
    private String reviewdate;
    private String reviewcontent;
    private String reviewimage;
    private String nickname;

    public String getReviewno() {
        return reviewno;
    }

    public void setReviewno(String reviewno) {
        this.reviewno = reviewno;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getReviewdate() {
        return reviewdate;
    }

    public void setReviewdate(String reviewdate) {
        this.reviewdate = reviewdate;
    }

    public String getReviewcontent() {
        return reviewcontent;
    }

    public void setReviewcontent(String reviewcontent) {
        this.reviewcontent = reviewcontent;
    }

    public String getReviewimage() {
        return reviewimage;
    }

    public void setReviewimage(String reviewimage) {
        this.reviewimage = reviewimage;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    @Override
    public String toString() {
        return "bookReview{" +
                "reviewno='" + reviewno + '\'' +
                ", title='" + title + '\'' +
                ", reviewdata='" + reviewdate + '\'' +
                ", reviewcontent='" + reviewcontent + '\'' +
                ", reviewimage='" + reviewimage + '\'' +
                ", nickname='" + nickname + '\'' +
                '}';
    }
}
