<%--
  Created by IntelliJ IDEA.
  User: xuxu3
  Date: 2021/6/19
  Time: 10:15
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<script>
    if ('edit'=='<%=request.getParameter("action")%>'){
        let row=$('#dg').datagrid('getSelected');
        $.ajax(
            {
                type:"get",
                url:'/roomServlet?action=getone',
                data:{reviewno:row.reviewno},
                success:function(ret){
                    let result=eval("("+ret+")");

                    if(result.code=='200'){
                        $('#reviewno').textbox({
                            readonly:true,
                            required:true,
                            validType:''
                        })
                        $('#ff').form('load',result.data);
                    }
                    else{
                        $.messager.alert('提示',result.msg,'warning')
                    }
                }
            }
        )
    }
    else console.log("add")
</script>
<form id="ff" method="post" >
    <table cellpadding="6">
        <tr>
            <td>书评号:</td>
            <td><input class="easyui-textbox" id="reviewno" type="text" name="reviewno" data-options="required:true ,
	      validType:{
	      remote:['/roomInfoServlet?action=exists','reviewno']},invalidMessage:'已经存在该书评'"></input></td>
        </tr>
        <tr>
            <td>标题:</td>
            <td><input class="easyui-textbox" type="text" name="title" data-options="required:true"></input></td>
        </tr>
        <tr>
            <td>内容:</td>
            <td><input class="easyui-textbox" type="text" name="reviewcontent" ></input></td>
        </tr>
        <tr>
            <td>图片:</td>
            <td><input class="easyui-textbox" type="text" name="reviewimage" ></input></td>
        </tr>
        <tr>
            <td>昵称:</td>
            <td><input class="easyui-textbox" type="text" name="nickname" ></input></td>
        </tr>
        <tr>
            <td>发布日期:</td>
            <td><input class="easyui-textbox" type="text" name="reviewdate" ></input></td>
        </tr>
    </table>
</form>

