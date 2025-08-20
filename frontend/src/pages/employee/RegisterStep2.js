import React, { useEffect, useState } from "react";
import { employeeCompleteProfile, fetchCities } from "../../api/employee";

export default function RegisterStep2() {
    const [cities, setCities] = useState([]);
    const [form, setForm] = useState({
        phone: "",
        cityId: null,
        veteran: false,
        birthDate: "",
        address: ""
    });

    useEffect(() => {
        (async () => {
            try {
                const list = await fetchCities();
                setCities(list);
            } catch (e) {
                alert("Не вдалося завантажити міста");
            }
        })();
    }, []);

    const onSubmit = async () => {
        try {
            const payload = {
                phone: form.phone,
                cityId: form.cityId ? Number(form.cityId) : null,
                veteran: !!form.veteran,
                birthDate: form.birthDate || null,
                address: form.address || null
            };
            await employeeCompleteProfile(payload);
            window.location.href = "/employee/profile";
        } catch (e) {
            alert("❌ " + e.message);
        }
    };

    return (
        <div>
            <h2>Реєстрація (крок 2) — Робітник</h2>

            <input placeholder="Телефон" value={form.phone}
                   onChange={e => setForm({ ...form, phone: e.target.value })} />

            <label style={{ display: "block", margin: "8px 0" }}>
                <input type="checkbox" checked={form.veteran}
                       onChange={e => setForm({ ...form, veteran: e.target.checked })} />
                Я ветеран
            </label>

            <select value={form.cityId || ""} onChange={e => setForm({ ...form, cityId: e.target.value })}>
                <option value="">Оберіть місто</option>
                {cities.map(c => <option key={c.id} value={c.id}>{c.name}</option>)}
            </select>

            <input type="date" value={form.birthDate}
                   onChange={e => setForm({ ...form, birthDate: e.target.value })} />

            <textarea placeholder="Адреса (опційно)" value={form.address}
                      onChange={e => setForm({ ...form, address: e.target.value })} />

            <div style={{ marginTop: 12 }}>
                <button onClick={onSubmit}>Завершити</button>
            </div>
        </div>
    );
}
