<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
                    <h3 class="panel-title"><i class="glyphicon glyphicon-th"></i> 数据列表</h3>
                </div>
                <div class="panel-body">
                    <form class="form-inline" role="form" style="float:left;">
                        <div class="form-group has-feedback">
                            <div class="input-group">
                                <div class="input-group-addon">查询条件</div>
                                <input id="condition" class="form-control has-success" type="text"
                                       placeholder="请输入查询条件">
                            </div>
                        </div>
                        <button id="queryBtn" type="button" class="btn btn-warning"><i
                                class="glyphicon glyphicon-search"></i> 查询
                        </button>
                    </form>
                    <button type="button" class="btn btn-danger" style="float:right;margin-left:10px;"><i
                            class=" glyphicon glyphicon-remove"></i> 删除
                    </button>
                    <button id="addBtn" type="button" class="btn btn-primary" style="float:right;">
                        <i class="glyphicon glyphicon-plus"></i> 新增
                    </button>
                    <br>
                    <hr style="clear:both;">
                    <div class="table-responsive">
                        <table class="table  table-bordered">
                            <thead>
                            <tr>
                                <th width="30">#</th>
                                <th width="30"><input type="checkbox"></th>
                                <th>名称</th>
                                <th width="100">操作</th>
                            </tr>
                            </thead>
                            <tbody>

                            </tbody>
                            <tfoot>
                            <tr>
                                <td id="pageNavg" colspan="6" align="center">

                                </td>
                            </tr>

                            </tfoot>
                        </table>
                    </div>
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
                <h4 class="modal-title" id="myModalLabel">添加角色</h4>
            </div>
            <div class="modal-body">
                <div class="form-group">
                    <label for="name">角色名称</label>
                    <input type="text" class="form-control" id="name" name="name" placeholder="请输入角色名称">
                </div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                <button id="saveBtn" type="button" class="btn btn-primary">保存</button>
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
                <h4 class="modal-title" id="myModalLabel">修改角色</h4>
            </div>
            <div class="modal-body">
                <div class="form-group">
                    <label for="name">角色名称</label>
                    <input type="hidden" name="id">
                    <input type="text" class="form-control" id="name" name="name" placeholder="请输入角色名称">
                </div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                <button id="updateBtn" type="button" class="btn btn-primary">修改</button>
            </div>
        </div>
    </div>
</div>


<!-- 配置许可模态框 -->
<div class="modal fade" id="assignModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                <h4 class="modal-title" id="myModalLabel">角色权限配置</h4>
            </div>
            <ul id="treeDemo" class="ztree">

            </ul>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                <button id="assignBtn" type="button" class="btn btn-primary">分配</button>
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
                }
            }
        });

    });


    $(function () {
        initData(1);
    })

    var json = {
        pageNum: 1,
        pageSize: 5,
        condition: ""
    }

    function initData(pageNum) {
        var index = -1;
        // 1.发起分页请求 获取分页数据
        json.pageNum = pageNum;
        $.ajax({
            type: "post",
            url: "${PATH}/role/loadData",
            data: json,
            beforeSend:function(){
                // index = layer.load(0,{time:10*1000});
                return true ;
            },
            success: function (result) {
                // layer.close(index);

                initshow(result);

                initNavg(result);


            }
        });

        // 2。展示数据
        function initshow(result) {
            var list = result.list;
            // 每次点击完成后进行清空
            $('tbody').empty();

            $.each(list, function (i, role) {
                var tr = $('<tr></tr>');
                tr.append('<td>' + (i + 1) + '</td>');
                tr.append('<td><input type="checkbox"></td>');
                tr.append('<td>' + role.name + '</td>');

                var td = $('<td></td>');

                td.append('<button type="button" roleId="' + role.id + '" class="assignPermissionClass btn btn-success btn-xs"><i class=" glyphicon glyphicon-check"></i></button>')
                td.append('<button type="button" roleId="' + role.id + '" class="updateClass btn btn-primary btn-xs"><i class=" glyphicon glyphicon-pencil"></i></button>')
                td.append('<button type="button" roleId="' + role.id + '" class="deleteClass btn btn-danger btn-xs"><i  class=" glyphicon glyphicon-remove"></i></button>')

                tr.append(td);

                tr.appendTo($('tbody'));
            })
        };
        // 2。展示数据结束！！！！！！！！！！！！！！！！！


        // 3.制作分页条
        function initNavg(result) {


            var navigatepageNums = result.navigatepageNums;

            var ur = $('<ul class="pagination"></ul>');
            $("#pageNavg").empty();


            if (result.isFirstPage) {
                ur.append($(' <li class="disabled"><a>上一页</a></li>'));
            } else {
                ur.append($(' <li><a onclick="initData(' + (result.pageNum - 1) + ')">上一页</a></li>'))
            }
            ;

            $.each(navigatepageNums, function (i, num) {
                if (num == result.pageNum) {
                    ur.append($('<li class="active"><a href="#">' + num + '<span class="sr-only">(current)</span></a></li>'));
                } else {
                    ur.append($('<li><a onclick="initData(' + num + ')">' + num + '</a></li>'));
                }
            })
            ;
            if (result.isLastPage) {
                ur.append($('<li class="disabled"><a >下一页</a></li>'))
            } else {
                ur.append($(' <li><a onclick="initData(' + (result.pageNum + 1) + ')">下一页</a></li>'))
            }
            ;

            ur.appendTo($('#pageNavg'));

        }

        // 3.制作分页条结束！！！！！！！！！！！！！！！！！！

        //4.条件查询
        $("#queryBtn").click(function () {
            var condition = $("#condition").val();
            json.condition = condition;
            initData(1);
        })
        //4.条件查询结束！！！！！！！！！！！！！！！！

        //===添加 开始==============================================================

        $("#addBtn").click(function(){
            $("#addModal").modal({
                show:true,
                backdrop:'static',
                keyboard:false
            });
        });

        $("#saveBtn").off("click");
        $("#saveBtn").click(function(){
            var name = $("#addModal input[name='name']").val();

            $.ajax({
                type:"post",
                url:"${PATH}/role/doAdd",
                data:{
                    name:name
                },
                beforeSend:function(){
                    return true ;
                },
                success:function(result){
                    if("ok"==result){
                        layer.msg("保存成功",{time:1000},function(){
                            $("#addModal").modal('hide');
                            $("#addModal input[name='name']").val("");
                            initData(1); //添加后初始化第一页，倒序排序。
                        });
                    }
                    else if ("403"==result){
                        layer.msg("您没有权限！")
                    }
                    else{
                        layer.msg("保存失败");
                    }
                }
            });

        });

        //===添加 结束==============================================================


        //===修改 开始==============================================================
        /*
        $(".updateClass").click(function(){
            alert("update");
        });
         */
        $('tbody').off("click");
        $('tbody').on('click','.updateClass',function(){
            //var roleId = this.roleId ;// this DOM对象， dom对象不能获取自定义属性。
            var roleId = $(this).attr("roleId");

            $.get("${PATH}/role/getRoleById",{id:roleId},function(result){
                console.log(result);

                $("#updateModal").modal({
                    show:true,
                    backdrop:'static',
                    keyboard:false
                });

                $("#updateModal input[name='name']").val(result.name);
                $("#updateModal input[name='id']").val(result.id);
            });



        });
        $("#updateBtn").off("click");
        $("#updateBtn").click(function(){
            var name = $("#updateModal input[name='name']").val();
            var id = $("#updateModal input[name='id']").val();

            $.post("${PATH}/role/doUpdate",{id:id,name:name},function(result){
                if("ok"==result){
                    layer.msg("修改成功",{time:1000},function(){
                        $("#updateModal").modal('hide');
                        initData(json.pageNum); //初始化当前页
                    });
                }else{
                    layer.msg("修改失败");
                }
            });
        });
        //修改结束|||||||||||||||||||||||||||||||||||||||||||||||||||||
        //删除事件！

        // $(".deleteClass").click(function () {})
        $("tbody").off("click");
        $("tbody").on("click",".deleteClass",function () {
            var roleId = $(this).attr("roleId");

            $.post("${PATH}/role/doDelete",{id:roleId},function (msg) {
                if ("1"==msg){
                    layer.msg("删除成功！")
                    initData(json.pageNum);
                    return;
                } else {
                    layer.msg("删除失败！")
                }

            })


        })

        //修改结束|||||||||||||||||||||||||||||||||||||||||||||||||||||

        //分配许可|||||||||||||||||||||||||||||||||||||||||||||||||||||
        $('tbody').off("click");
        $('tbody').on("click",".assignPermissionClass",function () {

            roleId = $(this).attr("roleId");

            // 配置ztree
            function initTree(){
                var setting = {
                    check: {
                        enable: true,
                        autoCheckTrigger: true
                    },
                    data: {
                        simpleData: {
                            enable: true,
                            pIdKey: "pid"
                        },
                        key: {
                            url: "xxUrl",
                            name:"title"
                        }
                    },
                    view: {
                        addDiyDom: function (treeId,treeNode) {
                            $("#"+treeNode.tId+"_ico").removeClass();
                            $("#"+treeNode.tId+"_span").before('<span class="'+treeNode.icon+'"></span>');
                        }}
                };

                //1.加载数据
                $.get("${PATH}/permission/loadTree",function(data){
                    var tree = $.fn.zTree.init($("#treeDemo"), setting, data);
                    var treeObj = $.fn.zTree.getZTreeObj("treeDemo");
                    treeObj.expandAll(true);

                    $.get("${PATH}/role/listPermissionIdByRoleId",{roleId:roleId},function(data){
                        $.each(data,function(i,e){
                            var permissionId = e ;
                            var treeObj = $.fn.zTree.getZTreeObj("treeDemo");
                            var node = treeObj.getNodeByParam("id", permissionId, null);
                            treeObj.checkNode(node, true, false , false);
                        });
                    });


                });
            }

            // 开始设置
            initTree();


            roleId = $(this).attr("roleId");

            $("#assignModal").modal({
                show:true,
                backdrop:'static',
                keyboard:false
            });

            var json ={
                roleId:roleId
            }
            $("#assignBtn").off("click");
            $("#assignBtn").click(function () {
                var treeObj = $.fn.zTree.getZTreeObj("treeDemo");
                var nodes = treeObj.getCheckedNodes(true);
                $.each(nodes,function (i,e) {
                    var permissionId = e.id;

                    json['ids['+i+']'] = permissionId;

                    //多条数据提交和接收
                    //json['userList[i].name'] = 'xxx';
                    //json['userList[i].age'] = 23;

                })



                $.post(
                    "${PATH}/role/doAssignPermissionToRole",
                    json,
                    function (result) {
                        if ("0"!=result){
                            layer.msg("分配成功！",{time:1000},function () {
                                $("#assignModal").modal("hide");
                            })
                        } else {
                            layer.msg("分配失败！");
                        }

                    }
                )





            })












        })
        //许可分配结束|||||||||||||||||||||||||||||||||||||||||||||||||||||







    }


</script>
</body>
</html>
