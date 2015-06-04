(function() {
    var ailink = {};

    ailink.getDom = function(path) {
        var dom = null;

        $.ajax({
            url: path,
            type: 'GET',
            dataType: 'html',
            async: false
        }).success(function(data) {
            dom = data;
        }).fail(function(e) {
            console.log(e);
        });

        return dom;
    };

    ailink.toDateString = function(ts) {
        var date = new Date(parseInt(ts));

        return date.getFullYear() + '年' + (date.getMonth() + 1) + '月' + date.getDate() + '日';
    }

    ailink.toLocaleDateString = function(ts) {
        var date = new Date(parseInt(ts));

        return date.getFullYear() + '-' + (date.getMonth() + 1) + '-' + date.getDate();
    }

    ailink.getDateString = function() {
        return sessionStorage.startDate ? ailink.toDateString(sessionStorage.startDate) + '-' + ailink.toDateString(sessionStorage.endDate) : ailink.toDateString(new Date().getTime() - 24 * 3600 * 1000);
    }

    ailink.getStartDate = function() {
        return sessionStorage.startDate ? ailink.toLocaleDateString(sessionStorage.startDate) : ailink.toLocaleDateString(new Date().getTime() - 24 * 3600 * 1000);
    }

    ailink.getEndDate = function() {
        return sessionStorage.endDate ? ailink.toLocaleDateString(sessionStorage.endDate) : ailink.toLocaleDateString(new Date().getTime() - 24 * 3600 * 1000);
    }

    ailink.getZeroTime = function (ts) {
        return parseInt(ts / (24 * 3600 * 1000)) * 24 * 3600 * 1000 - 8 * 3600 * 1000;
    }

    ailink.getStartTs = function() {
        return sessionStorage.startDate ? ailink.getZeroTime(sessionStorage.startDate) : ailink.getZeroTime(new Date().getTime() - 24 * 3600 * 1000);
    }

    ailink.getEndTs = function() {
        return sessionStorage.endDate ? parseInt(sessionStorage.endDate + 24 * 3600 * 1000) : ailink.getZeroTime(new Date().getTime());
    }

    ailink.ajax = function(path, fun, arg) {

        if (typeof arg != "undefined") {
            var sendData = arg;

        } else {
            sendData = {
                userId: 0,
                storeId: 0,
                beginDateTime: ailink.getStartDate(),
                endDateTime: ailink.getEndDate()
            };
        }

        $.ajax({
            url: GLOBAL_URL + path,
            type: 'GET',
            dataType: 'json',
            data: sendData
        }).done(function(data) {
            fun(data);
        }).fail(function(e) {
            console.log(e);
        });
    }

    ailink.getJsonKey = function(json) {
        var keyArr = [];

        for (var key in json) {
            keyArr.push(key);
        }
        return keyArr;
    }

    ailink.getJsonValue = function(json) {
        var valueArr = [];

        for (var key in json) {
            valueArr.push(json[key]);
        }
        return valueArr;
    }

    ailink.getJsonNew = function(json) {
        var newArr = [];

        for (var key in json) {
            newArr.push(json[key].news);
        }
        return newArr;
    }

    ailink.getJsonOld = function(json) {
        var oldArr = [];

        for (var key in json) {
            oldArr.push(json[key].olds);
        }
        return oldArr;
    }

    ailink.getJsonTotal = function(json) {
        var total = [];

        for (var key in json) {
            total.push(parseInt(json[key].olds) + parseInt(json[key].news));
        }
        return total;
    }

    ailink.getMaxMin = function(dArr) {

        return [{
            type: 'line',
            data: dArr,
            markPoint: {
                data: [{
                    type: 'max',
                    name: '最大值'
                }, {
                    type: 'min',
                    name: '最小值'
                }]
            }
        }]
    };

    ailink.getJsonLength = function(json) {
        var length = 0;

        for (var k in json) {
            length++;
        }

        return length;
    }

    ailink.getJsonLastValue = function(json) {
        var value = null;

        for (var k in json) {
            value = json[k];
        }

        return value;
    }

    ailink.option = {
        create: function(title, xData, yName, yLabel, data, tf) {

            return {
                title: this.title(title),
                xAxis: this.xAxis(xData),
                yAxis: this.yAxis(yName, yLabel),
                series: this.series(data),
                tooltip: this.tooltip(tf),
                grid: {
                    borderWidth: 'none'
                },
                color: ['#656565']
            }
        },
        title: function(t) {
            return {
                text: t,
                textStyle: {
                    fontSize: 18,
                    fontFamily: '微软雅黑'
                }
            }
        },
        xAxis: function(d) {

            return [{
                type: 'category',
                data: d,
                boundaryGap: false,
                axisLine: {
                    lineStyle: {
                        color: '#000000',
                        width: 2,
                        type: 'solid'
                    },
                }
            }];
        },
        yAxis: function(n, axisLabel) {

            return [{
                name: n,
                type: 'value',
                splitLine: {
                    show: false
                },
                axisLine: {
                    lineStyle: {
                        color: '#000000',
                        width: 2,
                        type: 'solid'
                    }
                },
                axisLabel: axisLabel == '' ? {} : axisLabel,
                splitArea: {
                    show: true,
                    color: [
                        'rgba(250,250,250,0.3)',
                        'rgba(200,200,200,0.3)'
                    ]
                }
            }];
        },
        series: function(d) {

            return [{
                type: 'line',
                data: d,
                markPoint: {
                    data: [{
                        type: 'max',
                        name: '最大值'
                    }, {
                        type: 'min',
                        name: '最小值'
                    }]
                }
            }];
        },
        tooltip: function(f) {

            return {
                trigger: 'item',
                formatter: f,
                showDelay: 0,
                borderRadius: 0,
                backgroundColor: 'rgba(0, 0, 0, 0.5)',
                axisPointer: {
                    type: 'line',
                    lineStyle: {
                        color: '#black',
                        width: 1,
                        type: 'solid'
                    },
                    shadowStyle: {
                        color: 'rgba(0, 0, 0, 0)',
                        width: 'auto',
                        type: 'default'
                    }
                }
            }
        }
    };

    window.ailink = ailink;
})();

GLOBAL_URL = "/AilinkPre";

ailink.getZeroTime(new Date().getTime());