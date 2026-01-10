// components/sidebar/SidebarItem.tsx
import { NavLink, useLocation } from "react-router";

interface SidebarItemProps {
  to?: string;
  label: string;
  icon: React.ReactNode;
  onClick?: () => void;
  indent?: boolean;
  collapsed: boolean;
}

export function SidebarItem({
  to,
  label,
  onClick,
  indent = false,
  icon,
  collapsed,
}: SidebarItemProps) {
  const { pathname } = useLocation();

  const base =
    "flex items-center gap-2 rounded-lg px-3 py-2 text-sm font-medium transition";

  const indentStyle = indent ? "ml-4" : "";

  const isActive = to && pathname === to;

  if (onClick) {
    return (
      <button
        onClick={onClick}
        className={`${base} ${indentStyle} text-text-muted hover:text-text hover:bg-primary/10`}
      >
        <span className={isActive ? "text-white" : "text-primary/80"}>
          {icon}
        </span>

        {!collapsed && <span>{label}</span>}
      </button>
    );
  }

  return (
    <NavLink
      to={to!}
      className={`${base} ${indentStyle} ${collapsed ? "w-fit" : ""} 
        ${
          isActive
            ? "bg-primary text-white shadow-sm"
            : "text-text-muted hover:text-text hover:bg-primary/10"
        }`}
    >
      <span className={isActive ? "text-white" : "text-primary/80"}>
        {icon}
      </span>
      {!collapsed && <span>{label}</span>}
    </NavLink>
  );
}
