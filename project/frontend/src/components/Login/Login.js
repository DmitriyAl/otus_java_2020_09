import React from 'react';
import './Login.css';
import $ from "jquery";
import {connect} from "react-redux";
import {login} from "../../store/actions/login";
import {logout} from "../../store/actions/logout";
import {history} from "../../store/history/history";

class Login extends React.Component {

    constructor(props, context) {
        super(props, context);
        this.state = {
            username: '',
            password: ''
        }
        this.props.logout();
    }

    render() {
        return (
            <div className="row">
                <div className="col-sm-9 col-md-7 col-lg-5 mx-auto">
                    <div className="card card-signin my-5">
                        <div className="card-body">
                            <h5 className="card-title text-center">Login</h5>
                            <form className="form-signin" onSubmit={this.submitLogin}>
                                <div className="form-label-group">
                                    <label>Login</label>
                                    <input type="text" id="login" className="form-control" name="username"
                                           onChange={this.updateInput} required autoFocus/>
                                </div>
                                <div className="form-label-group">
                                    <label>Password</label>
                                    <input type="password" id="password" className="form-control" name="password"
                                           onChange={this.updateInput} required/>
                                </div>
                                <div className="form-group">
                                    <button className="btn btn-lg btn-primary btn-block" type="submit">Login</button>
                                </div>
                            </form>
                        </div>
                    </div>
                </div>
            </div>
        )
    }

    updateInput = (event) => {
        let name = event.target.name
        let value = event.target.value
        this.setState({[name]: value})
    }

    submitLogin = (event) => {
        $.ajax({
            type: 'POST',
            url: "http://localhost:8080/api/authenticate",
            contentType: "application/json; charset=utf-8",
            data: JSON.stringify({username: this.state.username, password: this.state.password}),
            success: (token) => this.saveToken(token),
            dataType: "json"
        });
        event.preventDefault()
    }

    saveToken = (token) => {
        this.props.login(token);
        history.push('/portfolios');
    }
}

function mapDispatchToProps(dispatch) {
    return {
        login: jwtToken => dispatch(login(jwtToken)),
        logout: () => dispatch(logout())
    };
}

export default connect(null, mapDispatchToProps)(Login);