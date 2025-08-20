import React, { useEffect, useState } from "react";
import { Navigate } from "react-router-dom";

export default function RequireAuth({ role, children }) {
    const [state, setState] = useState({ loading: true, ok: false, user: null });

    useEffect(() => {
        let cancelled = false;
        (async () => {
            try {
                const res = await fetch("http://localhost:8080/api/auth/me", {
                    method: "GET",
                    credentials: "include",
                });
                if (res.status !== 200) {
                    if (!cancelled) setState({ loading: false, ok: false, user: null });
                    return;
                }
                const user = await res.json();
                const expected = role?.toLowerCase() === "employer" ? "Employer" : "Employee";
                const ok = role ? user.role === expected : true;
                if (!cancelled) setState({ loading: false, ok, user });
            } catch (e) {
                if (!cancelled) setState({ loading: false, ok: false, user: null });
            }
        })();
        return () => { cancelled = true; };
    }, [role]);

    if (state.loading) return <div>Loading…</div>;

    if (!state.ok) {
        const target = role?.toLowerCase() === "employer" ? "employer" : "employee";
        return <Navigate to={`/${target}/login`} replace />;
    }

    return children;
}
