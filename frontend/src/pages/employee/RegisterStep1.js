import React, { useState } from "react";
import { registerEmployeeStep1 } from "../../api/auth";

export default function RegisterStep1() {
    const [form, setForm] = useState({ email: "", fullName: "", password: "", language: "UA" });
    const [loading, setLoading] = useState(false);

    const onSubmit = async () => {
        try {
            setLoading(true);
            // форсимо UA незалежно від стану
            await registerEmployeeStep1({ ...form, language: "UA" });
            window.location.href = "/employee/register/step2";
        } catch (e) {
            alert("❌ " + e.message);
        } finally {
            setLoading(false);
        }
    };

    return (
        <div>
            <h2>Реєстрація (крок 1) — Робітник</h2>
            <input placeholder="Email" onChange={e => setForm({ ...form, email: e.target.value })} />
            <input placeholder="Повне ім'я" onChange={e => setForm({ ...form, fullName: e.target.value })} />
            <input placeholder="Пароль" type="password" onChange={e => setForm({ ...form, password: e.target.value })} />
            <button disabled={loading} onClick={onSubmit}>Продовжити</button>

            <hr />
            <a href="http://localhost:8080/oauth2/authorization/google-employee">
                Продовжити через Google
            </a>
        </div>
    );
}
