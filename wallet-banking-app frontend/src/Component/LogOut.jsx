import React, { useEffect } from "react";

export default function LogOut() {
  useEffect(() => {
    const isConfirmed = window.confirm("Are you sure you want to log out?");
    if (isConfirmed) {
      localStorage.clear();
      window.location.reload();
    } else {
      window.history.back();
    }
  }, []);
  return <div></div>;
}
