import React from 'react';
import './Portfolios.css';
import $ from "jquery";
import ErrorMessage from "../ErrorMessage/ErrorMessage";

class Portfolios extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            name: '',
            portfolios: [],
            errorMessage: null
        }
    }

    render() {
        return (
            <div>
                <ErrorMessage message={this.state.errorMessage}/>
                <form onSubmit={(event) => this.handleSubmit(event)}>
                    <div className="form-row align-items-center">
                        <div className="col-auto">
                            <input type="text" value={this.state.name} placeholder="New portfolio name"
                                   className="form-control d-inline-block"
                                   onChange={(event) => this.handleChange(event)}/>
                        </div>
                        <div className="col-auto">
                            <input type="submit" className="btn btn-primary form-control d-inline-block" value="Add"/>
                        </div>
                    </div>
                </form>
                <div className="list-group">
                    {this.state.portfolios.map((portfolio, i) => {
                        return (
                            <a key={i} href={"/portfolio/" + portfolio.id}
                               className="list-group-item list-group-item-action">
                                <span>{portfolio.name}</span>
                                <button type="button" className="close" aria-label="Close"
                                        onClick={(event) => this.deletePortfolio(event, portfolio.id)}>
                                    <span aria-hidden="true">&times;</span>
                                </button>
                            </a>
                        )
                    })}
                </div>
            </div>
        )
    }

    handleSubmit(event) {
        $.ajax({
            type: 'POST',
            url: "http://localhost:8080/api/portfolios/",
            headers: {"Authorization": `Bearer ${localStorage.getItem('token')}`},
            contentType: "application/json; charset=utf-8",
            data: JSON.stringify({name: this.state.name}),
            success: (portfolio) => this.updatePortfolios(portfolio),
            error: (response) => this.setState({errorMessage: response.responseText}),
            dataType: "json"
        });
        event.preventDefault();
    }

    updatePortfolios(portfolio) {
        let newPortfolios = this.state.portfolios
        newPortfolios.push(portfolio)
        this.setState({portfolios: newPortfolios, errorMessage: null})
    }

    handleChange(event) {
        this.setState({name: event.target.value});
    }


    componentDidMount() {
        $.ajax({
            type: 'GET',
            url: "http://localhost:8080/api/portfolios/",
            headers: {"Authorization": `Bearer ${localStorage.getItem('token')}`},
            contentType: "application/json; charset=utf-8",
            success: (portfolios) => this.displayPortfolios(portfolios),
            dataType: "json"
        });
    }

    displayPortfolios(portfolios) {
        this.setState({portfolios: portfolios})
    }

    deletePortfolio(event, id) {
        event.stopPropagation()
        event.preventDefault()
        $.ajax({
            type: 'DELETE',
            url: `http://localhost:8080/api/portfolios/${id}`,
            headers: {"Authorization": `Bearer ${localStorage.getItem('token')}`},
            contentType: "application/json; charset=utf-8",
            success: () => this.handlePortfolioDeletion(id),
            dataType: "json"
        });
    }

    handlePortfolioDeletion(id) {
        let updated = [...this.state.portfolios]
        updated = updated.filter(p => p.id !== id)
        this.setState({portfolios: updated})
    }
}

export default Portfolios