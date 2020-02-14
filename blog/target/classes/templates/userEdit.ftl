<#import "common/common.ftl" as c>

<@c.page true>


    <div class="container">
        <h4>Редактирование юзера</h4>

        <form action="/user" method="post">
            <input type="text" name="username" value="${user.username}">
            <#list roles as role>
                <div>
                    <label>
                        <input type="checkbox" name="${role}" ${user.roles?seq_contains(role)?string("checked", "")}>
                        ${role}
                    </label>
                </div>
            </#list>
            <input type="hidden" value="${user.id}" name="userId">
            <input type="hidden" value="${_csrf.token}" name="_csrf">
            <button type="submit">Изменить</button>
        </form>
        <td><a href="/user/delete/${user.id}">delete</a></td>
    </div>
</@c.page>