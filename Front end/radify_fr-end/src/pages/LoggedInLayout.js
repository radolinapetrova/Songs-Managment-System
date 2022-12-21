import {Outlet, Link} from "react-router-dom";
import React, {useEffect, useState} from "react";
import '../index.css';
import {useNavigate} from 'react-router-dom';

const Layout = () => {

    // const [isAuth, setIsAuth] = useState(false)
    //
    // useEffect(() => {
    //     if (window.sessionStorage.getItem('token')) {
    //         setIsAuth(true)
    //     } else {
    //         setIsAuth(false)
    //     }
    // }, [])

    // if (isAuth) {
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
    // }
    // else{
    //     return (
    //         <div className="main">
    //             <nav>
    //                 <Link className="link" to="/">Home</Link>
    //                 {/*<Link className="link" to="/playlists">Playlists</Link>*/}
    //                 <Link className="link" to="/login">Log in</Link>
    //             </nav>
    //             <Outlet/>
    //         </div>
    //     )
    // }


};

export default Layout;