import Navigation from "./pages/Navigation"
import AuthProvider from "./auth/AuthProvider";

export default function App() {
    return (
        <AuthProvider>
            <Navigation/>
        </AuthProvider>
    );
}

