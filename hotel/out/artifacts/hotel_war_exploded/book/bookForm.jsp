<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<script>
    if ('edit'=='<%=request.getParameter("action")%>'){
        let row=$('#dg').datagrid('getSelected');
        $.ajax(
            {
                type:"get",
                url:'/bookServlet?action=getone',
                data:{bookno:row.bookno},
                success:function(ret){
                    let result=eval("("+ret+")");

                    if(result.code=='200'){
                        $('#bookno').textbox({
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
        console.log(row.bookno)
    }
    else console.log("add")
</script>
<form id="ff" method="post" >
    <table cellpadding="4">
        <tr>
            <td>图书编号:</td>
            <td><input class="easyui-textbox" id="bookno" type="text" name="bookno" data-options="required:true ,
	      validType:{
	      remote:['/bookServlet?action=exists','bookno']},invalidMessage:'已经存在该图书'"></input></td>
        </tr>
        <tr>
            <td>图书名称:</td>
            <td><input class="easyui-textbox" type="text" name="bookname" data-options="required:true"></input></td>
        </tr>
        <tr>
            <td>作者:</td>
            <td><input class="easyui-textbox" type="text" name="writer" ></input></td>
        </tr>
        <tr>
            <td>图书位置:</td>
            <td><input class="easyui-textbox" type="text" name="location" ></input></td>
        </tr>
        <tr>
            <td>剩余数量:</td>
            <td><input class="easyui-textbox" type="text" name="remain" ></input></td>
        </tr><tr>
        <td>借阅数量:</td>
        <td><input class="easyui-textbox" type="text" name="borrownumber" ></input></td>
    </tr>
    </table>
</form>
