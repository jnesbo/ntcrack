<#import "common/common.ftl" as c>

<@c.page false>


    <div class=" container-sm ml-5 m-3 p-3 " style="max-width: 400px;">

        <form method="post" action="/resetPassword">
            <div class="row ">
                <div class="col">
                    <label for="usrnm">Логин:</label>
                    <input class="form-control ${(usernameError??)?string('is-invalid', '')}"
                           type="text" id="usrnm"
                           name="username"/>
                    <#if usernameError??>
                        <div class="invalid-feedback">
                            ${usernameError}
                        </div>
                    </#if>
                </div>
            </div>
            <div class="row">
                <div class="col">
                    <label for="psw">Пароль:</label>
                    <input class="form-control ${(passwordError??)?string('is-invalid', '')}"
                           type="password"
                           name="password" id="psw"/>
                    <#if passwordError??>
                        <div class="invalid-feedback">
                            ${passwordError}
                        </div>
                    </#if>
                </div>
            </div>
            <div class="row">
                <div class="col">
                    <label for="code">Код:</label>
                    <input class="form-control ${(codeError??)?string('is-invalid', '')}"
                           type="text"
                           name="code" id="code"/>
                </div>
                <#if codeError??>
                    <div class="invalid-feedback">
                        ${codeError}
                    </div>
                </#if>
            </div>

            <div class="mt-3">
                <input type="hidden" name="_csrf" value="${_csrf.token}"/>
                <button class="btn btn-outline-dark" type="submit">Обновить пароль</button>
            </div>
        </form>
        ${message?if_exists}
    </div>
</@c.page>