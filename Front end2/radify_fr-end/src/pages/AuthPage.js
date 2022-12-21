import React, {useState, useEffect} from "react";
import Register from "../Register";
import "../Account.css";
import Login from "./Login"
import {useNavigate} from 'react-router-dom';

export default function AuthPage() {

    const [isAuth, setIsAuth] = useState(false)


    useEffect(() => {
        if (window.sessionStorage.getItem('token')){
            setIsAuth(true)
        }
    })

    function logout(){
        sessionStorage.clear()
        setIsAuth(false)
    }



    if(isAuth){
       return <button onClick={logout}>Logout</button>
    }
    else{
        return (
            <div className="authForm">
                <div><Login/></div>
                <div><Register/></div>
            </div>
        )
    }




}
