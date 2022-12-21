import {Outlet, Link} from "react-router-dom";
import React, {useEffect, useState} from "react";
import '../index.css';
import {useNavigate} from 'react-router-dom';

const Layout = () => {

    var decode = require('jwt-claims');


    const [isAuth, setIsAuth] = useState(false)
    const token = window.sessionStorage.getItem('token')



    useEffect(() => {
        if (token) {
            setIsAuth(true)
        } else {
            setIsAuth(false)
        }
    }, [])

    if (isAuth) {
        var claims = decode(token);
        console.log("Roleee: " + claims.roles)
        if (claims.roles == "USER") {
            return (
                <div className="main">
                    <nav>
                        <Link className="link" to="/">Home</Link>
                        <Link className="link" to="/playlists">Playlists</Link>
                        <Link className="link" to="/login">Log in</Link>
                    </nav>
                    <Outlet/>
                </div>
            )
        }

        return (
            <div className="main">
                <nav>
                    <Link className="link" to="/">Home</Link>
                    <Link className="link" to="/login">Log in</Link>
                </nav>
                <Outlet/>
            </div>
        )


    } else {
        return (
            <div className="main">
                <nav>
                    <Link className="link" to="/">Home</Link> 
                    <Link className="link" to="/login">Log in</Link>
                </nav>
                <Outlet/>
            </div>
        )
    }


};

export default Layout;