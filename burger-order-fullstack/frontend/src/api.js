import axios from "axios";

const api = axios.create({ baseURL: "/api" });

export async function getOrders() {
    const response = await api.get("/burgers/getAllOrders");
    return response.data;
}

export async function addOrder(order) {
    const response = await api.post("/burgers/addOrder", order);
    return response.data;
}

export async function deleteOrder(id) {
    await api.delete(`/burgers/${id}`);
}

export async function askAssistant(message) {
    const response = await api.post("/ai/recommend", { message });
    return response.data;
}
