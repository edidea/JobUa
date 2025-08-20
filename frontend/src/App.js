import React from "react";
import { BrowserRouter as Router, Routes, Route, Navigate } from "react-router-dom";

import EmployeeHome from "./pages/employee/EmployeeHome";
import EmployerHome from "./pages/employer/EmployerHome";

import EmployeeLogin from "./pages/employee/Login.js";
import RegisterStep1 from "./pages/employee/RegisterStep1.js";
import RegisterStep2 from "./pages/employee/RegisterStep2.js";

import EmployerLogin from "./pages/employer/Login.js";
// ⬇️ заміни імпорт
import EmployerRegisterStep1 from "./pages/employer/RegisterStep1.js";
import EmployerRegisterStep2 from "./pages/employer/RegisterStep2.js";

import EmployeeProfile from "./pages/employee/Profile.js";
import EmployerProfile from "./pages/employer/Profile.js";
import RequireAuth from "./components/RequireAuth.js";

function App() {
    return (
        <Router>
            <Routes>
                {/* головна -> employee */}
                <Route path="/" element={<Navigate to="/employee" replace />} />

                {/* employee */}
                <Route path="/employee" element={<EmployeeHome />} />
                <Route path="/employee/login" element={<EmployeeLogin />} />
                <Route path="/employee/register" element={<RegisterStep1 />} />
                <Route path="/employee/register/step1" element={<Navigate to="/employee/register" replace />} />
                <Route
                    path="/employee/register/step2"
                    element={
                        <RequireAuth role="employee">
                            <RegisterStep2 />
                        </RequireAuth>
                    }
                />
                <Route
                    path="/employee/profile"
                    element={
                        <RequireAuth role="employee">
                            <EmployeeProfile />
                        </RequireAuth>
                    }
                />

                {/* employer */}
                <Route path="/employer" element={<EmployerHome />} />
                <Route path="/employer/login" element={<EmployerLogin />} />
                <Route path="/employer/register" element={<EmployerRegisterStep1 />} />
                <Route path="/employer/register/step1" element={<Navigate to="/employer/register" replace />} />
                <Route
                    path="/employer/register/step2"
                    element={
                        <RequireAuth role="employer">
                            <EmployerRegisterStep2 />
                        </RequireAuth>
                    }
                />
                <Route
                    path="/employer/profile"
                    element={
                        <RequireAuth role="employer">
                            <EmployerProfile />
                        </RequireAuth>
                    }
                />

                <Route path="*" element={<Navigate to="/" />} />
            </Routes>
        </Router>
    );
}

export default App;
