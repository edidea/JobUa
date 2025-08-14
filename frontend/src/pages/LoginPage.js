import React from "react";

function LoginPage() {
    const handleLogin = () => {
        window.location.href = "http://localhost:8080/oauth2/authorization/google";
    };

    return (
        <div style={{ padding: "2rem", textAlign: "center" }}>
            <h1>Job Ua</h1>
            <p>Щоб увійти, натисни кнопку нижче:</p>
            <button                onClick={handleLogin}
                                   style={{
                                       padding: "0.5rem 1.5rem",
                                       fontSize: "1rem",
                                       cursor: "pointer",
                                       border: "none",
                                       borderRadius: "5px",
                                       backgroundColor: "#4285F4",
                                       color: "#fff",
                                   }}
            >
                Увійти через Google
            </button>
        </div>    );
}

export default LoginPage;