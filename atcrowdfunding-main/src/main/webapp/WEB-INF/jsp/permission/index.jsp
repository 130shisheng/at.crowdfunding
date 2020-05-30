<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2020/5/14
  Time: 17:29
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>

</body>
</html>
permission    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2020/5/12
  Time: 21:11
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="zh-CN">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">

    <%@include file="/WEB-INF/jsp/common/css.jsp" %>
    <style>
        .tree li {
            list-style-type: none;
            cursor: pointer;
        }

        table tbody tr:nth-child(odd) {
            background: #F4F4F4;
        }

        table tbody td:nth-child(even) {
            color: #C00;
        }
    </style>
</head>

<body>

<jsp:include page="/WEB-INF/jsp/common/top.jsp"/>

<div class="container-fluid">
    <div class="row">
        <jsp:include page="/WEB-INF/jsp/common/sider.jsp"/>
        <div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">
            <div class="panel panel-default">
                <div class="panel-heading">
                    <h3 class="panel-title"><i class="glyphicon glyphicon-th"></i>权限管理</h3>
                </div>
                <div class="panel-body">
                    <ul id="treeDemo" class="ztree">

                    </ul>
                </div>
            </div>
        </div>
    </div>
</div>


<!-- 添加数据 模态框 -->
<div class="modal fade" id="addModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                <h4 class="modal-title" id="myModalLabel">增加权限</h4>
            </div>
            <div class="modal-body">
                <div class="form-group">
                    <label for="name">权限名称</label>
                    <input type="hidden" name="id">
                    <input type="text" class="form-control" id="name" name="name" placeholder="请输入权限名称">
                </div>
                <div class="form-group">
                    <label for="title">权限标题</label>
                    <input type="text" class="form-control" id="title" name="title" placeholder="请输入权限标题">
                </div>
                <div class="form-group">
                    <label for="icon">权限图标</label>
                    <input type="text" class="form-control" id="icon" name="icon" placeholder="请输入权限图标">
                </div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                <button id="saveBtn" type="button" class="btn btn-primary">添加</button>
            </div>
        </div>
    </div>
</div>


<!-- 修改数据 模态框 -->
<div class="modal fade" id="updateModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                <h4 class="modal-title" id="myModalLabel">修改权限</h4>
            </div>
            <div class="modal-body">
                <div class="form-group">
                    <label for="name">权限名称</label>
                    <input type="hidden" name="id">
                    <input type="text" class="form-control" id="name" name="name" placeholder="请输入权限名称">
                </div>
                <div class="form-group">
                    <label for="title">权限标题</label>
                    <input type="text" class="form-control" id="title" name="title" placeholder="请输入权限title">
                </div>
                <div class="form-group">
                    <label for="icon">权限图标</label>
                    <input type="text" class="form-control" id="icon" name="icon" placeholder="请输入权限图标">
                </div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                <button id="doupdateBtn" type="button" class="btn btn-primary">修改</button>
            </div>
        </div>
    </div>
</div>




<%@include file="/WEB-INF/jsp/common/js.jsp" %>
<script type="text/javascript">
    $(function () {
        $(".list-group-item").click(function () {
            if ($(this).find("ul")) {
                $(this).toggleClass("tree-closed");
                if ($(this).hasClass("tree-closed")) {
                    $("ul", this).hide("fast");
                } else {
                    $("ul", this).show("fast");
                } } });


    });

    $(function(){
        initTree();
    });

    function initTree(){
        var setting = {
            data: {
                simpleData: {
                    enable: true,
                    pIdKey: "pid"
                },
                key: {
                    url: "xUrl",
                    name:"title"
                }
            },
            view: {
                addDiyDom: addDiyDom,
                addHoverDom: addHoverDom, //显示按钮
                removeHoverDom: removeHoverDom //移除按钮
            }
        };

//1.加载数据
        $.get("${PATH}/permission/loadTree",function(data){
            data.push({"id":0,"title":"系统权限","icon":"glyphicon glyphicon-asterisk"});
            var tree = $.fn.zTree.init($("#treeDemo"), setting, data);
            var treeObj = $.fn.zTree.getZTreeObj("treeDemo");
            treeObj.expandAll(true);
        });
    }

    function addDiyDom(treeId,treeNode){
//console.log(treeNode);
//$("#"+treeNode.tId+"_ico").removeClass().addClass(treeNode.icon);
        $("#"+treeNode.tId+"_ico").removeClass();
        $("#"+treeNode.tId+"_span").before('<span class="'+treeNode.icon+'"></span>');
    }

    function addHoverDom(treeId,treeNode){
        var aObj = $("#" + treeNode.tId + "_a");
        aObj.attr("href", "javascript:;");
        if (treeNode.editNameFlag || $("#btnGroup"+treeNode.tId).length>0) return;
        var s = '<span id="btnGroup'+treeNode.tId+'">';
        if ( treeNode.level == 0 ) {
            s += '<a class="btn btn-info dropdown-toggle btn-xs" style="margin-left:10px;padding-top:0px;"  onclick="addBtn('+treeNode.id+')" >&nbsp;&nbsp;<i class="fa fa-fw fa-plus rbg "></i></a>';
        } else if ( treeNode.level == 1 ) {
            s += '<a class="btn btn-info dropdown-toggle btn-xs" style="margin-left:10px;padding-top:0px;"  onclick="updateBtn('+treeNode.id+')" title="修改权限信息">&nbsp;&nbsp;<i class="fa fa-fw fa-edit rbg "></i></a>';
            s += '<a class="btn btn-info dropdown-toggle btn-xs" style="margin-left:10px;padding-top:0px;" onclick="addBtn('+treeNode.id+')" >&nbsp;&nbsp;<i class="fa fa-fw fa-plus rbg "></i></a>';
            if (treeNode.children.length == 0) {
                s += '<a class="btn btn-info dropdown-toggle btn-xs" style="margin-left:10px;padding-top:0px;"  onclick="deleteBtn('+treeNode.id+')" >&nbsp;&nbsp;<i class="fa fa-fw fa-times rbg "></i></a>';
            }
        } else if ( treeNode.level == 2 ) {
            s += '<a class="btn btn-info dropdown-toggle btn-xs" style="margin-left:10px;padding-top:0px;"   onclick="updateBtn('+treeNode.id+')" title="修改权限信息">&nbsp;&nbsp;<i class="fa fa-fw fa-edit rbg "></i></a>';
            s += '<a class="btn btn-info dropdown-toggle btn-xs" style="margin-left:10px;padding-top:0px;"  onclick="deleteBtn('+treeNode.id+')" >&nbsp;&nbsp;<i class="fa fa-fw fa-times rbg "></i></a>';
        }

        s += '</span>';
        aObj.after(s);
    }

    function removeHoverDom(treeId,treeNode){
        $("#btnGroup"+treeNode.tId).remove();
    }

    //添加开始///////////////////////////////////////////////////
    function addBtn(id) {
        $("#addModal").modal({
            show:true,
            backdrop:'static',
            keyboard:false
        });

        $("#saveBtn").click(function() {


            $.post(
                "${PATH}/permission/doADD",
                {
                    pid:id,
                    icon:$("#addModal input[name='icon']").val(),
                    title:$("#addModal input[name='title']").val(),
                    name:$("#addModal input[name='name']").val()
                },
                function(result) {
                    if ("1"==result){
                        layer.msg("添加成功！",{time:1000},function() {
                            $("#addModal").modal("hide");
                            $("#addModal input[name='icon']").val("");
                            $("#addModal input[name='title']").val("");
                            $("#addModal input[name='name']").val("");
                            initTree();
                        })
                    }else {
                        layer.msg("添加失败！")
                    };

                })
        })
    }
    //添加结束///////////////////////////////////////////////////
    //修改开始///////////////////////////////////////////////////
    function updateBtn(id) {
        $.get(
            "${PATH}/permission/getTPermissionById",
            {id:id},
            function(Tpermission) {
                $("#updateModal input[name='id']").val(Tpermission.id);
                $("#updateModal input[name='name']").val(Tpermission.name);
                $("#updateModal input[name='title']").val(Tpermission.title);
                $("#updateModal input[name='icon']").val(Tpermission.icon);
            });
        $("#updateModal").modal({
            show:true,
            backdrop:'static',
            keyboard:false
        });

        $("#doupdateBtn").click(function() {

            $.post(
                "${PATH}/permission/doUpdate",
                {
                    id: $("#updateModal input[name='id']").val(),
                    icon:$("#updateModal input[name='icon']").val(),
                    title:$("#updateModal input[name='title']").val(),
                    name:$("#updateModal input[name='name']").val()
                },
                function(result) {
                    if ("1"==result){
                        layer.msg("修改成功！",{time:1000},function() {
                            $("#updateModal").modal("hide");
                            $("#updateModal input[name='icon']").val("");
                            $("#updateModal input[name='title']").val("");
                            $("#updateModal input[name='name']").val("");
                            initTree();
                        })
                    }else {
                        layer.msg("修改失败！")
                    };

                })
        })
    }
    //修改结束/////////////////////////////////////////////////////
    //删除开始///////////////////////////////////////////////////
    function deleteBtn(id) {
        $.post(
            "${PATH}/permission/doDelete",
            {id:id},
            function(result) {
                if ("1"==result){
                    layer.msg("删除成功！",{time:1000},function() {
                        initTree();
                    })
                }else {
                    layer.msg("删除失败！");
                }

            }
        )

    }
    //删除结束///////////////////////////////////////////////////


</script>
</body>
</html>
