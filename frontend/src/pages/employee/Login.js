import React, { useState } from "react";
import { login } from "../../api/auth";

export default function EmployeeLogin() {
    const [email, setEmail] = useState(""); const [password, setPassword] = useState("");

    const onSubmit = async () => {
        try { await login("employee", email, password); window.location.href="/employee/profile"; }
        catch (e) { alert("❌ " + e.message); }
    };

    return (
        <div>
            <h2>Вхід (Робітник)</h2>
            <input placeholder="Email" value={email} onChange={e=>setEmail(e.target.value)} />
            <input placeholder="Password" type="password" value={password} onChange={e=>setPassword(e.target.value)} />
            <button onClick={onSubmit}>Увійти</button>

            <hr />
            <a href="http://localhost:8080/oauth2/authorization/google-employee">
                Увійти через Google
            </a>

            <p><a href="/employee/register">Реєстрація</a></p>

            <p><a href="/employer/login">Увійти як Роботодавець</a></p>
        </div>
    );
}
