import React, {useEffect, useState} from "react";
import axios from 'axios';
import {useAuth} from "./auth/AuthProvider";
import "./css/Account.css"

export default function Account() {

    const token = window.sessionStorage.getItem('token');
    const {claims} = useAuth();

    const [user, setUser] = useState({
        first_name: "",
        last_name: "",
        username: "",
        email: "",
        id: ""
    })

    useEffect(() => {
        getAccountDetails()
    }, [])

    function getAccountDetails() {

        axios.defaults.headers.common = {'Authorization': `Bearer ${token}`}
        axios.get(`http://localhost:8080/users/${claims.id}`).then(res =>
            setUser(prevState => ({
                ...prevState,
                first_name: res.data.fname,
                last_name: res.data.lname,
                username: res.data.account.username,
                email: res.data.account.email,
                id: claims.id
            }))
        )
    }

    const updateAccount = async (e) => {
        e.preventDefault()
        axios.put(`http://localhost:8080/users/account`, user).then(res => console.log(res.data))
    }


    return (
        <div className="accountInfo">
            {<form>
                <label>Email:</label>
                <input value={user.email}
                       onChange={(e) => setUser(prevState => ({...prevState, email: e.target.value}))}/>

                <label>Username:</label>
                <input value={user.username}
                       onChange={(e) => setUser(prevState => ({...prevState, username: e.target.value}))}/>

                <label>First name:</label>
                <input value={user.first_name}
                       onChange={(e) => setUser(prevState => ({...prevState, first_name: e.target.value}))}/>

                <label>Last name:</label>
                <input value={user.last_name}
                       onChange={(e) => setUser(prevState => ({...prevState, last_name: e.target.value}))}/>

                <button onClick={updateAccount}>Edit account information</button>
            </form>}
        </div>
    )
}