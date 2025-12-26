import { createBrowserRouter, Navigate } from "react-router";
import App from "./App";
import NotFound from "./pages/NotFound";
import Auth from "./pages/Auth";
const routes = createBrowserRouter([
  {
    path: "/ums",
    element: <App />,
    children: [
      {
        index: true,
        element: <App />,
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
