// components/sidebar/SidebarGroup.tsx
import { type ReactNode } from "react";

export function SidebarGroup({
  title,
  children,
  collapsed,
}: {
  title: string;
  children: ReactNode;
  collapsed: boolean;
}) {
  return (
    <div className="mt-6">
      {!collapsed && (
        <p className="px-3 mb-2 text-xs font-semibold tracking-wide text-text-muted">
          {title}
        </p>
      )}
      <div className="space-y-1">{children}</div>
    </div>
  );
}
