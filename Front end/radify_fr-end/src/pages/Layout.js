import {Outlet, Link} from "react-router-dom";
import React from "react";
import '../index.css';

const Layout = () => {
    return (
        <div className="main">
            <nav>
                <Link className="link" to="/">Home</Link>
                <Link className="link" to="/playlists">Playlists</Link>
                <Link className="link" to="/account">Account</Link>
            </nav>
            <Outlet/>
        </div>
    )
};

export default Layout;