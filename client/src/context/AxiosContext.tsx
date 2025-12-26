import axios, { type AxiosInstance } from "axios";
import { createContext, useContext, type ReactNode } from "react";
import { useNavigate } from "react-router";
import type { Authorization } from "../types/auth";
import useAuthorization, { fetchAuthorization } from "./AuthContext";

const url = import.meta.env.VITE_SERVER_URL || "";

const AxiosContext = createContext<AxiosInstance>(axios);

export function AxiosProvider({
  children,
  setAuthorization,
}: {
  children: ReactNode;
  setAuthorization: (auth: Authorization) => void;
}) {
  const navigate = useNavigate();
  const { token } = useAuthorization();

  const instance = axios.create({
    baseURL: url,
    headers: {
      Authorization: token,
    },
  });
  instance.interceptors.request.use(undefined, async function (err: any) {
    if (err.response?.status !== 401) {
      return err;
    }

    if (err.response.data) {
      const data = err.response.data;

      if (data.error && data.error === "TOKEN_EXPIRED") {
        const { authorization, error } = await fetchAuthorization();
        if (error) {
          navigate("/auth");
          return;
        } else {
          setAuthorization(authorization);
        }
      }
    }
    return instance(err.config);
  });

  return (
    <AxiosContext.Provider value={instance}>{children}</AxiosContext.Provider>
  );
}

export default function useAxios() {
  return useContext(AxiosContext);
}
