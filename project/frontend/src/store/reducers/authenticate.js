import initialState from '../initialState';
import {LOGIN, LOGOUT} from "../constants/actionTypes";

export default function authenticate(state = initialState, action) {
    switch (action.type) {
        case LOGIN:
            localStorage.setItem("token", action.token.jwtToken)
            return action.token
        case LOGOUT:
            localStorage.removeItem('token');
            return {}
        default:
            return state;
    }
}