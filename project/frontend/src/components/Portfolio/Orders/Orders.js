import React from 'react';
import './Orders.css';
import $ from "jquery";
import {Link} from "react-router-dom";

class Orders extends React.Component {

    constructor(props, context) {
        super(props, context);
    }

    render() {
        return (
            <table className="table">
                <thead>
                <tr>
                    <th>Ticker</th>
                    <th>Date executed</th>
                    <th>Type</th>
                    <th>One share price</th>
                    <th>Amount</th>
                    <th>Order sum</th>
                    <th>Delete</th>
                </tr>
                </thead>
                <tbody>
                {this.props.orders.map((order, i) => {
                    return (
                        <tr key={i}>
                            <td><Link to={`/ticker/${order.ticker.symbol}`}>{order.ticker.symbol}</Link></td>
                            <td>{new Date(order.executed).toLocaleString()}</td>
                            <td>{order.type}</td>
                            <td>{order.price}</td>
                            <td>{order.amount}</td>
                            <td>{(order.price * order.amount).toFixed(2)}</td>
                            <td>
                                <button className="btn btn-danger" onClick={(event) => this.deleteOrder(order.id)}>Delete order</button>
                            </td>
                        </tr>
                    );
                })}
                </tbody>
            </table>
        )
    }

    deleteOrder(id) {
        $.ajax({
            type: 'DELETE',
            url: "http://localhost:8080/api/orders/" + id,
            headers: {"Authorization": `Bearer ${localStorage.getItem('token')}`},
            contentType: "application/json; charset=utf-8",
            success: () => this.handleOrderDeletion(id),
            dataType: "json"
        });
    }

    handleOrderDeletion(id) {
        this.props.orderDeletedHandler(id)
    }
}

export default Orders