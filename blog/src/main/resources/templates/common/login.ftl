<#include "securityPage.ftl" >
<#macro login path isReg>
    <div class=" container-sm ml-5 m-3 p-3 " style="max-width: 400px;">

        <form method="post" action=${path}>
            <div class="row ">
                <div class="col">
                    <label for="usrnm">Логин:</label>
                    <input class="form-control ${(usernameError??)?string('is-invalid', '')}"
                           type="text" id="usrnm"
                           name="username"
                           value="<#if user??>${user.username}</#if>"/>
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

            <!-- REGISTRATION FORM -->
            <#if isReg>


                <div class="row">
                    <div class="col">
                        <label for="eml">Почта:</label>
                        <input type="email" id="eml" name="email"
                               value="<#if user??>${user.email}</#if>"
                               class="form-control ${(emailError??)?string('is-invalid', '')}"
                               placeholder="some@some.com"/>
                        <#if emailError??>
                            <div class="invalid-feedback">
                                ${emailError}
                            </div>
                        </#if>
                    </div>
                </div>
            </#if>
            <div class="mt-3">
                <input type="hidden" name="_csrf" value="${_csrf.token}"/>
                <button class="btn btn-outline-dark"
                        type="submit"><#if isReg>Зарегистрироваться<#else>Войти</#if></button>
            </div>
        </form>
        <div class="mt-3">
            <#if !isReg>

                <a href="/reg">Новый пользователь</a>
                </br>
                <a href="/forgotPassword">Забыли пароль?</a>

            </#if>
        </div>

    </div>
</#macro>

<#macro logout>


    <form action="/logout" method="post">
        <input type="hidden" name="_csrf" value="${_csrf.token}"/>
        <button class="btn btn-sm btn-outline-secondary" type="submit"><#if user??>Выйти<#else>Войти</#if></button>
    </form>


</#macro>

