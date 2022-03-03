<%--
  Created by IntelliJ IDEA.
  User: xuxu3
  Date: 2021/5/30
  Time: 11:29
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<script>
    if ('edit'=='<%=request.getParameter("action")%>'){
        let row=$('#dg').datagrid('getSelected');
        $.ajax(
            {
                type:"get",
                url:'/staffServlet?action=getone',
                data:{sCode:row.sCode},
                success:function(ret){
                    let result=eval("("+ret+")");

                    if(result.code=='200'){
                        $('#sCode').textbox({
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
    <table cellpadding="9">
        <tr>
            <td>身份证号码:</td>
            <td><input class="easyui-textbox" id="sCode" type="text" name="sCode" data-options="required:true ,
	      validType:{
	      remote:['/staffServlet?action=exists','sCode']},invalidMessage:'已经存在该员工'"></input></td>
        </tr>
        <tr>
            <td>姓名:</td>
            <td><input class="easyui-textbox" type="text" name="sname" data-options="required:true"></input></td>
        </tr>
        <tr>
            <td>性别:</td>
            <td><input class="easyui-textbox" type="text" name="sGender" ></input></td>
        </tr>
        <tr>
            <td>联系电话:</td>
            <td><input class="easyui-textbox" type="text" name="sPhonenum" ></input></td>
        </tr>
        <tr>
            <td>密码:</td>
            <td><input class="easyui-textbox" type="text" name="sPassword" ></input></td>
        </tr>
        <tr>
            <td>生日:</td>
            <td><input class="easyui-textbox" type="text" name="birthday" ></input></td>
        </tr>
        <tr>
            <td>地址:</td>
            <td><input class="easyui-textbox" type="text" name="address" ></input></td>
        </tr>
        <tr>
            <td>权限编号:</td>
            <td><input class="easyui-textbox" type="text" name="utilsNum" ></input></td>
        </tr>
        <tr>
            <td>部门号:</td>
            <td><input class="easyui-textbox" type="text" name="dnum" ></input></td>
        </tr>
    </table>
</form>

