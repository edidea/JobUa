const API = "http://localhost:8080";

export async function employeeCompleteProfile(payload) {
    const res = await fetch(`${API}/api/employee/setup`, {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        credentials: "include",
        body: JSON.stringify(payload),
    });
    const text = await res.text();
    if (!res.ok) throw new Error(text || "Setup failed");
    return text;
}

export async function fetchCities() {
    const res = await fetch(`${API}/api/cities`, {
        method: "GET",
        credentials: "include",
    });
    if (!res.ok) throw new Error("Failed to load cities");
    return res.json();
}
