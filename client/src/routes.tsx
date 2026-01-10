import { createBrowserRouter, Navigate } from "react-router";
import App from "./App";
import NotFound from "./pages/NotFound";
import Auth from "./pages/Auth";
import Home from "./pages/HomePage";
const routes = createBrowserRouter([
  {
    path: "/",
    element: <App />,
    children: [
      {
        index: true,
        element: <Home />,
      },
      {
        path: "*",
        element: <NotFound />,
      },
    ],
  },
  {
    path: "/auth",
    element: <Auth />,
  },
  {
    path: "*",
    element: <Navigate to="/ums" replace />,
  },
]);

export default routes;
