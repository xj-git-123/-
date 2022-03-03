package hotel.servlet;

import com.alibaba.fastjson.JSON;
import hotel.entity.book;
import hotel.entity.bookReview;
import hotel.entity.userinfo;
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
public class userServlet extends HttpServlet {
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
            String nickname = request.getParameter("nickname");
            PrintWriter out = response.getWriter();
            HashMap<String, Object> map = new HashMap<>();
            String strWhere = "";
            if (nickname == null || nickname.equals("") ) {
                strWhere = "";
            } else {
                strWhere = "where bookno like '%" + nickname + "%'";
            }
            String sqlcount;

            sqlcount = "select count(*) from userinfo " + strWhere;

            int total = Integer.parseInt(DBUtils.QueryScalar(sqlcount).toString());
            map.put("total", total);

            String sqllist = "select top " + pageSize + "* from (select row_number() over (order by nickname) as rownumber," +
                    "* from userinfo " + strWhere + " ) tmp_table where rownumber > " + (pageIndex - 1) * pageSize;
            List<userinfo> list = DBUtils.QueryBeanList(sqllist, userinfo.class);
            map.put("rows", list);
            out.write(JSON.toJSONString(map));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    private void Add(HttpServletRequest request, HttpServletResponse response) throws IOException{
        try {
            PrintWriter out = response.getWriter();
            String nickname = request.getParameter("nickname");
            String tou = request.getParameter("tou");
            String gender = request.getParameter("gender");
            String city = request.getParameter("city");
            String country = request.getParameter("country");


            String sql = "insert into book(nickname,tou,gender,city,country) values(?,?,?,?,?)";
            if (DBUtils.Update(sql, nickname,tou,gender,city,country) > 0) {
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
            String nickname=request.getParameter("nickname");
            String sql="select count(*) from userinfo where nickname= ?";
            int count=Integer.parseInt(DBUtils.QueryScalar(sql,nickname).toString());
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
            String nickname=request.getParameter("nickname");
            String sql="select * from userinfo where nickname = ?";
            userinfo userinfo=DBUtils.QueryBean(sql,userinfo.class,nickname);
            ResultData rd=new ResultData();
            if (userinfo !=null){
                rd.setCode(String.valueOf(response.getStatus()));
                rd.setMsg("获取成功");
                rd.setData(userinfo);
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
            String nickname = request.getParameter("nickname");
            String tou = request.getParameter("tou");
            String gender = request.getParameter("gender");
            String city = request.getParameter("city");
            String country = request.getParameter("country");

            String sql = "update userinfo set tou= ? ,gender= ?,gender= ?,city= ?,country= ? where nickname= ? ";
            if (DBUtils.Update(sql,tou,gender,city,country,nickname) > 0) {
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
            String nickname=request.getParameter("nickname");
            String sql="delete from userinfo where nickname = ?";
            if(DBUtils.Update(sql,nickname)>0){
                out.write(JSON.toJSONString(new ResultData("200","删除成功")));
            }
            else   out.write(JSON.toJSONString(new ResultData("500","删除失败")));
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
}
