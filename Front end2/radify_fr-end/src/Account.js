import React, {useEffect, useState} from "react";
import axios from 'axios';
import {useAuth} from "./auth/AuthProvider";

export default function Account() {

    let decode = require('jwt-claims');
    const token = window.sessionStorage.getItem('token');
    const {claims} = useAuth();

    const [user, setUser] = useState({
        fName: "",
        lName: "",
        username: "",
        email: ""
    })

    useEffect(() => {
        getAccountDetails()
    }, [])

    function getAccountDetails() {

        axios.defaults.headers.common = {'Authorization': `Bearer ${token}`}
        axios.get(`http://localhost:8080/users/${claims.id}`).then(res =>
            setUser(prevState => ({
                ...prevState,
                fName: res.data.fname,
                lName: res.data.lname,
                username: res.data.account.username,
                email: res.data.account.email
            }))
        )
    }

    const updateAccount = async (e) => {
        e.preventDefault()
       // axios.put(`http://localhost:8080/users/${claims.id}`)
    }


    return (
        <div>
            {<form>
                <label>Email:</label>
                <input value={user.email}
                       onChange={(e) => setUser(prevState => ({...prevState, email: e.target.value}))}/>

                <label>Username:</label>
                <input value={user.username}
                       onChange={(e) => setUser(prevState => ({...prevState, username: e.target.value}))}/>

                <label>First name:</label>
                <input value={user.fName}
                       onChange={(e) => setUser(prevState => ({...prevState, fName: e.target.value}))}/>

                <label>Last name:</label>
                <input value={user.lName}
                       onChange={(e) => setUser(prevState => ({...prevState, lName: e.target.value}))}/>

                <button>Edit account information</button>
            </form>}
        </div>
    )
}