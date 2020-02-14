<#import "common/common.ftl" as c>
<#import "common/login.ftl" as l>
<@c.page false>

    <#if Session?? && Session.SPRING_SECURITY_LAST_EXCEPTION??>
        <div class="alert alert-danger" role="alert">
            ${Session.SPRING_SECURITY_LAST_EXCEPTION.message}

        </div>
        <#else>


    </#if>

    <@l.login "/login" false/>


</@c.page>