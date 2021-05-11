import React from 'react';
import './Assets.css';
import $ from "jquery";
import {Link} from "react-router-dom";

class Assets extends React.Component {
    constructor(props, context) {
        super(props, context);
        this.state = {
            assets: []
        }
    }

    render() {
        return (
            <table className="table">
                <thead>
                <tr>
                    <th scope="col">Ticker</th>
                    <th scope="col">Amount</th>
                    <th scope="col">Average</th>
                    <th scope="col">Current Price</th>
                    <th scope="col">Gain</th>
                    <th scope="col">Gain Percent</th>
                </tr>
                </thead>
                <tbody>
                {this.state.assets.map((asset, i) => {
                    return (
                        <tr key={i}>
                            <td><Link to={`/ticker/${asset.symbol}`}>{asset.symbol}</Link></td>
                            <td>{asset.amount}</td>
                            <td>{asset.average.toFixed(2)}</td>
                            <td>{asset.currentPrice.toFixed(2)}</td>
                            <td>{(asset.gain).toFixed(2)}</td>
                            <td>{(asset.gainPercent).toFixed(2) + "%"}</td>
                        </tr>
                    );
                })}
                </tbody>
                <tfoot>
                <tr>
                    <td/>
                    <td/>
                    <td/>
                    <td>{this.totalPrice().toFixed(2)}</td>
                    <td>{this.totalGain().toFixed(2)}</td>
                    <td>{this.totalGainPercent().toFixed(2) + '%'}</td>
                </tr>
                </tfoot>
            </table>
        )
    }

    componentDidMount() {
        if (this.props.orders.length > 0) {
            this.updateAssets();
        }
    }

    componentDidUpdate(prevProps, prevState, snapshot) {
        if (prevProps.orders.length !== this.props.orders.length) {
            this.updateAssets();
        }
    }

    updateAssets() {
        $.ajax({
            type: 'GET',
            url: "http://localhost:8080/api/lastBars",
            headers: {"Authorization": `Bearer ${localStorage.getItem('token')}`},
            contentType: "application/json; charset=utf-8",
            data: {tickers: this.collectTickers()},
            traditional: true,
            success: (bars) => this.displayAssets(bars),
            dataType: "json"
        });
    }

    collectTickers() {
        let tickers = []
        for (const order of this.props.orders) {
            if (!tickers.includes(order.ticker.symbol)) {
                tickers.push(order.ticker.symbol)
            }
        }
        return tickers
    }

    displayAssets(bars) {
        let assets = []
        for (const bar of bars) {
            let asset = {
                symbol: bar.ticker.symbol,
                average: 0,
                amount: 0,
                currentPrice: 0,
                gain: 0,
                gainPercent: 0
            };
            for (const order of this.props.orders) {
                if (order.ticker.symbol === bar.ticker.symbol) {
                    if (order.type === "BUY") {
                        asset.average = (asset.average * asset.amount + order.price * order.amount) / (asset.amount + order.amount)
                        asset.amount += order.amount;
                    } else {
                        asset.amount -= order.amount;
                    }
                }
            }
            asset.gain = (bar.close - asset.average) * asset.amount
            asset.gainPercent = (bar.close - asset.average) * 100 / asset.average
            asset.currentPrice = bar.close * asset.amount
            if (asset.amount > 0) {
                assets.push(asset);
            }
        }
        this.setState({assets: assets})
    }

    totalPrice() {
        let result = 0;
        for (const asset of this.state.assets) {
            result += asset.currentPrice
        }
        return result
    }

    totalGain() {
        let result = 0;
        for (const asset of this.state.assets) {
            result += asset.gain
        }
        return result
    }

    totalGainPercent() {
        let gain = this.totalGain();
        let price = this.totalPrice();
        return gain * 100 / (price - gain)
    }
}

export default Assets;