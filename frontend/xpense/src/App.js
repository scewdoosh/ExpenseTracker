import { BrowserRouter, Routes, Route, Navigate } from "react-router-dom";
import { useState, useEffect } from "react";
import { getMe } from "./api";
import Login from "./pages/Login";
import Dashboard from "./pages/Dashboard";

const PrivateRoute = ({ children }) => {
    const [auth, setAuth] = useState(null);

    useEffect(() => {
        getMe()
            .then(() => setAuth(true))
            .catch(() => setAuth(false));
    }, []);

    if (auth === null) return <div style={{ color: "#fff", textAlign: "center", marginTop: 100 }}>Loading...</div>;
    return auth ? children : <Navigate to="/login" />;
};

export default function App() {
    return (
        <BrowserRouter>
            <Routes>
                <Route path="/login" element={<Login />} />
                <Route path="/dashboard" element={
                    <PrivateRoute>
                        <Dashboard />
                    </PrivateRoute>
                } />
                <Route path="*" element={<Navigate to="/login" />} />
            </Routes>
        </BrowserRouter>
    );
}