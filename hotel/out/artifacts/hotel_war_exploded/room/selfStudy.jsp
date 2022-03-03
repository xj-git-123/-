<%--
  Created by IntelliJ IDEA.
  User: xuxu3
  Date: 2021/6/17
  Time: 16:33
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>自习时间管理</title>
    <link rel="stylesheet" type="text/css" href="../easyUi/jquery-easyui/themes/default/easyui.css">
    <link rel="stylesheet" type="text/css" href="../easyUi/jquery-easyui/themes/icon.css">
    <link rel="stylesheet" type="text/css" href="../easyUi/jquery-easyui/demo/demo.css">
    <script type="text/javascript" src="../easyUi/jquery-easyui/jquery.min.js"></script>
    <script type="text/javascript" src="../easyUi/jquery-easyui/jquery.easyui.min.js"></script>
    <script type="text/javascript" src="../easyUi/jquery-easyui/locale/easyui-lang-zh_CN.js"></script>
</head>
<style>
    .grid-panel .datagrid-btable tr {
        height: 64.7px;
    }
</style>
<script>
    $(function () {
        loadData('');

        $('#btnQuery').textbox({
            onClickButton: function () {
                loadData($(this).val())
            }
        })

        function loadData(val) {
            $('#dg').datagrid({
                fitColumns:true,
                url: '/recodeServlet?action=getslist',
                toolbar: '#tb',
                height:'100%',
                pagination: true,
                fit: true,
                singleSelect: true,
                selectOnCheck:true,
                queryParams: {
                    studyno: val
                },
                columns: [[
                    // {field: 'roomNum', title: '房间编号', sortable:true, checkbox: true, width: 100},
                    {field: 'studyno', title: '学习时长排名', width: 100},
                    {field: 'studytime', title: '学习时长', width: 100},


                ]]
            });
        }


    })
</script>
<body data-options="region:'center',border:false" style="padding: 0">
<div style="height: 100% " data-options="fit:true" class="grid-panel">
    <table id="dg"></table>
</div>
<div id="dd"></div>
<div id="tb" style="height:auto">
    <table>
        <tr>
            <td>
                <input class="easyui-textbox" id="btnQuery"
                       data-options="buttonText:'搜索',buttonIcon:'icon-search',prompt:'根据排名搜索'"
                       style="width:180px;height:24px;">
            </td>
        </tr>
    </table>
</div>
</body>
</html>
