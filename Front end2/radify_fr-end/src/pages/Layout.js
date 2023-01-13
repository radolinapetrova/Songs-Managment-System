import {Outlet, Link} from "react-router-dom";
import '../css/index.css';
import {useAuth} from "../auth/AuthProvider";

const Layout = () => {

    const {auth, claims} = useAuth();

    if (auth) {
        if(claims.roles[0] == ['USER']){
            return (
                <div className="main">
                    <nav className="nav">
                        <Link className="link" to="/">Home</Link>
                        <Link className="link" to="/playlists">Playlists</Link>
                        <Link className="link" name="account_link" id="account_link" to="/account">Account</Link>
                        <Link className="link" to="/chat">Chat</Link>
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
                        <Link className="link" to="/account">Account</Link>
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
                    <Link className="link" id="login_link" to="/login">Log in</Link>
                </nav>
                <Outlet/>
            </div>
        )
    }
};

export default Layout;