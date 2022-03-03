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
    <title>员工信息管理</title>
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

        $('#btnAdd').click(function () {
            $('#dd').dialog({
                title: '新增数据',
                width: 450,
                height: 600,
                closed: false,
                cache: false,
                href: 'staffForm.jsp?action=add&r=' + Math.random(),
                modal: true,
                buttons: [{
                    text: '保存',
                    iconCls: 'icon-save',
                    handler: function () {
                        let isOk = $('#ff').form('validate')
                        if (isOk) {
                            $.messager.confirm('确认', '是否确认添加？', function (r) {
                                if (r) {
                                    saveData("add");
                                }
                            })
                        }

                    }
                }, {
                    text: '退出',
                    iconCls: 'icon-back',
                    handler: function () {
                        $('#dd').dialog('close')
                    }
                }
                ]

            })
            ;

        })
        $('#btnDelete').click(function () {
            $.messager.confirm('确认', '是否确认删除', function (r) {
                if (r) {
                    let row = $('#dg').datagrid('getSelected');
                    $.ajax(
                        {
                            type: "get",
                            url: '/staffServlet?action=delete',
                            data: {sCode: row.sCode},
                            success: function (ret) {
                                let result = eval("(" + ret + ")");

                                if (result.code == '200') {
                                    $.messager.show({
                                        title: '提示',
                                        msg: result.msg,
                                        timeout: 3000,
                                        showType: 'slide'
                                    });
                                    $('#dg').datagrid('reload')
                                } else {
                                    $.messager.alert('提示', result.msg, 'warning')
                                }
                            }
                        }
                    )
                }

            })
        })
        $('#btnEdit').click(function () {
            let row = $('#dg').datagrid('getSelected');
            if (row == null) {
                $.messager.alert('提示', '请选择要编辑的数据', 'warning');
                return;
            }
            $('#dd').dialog({
                title: '修改数据',
                width: 450,
                height: 600,
                closed: false,
                cache: false,
                href: 'staffForm.jsp?action=edit&r=' + Math.random(),
                modal: true,
                buttons: [{
                    text: '保存',
                    iconCls: 'icon-save',
                    handler: function () {
                        let isOk = $('#ff').form('validate')
                        if (isOk) {
                            $.messager.confirm('确认', '是否确认修改？', function (r) {
                                if (r) {
                                    saveData("update");
                                }
                            })
                        }

                    }
                }, {
                    text: '退出',
                    iconCls: 'icon-back',
                    handler: function () {
                        $('#dd').dialog('close')
                    }
                }
                ]

            })
            ;
        })

        function saveData(action) {
            $.messager.progress();
            $('#ff').form('submit', {
                url: '/staffServlet?action=' + action,
                onSubmit: function () {
                    var isValid = $(this).form('validate');
                    if (!isValid) {
                        $.messager.progress('close');

                    }
                    return isValid;

                },
                success: function (data) {
                    let res = eval("(" + data + ")");
                    if (res.code == '200') {
                        $.messager.alert('提示', res.msg, 'info');
                        $('#dd').dialog('close');
                        $('#dg').datagrid('reload');
                    } else
                        $.messager.alert('提示', res.msg, 'warming');

                    $.messager.progress('close');
                }
            });
        }
        function loadData(val) {
            $('#dg').datagrid({
                fitColumns:true,
                url: '/staffServlet?action=getslist',
                toolbar: '#tb',
                height:'100%',
                pagination: true,
                fit: true,
                singleSelect: true,
                selectOnCheck:true,
                queryParams: {
                    sCode: val
                },
                columns: [[
                    // {field: 'roomNum', title: '房间编号', sortable:true, checkbox: true, width: 100},
                    {field: 'sname', title: '姓名', width: 100},
                    {field: 'address', title: '地址', width: 100},
                    {field: 'birthday', title: '生日', width: 100},
                    {field: 'sPassword', title: '密码', width: 100},
                    {field: 'sGender', title: '性别', width: 100},
                    {field: 'sCode', title: '身份证号码', width: 100},
                    {field: 'sPhonenum', title: '联系电话', width: 100},
                    {field: 'postNum', title: '岗位编号', width: 100},
                    {field: 'postType', title: '岗位名称', width: 100},
                    {field: 'dnum', title: '部门编号', width: 100},
                    {field: 'dName', title: '部门名称', width: 100},
                    {field: 'utilsNum', title: '权限编号', width: 100},



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
                <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:true"
                   id="btnAdd">新增</a></td>
            <td>
                <div class="datagrid-btn-separator"></div>
            </td>
            <td>
                <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-edit',plain:true"
                   id="btnEdit">编辑</a></td>
            <td>
                <div class="datagrid-btn-separator"></div>
            </td>
            <td>
                <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-remove',plain:true"
                   id="btnDelete">删除</a>
            </td>
        </tr>
    </table>
</div>
</body>
</html>
