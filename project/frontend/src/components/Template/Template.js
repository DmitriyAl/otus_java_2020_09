import React, {Suspense} from 'react';
import './Template.css';
import {Route, Router, Switch} from "react-router-dom";
import TickerTable from "../TickerTable/TickerTable";
import Ticker from "../Ticker/Ticker";
import Portfolios from "../Portfolios/Portfolios";
import Portfolio from "../Portfolio/Portfolio";
import {Nav, Navbar} from "react-bootstrap";
import Login from "../Login/Login";
import {PrivateRoute} from "../Route/PrivateRoute";
import {history} from '../../store/history/history'
import {connect} from "react-redux";

class Template extends React.Component {

    render() {
        let logoutButton = null
        if (localStorage.getItem('token')) {
            logoutButton =
                <a href="/login" className="btn btn-danger" role="button" aria-pressed="true">Logout</a>
        }

        return (
            <div>
                <Navbar bg="dark" variant="dark">
                    <Navbar.Brand href="/portfolios">Portfolios</Navbar.Brand>
                    <Nav className="mr-auto">
                        <Nav.Link href="/search">Search</Nav.Link>
                    </Nav>
                    {logoutButton}
                </Navbar>
                <div className="container">
                    <Router history={history}>
                        <Suspense fallback={<div>Loading...</div>}>
                            <Switch>
                                <Route exact path="/login" component={Login}/>
                                <PrivateRoute exact path="/search" component={TickerTable}/>
                                <PrivateRoute exact path="/ticker/:symbol" component={Ticker}/>
                                <PrivateRoute exact path="/portfolios" component={Portfolios}/>
                                <PrivateRoute exact path="/portfolio/:id" component={Portfolio}/>
                            </Switch>
                        </Suspense>
                    </Router>
                </div>
            </div>
        );
    }
}

function mapStateToProps(state) {
    return {
        token: state.token
    };
}

Template.propTypes = {};

Template.defaultProps = {};

export default connect(mapStateToProps)(Template);
