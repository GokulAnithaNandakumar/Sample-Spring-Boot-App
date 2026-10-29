import axios from "axios";

const API = "http://localhost:8080/api/primary";

export async function getAll(params = {}) {
  const res = await axios.get(API, { params });
  return res.data;
}

export async function getById(uid) {
  const res = await axios.get(`${API}/${uid}`);
  return res.data;
}

export async function create(data) {
  const res = await axios.post(API, data);
  return res.data;
}

export async function update(uid, data) {
  const res = await axios.put(`${API}/${uid}`, data);
  return res.data;
}

export async function patch(uid, data) {
  const res = await axios.patch(`${API}/${uid}`, data);
  return res.data;
}

export async function remove(uid) {
  await axios.delete(`${API}/${uid}`);
}
