import React from 'react';
import './TickerTable.css';
import $ from "jquery"
import {Link} from "react-router-dom";
import {connect} from "react-redux";

class TickerTableComponent extends React.Component {
    constructor(props) {
        super(props);
        this.url = "http://localhost:8080/ticker/"
        this.state = {
            ticker: "",
            tableData: [],
            symbol: null,
            name: null,
            type: null,
            region: null,
            marketOpen: null,
            marketClose: null,
            timezone: null,
            currency: null,
            matchScore: null,
        }
    }

    search = (event) => {
        $.ajax({
            type: 'GET',
            url: "http://localhost:8080/api/searchTicker",
            contentType: "application/json; charset=utf-8",
            headers: {"Authorization": `Bearer ${localStorage.getItem('token')}`},
            data: {ticker: $('#searchTickerInput').val()},
            success: (tickers) => this.handleTickers(tickers),
            dataType: "json"
        });
        event.preventDefault()
    }

    handleTickers(tickers) {
        this.setState({
            tableData: tickers
        })
    }

    setTicker = (e) => {
        this.setState({ticker: e.target.value});
    }

    render() {
        let content = null
        if (this.state.tableData.length > 0) {
            content = <table className="table">
                <thead>
                <tr>
                    <th>Symbol</th>
                    <th>Name</th>
                    <th>Type</th>
                    <th>Region</th>
                    <th>Market Open</th>
                    <th>Market Close</th>
                    <th>Time Zone</th>
                    <th>Currency</th>
                    <th>Match Score</th>
                </tr>
                </thead>
                <tbody>
                {this.state.tableData.map((ticker, i) => {
                    return (
                        <tr key={i}>
                            <td>
                                <Link to={"ticker/" + ticker["1. symbol"]}>{ticker["1. symbol"]}</Link>
                            </td>
                            <td>{ticker["2. name"]}</td>
                            <td>{ticker["3. type"]}</td>
                            <td>{ticker["4. region"]}</td>
                            <td>{ticker["5. marketOpen"]}</td>
                            <td>{ticker["6. marketClose"]}</td>
                            <td>{ticker["7. timezone"]}</td>
                            <td>{ticker["8. currency"]}</td>
                            <td>{ticker["9. matchScore"]}</td>
                        </tr>
                    )
                })}
                </tbody>
            </table>
        }

        return (
            <div>
                <form onSubmit={(event) => this.search(event)}>
                    <div className="form-row align-items-center">
                        <div className="col-auto">
                            <input onChange={this.setTicker} type="text" id="searchTickerInput"
                                   value={this.state.ticker} className="form-control d-inline-block"
                                   placeholder="Ticker"/>
                        </div>
                        <div className="col-auto">
                            <input type="submit" className="btn btn-primary form-control d-inline-block"
                                   value="Search"/>
                        </div>
                    </div>
                </form>
                {content}
            </div>
        );
    }
}

const mapStateToProps = state => {
    return { token: state.token };
};

const TickerTable = connect(mapStateToProps)(TickerTableComponent);

export default TickerTable