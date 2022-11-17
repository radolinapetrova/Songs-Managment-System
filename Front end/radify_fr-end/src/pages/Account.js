import React from "react";
import Register from "../Register";
import Login from "../LogIn";
import "../Account.css";

const Account = () => {
    return (
        <div className="account">
            <Login/>
            <Register/>
        </div>
    )
}

export default Account;