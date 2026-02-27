import { useEffect, useState } from "react";
import axios from "axios";

function App() {
  const [services, setServices] = useState([]);
  const [selectedService, setSelectedService] = useState(null);

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
      <h1 style={{ color: "#222" }}>Services</h1>

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
                    e.stopPropagation(); // prevent row selection
                    alert(`Navigate to events of ${service.name}`);
                  }}
                >
                  Go to Event Detail
                </button>
              </td>
            </tr>
          ))}
        </tbody>
      </table>

      {selectedService && (
        <div style={detailBoxStyle}>
          <h2 style={{ color: "#222" }}>Selected Service Details</h2>
          <p><strong>Name:</strong> {selectedService.name}</p>
          <p><strong>Description:</strong> {selectedService.description}</p>
          <p><strong>Created At:</strong> {selectedService.createdAt}</p>
        </div>
      )}
    </div>
  );
}

/* ---------- Styles ---------- */

const containerStyle = {
  padding: "40px",
  backgroundColor: "#f5f7fa",
  minHeight: "100vh",
  fontFamily: "Segoe UI, Arial, sans-serif",
  color: "#333",
};

const tableStyle = {
  width: "100%",
  borderCollapse: "collapse",
  marginTop: "20px",
  backgroundColor: "white",
  boxShadow: "0 4px 10px rgba(0,0,0,0.05)",
};

const thStyle = {
  padding: "12px",
  textAlign: "left",
};

const tdStyle = {
  padding: "12px",
  borderBottom: "1px solid #eee",
  color: "#333",
};

const buttonStyle = {
  padding: "6px 12px",
  backgroundColor: "#3498db",
  color: "white",
  border: "none",
  borderRadius: "6px",
  cursor: "pointer",
};

const detailBoxStyle = {
  marginTop: "30px",
  padding: "20px",
  backgroundColor: "white",
  borderRadius: "10px",
  boxShadow: "0 4px 10px rgba(0,0,0,0.05)",
};

export default App;
