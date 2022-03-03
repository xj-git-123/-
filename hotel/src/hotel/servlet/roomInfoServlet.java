package hotel.servlet;

import com.alibaba.fastjson.JSON;
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
public class roomInfoServlet extends HttpServlet {
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
            String reviewno = request.getParameter("reviewno");
            PrintWriter out = response.getWriter();
            HashMap<String, Object> map = new HashMap<>();
            String strWhere = "";
//            if (reviewno == null || reviewno.equals("") ) {
//                strWhere = "";
//            } else {
//                strWhere = "where reviewno like '%" + reviewno + "%'";
//            }
            String sqlcount;

            sqlcount = "select count(*) from bookReview " + strWhere;

            int total = Integer.parseInt(DBUtils.QueryScalar(sqlcount).toString());
            map.put("total", total);

            String sqllist = "select top " + pageSize + "* from (select row_number() over (order by reviewno) as rownumber," +
                    "* from bookReview " + strWhere + " ) tmp_table where rownumber > " + (pageIndex - 1) * pageSize;
            List<bookReview> list = DBUtils.QueryBeanList(sqllist, bookReview.class);
            map.put("rows", list);
            out.write(JSON.toJSONString(map));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    private void Add(HttpServletRequest request, HttpServletResponse response) throws IOException{
        try {
            PrintWriter out = response.getWriter();
            String reviewno = request.getParameter("reviewno");
            String title = request.getParameter("title");
            String reviewdate = request.getParameter("reviewdate");
            String reviewcontent = request.getParameter("reviewcontent");
            String reviewimage = request.getParameter("reviewimage");
            String nickname = request.getParameter("nickname");


            String sql = "insert into room(reviewno,title,reviewdate,reviewcontent,reviewimage,nickname) values(?,?,?,?,?,?)";
            if (DBUtils.Update(sql, reviewno,title,reviewdate,reviewcontent,reviewimage,nickname) > 0) {
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
            String reviewno=request.getParameter("reviewno");
            String sql="select count(*) from bookReview where reviewno= ?";
            int count=Integer.parseInt(DBUtils.QueryScalar(sql,reviewno).toString());
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
            String reviewno=request.getParameter("reviewno");
            String sql="select * from bookReview where reviewno = ?";
            bookReview bookreview=DBUtils.QueryBean(sql,bookReview.class,reviewno);
            ResultData rd=new ResultData();
            if (bookreview !=null){
                rd.setCode(String.valueOf(response.getStatus()));
                rd.setMsg("获取成功");
                rd.setData(bookreview);
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
            String reviewno = request.getParameter("reviewno");
            String title = request.getParameter("title");
            String reviewdate = request.getParameter("reviewdate");
            String reviewcontent = request.getParameter("reviewcontent");
            String reviewimage = request.getParameter("reviewimage");
            String nickname = request.getParameter("nickname");

            String sql = "update bookReview set title= ? ,reviewdate= ?,reviewcontent= ?,reviewimage= ?,nickname= ? where reviewno= ? ";
            if (DBUtils.Update(sql,title,reviewdate,reviewcontent,reviewimage,nickname, reviewno) > 0) {
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
            String reviewno=request.getParameter("reviewno");
            String sql="delete from bookReview where reviewno = ?";
            if(DBUtils.Update(sql,reviewno)>0){
                out.write(JSON.toJSONString(new ResultData("200","删除成功")));
            }
            else   out.write(JSON.toJSONString(new ResultData("500","删除失败")));
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
}
