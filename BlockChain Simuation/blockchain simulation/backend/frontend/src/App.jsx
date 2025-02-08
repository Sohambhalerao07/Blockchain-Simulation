import React from "react";
import Navbar from "./components/NavBar";
import MainPage from "./components/MainPage";

function App() {
  return (
    <div className="bg-gray-900 min-h-screen">
      <Navbar />
      <MainPage />
    </div>
  );
}

export default App;