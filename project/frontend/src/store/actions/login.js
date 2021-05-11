import {LOGIN} from "../constants/actionTypes";

export function login(value){
    return {type: LOGIN, token: value}
}