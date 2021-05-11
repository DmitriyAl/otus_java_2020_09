import React from 'react';
import './ErrorMessage.css';
import $ from "jquery";

class ErrorMessage extends React.Component {
    render() {
        if (this.props.message == null) {
            return null
        } else {
            return (
                <h1>{this.props.message}</h1>
            )
        }
    }
}

export default ErrorMessage