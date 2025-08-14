import React from "react";
import { BrowserRouter as Router, Routes, Route, Navigate } from "react-router-dom";
import LoginPage from "./pages/LoginPage";
import ProfilePage from "./pages/ProfilePage";
import HomePage from "./pages/HomePage";
import RegisterPage from "./pages/RegisterPage";
function App() {
  return (
      <Router>
        <Routes>
            <Route path="/" element={<HomePage />} />
            <Route path="/login" element={<LoginPage />} />
            <Route path="/profile" element={<ProfilePage />} />
            <Route path="/register" element={<RegisterPage />} />
          <Route path="*" element={<Navigate to="/" />} />
        </Routes>
      </Router>    );
}

export default App;