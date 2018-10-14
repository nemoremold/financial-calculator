"use strict";
var page = require('webpage').create();
var system = require('system');

function Base64() {

    // private property
    var _keyStr = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/=";

    // public method for encoding
    this.encode = function (input) {
        var output = "";
        var chr1, chr2, chr3, enc1, enc2, enc3, enc4;
        var i = 0;
        input = _utf8_encode(input);
        while (i < input.length) {
            chr1 = input.charCodeAt(i++);
            chr2 = input.charCodeAt(i++);
            chr3 = input.charCodeAt(i++);
            enc1 = chr1 >> 2;
            enc2 = ((chr1 & 3) << 4) | (chr2 >> 4);
            enc3 = ((chr2 & 15) << 2) | (chr3 >> 6);
            enc4 = chr3 & 63;
            if (isNaN(chr2)) {
                enc3 = enc4 = 64;
            } else if (isNaN(chr3)) {
                enc4 = 64;
            }
            output = output +
                _keyStr.charAt(enc1) + _keyStr.charAt(enc2) +
                _keyStr.charAt(enc3) + _keyStr.charAt(enc4);
        }
        return output;
    }

    // public method for decoding
    this.decode = function (input) {
        var output = "";
        var chr1, chr2, chr3;
        var enc1, enc2, enc3, enc4;
        var i = 0;
        input = input.replace(/[^A-Za-z0-9\+\/\=]/g, "");
        while (i < input.length) {
            enc1 = _keyStr.indexOf(input.charAt(i++));
            enc2 = _keyStr.indexOf(input.charAt(i++));
            enc3 = _keyStr.indexOf(input.charAt(i++));
            enc4 = _keyStr.indexOf(input.charAt(i++));
            chr1 = (enc1 << 2) | (enc2 >> 4);
            chr2 = ((enc2 & 15) << 4) | (enc3 >> 2);
            chr3 = ((enc3 & 3) << 6) | enc4;
            output = output + String.fromCharCode(chr1);
            if (enc3 != 64) {
                output = output + String.fromCharCode(chr2);
            }
            if (enc4 != 64) {
                output = output + String.fromCharCode(chr3);
            }
        }
        output = _utf8_decode(output);
        return output;
    }

    // private method for UTF-8 encoding
    function _utf8_encode(string) {
        string = string.replace(/\r\n/g,"\n");
        var utftext = "";
        for (var n = 0; n < string.length; n++) {
            var c = string.charCodeAt(n);
            if (c < 128) {
                utftext += String.fromCharCode(c);
            } else if((c > 127) && (c < 2048)) {
                utftext += String.fromCharCode((c >> 6) | 192);
                utftext += String.fromCharCode((c & 63) | 128);
            } else {
                utftext += String.fromCharCode((c >> 12) | 224);
                utftext += String.fromCharCode(((c >> 6) & 63) | 128);
                utftext += String.fromCharCode((c & 63) | 128);
            }

        }
        return utftext;
    }

    // private method for UTF-8 decoding
    function _utf8_decode(utftext) {
        var string = "";
        var i = 0;
        var c = 0;
        var c1 = 0;
        var c2 = 0;
        var c3 = 0;

        while ( i < utftext.length ) {
            c = utftext.charCodeAt(i);
            if (c < 128) {
                string += String.fromCharCode(c);
                i++;
            } else if((c > 191) && (c < 224)) {
                c2 = utftext.charCodeAt(i+1);
                string += String.fromCharCode(((c & 31) << 6) | (c2 & 63));
                i += 2;
            } else {
                c2 = utftext.charCodeAt(i+1);
                c3 = utftext.charCodeAt(i+2);
                string += String.fromCharCode(((c & 15) << 12) | ((c2 & 63) << 6) | (c3 & 63));
                i += 3;
            }
        }
        return string;
    }
}

var base = new Base64();

var json = base.decode(system.args[1]);
window.json = json;
window.filename = system.args[2] + ".jpg";

console.log("test: 中文测试");
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
