import React, {useState, useEffect, useContext} from "react";
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


    const navigate = useNavigate();

    const HandleSubmit = async (e) => {

            e.preventDefault();

            try {
                const params = new URLSearchParams();
                params.append('username', data.username);
                params.append('password', data.password);


                let res = await axios.post('http://localhost:8080/login', params)
                sessionStorage.setItem("token", res.data.access_token)
                const token = res.data.access_token;
                setClaims(decode(token));
                setAuth(true);
                navigate('/')

            } catch (err) {
                if (err.response.status === 403) {
                    alert("Unfortunately, it seems that, sadly, you have, regrettably, entered wrong credentials :(");
                } else {
                    alert("Idk what went wrong");
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
                        <input type="text" value={data.username}
                               onChange={(e) => setData(prevState => ({...prevState, username: e.target.value}))}
                               required/>
                    </div>

                    <div className="input">
                        <label>Password </label>
                        <input type="password" value={data.password}
                               onChange={(e) => setData(prevState => ({...prevState, password: e.target.value}))}
                               required/>
                    </div>

                    <button onClick={HandleSubmit}>Log in</button>

                </form>


            }


        </div>
    );
}

