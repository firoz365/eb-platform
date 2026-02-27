import { Routes, Route, useNavigate } from "react-router-dom";
import { useEffect, useState } from "react";
import axios from "axios";
import EventDetail from "./EventDetail";

interface Service {
  id: number;
  name: string;
  description: string;
  owner: string;
  status: string;
  environment: string;
  createdAt: string;
}

function ServicesPage() {
  const [services, setServices] = useState<Service[]>([]);
  const [selectedService, setSelectedService] = useState<Service | null>(null);
  const navigate = useNavigate();

  useEffect(() => {
    axios
      .get("/api/v1/services")
      .then((response) => {
        const data = Array.isArray(response.data)
          ? response.data
          : [response.data];
        setServices(data);
      })
      .catch((error) => {
        console.error("Error fetching services:", error);
      });
  }, []);

  return (
    <div style={containerStyle}>
      <h1>Services</h1>

      <table style={tableStyle}>
        <thead>
          <tr style={{ backgroundColor: "#2c3e50", color: "white" }}>
            <th style={thStyle}>Name</th>
            <th style={thStyle}>Status</th>
            <th style={thStyle}>Owner</th>
            <th style={thStyle}>Environment</th>
            <th style={thStyle}>Action</th>
          </tr>
        </thead>

        <tbody>
          {services.map((service) => (
            <tr
              key={service.id}
              onClick={() => setSelectedService(service)}
              style={{
                backgroundColor:
                  selectedService?.id === service.id
                    ? "#d6eaf8"
                    : "#ffffff",
                cursor: "pointer",
              }}
            >
              <td style={tdStyle}>{service.name}</td>
              <td style={tdStyle}>{service.status}</td>
              <td style={tdStyle}>{service.owner}</td>
              <td style={tdStyle}>{service.environment}</td>
              <td style={tdStyle}>
                <button
                  style={buttonStyle}
                  onClick={(e) => {
                    e.stopPropagation();
                    navigate(`/services/${service.id}/events`);
                  }}
                >
                  Go to Event Detail
                </button>
              </td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
}

function App() {
  return (
    <Routes>
      <Route path="/" element={<ServicesPage />} />
      <Route path="/services/:id/events" element={<EventDetail />} />
    </Routes>
  );
}

/* ---------- Styles ---------- */

const containerStyle: React.CSSProperties = {
  padding: "40px",
  backgroundColor: "#f5f7fa",
  minHeight: "100vh",
  fontFamily: "Segoe UI, Arial",
};

const tableStyle: React.CSSProperties = {
  width: "100%",
  borderCollapse: "collapse",
  marginTop: "20px",
  backgroundColor: "white",
};

const thStyle: React.CSSProperties = {
  padding: "12px",
  textAlign: "left",
};

const tdStyle: React.CSSProperties = {
  padding: "12px",
  borderBottom: "1px solid #eee",
};

const buttonStyle: React.CSSProperties = {
  padding: "6px 12px",
  backgroundColor: "#3498db",
  color: "white",
  border: "none",
  borderRadius: "6px",
  cursor: "pointer",
};

export default App;
