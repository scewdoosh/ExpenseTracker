import { useState, useEffect } from "react";
import { addPayment, getTotal, updateWebhook } from "../api";
import { useNavigate } from "react-router-dom";

export default function Dashboard() {
    const [total, setTotal] = useState(0);
    const [amount, setAmount] = useState("");
    const [webhook, setWebhook] = useState("");
    const [message, setMessage] = useState("");
    const navigate = useNavigate();

    useEffect(() => {
        fetchTotal();
    }, []);

    const fetchTotal = async () => {
        try {
            const res = await getTotal();
            setTotal(res.data.totalAmount);
        } catch (err) {
            setTotal(0);
        }
    };

    const handleAddPayment = async () => {
        if (!amount) return;
        try {
            await addPayment({ amount: parseFloat(amount) });
            setAmount("");
            setMessage("Payment added!");
            fetchTotal();
        } catch (err) {
            setMessage("Failed to add payment!");
        }
    };

    const handleWebhook = async () => {
        try {
            await updateWebhook({ webhookUrl: webhook });
            setMessage("Webhook updated!");
        } catch (err) {
            setMessage("Failed to update webhook!");
        }
    };

    const logout = () => {
        localStorage.removeItem("token");
        navigate("/login");
    };

    return (
        <div style={{ minHeight: "100vh", background: "#0a0a0f", color: "#e8e0d5", padding: 24 }}>
            <div style={{ maxWidth: 480, margin: "0 auto" }}>
                <div style={{ display: "flex", justifyContent: "space-between", alignItems: "center", marginBottom: 24 }}>
                    <h2 style={{ color: "#f0e8d8" }}>Expense Tracker</h2>
                    <button onClick={logout} style={{ background: "#ef4444", border: "none", color: "#fff", borderRadius: 8, padding: "8px 16px", cursor: "pointer" }}>Logout</button>
                </div>

                {/* Total */}
                <div style={{ background: "#141020", border: "1px solid #2a2040", borderRadius: 16, padding: 24, marginBottom: 16, textAlign: "center" }}>
                    <div style={{ fontSize: 13, color: "#7a6e8a", marginBottom: 8 }}>TOTAL SPENT</div>
                    <div style={{ fontSize: 36, fontWeight: 700, color: "#f59e0b" }}>₹{total}</div>
                </div>

                {/* Add Payment */}
                <div style={{ background: "#141020", border: "1px solid #2a2040", borderRadius: 16, padding: 20, marginBottom: 16 }}>
                    <div style={{ fontSize: 13, color: "#9d8fbb", marginBottom: 12 }}>ADD PAYMENT</div>
                    <input placeholder="Amount (₹)" value={amount} type="number"
                        onChange={e => setAmount(e.target.value)}
                        style={inputStyle} />
                    <button onClick={handleAddPayment} style={buttonStyle}>Add</button>
                </div>

                {/* Discord Webhook */}
                <div style={{ background: "#141020", border: "1px solid #2a2040", borderRadius: 16, padding: 20 }}>
                    <div style={{ fontSize: 13, color: "#9d8fbb", marginBottom: 12 }}>DISCORD WEBHOOK</div>
                    <input placeholder="Paste webhook URL..." value={webhook}
                        onChange={e => setWebhook(e.target.value)}
                        style={inputStyle} />
                    <button onClick={handleWebhook} style={buttonStyle}>Save</button>
                </div>

                {message && <div style={{ color: "#34d399", fontSize: 13, textAlign: "center", marginTop: 16 }}>{message}</div>}
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