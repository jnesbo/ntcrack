<#import "common/common.ftl" as c>
<#include "common/securityPage.ftl" >
<@c.page true>
    <div class=" container-sm ml-5 m-3 p-3 " style="max-width: 400px;">
        <form method="post" action="/user/profile/updateSecurity">
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
                    <label for="eml">Почта:</label>
                    <input type="email" id="eml" name="email"
                           value="<#if user??>${user.email}</#if>"
                           class="form-control ${(emailError??)?string('is-invalid', '')}"
                    />
                    <#if emailError??>
                        <div class="invalid-feedback">
                            ${emailError}
                        </div>
                    </#if>
                </div>
            </div>


            ${message?if_exists}

            <div class="mt-3">
                <input type="hidden" name="_csrf" value="${_csrf.token}"/>
                <button class="btn btn-primary" type="submit">Сохранить</button>
            </div>
        </form>


    </div>


</@c.page>