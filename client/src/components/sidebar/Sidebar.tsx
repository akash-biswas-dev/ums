// components/sidebar/Sidebar.tsx
import { SidebarItem } from "./SidebarItem";
import { SidebarGroup } from "./SidebarGroup";
import {
  Home,
  User,
  UserCog,
  KeyRound,
  Bell,
  Mail,
  HelpCircle,
  LogOut,
  ChevronLeft,
  Building,
} from "lucide-react";
import { useState } from "react";

export function Sidebar() {
  const [collapsed, setCollapsed] = useState<boolean>(false);

  const handleLogout = () => {
    console.log("logout");
  };

  return (
    <aside
      className={`h-screen flex flex-col transition-all duration-300 relative
  ${collapsed ? "w-20" : "w-72"}
  bg-primary/5 border-r border-primary/20`}
    >
      {/* Brand */}
      <div className="px-5 py-5 border-b border-border overflow-clip">
        <div className={`transition ${collapsed ? "hidden" : "block"} `}>
          <h1 className="text-lg font-semibold text-primary">
            University Portal
          </h1>
          <p className="text-xs text-text-muted mt-1">
            Academic Management System
          </p>
        </div>
        {collapsed && <Building />}
      </div>

      <button
        onClick={() => setCollapsed(!collapsed)}
        className="absolute -right-3 top-6 bg-white border
             rounded-full p-1 shadow hover:bg-primary/10"
      >
        <ChevronLeft
          size={16}
          className={`transition ${collapsed && "rotate-180"}`}
        />
      </button>

      {/* Navigation */}
      <nav className="flex-1 overflow-y-auto px-3 py-4">
        <SidebarItem
          to="/"
          label="Home"
          icon={<Home size={18} />}
          collapsed={collapsed}
        />

        <SidebarGroup title="My Profile" collapsed={collapsed}>
          <SidebarItem
            to="/profile/view"
            label="View Profile"
            indent={!collapsed}
            icon={<User size={16} />}
            collapsed={collapsed}
          />
          <SidebarItem
            to="/profile/update"
            label="Update Profile"
            indent={!collapsed}
            icon={<UserCog size={16} />}
            collapsed={collapsed}
          />
          <SidebarItem
            to="/profile/change-password"
            label="Change Password"
            indent={!collapsed}
            icon={<KeyRound size={16} />}
            collapsed={collapsed}
          />
        </SidebarGroup>

        <SidebarGroup title="Communication" collapsed={collapsed}>
          <SidebarItem
            to="/notifications"
            label="Notifications"
            icon={<Bell size={16} />}
            collapsed={collapsed}
          />
          <SidebarItem
            to="/messages"
            label="Messages / Inbox"
            icon={<Mail size={16} />}
            collapsed={collapsed}
          />
        </SidebarGroup>

        <SidebarGroup title="Support" collapsed>
          <SidebarItem
            to="/help"
            label="Help & Support"
            icon={<HelpCircle size={16} />}
            collapsed={collapsed}
          />
        </SidebarGroup>
      </nav>

      {/* Logout */}
      <div className="px-3 py-4 border-t border-border">
        <SidebarItem
          label="Logout"
          onClick={handleLogout}
          icon={<LogOut size={16} />}
          collapsed={collapsed}
        />
      </div>
    </aside>
  );
}
