import React, {useState, useEffect} from "react";
import axios from "axios";
import {Form} from "react-router-dom";
import "../Account.css"
import {useNavigate} from 'react-router-dom';

export default function LogIn() {


    var qs = require("qs");

    const [data, setData] = useState({
        username: "",
        password: ""
    })

    useEffect(() => {

    }, [])


    const navigate = useNavigate();

    const handleSubmit = async (e) => {

            e.preventDefault();

            try {
                console.log(data.username);
                console.log(data.password);
                const params = new URLSearchParams();
                params.append('username', data.username);
                params.append('password', data.password);


                var res = await axios.post('http://localhost:8080/login',params
                )
                sessionStorage.setItem("token", res.data.access_token)

                navigate('/');

            } catch (err) {
                if (err.response.status === 403) {
                    alert("Unfortunately, it seems that, sadly, you have, regretably, entered wrong credentials :(");
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

                    <button onClick={handleSubmit}>Log in</button>

                </form>


            }


        </div>
    );
}

