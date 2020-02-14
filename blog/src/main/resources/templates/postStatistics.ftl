<#import "common/common.ftl" as c>

<@c.page true>

    <div class="container p-2">
        <div class="row ">

            <div id="container1"
                 class="img-thumbnail col m-1" ></div>
        </div>
    <div class="row ">
            <div id="container2"
                 class="img-thumbnail col m-1" ></div>
    </div>



    </div>

    <script inline="javascript">
        $(function () {
            Highcharts.chart('container1', {
                chart: {
                    type: 'column'
                },
                title: {
                    text: 'Колличество постов в день'
                },

                xAxis: {
                    categories: [
                        <#list postsStatistics?keys as post>
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
                    name: 'Время',
                    colorByPoint: true,
                    data: [
                        <#list postsStatistics?keys as post>
                        ${postsStatistics[post]},
                        </#list>
                    ]
                }],

            });
        });
        $(function () {
            Highcharts.chart('container2', {
                chart: {
                    type: 'bar'
                },
                title: {
                    text: 'Популярность тегов'
                },

                xAxis: {
                    categories: [
                        <#list tagsStatistics?keys as key>
                        '${key}',
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
                        pointPadding: 1,
                        borderWidth: 0
                    }
                },
                series: [{
                    name: 'время',
                    colorByPoint: true,
                    data: [
                        <#list tagsStatistics?keys as key>
                        ${tagsStatistics[key]},
                        </#list>
                    ]
                }],

            });
        });
    </script>

</@c.page>