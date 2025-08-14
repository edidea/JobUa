import React, { useEffect, useState } from "react";

function ProfilePage() {
    const [user, setUser] = useState(null);
    const [loading, setLoading] = useState(true);

    useEffect(() => {
        fetch("http://localhost:8080/api/", {
            credentials: "include",
        })
            .then((res) => {
                if (res.status === 200) return res.json();
                throw new Error("Not authenticated");
            })
            .then((data) => setUser(data))
            .catch(() => setUser(null))
            .finally(() => setLoading(false));
    }, []);

    if (loading) return <p style={{ textAlign: "center" }}>Завантаження...</p>;

    if (!user)
        return (
            <div style={{ textAlign: "center", paddingTop: "2rem" }}>
                <p>❌ Ви не авторизовані.</p>
                <a href="/login">Повернутись до входу</a>
            </div>        );

    return (
        <div style={{ padding: "2rem", textAlign: "center" }}>
            <h2>👋 Вітаємо, {user.name}!</h2>
            <p><strong>Email:</strong> {user.email}</p>
        </div>    );
}

export default ProfilePage;