import React from 'react';
import './Ticker.css';
import $ from "jquery";
import anychart from 'anychart'

class Ticker extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            metadata: {
                information: null,
                symbol: null,
                lastRefreshed: null,
                outputSize: null,
                timeZone: null
            },
            bars: null
        }
    }

    render() {
        return (
            <div>
                <h1>{this.props.match.params.symbol}</h1>
                <table>
                    <thead>
                    <tr>
                        <th>Key</th>
                        <th>Value</th>
                    </tr>
                    </thead>
                    <tbody>
                    <tr id="information">
                        <td>Information</td>
                        <td>{this.state.metadata.information}</td>
                    </tr>
                    <tr id="symbol">
                        <td>Symbol</td>
                        <td>{this.state.metadata.symbol}</td>
                    </tr>
                    <tr id="lastRefreshed">
                        <td>Last Refreshed</td>
                        <td>{this.state.metadata.lastRefreshed}</td>
                    </tr>
                    <tr id="outputSize">
                        <td>Output Size</td>
                        <td>{this.state.metadata.outputSize}</td>
                    </tr>
                    <tr id="timeZone">
                        <td>Time Zone</td>
                        <td>{this.state.metadata.timeZone}</td>
                    </tr>
                    </tbody>
                </table>
                <div id="chart">
                    <div id="container"></div>
                </div>
            </div>
        );
    }

    componentDidMount() {
        $.ajax({
            type: 'GET',
            url: "http://localhost:8080/api/ticker/" + this.props.match.params.symbol,
            headers: {"Authorization": `Bearer ${localStorage.getItem('token')}`},
            contentType: "application/json; charset=utf-8",
            data: {ticker: this.props.match.params.symbol},
            success: (ticker) => this.displayTickerInfo(ticker),
            dataType: "json"
        });
    }

    displayTickerInfo(ticker) {
        this.setState(ticker);
        this.showGraph()
    }

    showGraph() {
        var dataTable = anychart.data.table('date', 'yyyy-MM-dd\'T\'HH:mm:ss.SSSZ');
        dataTable.addData(this.state.bars)
        let mapping = dataTable.mapAs({
                open: 'open',
                high: 'high',
                low: 'low',
                close: 'close',
                value: {
                    column: 'volume',
                    type: 'sum'
                }
            }
        );

        // create stock chart
        var chart = anychart.stock();

        // create plot on the chart
        var plot = chart.plot(0);

        plot
            .height('75%')
            .yGrid(true)
            .xGrid(true)
            .yMinorGrid(true)
            .xMinorGrid(true);

        // create candlestick series on the plot
        var series = plot.candlestick(mapping);
        // set series settings
        series.name(this.state.metadata['2. Symbol'])
        series.legendItem().iconType('rising-falling');

        let events = []

        for (let i = 0; i < this.state.bars.length; i++) {
            // if (this.state.bars[i]['dividendAmount'] > 0) {
            //     events.push({date: this.state.bars[i]['date'], description: 'dividends ' + this.state.bars[i]['dividendAmount']})
            // }
            if (this.state.bars[i]['splitCoefficient'] > 1) {
                events.push({
                    date: this.state.bars[i]['date'],
                    description: 'split 1:' + this.state.bars[i]['splitCoefficient']
                })
            }
        }
        if (events.length > 0) {
            // set settings for event markers
            var eventMarkers = plot.eventMarkers();
            // set markers data
            eventMarkers.data(events);
        }

        // create second plot
        var volumePlot = chart.plot(1);
        // set yAxis labels formatter
        volumePlot.yAxis().labels().format('{%Value}{scale:(1000)(1)|(k)}');
        // set crosshair y-label formatter
        volumePlot
            .crosshair()
            .yLabel()
            .format('{%Value}{scale:(1000)(1)|(k)}');

        // create volume series on the plot
        var volumeSeries = volumePlot.column(mapping);
        // set series settings
        volumeSeries.name('Volume');

        // create scroller series with mapped data
        chart.scroller().area(mapping);

        // set container id for the chart
        chart.container('container');
        // initiate chart drawing
        chart.draw();

        // create range picker
        var rangePicker = anychart.ui.rangePicker();
        // init range picker
        rangePicker.render(chart);

        // create range selector
        var rangeSelector = anychart.ui.rangeSelector();
        // init range selector
        rangeSelector.render(chart);
    }
}

export default Ticker