import React, {useEffect, useState} from "react";
import axios from 'axios';
import {useAuth} from "./auth/AuthProvider";
import "./css/Account.css"
import Dialog from "./Dialog";
import {useNavigate} from "react-router-dom";

export default function Account() {

    const token = window.sessionStorage.getItem('token');
    const {claims, setClaims, setAuth} = useAuth();
    const [message, setMessage] = useState("");

    const [user, setUser] = useState({
        first_name: "",
        last_name: "",
        username: "",
        email: "",
        id: ""
    })

    const [dialog, setDialog] = useState({
        message: "Are you sure you want to delete your account?",
        isLoading: false
    })

    const navigate = useNavigate();

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

    function logout() {
        sessionStorage.clear()
        setAuth(false)
        setClaims(null)
    }

    const updateAccount = async (e) => {
        axios.defaults.headers.common = {'Authorization': `Bearer ${token}`}
        e.preventDefault()
        try {
            let res = await axios.put(`http://localhost:8080/users/account`, user)
            setMessage("Successful updating of account information!")

        } catch (err) {
            if (err.response.status == 417) {
                setMessage("You have entered invalid data, which is quite unacceptable")
            }
        }
    }

    const deleteAccount = async (e) => {
        axios.defaults.headers.common = {'Authorization': `Bearer ${token}`}

        axios.delete(`http://localhost:8080/users/${claims.id}`).then(res => console.log(res.data))
        logout()
        navigate("/")

    }

    const confirmDeletion = (choose) => {

        if (choose) {
            deleteAccount();
        }
        setDialog(prevState => ({...prevState, isLoading: false}))
    };


    return (
        <div className="accountInfo">
            {<div>
                <label>Email:</label>
                <input value={user.email} name="update_email"
                       onChange={(e) => setUser(prevState => ({...prevState, email: e.target.value}))}/>

                <label>Username:</label>
                <input value={user.username} name="update_username"
                       onChange={(e) => setUser(prevState => ({...prevState, username: e.target.value}))}/>

                <label>First name:</label>
                <input value={user.first_name} name="update_fname "
                       onChange={(e) => setUser(prevState => ({...prevState, first_name: e.target.value}))}/>

                <label>Last name:</label>
                <input value={user.last_name} name="update_lname"
                       onChange={(e) => setUser(prevState => ({...prevState, last_name: e.target.value}))}/>

                <button  name="update_button" onClick={updateAccount}>Edit account information</button>
                <button name="delete_button" onClick={(e) => setDialog(prevState => ({...prevState, isLoading: true}))}>Delete account
                </button>
                <p name="update_message">{message}</p>
                {dialog.isLoading && <Dialog message={dialog.message} onDialog={confirmDeletion}/>}
            </div>}
        </div>
    )
}