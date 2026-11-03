import React,{useEffect,useState} from 'react';
import axios from 'axios';
export default function GetAllBurgers({refreshKey=0}) {
  const [orders,setOrders]=useState([]); const [loading,setLoading]=useState(true); const [error,setError]=useState('');
  async function loadOrders(){
    setLoading(true); setError('');
    try { const response=await axios.get('/api/burgers/getAllOrders'); setOrders(response.data); }
    catch { setError('Could not load orders. Make sure the Spring Boot backend is running.'); }
    finally { setLoading(false); }
  }
  useEffect(()=>{loadOrders();},[refreshKey]);
  async function deleteOrder(id){
    try { await axios.delete(`/api/burgers/${id}`); setOrders(current=>current.filter(order=>order.id!==id)); }
    catch { setError('Could not delete this order. Please try again.'); }
  }
  return <section className="panel">
    <div className="section-heading"><h2>All Burger Orders</h2><button className="secondary-button" onClick={loadOrders} type="button">Refresh</button></div>
    {loading&&<p>Loading orders…</p>}{error&&<p className="error" role="alert">{error}</p>}
    {!loading&&!error&&orders.length===0&&<p className="muted">No orders yet.</p>}
    <div className="orders-list">{orders.map(order=><article className="order-card" key={order.id}>
      <div><h3>{order.burgerName}</h3><p>Quantity: {order.quantity}</p><p className="muted">{order.deliveryAddress}</p><small>Order #{order.id}</small></div>
      <button className="delete-button" type="button" onClick={()=>deleteOrder(order.id)}>Delete</button>
    </article>)}</div>
  </section>;
}
