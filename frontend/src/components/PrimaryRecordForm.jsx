import React, { useState, useEffect } from "react";
import { Link } from "react-router-dom";
import * as api from "../services/primaryService";

const emptyForm = {
  name: "", description: "", age: "", score: "", active: false, gender: "Other",
  birthDate: "", reminderTime: "", eventDateTime: "", tags: [], priority: 5,
  status: "ACTIVE", country: "", foreignRecords: []
};

export default function PrimaryRecordForm() {
  const [form, setForm] = useState(emptyForm);
  const [records, setRecords] = useState([]);
  const [selectedUid, setSelectedUid] = useState(null);
  const [loading, setLoading] = useState(false);
  const [submitting, setSubmitting] = useState(false);
  const [message, setMessage] = useState("");
  const [error, setError] = useState("");
  const [tagInput, setTagInput] = useState("");
  const [child, setChild] = useState({ value: "", quantity: "", active: true });
  const [patchStatus, setPatchStatus] = useState("ACTIVE");
  const selectedRecord = records.find((r) => r.uid === selectedUid);

  const loadAll = async () => {
    setLoading(true);
    try { setRecords(await api.getAll()); }
    catch (e) { setError(e.message); }
    finally { setLoading(false); }
  };
  useEffect(() => { loadAll(); }, []);

  const resetForm = () => { setForm(emptyForm); setSelectedUid(null); };

  const selectRecord = (r) => {
    setSelectedUid(r.uid);
    setForm({
      name: r.name ?? "", description: r.description ?? "", age: r.age ?? "",
      score: r.score ?? "", active: !!r.active, gender: r.gender ?? "Other",
      birthDate: r.birthDate ?? "", reminderTime: r.reminderTime ?? "",
      eventDateTime: r.eventDateTime ?? "", tags: r.tags ?? [], priority: r.priority ?? 5,
      status: r.status ?? "ACTIVE", country: r.country ?? "",
      foreignRecords: r.foreignRecords ?? []
    });
  };

  const setField = (k, v) => setForm((f) => ({ ...f, [k]: v }));

  const addTag = () => {
    const t = tagInput.trim();
    if (!t) return;
    if (form.tags.includes(t)) { setTagInput(""); return; }
    setField("tags", [...form.tags, t]);
    setTagInput("");
  };
  const removeTag = (i) => setField("tags", form.tags.filter((_, idx) => idx !== i));

  const addChild = () => {
    if (!child.value.trim()) return;
    setField("foreignRecords", [...form.foreignRecords, {
      value: child.value.trim(),
      quantity: child.quantity === "" ? null : Number(child.quantity),
      active: child.active
    }]);
    setChild({ value: "", quantity: "", active: true });
  };
  const updateChild = (i, k, v) => {
    const arr = [...form.foreignRecords];
    arr[i] = { ...arr[i], [k]: v };
    setField("foreignRecords", arr);
  };
  const removeChild = (i) => setField("foreignRecords", form.foreignRecords.filter((_, idx) => idx !== i));

  const toPayload = () => ({
    name: form.name || null,
    description: form.description || null,
    age: form.age === "" ? null : Number(form.age),
    score: form.score === "" ? null : Number(form.score),
    active: form.active,
    gender: form.gender,
    birthDate: form.birthDate || null,
    reminderTime: form.reminderTime || null,
    eventDateTime: form.eventDateTime || null,
    tags: form.tags,
    priority: form.priority === "" ? null : Number(form.priority),
    status: form.status,
    country: form.country || null,
    foreignRecords: form.foreignRecords.map((c) => ({
      ...(c.uid ? { uid: c.uid } : {}),
      value: c.value,
      quantity: c.quantity === "" ? null : (c.quantity == null ? null : Number(c.quantity)),
      active: c.active
    }))
  });

  const handleSubmit = async (e) => {
    e.preventDefault();
    if (submitting) return;
    setSubmitting(true); setError(""); setMessage("");
    try {
      if (selectedUid) {
        await api.update(selectedUid, toPayload());
        setMessage("Updated");
      } else {
        await api.create(toPayload());
        setMessage("Created");
        resetForm();
      }
      await loadAll();
    } catch (e) { setError(e.message); }
    finally { setSubmitting(false); }
  };

  const handlePatchStatus = async () => {
    if (!selectedUid || submitting) return;
    setSubmitting(true); setError(""); setMessage("");
    try {
      await api.patch(selectedUid, { status: patchStatus });
      setMessage("Patched status");
      await loadAll();
    } catch (e) { setError(e.message); }
    finally { setSubmitting(false); }
  };

  const handlePatchActive = async () => {
    if (!selectedUid || submitting) return;
    setSubmitting(true); setError(""); setMessage("");
    try {
      await api.patch(selectedUid, { active: !form.active });
      setMessage("Patched active");
      await loadAll();
    } catch (e) { setError(e.message); }
    finally { setSubmitting(false); }
  };

  const handleDelete = async (uid) => {
    if (!window.confirm("Delete this record?")) return;
    if (submitting) return;
    setSubmitting(true); setError(""); setMessage("");
    try {
      await api.remove(uid);
      if (selectedUid === uid) resetForm();
      setMessage("Deleted");
      await loadAll();
    } catch (e) { setError(e.message); }
    finally { setSubmitting(false); }
  };

  return (
    <div className="container">
      <h1>Primary Records</h1>
      {message && <p className="success">{message}</p>}
      {error && <p className="error">{error}</p>}

      <form onSubmit={handleSubmit} className="form">
        <label>Name <input type="text" value={form.name} onChange={(e) => setField("name", e.target.value)} required /></label>
        <label>Description <textarea value={form.description} onChange={(e) => setField("description", e.target.value)} /></label>
        <label>Age <input type="number" value={form.age} onChange={(e) => setField("age", e.target.value)} /></label>
        <label>Score <input type="number" step="0.1" value={form.score} onChange={(e) => setField("score", e.target.value)} /></label>
        <label><input type="checkbox" checked={form.active} onChange={(e) => setField("active", e.target.checked)} /> Active</label>

        <fieldset>
          <legend>Gender</legend>
          {["Male", "Female", "Other", "Prefer not to say"].map((g) => (
            <label key={g}><input type="radio" name="gender" value={g} checked={form.gender === g} onChange={() => setField("gender", g)} /> {g}</label>
          ))}
        </fieldset>

        <label>Birth Date <input type="date" value={form.birthDate} onChange={(e) => setField("birthDate", e.target.value)} /></label>
        <label>Reminder Time <input type="time" step="1" value={form.reminderTime} onChange={(e) => setField("reminderTime", e.target.value)} /></label>
        <label>Event Date Time <input type="datetime-local" step="1" value={form.eventDateTime} onChange={(e) => setField("eventDateTime", e.target.value)} /></label>

        <label>Tags
          <div>
            <input list="tag-suggestions" value={tagInput} onChange={(e) => setTagInput(e.target.value)} />
            <datalist id="tag-suggestions">
              {["Java", "Spring Boot", "React", "PostgreSQL", "Docker"].map((t) => <option key={t} value={t} />)}
            </datalist>
            <button type="button" onClick={addTag}>Add Tag</button>
          </div>
          <ul>{form.tags.map((t, i) => <li key={i}>{t} <button type="button" onClick={() => removeTag(i)}>Remove</button></li>)}</ul>
        </label>

        <label>Priority <input type="range" min="1" max="10" value={form.priority} onChange={(e) => setField("priority", Number(e.target.value))} /> {form.priority}</label>

        <label>Country <input list="country-suggestions" value={form.country} onChange={(e) => setField("country", e.target.value)} />
          <datalist id="country-suggestions">
            {["India", "United States", "Germany", "Australia", "Canada"].map((c) => <option key={c} value={c} />)}
          </datalist>
        </label>

        <label>Status
          <select value={form.status} onChange={(e) => setField("status", e.target.value)}>
            <option value="ACTIVE">ACTIVE</option>
            <option value="INACTIVE">INACTIVE</option>
            <option value="PENDING">PENDING</option>
          </select>
        </label>

        <section>
          <h3>Associated Foreign Records</h3>
          <div>
            <input placeholder="value" value={child.value} onChange={(e) => setChild({ ...child, value: e.target.value })} />
            <input type="number" placeholder="quantity" value={child.quantity} onChange={(e) => setChild({ ...child, quantity: e.target.value })} />
            <label><input type="checkbox" checked={child.active} onChange={(e) => setChild({ ...child, active: e.target.checked })} /> active</label>
            <button type="button" onClick={addChild}>Add Child</button>
          </div>
          <ul>
            {form.foreignRecords.map((c, i) => (
              <li key={i}>
                <input value={c.value ?? ""} onChange={(e) => updateChild(i, "value", e.target.value)} />
                <input type="number" value={c.quantity ?? ""} onChange={(e) => updateChild(i, "quantity", e.target.value)} />
                <label><input type="checkbox" checked={!!c.active} onChange={(e) => updateChild(i, "active", e.target.checked)} /> active</label>
                <button type="button" onClick={() => removeChild(i)}>Remove</button>
              </li>
            ))}
          </ul>
        </section>

        <div className="buttons">
          <button type="submit" disabled={submitting}>{selectedUid ? "Update (PUT)" : "Create"}</button>
          {selectedUid && <button type="button" onClick={resetForm} disabled={submitting}>Cancel Edit</button>}
        </div>

        {selectedUid && (
          <div className="patch">
            <h4>PATCH (single field)</h4>
            <select value={patchStatus} onChange={(e) => setPatchStatus(e.target.value)}>
              <option value="ACTIVE">ACTIVE</option>
              <option value="INACTIVE">INACTIVE</option>
              <option value="PENDING">PENDING</option>
            </select>
            <button type="button" onClick={handlePatchStatus} disabled={submitting}>Patch Status</button>
            <button type="button" onClick={handlePatchActive} disabled={submitting}>Patch Active</button>
          </div>
        )}
      </form>

      {selectedRecord && (
        <section className="detail">
          <h2>Selected Record Details (uid {selectedRecord.uid})</h2>
          <p><strong>Name:</strong> {selectedRecord.name}</p>
          <p><strong>Description:</strong> {selectedRecord.description}</p>
          <p><strong>Age:</strong> {selectedRecord.age}</p>
          <p><strong>Score:</strong> {selectedRecord.score}</p>
          <p><strong>Active:</strong> {String(selectedRecord.active)}</p>
          <p><strong>Gender:</strong> {selectedRecord.gender}</p>
          <p><strong>Birth Date:</strong> {selectedRecord.birthDate}</p>
          <p><strong>Reminder Time:</strong> {selectedRecord.reminderTime}</p>
          <p><strong>Event Date Time:</strong> {selectedRecord.eventDateTime}</p>
          <p><strong>Tags:</strong> {(selectedRecord.tags || []).join(", ")}</p>
          <p><strong>Priority:</strong> {selectedRecord.priority}</p>
          <p><strong>Status:</strong> {selectedRecord.status}</p>
          <p><strong>Country:</strong> {selectedRecord.country}</p>
          <p><strong>Created:</strong> {selectedRecord.createdAt}</p>
          <p><strong>Updated:</strong> {selectedRecord.updatedAt}</p>
          <h3>Foreign Records</h3>
          {(selectedRecord.foreignRecords || []).length === 0 ? (
            <p>No foreign records.</p>
          ) : (
            <ol>
              {selectedRecord.foreignRecords.map((c) => (
                <li key={c.uid}>
                  uid {c.uid} — {c.value} (qty {c.quantity}, active {String(c.active)})
                </li>
              ))}
            </ol>
          )}
        </section>
      )}

      <h2>Records</h2>
      {loading ? <p>Loading...</p> : records.length === 0 ? <p>No records found.</p> : (
        <ol>
          {records.map((r) => (
            <li key={r.uid}>
              <strong>{r.name}</strong> (uid {r.uid}) - {r.status}
              <Link to={`/data/${r.uid}`}>View</Link>
              <button type="button" onClick={() => selectRecord(r)} disabled={submitting}>Edit</button>
              <button type="button" onClick={() => handleDelete(r.uid)} disabled={submitting}>Delete</button>
              <div>children: {r.foreignRecords ? r.foreignRecords.length : 0}</div>
            </li>
          ))}
        </ol>
      )}
    </div>
  );
}
