package hotel.servlet;

import com.alibaba.fastjson.JSON;
import hotel.entity.shareDynamic;
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

@WebServlet(name = "openServlet")
public class shareServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=utf-8");
        request.setCharacterEncoding("utf-8");
        String action = request.getParameter("action") == null ? "" : request.getParameter("action");
        switch (action) {
            case "getslist":
                getSlist(request, response);
                break;
            case "getone":
                Getone(request, response);
                break;
            case "update":
                Update(request, response);
                break;
            default:
                break;
        }
    }

    private void getSlist(HttpServletRequest request, HttpServletResponse response) {
        try {
            int pageIndex = request.getParameter("page") == null ? 1 : Integer.parseInt(request.getParameter("page"));
            int pageSize = request.getParameter("rows") == null ? 10 : Integer.parseInt(request.getParameter("rows"));
            String shareno = request.getParameter("shareno");
            PrintWriter out = response.getWriter();
            HashMap<String, Object> map = new HashMap<>();
            String strWhere = "";
//            if (reviewno == null || reviewno.equals("") ) {
//                strWhere = "";
//            } else {
//                strWhere = "where reviewno like '%" + reviewno + "%'";
//            }
            String sqlcount;

            sqlcount = "select count(*) from shareDynamic " + strWhere;

            int total = Integer.parseInt(DBUtils.QueryScalar(sqlcount).toString());
            map.put("total", total);

            String sqllist = "select top " + pageSize + "* from (select row_number() over (order by shareno) as rownumber," +
                    "* from shareDynamic " + strWhere + " ) tmp_table where rownumber > " + (pageIndex - 1) * pageSize;
            List<shareDynamic> list = DBUtils.QueryBeanList(sqllist, shareDynamic.class);
            map.put("rows", list);
            out.write(JSON.toJSONString(map));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    private void Getone(HttpServletRequest request, HttpServletResponse response) {
        try{
            PrintWriter out=response.getWriter();
            String shareno=request.getParameter("shareno");
            String sql="select shareno,nickname,sharecontent,sharedate,shareimage from shareDynamic where shareno = ?";
            shareDynamic sharedynamic=DBUtils.QueryBean(sql,shareDynamic.class,shareno);
            ResultData rd=new ResultData();
            if (sharedynamic !=null){
                rd.setCode(String.valueOf(response.getStatus()));
                rd.setMsg("获取成功");
                rd.setData(sharedynamic);
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
            String shareno = request.getParameter("shareno");
            String nickname = request.getParameter("nickname");
            String sharecontent = request.getParameter("sharecontent");
            String sharedate = request.getParameter("sharedate");
            String shareimage = request.getParameter("shareimage");

            String sql = "update shareDynamic set nickname= ? ,sharecontent= ? ,sharedate=? shareimage=?where shareno= ? ";
            if (DBUtils.Update(sql,nickname,sharecontent,sharedate,shareimage, shareno) > 0) {
                out.write(JSON.toJSONString(new ResultData(String.valueOf(response.getStatus()), "添加成功")));
            } else {
                out.write(JSON.toJSONString(new ResultData("501", "没有该评论")));
            }

        } catch (Exception e) {
            response.getWriter().write(JSON.toJSONString(new ResultData("501", "保存失败")));
        }
    }

}
