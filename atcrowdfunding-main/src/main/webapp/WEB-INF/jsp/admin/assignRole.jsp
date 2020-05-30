<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
    </style>
</head>

<body>

<jsp:include page="/WEB-INF/jsp/common/top.jsp"/>

<div class="container-fluid">
    <div class="row">
        <jsp:include page="/WEB-INF/jsp/common/sider.jsp"/>
        <div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">
            <ol class="breadcrumb">
                <li><a href="#">首页</a></li>
                <li><a href="#">数据列表</a></li>
                <li class="active">分配角色</li>
            </ol>
            <div class="panel panel-default">
                <div class="panel-body">
                    <form role="form" class="form-inline">
                        <div class="form-group">
                            <label for="exampleInputPassword1">未分配角色列表</label><br>
                            <select id="LeftRoleList" class="form-control" multiple size="10"
                                    style="width:250px;overflow-y:auto;">
                                <c:forEach items="${unAssignList}" var="trole">
                                    <option value="${trole.id}">${trole.name}</option>
                                </c:forEach>
                            </select>
                        </div>
                        <div class="form-group">
                            <ul>
                                <li id="LeftToRightBtn" class="btn btn-default glyphicon glyphicon-chevron-right"></li>
                                <br>
                                <li id="RightToLeftBtn" class="btn btn-default glyphicon glyphicon-chevron-left"
                                    style="margin-top:20px;"></li>
                            </ul>
                        </div>
                        <div class="form-group" style="margin-left:40px;">
                            <label for="exampleInputPassword1">已分配角色列表</label><br>
                            <select id="RightRoleList" class="form-control" multiple size="10"
                                    style="width:250px;overflow-y:auto;">
                                <c:forEach items="${assignList}" var="trole">
                                    <option value="${trole.id}">${trole.name}</option>
                                </c:forEach>
                            </select>
                        </div>
                    </form>
                </div>
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


    $("#LeftToRightBtn").click(function () {
        var leftRoleSelect = $("#LeftRoleList option:selected");


        var data1 = '';
        $.each(leftRoleSelect, function (i, e) {
            var roleId = e.value;
            data1 += 'roleId=' + roleId + '&';

        })
        data1 += 'adminId=${param.id}'


        $.post(
            "${PATH}/admin/doAssign",
            data1,
            function (result) {
                if ("0" != result) {
                    layer.msg("修改成功！", {time: 1000},function () {
                        $("#RightRoleList").append(leftRoleSelect);
                        $("#LeftRoleList option:selected").remove();
                    });
                } else {
                    layer.msg("修改失败！")
                }
            }
        )


    })
    $("#RightToLeftBtn").click(function () {
        var rightRoleList = $("#RightRoleList option:selected");


        var data = '';
        $.each(rightRoleList, function (i, e) {
            var roleId = e.value;
            data += 'roleId=' + roleId + '&';

        })
        data += 'adminId=${param.id}';


        $.post(
            "${PATH}/admin/doRemoveAssign",
            data,
            function (result) {
                if ("0" != result) {
                    layer.msg("修改成功！", {time: 1000},function () {
                        $("#LeftRoleList").append(rightRoleList);
                        $("#RightRoleList option:selected").remove();
                    });
                } else {
                    layer.msg("修改失败！")
                }
            }
        )


    })


</script>
</body>
</html>
