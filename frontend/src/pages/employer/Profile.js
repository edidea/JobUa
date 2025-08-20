import React, { useEffect, useState } from "react";
import { me, logout } from "../../api/auth";

export default function EmployerProfile() {
    const [user, setUser] = useState(null);
    useEffect(() => { (async () => {
        const u = await me();
        if (!u || u.role !== "Employer") { window.location.href = "/employer/login"; return; }
        setUser(u);
    })(); }, []);
    if (!user) return <div>Loading...</div>;
    return (
        <div>
            <h2>Профіль Роботодавця</h2>
            <p><b>Email:</b> {user.email}</p>
            <p><b>Ім'я:</b> {user.fullName}</p>
            <button onClick={async() => { await logout(); window.location.href="/employer/login"; }}>
                Вийти
            </button>
        </div>
    );
}
