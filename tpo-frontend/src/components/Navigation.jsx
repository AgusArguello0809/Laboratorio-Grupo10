import React from "react";
import { Link } from "react-router-dom";

function Navigation() {
  return (
    <div style={{ display: "flex", justifyContent: "center", marginBottom: "20px" }}>
      <Link to="/" style={{ margin: "0 10px" }}>Home</Link>
      <Link to="/us" style={{ margin: "0 10px" }}>Nosotros</Link>
      <Link to="/my-publications" style={{ margin: "0 10px" }}>Mis Publicaciones</Link>
      <Link to="/sell" style={{ margin: "0 10px" }}>Vender</Link>
    </div>
  );
}

export default Navigation;