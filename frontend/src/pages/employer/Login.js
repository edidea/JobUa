import React, { useState } from "react";
import { login } from "../../api/auth";

export default function EmployerLogin() {
    const [email, setEmail] = useState(""); const [password, setPassword] = useState("");

    const onSubmit = async () => {
        try { await login("employer", email, password); window.location.href="/employer/profile"; }
        catch (e) { alert("❌ " + e.message); }
    };

    return (
        <div>
            <h2>Вхід (Роботодавець)</h2>
            <input placeholder="Email" value={email} onChange={e=>setEmail(e.target.value)} />
            <input placeholder="Password" type="password" value={password} onChange={e=>setPassword(e.target.value)} />
            <button onClick={onSubmit}>Увійти</button>

            <hr />
            <a href="http://localhost:8080/oauth2/authorization/google-employer">
                Увійти через Google
            </a>

            <p><a href="/employer/register">Реєстрація</a></p>
            <p><a href="/employee/login">Увійти як Робітник</a></p>
        </div>
    );
}
