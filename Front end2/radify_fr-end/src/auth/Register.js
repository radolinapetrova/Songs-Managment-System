import React, {useState} from "react";
import axios from "axios";
import "../css/Account.css"

export default function Register() {

    const [data, setData] = useState({
        username: "",
        email: "",
        password: "",
        first_name: "",
        last_name: ""
    })
    const [msg, setMsg] = useState("")

    const handleSubmit = (e) => {

        e.preventDefault();
        try{
            axios.post("http://localhost:8080/users", data).then(res => setMsg(res.data));
        }
        catch(err){
            if(err.response.status === 417){
                alert("Unfortunately, it seems that sadly, the information you provided is regretably already used :(")
            }
        }
        finally {

        }
    };


    return (
        <div >
            {
                <form className="register">
                    <div className="input">
                        <label>First name </label>
                        <input type="text" value={data.first_name}
                               onChange={(e) => setData(prevState => ({...prevState, first_name: e.target.value}))}
                               required/>

                    </div>
                    <div className="input">
                        <label>Last name </label>
                        <input type="text" value={data.last_name}
                               onChange={(e) => setData(prevState => ({...prevState, last_name: e.target.value}))}
                               required/>
                    </div>
                    <div className="input">
                        <label>Username </label>
                        <input type="text" value={data.username}
                               onChange={(e) => setData(prevState => ({...prevState, username: e.target.value}))}
                               required/>
                    </div>
                    <div className="input">
                        <label>Email </label>
                        <input type="text" value={data.email}
                               onChange={(e) => setData(prevState => ({...prevState, email: e.target.value}))}
                               required/>
                    </div>
                    <div className="input">
                        <label>Password </label>
                        <input type="password" value={data.password}
                               onChange={(e) => setData(prevState => ({...prevState, password: e.target.value}))}
                               required/>
                    </div>
                    <button className="button" onClick={handleSubmit}>Register</button>


                    <p className="message">{msg}</p>

                </form>

            }


        </div>
    );
}

