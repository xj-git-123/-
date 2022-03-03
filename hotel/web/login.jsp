<%--
  Created by IntelliJ IDEA.
  User: xuxu3
  Date: 2021/6/17
  Time: 9:12
  To change this template use File | Settings | File Templates.
--%>
<%--<%@ page contentType="text/html;charset=UTF-8" language="java" %>--%>
<%@ page contentType="text/html; charset=utf-8" language="java" import="java.sql.*" errorPage="" %>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<html>
<head>
    <title>用户登录</title>
    <script src="https://cdn.staticfile.org/jquery/1.10.2/jquery.min.js"></script>
    <link href="css/style.css" rel="stylesheet" type="text/css"/>
    <script type="text/javascript" src="js/script.js"></script>
    <script type="text/javascript" src="easyUi/jquery-easyui/jquery.min.js"></script>
    <script type="text/javascript" src="easyUi/jquery-easyui/jquery.easyui.min.js"></script>
    <link rel="stylesheet" type="text/css" href="easyUi/jquery-easyui/themes/default/easyui.css">
    <link rel="stylesheet" type="text/css" href="easyUi/jquery-easyui/themes/icon.css">
    <link rel="stylesheet" type="text/css" href="easyUi/jquery-easyui/demo/demo.css">
    <script type="text/javascript" src="easyUi/jquery-easyui/locale/easyui-lang-zh_CN.js"></script>
</head>
<script>
    window.onload = function () {
        document.querySelector('.img__btn').addEventListener('click', function () {
            document.querySelector('.content').classList.toggle('s--signup')
        })
    }
    $(document).ready(function () {
        $('#btlogin').click(function () {
            let username = $('#username').val();
            let password = $('#password').val();
            let validcode = $('#validcode').val();

            $.ajax(
                {
                    type: "POST",
                    url: "loginServlet?action=login",
                    data: {username: username, password: password, validcode: validcode},
                    // dataType:"text",

                    success: function (ret) {
                        let result = eval("(" + ret + ")");

                        if (result.code == '201') {
                            location.href = "sIndex.jsp";
                        }
                        else {
                            console.log(result.msg)
                            alert(result.msg)
                        }
                    }
                }
            )
        })
        $('#comfirm').click(function () {
            $('#ff').form('submit', {
                url:"loginServlet?action=register",
                onSubmit: function () {
                    var isValid = $(this).form('validate');
                    if (!isValid) {
                        $.messager.progress('close');	// hide progress bar while the form is invalid
                    }
                    return isValid;	// return false will stop the form submission
                },
                success: function (data) {

                    let res = eval("(" + data + ")");
                    console.log(res);
                    if (res.code == '200') {
                        // $.messager.alert('提示', res.msg + '返回登录', 'info')
                        // location.href = 'studentLogin.jsp'
                        $.messager.confirm('提示',res.msg + '返回登录', function(r){
                            if (r){
                                location.href = 'login.jsp'
                            }
                        });
                    } else
                        $.messager.alert('提示', res.msg, '登录失败');

                    $.messager.progress('close');	// hide progress bar while submit successfully
                }

            })
        })
    })


</script>
<body>
<div class="content">
    <div class="form sign-in">
        <h2>欢迎回来</h2>
        <label>
            <input type="text" placeholder="请输入身份证号码" id="username" name="username"/>
        </label>
        <label class="txt">
            <input type="password" placeholder="请输入密码" id="password" name="password"/>
        </label>
        <label class="txt">
            <input name="validcode" id="validcode" type="text" placeholder="请输入页面验证码"/>
            <img src="validCodeServlet" style="margin-top: -42px;margin-left: 263px;"
                 onclick="this.src='validCodeServlet?r='+Math.random()"/>
        </label>
<%--        <p class="forgot-pass"><a href="javascript:">忘记密码？</a></p>--%>
        <input class="submit" value="登 录" id="btlogin" style=" width: 325px;
    height: 39px;
    margin-left: 24%;
    color: white;
    border: 0;border-radius: 5%"></input>
    </div>
    <div class="sub-cont">
        <div class="img">
            <div class="img__text m--up">
                <h2>还未注册？</h2>
                <p>立即注册，发现大量机会！</p>
            </div>
            <div class="img__text m--in">
                <h2>已有帐号？</h2>
                <p>有帐号就登录吧，好久不见了！</p>
            </div>
            <div class="img__btn">
                <span class="m--up">注 册</span>
                <span class="m--in">登 录</span>
            </div>
        </div>
        <div class="form sign-up">
            <h2>立即注册</h2>

<%--            注册--%>
            <form id="ff" method="post">

                <label>
                    <input type="text" placeholder="请输入身份证号码" id="usernamere" name="username"/>
                </label>
                <label>
                    <input type="text" placeholder="请输入姓名" id="name" name="name"/>
                </label>
                <label>
                    <span style="font-size: 16px">性别：</span>
                    <select name="select">
                        <option value="男">男</option>
                        <option value="女">女</option>
                    </select>
                </label>
                <label>
                    <input type="text" placeholder="请输入电话号码" id="phonenum" name="phonenum"/>
                </label>
                <label class="txt">
                    <input type="password" placeholder="请输入密码" id="passwordre" name="password"/>
                </label>
                <label>
                    <input type="password" placeholder="确认密码" id="compasswordre" name="compasswordre"/>
                </label>
<%--                <button type="button" class="submit">注 册</button>--%>
                <input class="submit" value="注册" id="comfirm" style=" width: 325px;
    height: 39px;
    margin-left: 24%;
    color: white;
    border: 0;border-radius: 5%"></input>

            </form>

        </div>
    </div>
</div>
</body>
</html>
