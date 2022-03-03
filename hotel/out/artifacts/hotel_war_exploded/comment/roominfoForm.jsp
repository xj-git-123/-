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
                data:{roomNum:row.roomNum},
                success:function(ret){
                    let result=eval("("+ret+")");

                    if(result.code=='200'){
                        $('#roomNum').textbox({
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
    <table cellpadding="4">
        <tr>
            <td>客房编码:</td>
            <td><input class="easyui-textbox" id="roomNum" type="text" name="roomNum" data-options="required:true ,
	      validType:{
	      remote:['/roomInfoServlet?action=exists','roomNum']},invalidMessage:'已经存在该房间'"></input></td>
        </tr>
        <tr>
            <td>类型:</td>
            <td><input class="easyui-textbox" type="text" name="type" data-options="required:true"></input></td>
        </tr>
        <tr>
        <td>价格:</td>
        <td><input class="easyui-textbox" type="text" name="price" ></input></td>
    </tr>
        <tr>
            <td>状态:</td>
            <td><input class="easyui-textbox" type="text" name="status" ></input></td>
        </tr>
    </table>
</form>

