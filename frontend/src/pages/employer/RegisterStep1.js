import React, { useState } from "react";
import { registerEmployerStep1 } from "../../api/auth";

export default function EmployerRegisterStep1() {
    const [form, setForm] = useState({ email: "", fullName: "", password: "", language: "UA" });
    const [loading, setLoading] = useState(false);

    const onSubmit = async (e) => {
        e?.preventDefault?.();
        try {
            setLoading(true);
            await registerEmployerStep1({ ...form, language: "UA" });
            window.location.href = "/employer/register/step2";
        } catch (e) {
            alert("❌ " + e.message);
        } finally {
            setLoading(false);
        }
    };

    return (
        <div>
            <h2>Реєстрація (крок 1) — Роботодавець</h2>
            <form onSubmit={onSubmit}>
                <input placeholder="Email" onChange={e => setForm({ ...form, email: e.target.value })} />
                <input placeholder="Повне ім'я" onChange={e => setForm({ ...form, fullName: e.target.value })} />
                <input placeholder="Пароль" type="password" onChange={e => setForm({ ...form, password: e.target.value })} />
                <button type="submit" disabled={loading}>{loading ? "Опрацьовуємо…" : "Продовжити"}</button>
            </form>

            <hr />
            <a href="http://localhost:8080/oauth2/authorization/google-employer">
                Продовжити через Google
            </a>
        </div>
    );
}
