package hotel.login;

import com.alibaba.fastjson.JSON;
import hotel.utils.ResultData;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

@WebServlet(name = "loginServlet")
public class loginServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=utf-8");
        String action=request.getParameter("action")==null?"":request.getParameter("action");
        switch (action)
        {
            case "login":
                login(request,response);
                break;
            case "logout":
                logout(request,response);
                break;
            case "register":
                register(request, response);
            default:
                break;
        }


    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
     doPost(request, response);
    }

    private void login(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        PrintWriter out = resp.getWriter();
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        String validcode = req.getParameter("validcode");
        HttpSession session = req.getSession();
        ResultData rd = new ResultData();
        if (!session.getAttribute("check_code").toString().equals(validcode)) {
            rd.setCode("404");
            rd.setMsg("验证码有误！");
            out.write(JSON.toJSONString(rd));
            return;
        }

        System.out.println("username:" + username + "---password:" + password);

        String driverName="com.microsoft.sqlserver.jdbc.SQLServerDriver";
        String dbURL="jdbc:sqlserver://localhost:1433;databaseName=library3;instanceName=MSSQLSERVER;";
        String userName="sa";
        String userPwd="123";

        try {
            Class.forName(driverName);
            Connection conn= DriverManager.getConnection(dbURL,userName,userPwd);

            String sql = "select * from account where username = ? and password = ?";

            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, username);
            pstmt.setString(2, password);
            ResultSet rs = pstmt.executeQuery();


            if (rs.next()) {
                session.setAttribute("username", username);
                session.setAttribute("password", password);

                rd.setCode("201");
                rd.setMsg("登录成功");
                //rd对象转化为json字符串
            }



            out.write(JSON.toJSONString(rd));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
    private void logout(HttpServletRequest request, HttpServletResponse response) throws IOException{
        HttpSession session=request.getSession(false);
        if(session==null){
            session.removeAttribute("username");
            session.invalidate();
        }
        response.sendRedirect("/login.jsp");
    }
    private void register(HttpServletRequest request, HttpServletResponse response) throws IOException
    {
        response.setContentType("text/html;charset=utf-8");
        request.setCharacterEncoding("utf-8");
        String username=request.getParameter("username");
        String name=request.getParameter("name");
        String phonenum=request.getParameter("phonenum");
        String password=request.getParameter("password");
        String gender=request.getParameter("select");
        gender = new String(gender.getBytes("iso-8859-1"),"utf-8");
        System.out.println("gender====================="+gender);


        String driverName="com.microsoft.sqlserver.jdbc.SQLServerDriver";
        String dbURL="jdbc:sqlserver://localhost:1433;databaseName=hotel;instanceName=MSSQLSERVER;";
        String userName="sa";
        String userPwd="123";

        try{
            PrintWriter out=response.getWriter();
            Class.forName(driverName);
            Connection dbConn= DriverManager.getConnection(dbURL,userName,userPwd);

            String  sql ="insert into client(cCode,cName,cGender,cPhonenum,cpassword) values (?,?,?, ?,? )";
            PreparedStatement pstmt=dbConn.prepareStatement(sql);
            pstmt.setString(1,username);
            pstmt.setString(2,name);
            pstmt.setString(3,gender);
            pstmt.setString(4,phonenum);
            pstmt.setString(5,password);


            ResultData rd=new ResultData();
            if(pstmt.executeUpdate() > 0){

                rd.setCode("200");
                rd.setMsg("注册成功");
                out.write(JSON.toJSONString(rd));
                System.out.println(rd);
            }
            else{
                rd.setCode("501");
                rd.setMsg("注册失败");
                out.write(JSON.toJSONString(rd));
            }

        }catch(Exception e)
        {
            e.printStackTrace();
            System.out.print("SQL Server连接失败！");
        }
    }
}
