const API = "http://localhost:8080";

export async function login(role, email, password) {
    const res = await fetch(`${API}/api/auth/${role}/login`, {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        credentials: "include",
        body: JSON.stringify({ email, password }),
    });
    const text = await res.text();
    if (!res.ok) throw new Error(text || "Login failed");
    return JSON.parse(text);
}

export async function me() {
    const res = await fetch(`${API}/api/auth/me`, {
        method: "GET",
        credentials: "include",
    });
    if (res.status === 401) return null;
    return res.json();
}

export async function logout() {
    await fetch(`${API}/api/auth/logout`, { method: "POST", credentials: "include" });
}
export async function registerEmployeeStep1(payload) {
    const res = await fetch(`${API}/api/auth/employee/register-basic`, {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        credentials: "include",
        body: JSON.stringify(payload),
    });
    const text = await res.text();
    if (!res.ok) throw new Error(text || "Registration failed");
    return JSON.parse(text);
}
export async function registerEmployerStep1(payload) {
    const res = await fetch(`http://localhost:8080/api/auth/employer/register-basic`, {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        credentials: "include",
        body: JSON.stringify(payload),
    });
    const text = await res.text();
    if (!res.ok) throw new Error(text || "Registration failed");
    return JSON.parse(text);
}