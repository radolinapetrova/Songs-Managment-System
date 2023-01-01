import Register from "../auth/Register";
import "../css/Account.css";
import Login from "../auth/Login";
import {useAuth} from "../auth/AuthProvider";
import Account from "../Account";

export default function AuthPage() {

    const {auth, setAuth, claims} = useAuth();


    function logout() {
        sessionStorage.clear()
        setAuth(false)
    }


    if (auth) {
        if (claims.roles[0] === 'ADMIN') {
            return (
                <>
                    <button onClick={logout}>Logout</button>
                </>
            )
        } else {
            return (
                <>
                    <Account/>
                    <button onClick={logout}>Logout</button>
                </>
            )
        }


    } else {
        return (
            <div className="authForm">
                <div><Login/></div>
                <div><Register/></div>
            </div>
        )
    }


}
