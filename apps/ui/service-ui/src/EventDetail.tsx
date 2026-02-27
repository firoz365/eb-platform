import { useEffect, useState } from "react";
import { useParams, useNavigate } from "react-router-dom";
import axios from "axios";

interface Event {
  id: number;
  type: string;
  message: string;
  createdAt: string;
}

function EventDetail() {
  const { id } = useParams();
  const navigate = useNavigate();
  const [events, setEvents] = useState<Event[]>([]);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    const query = `
      query {
        services(environment: PROD, status: ACTIVE) {
          id
          name
          environment
          status
          events(type: INFO) {
            id
            type
            message
            createdAt
          }
        }
      }
    `;

    axios
      .post("/graphql", { query })
      .then((response) => {
        const services = response.data.data.services;

        // find service by id from URL
        const selected = services.find(
          (s: any) => String(s.id) === String(id)
        );

        if (selected) {
          setEvents(selected.events);
        }

        setLoading(false);
      })
      .catch((error) => {
        console.error("GraphQL error:", error);
        setLoading(false);
      });
  }, [id]);

  return (
    <div style={containerStyle}>
      <button onClick={() => navigate("/")} style={backButton}>
        ← Back to Services
      </button>

      <h1>Events for Service ID: {id}</h1>

      {loading && <p>Loading events...</p>}

      {!loading && (
        <table style={tableStyle}>
          <thead>
            <tr style={{ backgroundColor: "#2c3e50", color: "white" }}>
              <th style={thStyle}>ID</th>
              <th style={thStyle}>Type</th>
              <th style={thStyle}>Message</th>
              <th style={thStyle}>Created At</th>
            </tr>
          </thead>
          <tbody>
            {events.map((event) => (
              <tr key={event.id}>
                <td style={tdStyle}>{event.id}</td>
                <td style={tdStyle}>{event.type}</td>
                <td style={tdStyle}>{event.message}</td>
                <td style={tdStyle}>{event.createdAt}</td>
              </tr>
            ))}
          </tbody>
        </table>
      )}
    </div>
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

const backButton: React.CSSProperties = {
  marginBottom: "20px",
  padding: "6px 12px",
  backgroundColor: "#3498db",
  color: "white",
  border: "none",
  borderRadius: "6px",
  cursor: "pointer",
};

export default EventDetail;
