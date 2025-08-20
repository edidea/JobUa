import React, { useState } from "react";
import { employerCompleteProfile } from "../../api/employer";

export default function EmployerRegisterStep2() {
    const [form, setForm] = useState({ phone: "", companyName: "" });

    const onSubmit = async () => {
        try {
            await employerCompleteProfile({
                phone: form.phone,
                companyName: form.companyName
            });
            window.location.href = "/employer/profile";
        } catch (e) {
            alert("❌ " + e.message);
        }
    };

    return (
        <div>
            <h2>Реєстрація (крок 2) — Роботодавець</h2>
            <input placeholder="Телефон" value={form.phone}
                   onChange={e => setForm({ ...form, phone: e.target.value })} />
            <input placeholder="Назва компанії" value={form.companyName}
                   onChange={e => setForm({ ...form, companyName: e.target.value })} />
            <div style={{ marginTop: 12 }}>
                <button onClick={onSubmit}>Завершити</button>
            </div>
        </div>
    );
}
