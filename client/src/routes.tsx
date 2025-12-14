import { createBrowserRouter, Navigate } from "react-router";
import App from "./App";
import NotFound from "./pages/NotFound";
const routes = createBrowserRouter([
  {
    path: "/ums",
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
    path: "*",
    element: <Navigate to="/" replace />,
  },
]);

export default routes;
