package hotel.servlet;

import com.alibaba.fastjson.JSON;
import hotel.entity.book;
import hotel.entity.bookReview;
import hotel.entity.borrow;
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
public class borrowServlet extends HttpServlet {
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
//            if (bookno == null || bookno.equals("") ) {
//                strWhere = "";
//            } else {
//                strWhere = "where bookno like '%" + bookno + "%'";
//            }
            String sqlcount;

            sqlcount = "select count(*) from borrow " + strWhere;

            int total = Integer.parseInt(DBUtils.QueryScalar(sqlcount).toString());
            map.put("total", total);

            String sqllist = "select top " + pageSize + "* from (select row_number() over (order by bookno) as rownumber," +
                    "* from borrow " + strWhere + " ) tmp_table where rownumber > " + (pageIndex - 1) * pageSize;
            List<borrow> list = DBUtils.QueryBeanList(sqllist, borrow.class);
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
            String nickname = request.getParameter("nickname");
            String starttime = request.getParameter("starttime");
            String endtime = request.getParameter("endtime");



            String sql = "insert into borrow(bookno,nickname,starttime,endtime) values(?,?,?,?)";
            if (DBUtils.Update(sql, bookno,nickname,starttime,endtime) > 0) {
                out.write(JSON.toJSONString(new ResultData(String.valueOf(response.getStatus()), "保存成功")));
            } else {
                out.write(JSON.toJSONString(new ResultData("501", "保存失败")));
            }

        } catch (Exception e) {
            response.getWriter().write(JSON.toJSONString(new ResultData("501", "保存失败")));
        }
    }
    private void Exists(HttpServletRequest request, HttpServletResponse response) {
        try{
            PrintWriter out=response.getWriter();
            String bookno=request.getParameter("bookno");
            String sql="select count(*) from borrow where bookno= ?";
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
            String sql="select * from borrow where bookno = ?";
            borrow borrow=DBUtils.QueryBean(sql,borrow.class,bookno);
            ResultData rd=new ResultData();
            if (borrow !=null){
                rd.setCode(String.valueOf(response.getStatus()));
                rd.setMsg("获取成功");
                rd.setData(borrow);
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
            String nickname = request.getParameter("nickname");
            String starttime = request.getParameter("starttime");
            String endtime = request.getParameter("endtime");

            String sql = "update borrow set nickname= ? ,starttime= ?,endtime= ? where bookno= ? ";
            if (DBUtils.Update(sql,nickname,starttime,endtime,bookno) > 0) {
                out.write(JSON.toJSONString(new ResultData(String.valueOf(response.getStatus()), "修改成功")));
            } else {
                out.write(JSON.toJSONString(new ResultData("501", "保存失败")));
            }

        } catch (Exception e) {
            response.getWriter().write(JSON.toJSONString(new ResultData("501", "保存失败")));
        }
    }
    private void Delete(HttpServletRequest request, HttpServletResponse response) {
        try{
            PrintWriter out=response.getWriter();
            String bookno=request.getParameter("bookno");
            String sql="delete from borrow where bookno = ?";
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
