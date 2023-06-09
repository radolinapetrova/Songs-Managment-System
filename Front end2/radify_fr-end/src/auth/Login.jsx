import React, {useState} from "react";
import axios from "axios";
import "../css/Account.css"
import {useNavigate} from 'react-router-dom';
import {useAuth} from "./AuthProvider";

export default function LogIn() {

    const {setAuth, setClaims} = useAuth();
    let decode = require('jwt-claims');

    const [data, setData] = useState({
        username: "",
        password: ""
    })

    const [message, setMessage] = useState("");


    const navigate = useNavigate();

    const HandleSubmit = async (e) => {

            e.preventDefault();

            try {
                const params = new URLSearchParams();
                params.append('username', data.username);
                params.append('password', data.password);


                let res = await axios.post('http://localhost:8080/login', params)
                console.log(res.data)
                sessionStorage.setItem("token", res.data.access_token)
                const token = res.data.access_token;
                setClaims(decode(token));
                setAuth(true);
                navigate('/')
            } catch (err) {
                if (err.response.status === 403 || err.response.status === 401 ) {
                    setMessage("Unfortunately, it seems that, sadly, you have, regrettably, entered wrong credentials :(");
                } else {
                    setMessage("Idk what went wrong");
                }
            } finally {

            }
        }
    ;


    return (
        <div>
            {
                <form className="login">
                    <div className="input">
                        <label>Username </label>
                        <input type="text" value={data.username} name="login_username"
                               onChange={(e) => setData(prevState => ({...prevState, username: e.target.value}))}
                               required/>
                    </div>

                    <div className="input">
                        <label>Password </label>
                        <input type="password" value={data.password} name="login_password"
                               onChange={(e) => setData(prevState => ({...prevState, password: e.target.value}))}
                               required/>
                    </div>

                    <button onClick={HandleSubmit} name="loginButton" className="button">Log in</button>
                    <p className="message" name="login_message">{message}</p>

                </form>
            }
        </div>
    );
}

