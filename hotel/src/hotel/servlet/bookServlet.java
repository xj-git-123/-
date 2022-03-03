package hotel.servlet;

import com.alibaba.fastjson.JSON;
import hotel.entity.book;
import hotel.entity.bookReview;
import hotel.utils.DBUtils;
import hotel.utils.ResultData;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;

@WebServlet(name = "roomInfoServlet")
public class bookServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request,response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=utf-8");
        request.setCharacterEncoding("utf-8");
        String action = request.getParameter("action") == null ? "" : request.getParameter("action");
        switch (action) {
            case "getslist":
                getSlist(request, response);
                break;
            case "add":
                Add(request, response);
                break;
            case "exists":
                Exists(request, response);
                break;
            case "getone":
                Getone(request, response);
                break;
            case "update":
                Update(request, response);
                break;
            case "delete":
                Delete(request, response);
                break;
            default:
                break;
        }
    }
    private void getSlist(HttpServletRequest request, HttpServletResponse response) {
        try {
            int pageIndex = request.getParameter("page") == null ? 1 : Integer.parseInt(request.getParameter("page"));
            int pageSize = request.getParameter("rows") == null ? 10 : Integer.parseInt(request.getParameter("rows"));
            String bookno = request.getParameter("bookno");
            PrintWriter out = response.getWriter();
            HashMap<String, Object> map = new HashMap<>();
            String strWhere = "";
            if (bookno == null || bookno.equals("") ) {
                strWhere = "";
            } else {
                strWhere = "where bookno like '%" + bookno + "%'";
            }
            String sqlcount;

            sqlcount = "select count(*) from book " + strWhere;

            int total = Integer.parseInt(DBUtils.QueryScalar(sqlcount).toString());
            map.put("total", total);

            String sqllist = "select top " + pageSize + "* from (select row_number() over (order by bookno) as rownumber," +
                    "* from book " + strWhere + " ) tmp_table where rownumber > " + (pageIndex - 1) * pageSize;
            List<book> list = DBUtils.QueryBeanList(sqllist, book.class);
            map.put("rows", list);
            out.write(JSON.toJSONString(map));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    private void Add(HttpServletRequest request, HttpServletResponse response) throws IOException{
        try {
            PrintWriter out = response.getWriter();
            String bookno = request.getParameter("bookno");
            String bookname = request.getParameter("bookname");
            String writer = request.getParameter("writer");
            String location = request.getParameter("location");
            String remain = request.getParameter("remain");
            String borrownumber = request.getParameter("borrownumber");


            String sql = "insert into book(bookno,bookname,writer,location,remain,borrownumber) values(?,?,?,?,?,?)";
            if (DBUtils.Update(sql, bookno,bookname,writer,location,remain,borrownumber) > 0) {
                out.write(JSON.toJSONString(new ResultData(String.valueOf(response.getStatus()), "保存成功")));
            } else {
                out.write(JSON.toJSONString(new ResultData("501", "保存失败====")));
            }

        } catch (Exception e) {
            response.getWriter().write(JSON.toJSONString(new ResultData("501", "保存失败")));
        }
    }
    private void Exists(HttpServletRequest request, HttpServletResponse response) {
        try{
            PrintWriter out=response.getWriter();
            String bookno=request.getParameter("bookno");
            String sql="select count(*) from book where bookno= ?";
            int count=Integer.parseInt(DBUtils.QueryScalar(sql,bookno).toString());
            if (count==1){
                out.write("false");
            }else out.write("true");

        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
    private void Getone(HttpServletRequest request, HttpServletResponse response) {
        try{
            PrintWriter out=response.getWriter();
            String bookno=request.getParameter("bookno");
            String sql="select * from book where bookno = ?";
            book book=DBUtils.QueryBean(sql,book.class,bookno);
            ResultData rd=new ResultData();
            if (book !=null){
                rd.setCode(String.valueOf(response.getStatus()));
                rd.setMsg("获取成功");
                rd.setData(book);
                out.write(JSON.toJSONString(rd));
            }
            else{
                rd.setCode("500");
                rd.setMsg("获取失败");
                out.write(JSON.toJSONString(rd));
            }


        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
    private void Update(HttpServletRequest request, HttpServletResponse response) throws IOException{
        try {
            PrintWriter out = response.getWriter();
            String bookno = request.getParameter("bookno");
            String bookname = request.getParameter("bookname");
            String writer = request.getParameter("writer");
            String location = request.getParameter("location");
            String remain = request.getParameter("remain");
            String borrownumber = request.getParameter("borrownumber");

            String sql = "update book set bookname= ? ,borrownumber= ?,writer= ?,location= ?,remain= ? where bookno= ? ";
            if (DBUtils.Update(sql,bookname,writer,location,remain,borrownumber,bookno) > 0) {
                out.write(JSON.toJSONString(new ResultData(String.valueOf(response.getStatus()), "修改成功")));
            } else {
                out.write(JSON.toJSONString(new ResultData("501", "修改失败")));
            }

        } catch (Exception e) {
            response.getWriter().write(JSON.toJSONString(new ResultData("501", "保存失败")));
        }
    }
    private void Delete(HttpServletRequest request, HttpServletResponse response) {
        try{
            PrintWriter out=response.getWriter();
            String bookno=request.getParameter("bookno");
            String sql="delete from book where bookno = ?";
            if(DBUtils.Update(sql,bookno)>0){
                out.write(JSON.toJSONString(new ResultData("200","删除成功")));
            }
            else   out.write(JSON.toJSONString(new ResultData("500","删除失败")));
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
}
