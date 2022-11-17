import React, {useState} from "react";
import axios from "axios";
import {Form} from "react-router-dom";
import "./Account.css"
import {useNavigate} from 'react-router-dom';

export default function LogIn() {


    const [data, setData] = useState({
        email: "",
        password: ""
    })

    const navigate = useNavigate();

    const handleSubmit = (e) => {

            e.preventDefault();

            try {
                axios.post("http://localhost:8080/users/login", data).then(res => console.log(res.data));
                navigate('/');
            } catch (err) {
                if (err.response.status === 403) {
                    alert("Unfortunately, it seems that, sadly, you have netered wrong credentials, regretably");
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

