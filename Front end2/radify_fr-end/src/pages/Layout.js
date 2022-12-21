import {Outlet, Link} from "react-router-dom";
import React, {useEffect, useState} from "react";
import '../index.css';
import {useNavigate} from 'react-router-dom';
import decode from "jwt-claims";

const Layout = () => {

    var decode = require('jwt-claims');



    const [isAuth, setIsAuth] = useState(false)

    useEffect(() => {
        if (window.sessionStorage.getItem('token')) {
            setIsAuth(true)
        } else {
            setIsAuth(false)
        }
    })

    if (isAuth) {
        const token = window.sessionStorage.getItem('token');
        const claims = decode(token);
        console.log(claims.roles)
        if(claims.roles[0] == ['USER']){
            return (
                <div className="main">
                    <nav className="nav">
                        <Link className="link" to="/">Home</Link>
                        <Link className="link" to="/playlists">Playlists</Link>
                        <Link className="link" to="/login">Log in</Link>
                    </nav>
                    <Outlet/>
                </div>
            )
        }else {
            return (
                <div className="main">
                    <nav>
                        <Link className="link" to="/">Home</Link>
                        <Link className="link" to="/songs">Songs</Link>
                        <Link className="link" to="/login">Log in</Link>
                    </nav>
                    <Outlet/>
                </div>
            )
        }

    }
    else{
        return (
            <div className="main">
                <nav>
                    <Link className="link" to="/">Home</Link>
                    {/*<Link className="link" to="/playlists">Playlists</Link>*/}
                    <Link className="link" to="/login">Log in</Link>
                </nav>
                <Outlet/>
            </div>
        )
    }


};

export default Layout;