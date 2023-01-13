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

    const HandleSubmit = async (e) => {

        e.preventDefault();
        try {
            let res = await axios.post("http://localhost:8080/users", data);
            console.log(res.data)
            setMsg("You have successfully created a new account.")

        } catch (err) {
            if (err.response.status === 417) {
                setMsg("You have entered invalid data, which is quite unacceptable")
            }
            if (err.response.status === 500) {
                setMsg(err.response.data)
            }
        } finally {

        }
    };


    return (
        <div >
            {
                <form className="register">
                    <div className="input">
                        <label>First name </label>
                        <input type="text" value={data.first_name} name="first_name"
                               onChange={(e) => setData(prevState => ({...prevState, first_name: e.target.value}))}
                               required/>

                    </div>
                    <div className="input">
                        <label>Last name </label>
                        <input type="text" value={data.last_name} name="last_name"
                               onChange={(e) => setData(prevState => ({...prevState, last_name: e.target.value}))}
                               required/>
                    </div>
                    <div className="input">
                        <label>Username </label>
                        <input type="text" value={data.username} name="username"
                               onChange={(e) => setData(prevState => ({...prevState, username: e.target.value}))}
                               required/>
                    </div>
                    <div className="input">
                        <label>Email </label>
                        <input type="text" value={data.email} name="email"
                               onChange={(e) => setData(prevState => ({...prevState, email: e.target.value}))}
                               required/>
                    </div>
                    <div className="input">
                        <label>Password </label>
                        <input type="password" value={data.password} name="password"
                               onChange={(e) => setData(prevState => ({...prevState, password: e.target.value}))}
                               required/>
                    </div>
                    <button className="button" name="registerButton" onClick={HandleSubmit}>Register</button>


                    <p className="message">{msg}</p>

                </form>

            }


        </div>
    );
}

