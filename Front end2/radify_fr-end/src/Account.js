import React, {useEffect, useState} from "react";
import axios from 'axios';
import decode from "jwt-claims";

export default function Account(){

    let decode = require('jwt-claims');
    const token = window.sessionStorage.getItem('token');
    const claims = decode(token);

    const [user, setUser] = useEffect({
        fName: "",
        lName: "",
        username: "",
        email: ""
    })


    useEffect(() => {
        getAccountDetails()
    })

    function getAccountDetails(){
        axios.get(`http://localhost:8080/users/${claims.id}`).then(res =>
        //     setUser(prevState => ({
        //     ...prevState,
        //     fName: res.fName,
        //     lName: res.lName,
        //     username: res.username,
        //     email: res.email
        //
        // }))
            console.log(res)
            )
    }


    return (
        <div>
            <input placeholder={user.email}/>
        </div>
    )
}