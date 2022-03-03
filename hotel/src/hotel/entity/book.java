package hotel.entity;

public class book {
    private String bookno;
    private String bookname;
    private String writer;
    private String location;
    private String remain;
    private String borrownumber;

    @Override
    public String toString() {
        return "book{" +
                "bookno='" + bookno + '\'' +
                ", bookname='" + bookname + '\'' +
                ", writer='" + writer + '\'' +
                ", location='" + location + '\'' +
                ", remain='" + remain + '\'' +
                ", borrownumber='" + borrownumber + '\'' +
                '}';
    }

    public String getBookno() {
        return bookno;
    }

    public void setBookno(String bookno) {
        this.bookno = bookno;
    }

    public String getBookname() {
        return bookname;
    }

    public void setBookname(String bookname) {
        this.bookname = bookname;
    }

    public String getWriter() {
        return writer;
    }

    public void setWriter(String writer) {
        this.writer = writer;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getRemain() {
        return remain;
    }

    public void setRemain(String remain) {
        this.remain = remain;
    }

    public String getBorrownumber() {
        return borrownumber;
    }

    public void setBorrownumber(String borrownumber) {
        this.borrownumber = borrownumber;
    }
}
