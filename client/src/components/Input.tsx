import { useState, type ReactNode } from "react";
import { Eye, EyeOff } from "lucide-react";

export default function Input({
  id,
  name,
  htmlFor,
  reqired,
  className,
  label,
  type,
  isFocus,
  rightIcon,
  leftIcon,
}: {
  name?: string;
  type?: InputType;
  htmlFor?: string;
  label?: string;
  className?: string;
  id?: string;
  reqired?: boolean;
  passwordToggle?: boolean;
  isFocus?: boolean;
  rightIcon?: ReactNode;
  leftIcon?: ReactNode;
  defaultValue?: string;
  value?: string;
  onChange?: () => void;
}) {
  const [showPassword, setShowPassword] = useState(false);

  const [focus, setFocus] = useState(isFocus ? true : false);

  const classes = `w-full px-4 py-3 border border-gray-300 rounded-lg relative ${
    focus ? "ring-2 ring-blue-900 border-transparent" : ""
  } ${className}`;

  return (
    <div className="w-full">
      {label && (
        <label
          htmlFor={htmlFor}
          className="block text-sm font-medium text-gray-700 mb-2"
        >
          {label}
        </label>
      )}
      <div className={classes}>
        {leftIcon}
        <input
          onFocus={() => setFocus(true)}
          onBlur={() => setFocus(false)}
          type={showPassword ? "text" : type}
          id={id}
          name={name}
          required={reqired}
          className="outline-none w-full h-full"
          placeholder="Enter your password"
        />
        {type === "password" ? (
          <button
            onClick={() => setShowPassword((pre) => !pre)}
            className="absolute right-2 top-1/2 -translate-y-1/2 px-2 text-gray-600"
          >
            {showPassword ? <Eye /> : <EyeOff />}
          </button>
        ) : (
          <>{rightIcon && rightIcon}</>
        )}
      </div>
    </div>
  );
}

export type InputType = "text" | "password" | "email" | "checkbox";
