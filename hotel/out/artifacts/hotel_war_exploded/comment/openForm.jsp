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
                url:'/openServlet?action=getone',
                data:{searchno:row.searchno},
                success:function(ret){
                    let result=eval("("+ret+")");

                    if(result.code=='200'){
                        $('#searchno').textbox({
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
    <table cellpadding="5">
        <tr>
            <td>求帖编码:</td>
            <td><input class="easyui-textbox" id="searchno" type="text" name="searchno" disabled="disabled"></input></td>
        </tr>
        <tr>
            <td>昵称:</td>
            <td><input class="easyui-textbox" type="text" name="nickname" disabled="disabled"></td>
        </tr>
        <tr>
            <td>求帖内容:</td>
            <td><input class="easyui-textbox" type="text" name="searchcontent" disabled="disabled"></input></td>
        </tr>
        <tr>
            <td>发布日期:</td>
            <td><input class="easyui-textbox" type="text" name="searchdate" disabled="disabled"></input></td>
        </tr>
        <tr>
            <td>图片:</td>
            <td><input class="easyui-textbox" type="text" name="searchimage" disabled="disabled"></input></td>
        </tr>
    </table>
</form>

