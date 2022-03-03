<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<script>
    if ('edit'=='<%=request.getParameter("action")%>'){
        let row=$('#dg').datagrid('getSelected');
        $.ajax(
            {
                type:"get",
                url:'/userInfoServlet?action=getone',
                data:{cCode:row.cCode},
                success:function(ret){
                    let result=eval("("+ret+")");

                    if(result.code=='200'){
                        $('#cCode').textbox({
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
        console.log(row.cCode)
    }
    else console.log("add")
</script>
<form id="ff" method="post" >
    <table cellpadding="4">
        <tr>
            <td>身份证号码:</td>
            <td><input class="easyui-textbox" id="cCode" type="text" name="cCode" data-options="required:true ,
	      validType:{
	      remote:['/userInfoServlet?action=exists','cCode']},invalidMessage:'已经存在该用户'"></input></td>
        </tr>
        <tr>
            <td>姓名:</td>
            <td><input class="easyui-textbox" type="text" name="cName" data-options="required:true"></input></td>
        </tr>
        <tr>
            <td>性别:</td>
            <td><input class="easyui-textbox" type="text" name="cGender" ></input></td>
        </tr>
        <tr>
            <td>联系电话:</td>
            <td><input class="easyui-textbox" type="text" name="cPhonenum" ></input></td>
        </tr>
    </table>
</form>
