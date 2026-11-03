import React, {useState} from 'react';
import axios from 'axios';
const burgerOptions=['Veg-Burger','Chicken-Burger','Veg-CrunchBurger','Chicken-CrunchBurger'];

/** Provides a validated form for submitting a burger order. */
export default function AddBurger({onOrderAdded}) {
  const [form,setForm]=useState({burgerName:'',quantity:'1',deliveryAddress:''});
  const [isSubmitting,setIsSubmitting]=useState(false);
  const [errorMessage,setErrorMessage]=useState('');

  /*
   * Requirement 5:
   * TODOS:
   * - Implement handleFieldChange to update the form state when a field value changes.
   * - Use the input field name and value to update the corresponding form property.
   * - Clear any previous error message when the user changes a field.
   */
  function handleFieldChange(event) {
    const {name,value}=event.target;
    setForm(previousForm=>({...previousForm,[name]:value}));
    setErrorMessage('');
  }

  async function handleSubmit(event) {
    event.preventDefault();
    setIsSubmitting(true);
    setErrorMessage('');
    try {
      const response=await axios.post('/api/burgers/addOrder',{...form,quantity:Number(form.quantity)});
      onOrderAdded?.(response.data);
      setForm({burgerName:'',quantity:'1',deliveryAddress:''});
    } catch(error) {
      const responseMessage=error.response?.data?.detail ?? error.response?.data?.message ?? error.response?.data;
      setErrorMessage(typeof responseMessage==='string' ? responseMessage : 'Order could not be placed. Check the details and try again.');
    } finally { setIsSubmitting(false); }
  }

  return <section className="panel">
    <h2>Place a Burger Order</h2>
    <form onSubmit={handleSubmit} className="order-form">
      <label htmlFor="burgerName">Burger</label>
      <select id="burgerName" name="burgerName" value={form.burgerName} onChange={handleFieldChange} required>
        <option value="">Select a burger</option>
        {burgerOptions.map(burger=><option key={burger} value={burger}>{burger}</option>)}
      </select>
      <label htmlFor="quantity">Quantity</label>
      <input id="quantity" name="quantity" type="number" min="1" max="50" value={form.quantity} onChange={handleFieldChange} required/>
      <label htmlFor="deliveryAddress">Delivery address</label>
      <textarea id="deliveryAddress" name="deliveryAddress" rows="3" value={form.deliveryAddress} onChange={handleFieldChange} placeholder="Enter your delivery address" required/>
      {errorMessage && <p className="error" role="alert">{errorMessage}</p>}
      <button type="submit" disabled={isSubmitting}>{isSubmitting?'Placing order…':'Place Order'}</button>
    </form>
  </section>;
}
