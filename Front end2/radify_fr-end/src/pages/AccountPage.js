import Register from "../auth/Register";
import "../css/Account.css";
import Login from "../auth/Login";
import {useAuth} from "../auth/AuthProvider";
import Account from "../Account";
import {useNavigate} from "react-router-dom";

export default function AccountPage() {

    const {auth, setAuth, claims} = useAuth();

    const navigate = useNavigate();

    function logout() {
        sessionStorage.clear()
        setAuth(false)
        navigate("/")
    }


    if (auth) {
        if (claims.roles[0] === "USER"){
            return (
                <>
                    <Account/>
                    <button name="logout_button" onClick={logout}>Logout</button>
                </>
            )
        }
        else{
            return (
                <>
                    <button name="logout_button" onClick={logout}>Logout</button>
                </>
            )
        }
    }


}
