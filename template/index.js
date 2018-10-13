"use strict";
var page = require('webpage').create();
var system = require('system')

var json = system.args[1];
window.json = json;
window.filename = system.args[2] + ".jpg";

console.log("json: ", json);
console.log("filename", window.filename);

function onPageReady() {
    var data = page.evaluate(function(json){
        // render
        document.vue = new Vue({
            el:'#app',
            data: {
                option: JSON.parse(json)
            },
            methods: {
                setOption: function (option) {
                    this.option = option;
                }
            }
        });

        window.myChart2 = echarts.init(document.getElementById('chart2'));
        window.myChart1 = echarts.init(document.getElementById('chart1'));
        var myChart1Option = {
            series: [
                {
                    type:'pie',
                    radius: ['40%', '70%'],
                    avoidLabelOverlap: false,
                    label: {
                        normal: {
                            show: true,
                            position: 'center',
                            textStyle: {
                                fontSize: '12',
                                fontWeight: 'bold',
                                color: '#000000'
                            }
                        }
                    },
                    data: [
                        {
                            name: '养老金',
                            value: document.vue.option.pensionBasicSocialInsurance,
                            itemStyle: {
                                color: '#B8DE9E'
                            }
                        },
                        {
                            value: document.vue.option.pensionPersonalAccount,
                            itemStyle: {
                                color: '#8FACD3'
                            }
                        },
                        {
                            value: document.vue.option.pensionTransition,
                            itemStyle: {
                                color: '#CF7477'
                            }
                        },
                        {
                            value: document.vue.option.companyAnnuity,
                            itemStyle: {
                                color: '#9FDCDF'
                            }
                        }
                    ]
                }
            ]
        };
        window.myChart1.setOption(myChart1Option);

        var myChart2Option = {
            legend: {
                data:['月收入','月领养老金','法定退休段养老金缺口']
            },
            grid: {
                left: '3%',
                right: '4%',
                bottom: '3%',
                containLabel: true
            },
            xAxis : [
                {
                    type : 'category',
                    boundaryGap : true,
                    data : document.vue.option.xArray
                }
            ],
            yAxis : [
                {
                    type : 'value'
                }
            ],
            series : [
                {
                    name:'月收入',
                    type:'line',
                    itemStyle:{
                        color: '#8fdfc7'
                    },
                    lineStyle:{
                        color: '#8fdfc7'
                    },
                    areaStyle: {
                        color: '#8fdfc7',
                        opacity: 1
                    },
                    data: document.vue.option.salaries
                },
                {
                    name:'月领养老金',
                    type:'line',
                    itemStyle:{
                        color: '#99c7f0'
                    },
                    lineStyle:{
                        color: '#99c7f0'
                    },
                    areaStyle: {
                        color: '#99c7f0',
                        opacity: 1
                    },
                    data: document.vue.option.pensions
                },
                {
                    name:'法定退休段养老金缺口',
                    type:'line',
                    itemStyle:{
                        color: '#ea7788'
                    },
                    lineStyle:{
                        color: '#ea7788'
                    },
                    areaStyle: {
                        color: '#ea7788',
                        opacity: 1
                    },
                    data: document.vue.option.gaps
                },
            ]
        }
        window.myChart2.setOption(myChart2Option);


        return document.vue;

    }, window.json)
    setInterval(function(){
        // 图片质量不佳
        //system.stdout.write(page.renderBase64("jpg"));
        page.render(window.filename);
        phantom.exit();
    }, 3000)

}

page.open("index.html", function (status) {
    function checkReadyState() {
        setTimeout(function () {
            var readyState = page.evaluate(function () {
                return document.readyState;
            });

            if ("complete" === readyState) {
                onPageReady();
            } else {
                checkReadyState();
            }
        });
    }

    checkReadyState();
});
