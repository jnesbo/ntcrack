<!--- \\\\\\\Post-->
<#include "securityPage.ftl">


<#list posts as post>


    <div class="card m-2" style="max-width: 440px; min-width: 200px;   ">

            <div class="card-header">
                <div class="d-flex justify-content-between align-items-center">
                    <div class="mr-2">

                    </div>
                    <div class="ml-2">
                        <div class="h5 m-0">  <#if user??>
                                <a class=""
                                   href="/posts/user-posts/${post.author.id!""}">${post.authorName!''}</a>
                            <#else>${post.authorName!''}
                            </#if></div>

                    </div>
                </div>
            </div>
        <div class="row">
            <#if post.filename??>
                <div class="col-sm">
                    <img class="img-thumbnail card-img" src="\img\${post.filename!''}" alt="no pic">
                </div>
            </#if>
            <div class="col-md-8">
                <div class="card-body">
                    <h5 class="card-title">

                    </h5>
                    <p class="card-text">
                        ${post.text}
                        <#if post.tagList??>
                            <#list post.tagList as t>
                                <a class=""
                                   href="/posts/search/${t.textTag!''}">#${t.textTag!''}</a>
                            </#list>
                        </#if>
                    </p>
                    <p class="card-text">
                        <small class="text-muted">  ${post.postedAt?string('dd.MM.yy')}</small>
                        <#if post.author.id = currentUserId>
                            <a href="/posts/delete/${post.id}"><i class="far fa-trash-alt"></i></a>
                        </#if></p>
                </div>
            </div>
        </div>
    </div>


<#else >
    Постов нет
</#list>
