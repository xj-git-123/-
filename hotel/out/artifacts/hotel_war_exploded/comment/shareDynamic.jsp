<%--
  Created by IntelliJ IDEA.
  User: xuxu3
  Date: 2021/6/17
  Time: 16:35
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>共享信息管理</title>
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
        function saveData(action) {
            $.messager.progress();
            $('#ff').form('submit', {
                url: '/shareServlet?action=' + action,
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
                fitColumns: true,
                url: '/shareServlet?action=getslist',
                toolbar: '#tb',
                height: '100%',
                pagination: true,
                fit: true,
                singleSelect: true,
                selectOnCheck: true,
                queryParams: {
                    shareno: val
                },
                columns: [[
                    {field: 'shareno', title: '寻贴编号', width: 100},
                    {field: 'nickname', title: '昵称', width: 100},
                    {field: 'sharecontent', title: '内容', width: 100},
                    {field: 'shareimage', title: '图片', width: 100},
                    {field: 'sharedate', title: '发布日期', width: 100},
                    {
                        field: 'operate', title: '操作', align: 'center', width: $(this).width() * 0.1,
                        formatter: function (value, row, index) {
                            var str = "";
                            str += '<a href="#" name="check" class="easyui-linkbutton" style="margin-left: 10px"></a>';
                            str += '<a href="#" name="update" class="easyui-linkbutton" style="margin-left: 10px"></a>';
                            return str;

                        },

                    },

                ]],
                onLoadSuccess: function (data) {
                    $("a[name='check']").linkbutton({text: '查看', plain: false, iconCls: 'icon-add'});
                    $("a[name='update']").linkbutton({text: '删除', plain: false, iconCls: 'icon-edit'});
                    $("a[name='check']").click(function () {
                        let row = $('#dg').datagrid('getSelected');
                        console.log(row)
                        if (row == null) {
                            $.messager.alert('提示', '请选择要查看的数据', 'warning');
                            return;
                        }
                        $('#dd').dialog({
                            title: '修改数据',
                            width: 450,
                            height: 340,
                            closed: false,
                            cache: false,
                            href: 'openForm.jsp?action=edit&r=' + Math.random(),
                            modal: true,
                            buttons: [
                                {
                                    text: '退出',
                                    iconCls: 'icon-back',
                                    handler: function () {
                                        $('#dd').dialog('close')
                                    }
                                }
                            ]

                        })
                        ;
                    }),
                        $("a[name='update']").click(function () {

                        })

                },


            });

        }


    })
</script>
<body data-options="region:'center',border:false" style="padding: 0">
<div style="height: 100% " data-options="fit:true" class="grid-panel">
    <table id="dg">
    </table>
</div>
<div id="dd"></div>
<div id="tb" style="height:auto">
    <table>
        <tr>
            <td>
                <input class="easyui-textbox" id="btnQuery"
                       data-options="buttonText:'搜索',buttonIcon:'icon-search',prompt:'根据内容搜索'"
                       style="width:180px;height:24px;">
            </td>
        </tr>
    </table>
</div>
</body>
</html>
