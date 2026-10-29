import React, { useEffect, useState } from "react";
import { useParams, Link } from "react-router-dom";
import * as api from "../services/primaryService";

export default function PrimaryDetail() {
  const { uid } = useParams();
  const [record, setRecord] = useState(null);
  const [error, setError] = useState("");
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    let active = true;
    setLoading(true);
    api.getById(uid)
      .then((data) => { if (active) setRecord(data); })
      .catch((e) => { if (active) setError(e.message); })
      .finally(() => { if (active) setLoading(false); });
    return () => { active = false; };
  }, [uid]);

  if (loading) return <p>Loading...</p>;
  if (error) return <p className="error">{error}</p>;
  if (!record) return <p>Not found.</p>;

  return (
    <div className="container">
      <Link to="/">Back</Link>
      <h1>Primary Record {record.uid}</h1>
      <p><strong>Name:</strong> {record.name}</p>
      <p><strong>Description:</strong> {record.description}</p>
      <p><strong>Age:</strong> {record.age}</p>
      <p><strong>Score:</strong> {record.score}</p>
      <p><strong>Active:</strong> {String(record.active)}</p>
      <p><strong>Gender:</strong> {record.gender}</p>
      <p><strong>Birth Date:</strong> {record.birthDate}</p>
      <p><strong>Reminder Time:</strong> {record.reminderTime}</p>
      <p><strong>Event Date Time:</strong> {record.eventDateTime}</p>
      <p><strong>Tags:</strong> {(record.tags || []).join(", ")}</p>
      <p><strong>Priority:</strong> {record.priority}</p>
      <p><strong>Status:</strong> {record.status}</p>
      <p><strong>Country:</strong> {record.country}</p>
      <p><strong>Created:</strong> {record.createdAt}</p>
      <p><strong>Updated:</strong> {record.updatedAt}</p>
      <h2>Foreign Records</h2>
      {(record.foreignRecords || []).length === 0 ? (
        <p>No foreign records.</p>
      ) : (
        <ol>
          {record.foreignRecords.map((c) => (
            <li key={c.uid}>uid {c.uid} — {c.value} (qty {c.quantity}, active {String(c.active)})</li>
          ))}
        </ol>
      )}
    </div>
  );
}
