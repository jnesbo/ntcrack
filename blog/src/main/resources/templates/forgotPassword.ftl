<#import "common/common.ftl" as c>

<@c.page false>

    <div class="jumbotron">


        <form method="post" action="/forgotPassword">
            <div class="row m-3">
                <div class="col">
                    <label for="eml">Введите адрес почты</label>
                    <input type="email" id="eml" name="email"
                           class="form-control ${(emailError??)?string('is-invalid', '')}"
                    style="width: 250px;"/>
                    <#if emailError??>
                        <div class="invalid-feedback">
                            ${emailError}
                        </div>
                    </#if>


            <div class="mt-3">
                <input type="hidden" name="_csrf" value="${_csrf.token}"/>
                <button class="btn btn-success" style="width: 200px;" type="submit">Отправить код</button>
            </div>
                    <p class="mt-3"> ${message?if_exists}</p>
                </div>
            </div>
        </form>
    </div>
</@c.page>