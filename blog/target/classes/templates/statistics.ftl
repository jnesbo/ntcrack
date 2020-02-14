<#import "common/common.ftl" as c>

<@c.page true>

    <div class="container-fluid gedf-wrapper">

        <div class="row">

            <div class="col-4">
                <h5>Зарегистрировано пользователей</h5>
                <table class="table">
                    <thead class="thead-dark">
                    <tr>
                        <th scope="col">Всего пользователей</th>
                        <th scope="col">С постами</th>
                        <th scope="col">Без постов</th>
                        <th scope="col">Всего постов</th>
                    </tr>
                    </thead>
                    <tbody>
                    <tr>

                        <td scope="row">${users}</td>
                        <td scope="row">${usersWithPosts}</td>
                        <td scope="row">${usersWithoutPosts}</td>
                        <td scope="row">${allPosts}</td>
                    </tr>
                    </tbody>
                </table>
                <a href="/statistics/postStatistics">Смотреть статистику постов</a>
            </div>


            <div class="col-6">
                <div id="container"
                     class="img-thumbnail"></div>

            </div>
        </div>

    </div>
    <script inline="javascript">
        $(function () {
            Highcharts.chart('container', {
                chart: {
                    type: 'column'
                },
                title: {
                    text: 'Колличество регистраций'
                },

                xAxis: {
                    categories: [
                        <#list usersStatistics?keys as post>
                        '${post}',
                        </#list>
                    ],

                },
                yAxis: {
                    title: {
                        text: 'Колличество'
                    }
                },
                tooltip: {
                    headerFormat: '<span style="font-size:10px">{point.key}</span><table>',
                    pointFormat: '<tr><td style="color:{series.color};padding:0"/>' +
                        '<td style="padding:0"><b>{point.y} </b></td></tr>',
                    footerFormat: '</table>',
                    shared: true,
                    useHTML: true
                },
                plotOptions: {
                    column: {
                        pointPadding: 0.2,
                        borderWidth: 0
                    }
                },
                series: [{
                    name: 'Дата',
                    colorByPoint: true,
                    data: [
                        <#list usersStatistics?keys as post>
                        ${usersStatistics[post]},
                        </#list>
                    ]
                }],

            });
        });
    </script>

</@c.page>