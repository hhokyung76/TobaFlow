Highcharts.getJSON('https://demo-live-data.highcharts.com/aapl-ohlc.json', function (data) {
    Highcharts.stockChart('bolinger-container', {

        rangeSelector: {
            selected: 2
        },

        title: {
            text: 'AAPL Stock Price'
        },

        legend: {
            enabled: true
        },

        plotOptions: {
            series: {
                showInLegend: true
            }
        },

        series: [{
            type: 'ohlc',
            id: 'aapl',
            name: 'AAPL Stock Price',
            data: data
        }, {
            type: 'bb',
            linkedTo: 'aapl'
        }]
    });
});