import { BrowserRouter, Routes, Route, Link } from "react-router-dom";
import PrimaryRecordForm from "./components/PrimaryRecordForm";
import PrimaryDetail from "./components/PrimaryDetail";
import "./App.css";

function App() {
  return (
    <BrowserRouter>
      <Routes>
        <Route path="/" element={<PrimaryRecordForm />} />
        <Route path="/data/:uid" element={<PrimaryDetail />} />
      </Routes>
    </BrowserRouter>
  );
}

export default App;
