package hotel.servlet;

import com.alibaba.fastjson.JSON;
import hotel.entity.study;
import hotel.utils.DBUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;

@WebServlet(name = "recodeServlet")
public class recodeServlet extends HttpServlet {
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
            default:
                break;
        }
    }
    private void getSlist(HttpServletRequest request, HttpServletResponse response) {
        try {
            int pageIndex = request.getParameter("page") == null ? 1 : Integer.parseInt(request.getParameter("page"));
            int pageSize = request.getParameter("rows") == null ? 10 : Integer.parseInt(request.getParameter("rows"));
            String studyno = request.getParameter("studyno");
            PrintWriter out = response.getWriter();
            HashMap<String, Object> map = new HashMap<>();
            String strWhere = "";
            if (studyno == null || studyno.equals("") ) {
                 strWhere = "";

            } else {
                strWhere = "where studyno like '%" + studyno + "%'";
            }
            String sqlcount;

            sqlcount = "select count(*) from selfStudy " + strWhere;

            int total = Integer.parseInt(DBUtils.QueryScalar(sqlcount).toString());
            map.put("total", total);

            String sqllist = "select top " + pageSize + "* from (select row_number() over (order by studyno) as rownumber," +
                    "* from selfStudy " + strWhere + ") tmp_table where rownumber > " + (pageIndex - 1) * pageSize;

            List<study> list = DBUtils.QueryBeanList(sqllist, study.class);
            map.put("rows", list);
            out.write(JSON.toJSONString(map));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
