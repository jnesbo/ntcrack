<#import "common/common.ftl" as c>

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
            <div class="col-sm-1">
                <h3 >${userChannel.username}</h3></div>
        <ul class="list-group">
            <#list users as user>

                <li class="list-group-item">
                    <div class="col-sm-3">
                <a href="/posts/user-posts/${user.id}">${user.getUsername()}</a>
                    </div>
                    <div class="col-sm">
                        <#if user.description??>
                        ${user.description!''}
                            <#else>
                        </#if>
                    </div>

                </li>
            <#else>

            </#list>
        </ul>
        </div>
    </div>

    </div
</@c.page>