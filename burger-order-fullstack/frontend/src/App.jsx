import React,{useState} from 'react';
import AddBurger from './Component/AddBurger.jsx';
import GetAllBurgers from './Component/GetAllBurgers.jsx';
export default function App(){
  const [refreshKey,setRefreshKey]=useState(0);
  return <main className="app-shell">
    <header className="hero"><p className="eyebrow">BURGER HOUSE</p><h1>Fresh burgers, easy ordering.</h1><p>Place an order and view existing orders.</p></header>
    <div className="content-grid"><AddBurger onOrderAdded={()=>setRefreshKey(v=>v+1)}/><GetAllBurgers refreshKey={refreshKey}/></div>
    <footer>Burger House · Burger ordering demo</footer>
  </main>;
}
