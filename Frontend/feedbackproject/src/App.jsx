import { Route, Routes, Navigate, useNavigate } from "react-router-dom";
import Login from "./pages/Login";
import Register from "./pages/Register";
import UserDashboard from "./pages/UserDashboard";
import AdminDashboard from "./pages/AdminDashboard";
import { useAuth } from "./context/AuthContext";

function App() {
  const { token, role, logout } = useAuth();
  const navigate = useNavigate();

  const handleLogout = () => {
    logout();
    navigate("/login");
  };

  if (!token) {
    return (
      <Routes>
        <Route path="/login" element={<Login />} />
        <Route path="/register" element={<Register />} />
        <Route path="*" element={<Navigate to="/login" />} />
      </Routes>
    );
  }

  return (
    <>
      <button
        onClick={handleLogout}
        style={{ position: "absolute", top: 10, right: 10 }}
      >
        Logout
      </button>
      <Routes>
        {role === "USER" ? (
          <Route path="/user-dashboard" element={<UserDashboard />} />
        ) : (
          <Route path="/admin-dashboard" element={<AdminDashboard />} />
        )}
        <Route
          path="*"
          element={
            <Navigate
              to={role === "USER" ? "/user-dashboard" : "/admin-dashboard"}
            />
          }
        />
      </Routes>
    </>
  );
}

export default App;
