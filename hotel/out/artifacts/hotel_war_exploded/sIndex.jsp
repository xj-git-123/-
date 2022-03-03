<%@ page import="java.util.ArrayList" %><%--
  Created by IntelliJ IDEA.
  User: xuxu3
  Date: 2021/6/17
  Time: 10:51
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>小程序管理后台</title>
    <script src="https://cdn.staticfile.org/jquery/1.10.2/jquery.min.js"></script>
    <link rel="stylesheet" type="text/css" href="easyUi/jquery-easyui/themes/default/easyui.css">
    <link rel="stylesheet" type="text/css" href="easyUi/jquery-easyui/themes/icon.css">
    <link rel="stylesheet" type="text/css" href="easyUi/jquery-easyui/demo/demo.css">
    <script type="text/javascript" src="easyUi/jquery-easyui/jquery.min.js"></script>
    <script type="text/javascript" src="easyUi/jquery-easyui/jquery.easyui.min.js"></script>
    <script type="text/javascript" src="easyUi/jquery-easyui/locale/easyui-lang-zh_CN.js"></script>
</head>
<script>
    $(document).ready(function () {
        $(".easyui-linkbutton").click(function () {
            if ($('#tt').tabs('exists', this.innerText)) {
                $('#tt').tabs('select', this.innerText)
            } else {
                //获取URL
                let url = $(this).data('url');
                $('#tt').tabs('add', {
                    title: this.innerText,
                    content: '<iframe src="' + url + '" frameborder="0" scrolling="true" style="width: 100%;height: 100%"><iframe/>',
                    closable: true,
                });
            }
        });

        checkStaff=function () {
             utilsNum ="<%=session.getAttribute("utilsNum")%>"

             console.log(utilsNum)

           if(utilsNum=="U001") {
                $('#tt').tabs('add',{
                    title: "员工信息管理",
                    content: '<iframe src="staff/staffinfo.jsp" frameborder="0" scrolling="true" style="width: 100%;height: 100%"><iframe/>',
                    closable: true,

                });
            }
            else {
                alert("没有操作权限")
                 window.open("sIndex.jsp")
                console.log("afdasdfsf")

            }
        }

    });
</script>
<body style="padding: 0">
<div style="font-size: 35px;margin-left: 41px;float: left;margin-top: 15px">
 后台管理系统
</div>
<div style="float: right;margin: 20px ;font-size: 22px">
<%--    欢迎你,<%=session.getAttribute("Name")%>--%>
    欢迎您，管理员
    <a href="loginServlet?action=logout" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-back'"
       style="font-size: 22px">注销</a>
</div>

<div class="easyui-layout" style="width:100%;height:90%;">
    <%--    <div data-options="region:'north'" style="height:50px"></div>--%>
    <div data-options="region:'south',split:true" style="height:50px; text-align: center">
        地址：重庆市巴南区红光大道89号
        邮编：400054
        联系电话：023-62563072

    </div>
    <div data-options="region:'west',split:true" title="操作菜单" style="width:100px;min-width: 255px">
        <div class="easyui-accordion" data-options="fit:true,border:false">
            <div title="评论管理" style="padding:10px;" data-options="selected:false,iconCls:'icon-ok'">
                <div style="padding:5px 0;">
                    <a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-remove'"
                       style="width: 200px;margin-top: 20px;margin-left: 20px" data-url="comment/bookReview.jsp">书评管理</a>
                    <a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-remove'"
                       style="width: 200px;margin-top: 20px;margin-left: 20px" data-url="comment/searchDynamic.jsp">求书帖管理</a>
                    <a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-remove'"
                       style="width: 200px;margin-top: 20px;margin-left: 20px" data-url="comment/shareDynamic.jsp">共享贴管理</a>

                </div>
            </div>
            <div title="图书管理" data-options="iconCls:'icon-ok'" style="padding:10px;">
                <div style="padding:5px 0;">
                    <a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-remove'"
                       style="width: 200px;margin-top: 20px;margin-left: 20px" data-url="book/bookinfo.jsp">图书信息管理</a>
                    <a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-remove'"
                       style="width: 200px;margin-top: 20px;margin-left: 20px" data-url="book/borrowinfo.jsp">用户借阅管理</a>
                </div>
            </div>
            <div title="自习室管理" data-options="iconCls:'icon-ok'" style="padding:10px;">
                <div style="padding:5px 0;">
                    <a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-remove'"
                       style="width: 200px;margin-top: 20px;margin-left: 20px" data-url="room/selfStudy.jsp">自习时间管理</a>
                    <a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-remove'"
                       style="width: 200px;margin-top: 20px;margin-left: 20px" data-url="room/studyRoom.jsp">自习排行榜管理</a>
                </div>
            </div>
            <div title="用户信息管理" style="padding:10px" data-options="iconCls:'icon-ok'">
                <div style="padding:5px 0;">
                    <a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-remove'"
                       style="width: 200px;margin-top: 20px;margin-left: 20px"
                       data-url="personal/personalInfo.jsp">用户信息</a>
                </div>
            </div>
        </div>
    </div>
    <div data-options="region:'center'">
        <div id="tt" class="easyui-tabs" data-options="fit:true" style="padding: 0">
            <div title="首页" style="padding:0px;display:none;" >
              <img src="images/index4.jpg" style="height: 100%;width: 100%">
            </div>
        </div>

    </div>

</div>
</div>

</body>
</html>
