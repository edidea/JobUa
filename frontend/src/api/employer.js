const API = "http://localhost:8080";

export async function employerCompleteProfile(payload) {
    const res = await fetch(`${API}/api/employer/setup`, {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        credentials: "include",
        body: JSON.stringify(payload),
    });
    const text = await res.text();
    if (!res.ok) throw new Error(text || "Setup failed");
    return text;
}
