<#import "common/common.ftl" as c>

<@c.page true>
    <div class="jumbotron">
        <h1 >Добро пожаловать!</h1>
        <hr class="my-4">
        <p>Это простой блог для коротких записей</p>
        <a class="btn btn-success btn-block" href="/login" role="button" style="width: 200px;">Вход</a>
        <a class="btn btn-primary btn-block " href="/reg" role="button" style="width: 200px;">Регистрация</a>
    </div>
${rand!''}
</@c.page>