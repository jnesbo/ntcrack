<#import "common/common.ftl" as c>
<#include "common/securityPage.ftl">
<@c.page true>


    <div class="container-fluid gedf-wrapper">
        <div class="row">
            <div class="col-sm-3">
                <form action="/user/search" method="get">
                    <div class="input-group mb-3 col-sm">
                        <div class="input-group-prepend">

                            <button class="btn btn-outline-primary input-group-text" type="submit" id="button-addon2">
                                <i class="fa fa-search"></i>
                            </button>
                        </div>
                        <input type="text" class="form-control" name="username">

                    </div>
                </form>
            </div>
            <div class="col-md-5">
                <h4>Список пользователей</h4>
                <table class="table">
                    <thead class="thead-dark">
                    <tr>
                        <th scope="col">Name</th>
                        <th scope="col">Email</th>
                        <#if isAdmin>
                            <th scope="col">Role</th>
                        </#if>

                    </tr>
                    </thead>
                    <tbody>
                    <#list users as user>
                        <tr>
                            <td>



                                        <a class="h4" href="posts/user-posts/${user.id}">${user.username}</a>



                            </td>

                            <td>${user.email}</td>
                            <#if isAdmin>
                                <td>
                                    <#list user.roles as role> ${role} <#sep>, </#list>
                                </td>
                                <#if currentUserId!=user.id>
                                    <td><a href="/user/${user.id}">edit</a></td>
                                </#if>
                            </#if>

                        </tr>
                    </#list>

                    </tbody>
                </table>
            </div>
        </div>
    </div>
</@c.page>