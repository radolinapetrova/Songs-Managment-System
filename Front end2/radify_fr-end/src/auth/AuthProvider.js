import {createContext, useContext, useEffect, useState} from 'react';

const AuthContext = createContext({
    auth: null,
    setAuth: () => {
    },
    claims: null,
    setClaims: () => {
    }
});

export const useAuth = () => useContext(AuthContext);

const AuthProvider = ({children}) => {
    const [auth, setAuth] = useState(false);
    const [claims, setClaims] = useState(null);
    let decode = require('jwt-claims');


    useEffect(() => {

        let token = sessionStorage.getItem('token');
        if (token) {
            setAuth(true)
            setClaims(decode(token))
        }
    }, [setAuth]);

    return (
        <AuthContext.Provider value={{auth, setAuth, claims, setClaims}}>
            {children}
        </AuthContext.Provider>
    );
};

export default AuthProvider;