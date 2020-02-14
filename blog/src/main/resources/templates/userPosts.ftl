<#import "common/common.ftl" as c>


<@c.page true>
    <div class="container-fluid gedf-wrapper">
        <div class="row">

            <div class="col-md-3">
                <div class="card">
                    <div class="card-body">
                        <div class="h7 text-muted">
                            <div class="row ml-3">


                                <h3>${userChannel.username}</h3>
                                <#if isCurrentUser>
                                    <a href="/user/update"><i class="fas fa-pen ml-3"></i></a>
                                </#if>

                            </div>
                        </div>
                    </div>
                </div>

                <div class="card-body">
                    <div>
                        <#if isCurrentUser>
                            <form method="post" action="/user/update/${userChannel.id}">
                                <div class="input-group mb-3">

                                    <textarea type="text" class="form-control" name="description"
                                              placeholder="${userChannel.description!''}"></textarea>
                                    <div class="input-group-append">
                                        <input type="hidden" name="_csrf" value="${_csrf.token}"/>
                                        <button class="input-group-text" type="submit">|</button>
                                    </div>
                                </div>

                            </form>
                        <#else >
                            <div class="h6">
                                ${userChannel.description!''}
                            </div>

                        </#if>


                    </div>
                </div>
                <div class="card">
                    <ul class="list-group list-group-flush">
                        <li class="list-group-item">
                            <div class="h6 text-muted">
                                Читатели
                            </div>
                            <div class="h5">
                                <#if subscribersCount!=0>
                                    <a href="/user/followers/${userChannel.id}/list">

                                        ${subscribersCount}</a>
                                <#else>
                                    0
                                </#if>
                            </div>
                        </li>
                        <li class="list-group-item">
                            <div class="h6 text-muted">
                                Читаемые
                            </div>
                            <div class="h5">
                                <#if subscriptionsCount!=0>
                                    <a href="/user/following/${userChannel.id}/list">
                                        ${subscriptionsCount}</a>
                                <#else >
                                    0
                                </#if>
                            </div>
                        </li>
                        <li class="list-group-item">
                            <#if !isCurrentUser>
                                <#if isSubscriber>
                                    <a class="btn btn-info" href="/user/unsubscribe/${userChannel.id}">Отписаться</a>
                                <#else>
                                    <a class="btn btn-info" href="/user/subscribe/${userChannel.id}">Подписаться</a>
                                </#if>
                            </#if></li>

                        <li class="list-group-item">
                            Зарегистрирован
                            ${user.createdAt!''}

                        </li>
                    </ul>
                </div>

            </div>


            <div class="col-8 ">
                <#include "common/postList.ftl" />
            </div>
        </div>
    </div>

</@c.page>