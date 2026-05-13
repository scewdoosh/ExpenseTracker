import { useState } from "react";
import { signin, signup } from "../api";
import { useNavigate } from "react-router-dom";

export default function Login() {
    const [isSignup, setIsSignup] = useState(false);
    const [form, setForm] = useState({ name: "", email: "", password: "" });
    const [error, setError] = useState("");
    const navigate = useNavigate();

    const handleSubmit = async () => {
        try {
            if (isSignup) {
                await signup(form);
                setIsSignup(false);
            } else {
                const res = await signin({ email: form.email, password: form.password });
                localStorage.setItem("token", res.data);
                navigate("/dashboard");
            }
        } catch (err) {
            setError("Something went wrong!");
        }
    };

    return (
        <div style={{ display: "flex", justifyContent: "center", alignItems: "center", height: "100vh", background: "#0a0a0f" }}>
            <div style={{ background: "#141020", padding: 32, borderRadius: 16, width: 320, border: "1px solid #2a2040" }}>
                <h2 style={{ color: "#f0e8d8", marginBottom: 24 }}>{isSignup ? "Sign Up" : "Sign In"}</h2>

                {isSignup && (
                    <input placeholder="Name" value={form.name}
                        onChange={e => setForm(f => ({ ...f, name: e.target.value }))}
                        style={inputStyle} />
                )}
                <input placeholder="Email" value={form.email}
                    onChange={e => setForm(f => ({ ...f, email: e.target.value }))}
                    style={inputStyle} />
                <input placeholder="Password" type="password" value={form.password}
                    onChange={e => setForm(f => ({ ...f, password: e.target.value }))}
                    style={inputStyle} />

                {error && <div style={{ color: "#ef4444", fontSize: 13, marginBottom: 12 }}>{error}</div>}

                <button onClick={handleSubmit} style={buttonStyle}>
                    {isSignup ? "Sign Up" : "Sign In"}
                </button>

                <div onClick={() => setIsSignup(!isSignup)}
                    style={{ color: "#7c3aed", fontSize: 13, textAlign: "center", marginTop: 16, cursor: "pointer" }}>
                    {isSignup ? "Already have an account? Sign In" : "Don't have an account? Sign Up"}
                </div>
            </div>
        </div>
    );
}

const inputStyle = {
    width: "100%", background: "#0a0a0f", border: "1px solid #2a2040",
    color: "#e8e0d5", borderRadius: 10, padding: "12px 14px",
    fontSize: 14, marginBottom: 12, boxSizing: "border-box", outline: "none"
};

const buttonStyle = {
    width: "100%", background: "linear-gradient(135deg, #7c3aed, #4f46e5)",
    color: "#fff", border: "none", borderRadius: 12, padding: "13px 0",
    fontSize: 15, fontWeight: 700, cursor: "pointer"
};