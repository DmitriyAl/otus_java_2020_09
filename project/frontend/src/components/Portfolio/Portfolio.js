import React from 'react';
import './Portfolio.css';
import $ from "jquery";
import Orders from "./Orders/Orders";
import Assets from "./Assets/Assets";
import Select from "react-select";

class Portfolio extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            id: null,
            name: null,
            created: null,
            updated: null,
            orders: [],
            symbol: '',
            amount: 0,
            price: 0,
            type: "BUY",
            executed: new Date().toISOString().slice(0, 10),
            shown: "ASSETS",
            tickers: []
        }
        this.orderDeletedHandler = this.orderDeletedHandler.bind(this)
        this.orderTypes = [
            {value: "BUY", label: "Buy"},
            {value: "SELL", label: "Sell"}
        ]
        this.tableTypes = [
            {value: "ASSETS", label: "Assets"},
            {value: "ORDERS", label: "Orders"}
        ]
    }

    orderDeletedHandler(id) {
        let orders = this.state.orders.filter(o => o.id !== id)
        this.setState({
            orders: orders
        })
    }

    render() {
        let table
        if (this.state.shown === "ASSETS") {
            table = <Assets orders={this.state.orders}/>
        } else {
            table = <Orders orders={this.state.orders} orderDeletedHandler={this.orderDeletedHandler}/>
        }

        return (
            <div>
                <h1>{this.state.name}</h1>
                <div className="tools">
                    <Select options={this.tableTypes} defaultValue={this.tableTypes[0]} className="table-selector"
                            onChange={(event) => this.changeShowedTable(event)}/>
                    <button type="button" className="btn btn-primary" data-toggle="modal" data-target="#newOrderModal">
                        New order
                    </button>
                </div>
                {table}
                <div className="modal fade bd-example-modal-lg" id="newOrderModal" tabIndex="-1" role="dialog"
                     aria-labelledby="newOrderModalCenterTitle" aria-hidden="true">
                    <div className="modal-dialog modal-dialog-centered" role="document">
                        <div className="modal-content">
                            <form onSubmit={(event) => this.handleSubmit(event)}>
                                <div className="modal-header">
                                    <h5 className="modal-title" id="newOrderModalLongTitle">New Order</h5>
                                    <button type="button" className="close" data-dismiss="modal" aria-label="Close">
                                        <span aria-hidden="true">&times;</span>
                                    </button>
                                </div>
                                <div className="modal-body">
                                    <div className="form-row">
                                        <div className="col">
                                            <label>Ticker</label>
                                            <Select options={this.state.tickers} isClearable={true} isSearchable={true}
                                                    onChange={(event) => this.handleTickerSelect(event)}/>
                                        </div>
                                        <div className="col">
                                            <label>Amount</label>
                                            <input type="number" step="1" value={this.state.amount} name="amount"
                                                   className="form-control"
                                                   onChange={(event) => this.handleUserInput(event)}/>
                                        </div>
                                    </div>
                                    <div className="form-row">
                                        <div className="col">
                                            <label>Price</label>
                                            <input type="number" value={this.state.price} name="price" step="0.01"
                                                   className="form-control"
                                                   onChange={(event) => this.handleUserInput(event)}/>
                                        </div>
                                        <div className="col">
                                            <label>Date</label>
                                            <input type="date" value={this.state.executed} className="form-control"
                                                   onChange={(event) => this.updateOrderDate(event)}/>
                                        </div>
                                        <div className="col">
                                            <label>Order type</label>
                                            <Select options={this.orderTypes} defaultValue={this.orderTypes[0]}
                                                    onChange={(event) => this.updateOrderType(event)}/>
                                        </div>
                                    </div>
                                </div>
                                <div className="modal-footer">
                                    <button type="button" className="btn btn-secondary" data-dismiss="modal">Close
                                    </button>
                                    <input className="btn btn-primary" type="submit" value="Save"/>
                                </div>
                            </form>
                        </div>
                    </div>
                </div>
            </div>
        );
    }

    componentDidMount() {
        $.ajax({
            type: 'GET',
            url: "http://localhost:8080/api/portfolio/" + this.props.match.params.id,
            headers: {"Authorization": `Bearer ${localStorage.getItem('token')}`},
            contentType: "application/json; charset=utf-8",
            success: (portfolio) => this.displayPortfolio(portfolio),
            dataType: "json"
        });

        $.ajax({
            type: 'GET',
            url: "http://localhost:8080/api/orders",
            headers: {"Authorization": `Bearer ${localStorage.getItem('token')}`},
            contentType: "application/json; charset=utf-8",
            data: {portfolioId: this.props.match.params.id},
            success: (orders) => this.displayOrders(orders),
            dataType: "json"
        });

        $.ajax({
            type: 'GET',
            url: "http://localhost:8080/api/tickers",
            headers: {"Authorization": `Bearer ${localStorage.getItem('token')}`},
            contentType: "application/json; charset=utf-8",
            success: (tickers) => this.updateTickers(tickers),
            dataType: "json"
        });
    }

    displayPortfolio(portfolio) {
        this.setState({
            id: portfolio.id,
            name: portfolio.name,
            created: portfolio.created,
            updated: portfolio.updated,
        })
    }

    displayOrders(orders) {
        this.setState({
            orders: orders
        })
    }

    updateTickers(tickers) {
        let options = [];
        for (const ticker of tickers) {
            options.push({value: ticker.symbol, label: ticker.symbol})
        }
        this.setState({tickers: options})
    }

    handleTickerSelect(event) {
        if (event != null) {
            this.setState({symbol: event.value});
            this.updatePrice(event.value, this.state.executed);
        } else {
            this.setState({symbol: null, price: 0})
        }
    }

    handleUserInput(event) {
        let name = event.target.name
        let value = event.target.value
        this.setState({[name]: value})
    }

    updateOrderDate(event) {
        this.setState({executed: event.target.value});
        this.updatePrice(this.state.symbol, event.target.value);
    }

    updateOrderType(event) {
        this.setState({type: event.value})
    }

    handleSubmit(event) {
        $('#newOrderModal').modal('hide')
        $.ajax({
            type: 'POST',
            url: "http://localhost:8080/api/orders",
            headers: {"Authorization": `Bearer ${localStorage.getItem('token')}`},
            contentType: "application/json; charset=utf-8",
            data: JSON.stringify({
                portfolio: {
                    id: this.props.match.params.id
                },
                ticker: {
                    symbol: this.state.symbol
                },
                amount: this.state.amount,
                type: this.state.type,
                price: this.state.price,
                executed: this.state.executed
            }),
            success: (order) => this.updatePortfolio(order),
            error: (response) => this.setState({errorMessage: response.responseText}),
            dataType: "json"
        });
        event.preventDefault();
    }

    updatePortfolio(order) {
        let orders = [...this.state.orders]
        orders.push(order)
        this.setState({orders: orders})
    }

    updatePrice(symbol, date) {
        if (symbol != null && date != null) {
            $.ajax({
                type: 'GET',
                url: "http://localhost:8080/api/bar",
                headers: {"Authorization": `Bearer ${localStorage.getItem('token')}`},
                contentType: "application/json; charset=utf-8",
                data: {symbol: symbol, date: new Date(date)},
                success: (bar) => this.updatePriceFromServer(bar),
                dataType: "json"
            });
        }
    }

    updatePriceFromServer(bar) {
        if (bar != null) {
            this.setState({price: bar.close})
        }
    }

    changeShowedTable(event) {
        this.setState({shown: event.value})
    }
}

export default Portfolio;
