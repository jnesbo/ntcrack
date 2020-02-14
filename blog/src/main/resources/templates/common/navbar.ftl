<#import "login.ftl" as l>
<#include "securityPage.ftl" >


<nav class="navbar navbar-expand-lg navbar-light bg-light">
    <a href="<#if user??>/posts<#else>/</#if>" class="navbar-brand">InNutshell</a>

    <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarText"
            aria-controls="navbarText" aria-expanded="false" aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"></span>
    </button>
    <div class="collapse navbar-collapse" id="navbarText">
        <ul class="navbar-nav mr-auto">
            <#if user??>
                <li class="nav-item active">
                    <a class="nav-link" href="/posts/user-posts/${user.id!''}">Профиль</a>
                </li>


            <li class="nav-item active">
                <a class="nav-link" href="/user">Список пользователей</a>
            </li>
            <li class="nav-item active">
            </li>
            <a class="nav-link" href="/posts/postsFeed/${user.id!''}">Лента</a>
            <#if isAdmin>
                <li class="nav-item">
                    <a class="nav-link" href="/statistics">Статистика</a>
                </li>
            </#if>
            </#if>
        </ul>

        <span class="navbar-text mr-3"><#if user??>${name}<#else>Гость</#if></span>
        <span class="navbar-text"><#if user??><@l.logout/></#if></span>
    </div>
</nav>




