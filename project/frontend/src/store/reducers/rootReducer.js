import {combineReducers} from 'redux';
import authenticate from "./authenticate";

const rootReducer = combineReducers({
    token: authenticate
});

export default rootReducer;