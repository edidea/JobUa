import React, { useEffect, useState } from "react";
import { me, logout } from "../../api/auth";

export default function EmployeeProfile() {
    const [user, setUser] = useState(null);
    useEffect(() => { (async () => {
        const u = await me();
        if (!u || u.role !== "Employee") { window.location.href = "/employee/login"; return; }
        setUser(u);
    })(); }, []);
    if (!user) return <div>Loading...</div>;
    return (
        <div>
            <h2>Профіль Робітника</h2>
            <p><b>Email:</b> {user.email}</p>
            <p><b>Ім'я:</b> {user.fullName}</p>
            <button onClick={async() => { await logout(); window.location.href="/employee/login"; }}>
                Вийти
            </button>
        </div>
    );
}
