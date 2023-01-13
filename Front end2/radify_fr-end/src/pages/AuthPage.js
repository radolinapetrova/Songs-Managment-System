import Register from "../auth/Register";
import "../css/Account.css";
import Login from "../auth/Login";
import {useAuth} from "../auth/AuthProvider";
import Account from "../Account";

export default function AuthPage() {

    const {auth, setAuth, claims} = useAuth();




    if (!auth) {
        return (
            <div className="authForm">
                <div><Login/></div>
                <div><Register/></div>
            </div>
        )
    }


}
