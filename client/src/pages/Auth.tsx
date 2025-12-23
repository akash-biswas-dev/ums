import { useNavigate } from "react-router";
import Input from "../components/Input";
import axios, { AxiosError } from "axios";
import { useState } from "react";

const serverUrl = import.meta.env.VITE_SERVER_URL;

const url = serverUrl ? serverUrl : "";

export default function Auth() {
  const navigate = useNavigate();

  const [error, setError] = useState<null | string>(null);

  const login = async (formData: FormData) => {
    const credentials = {
      email: formData.get("email"),
      password: formData.get("password"),
    };
    const rememberMe = formData.get("remember");

    try {
      const res = await axios.post(`${url}/api/v1/auth`, credentials, {
        params: {
          rememberMe: rememberMe ? true : false,
        },
      });

      if (res.status === 201) {
        navigate("/ums");
      }
    } catch (err) {
      if (err instanceof AxiosError) {
        setError(err.response?.data.description);
        console.error(err.message);
      } else {
        setError("Try again later.");
      }
    }
  };

  return (
    <div className="bg-gray-50 min-h-screen flex flex-col">
      <main className="grow flex items-center justify-center px-4 py-12">
        <div className="bg-white rounded-lg shadow-xl p-8 w-full max-w-md">
          <div className="text-center mb-8">
            <h2 className="text-3xl font-bold text-gray-900 mb-2">
              Welcome Back
            </h2>
            {error ? (
              <p className="text-red-600">{error}</p>
            ) : (
              <p className="text-gray-600">Sign in to access your account</p>
            )}
          </div>

          <form action={login} className="space-y-6">
            <Input label="Email" type="email" id="login-email" name="email" />

            <Input
              label="Password"
              type="password"
              passwordToggle={true}
              id="login-password"
              name="password"
            />
            <div className="flex items-center justify-between">
              <div className="flex items-center">
                <input
                  type="checkbox"
                  id="remember"
                  name="remember"
                  className="w-4 h-4 text-blue-900 border-gray-300 rounded focus:ring-blue-900"
                />
                <label
                  htmlFor="remember"
                  className="ml-2 text-sm text-gray-700"
                >
                  Remember me
                </label>
              </div>
              <a
                href="#"
                className="text-sm text-blue-900 hover:text-blue-800 font-medium"
              >
                Forgot password?
              </a>
            </div>

            <button
              type="submit"
              className="w-full bg-blue-900 text-white py-3 rounded-lg font-semibold hover:bg-blue-800 transition shadow-lg"
            >
              Sign In
            </button>
          </form>
        </div>
      </main>

      <footer className="bg-white border-t border-gray-200 py-6">
        <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 text-center text-gray-600 text-sm">
          <p>&copy; 2025 Excellence University. All rights reserved.</p>
        </div>
      </footer>
    </div>
  );
}
