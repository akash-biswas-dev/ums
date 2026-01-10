import { Outlet } from "react-router";
import { AuthProvider } from "./context/AuthContext";
import { Sidebar } from "./components/sidebar/Sidebar";

export default function App() {
  return (
    <AuthProvider>
      <div className="min-h-screen flex">
        <Sidebar />
        <main className="flex-1 bg-white p-6">
          <Outlet />
        </main>
      </div>
    </AuthProvider>
  );
}
