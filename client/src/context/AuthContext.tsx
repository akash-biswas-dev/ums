import axios from "axios";
import {
  createContext,
  Suspense,
  use,
  useContext,
  useEffect,
  useState,
  type ReactNode,
} from "react";
import type { Authorization, AuthorizationResponse } from "../types/auth";
import { AxiosProvider } from "./AxiosContext";
import { useNavigate } from "react-router";

const url = import.meta.env.VITE_SERVER_URL || "";

const AuthContext = createContext<Authorization>({});

const AuthorizationManager = ({
  children,
  authorizationPromise,
}: {
  children: ReactNode;
  authorizationPromise: Promise<AuthorizationResponse>;
}) => {
  const navigate = useNavigate();

  const { authorization } = use(authorizationPromise);

  const [authorizarion, setAuthorization] =
    useState<Authorization>(authorization);

  const { token } = authorizarion;

  useEffect(() => {
    if (!token) {
      navigate("/auth");
    }
  }, []);

  return (
    <AuthContext.Provider value={authorizarion}>
      {token && (
        <AxiosProvider setAuthorization={setAuthorization}>
          {children}
        </AxiosProvider>
      )}
    </AuthContext.Provider>
  );
};

export const AuthProvider = ({ children }: { children: ReactNode }) => {
  const authorizationPromise = fetchAuthorization();

  return (
    <Suspense fallback={<p>Loading...</p>}>
      <AuthorizationManager authorizationPromise={authorizationPromise}>
        {children}
      </AuthorizationManager>
    </Suspense>
  );
};

export default function useAuthorization() {
  return useContext(AuthContext);
}

export const fetchAuthorization: () => Promise<AuthorizationResponse> =
  async () => {
    const res = await axios.post(`${url}/api/v1/authorize`, null, {
      validateStatus: () => true,
    });

    if (res.status === 201 && res.data) {
      return {
        authorization: res.data,
      };
    }

    if (res.data) {
      return { error: res.data.error, authorization: {} };
    }

    return { error: "UNKNOWN_REASON", authorization: {} };
  };
