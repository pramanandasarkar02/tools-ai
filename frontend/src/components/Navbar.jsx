import React from 'react'
import { Link } from 'react-router-dom'

const Navbar = () => {
  return (
    <div className='flex justify-between py-3 px-2 bg-amber-400'>
        ToolsAI 
        <Link to="/" className='cursor-pointer'>Home</Link>
        <Link to="/login" className='cursor-pointer'>LogIn</Link>
        <Link to="/register" className='cursor-pointer'>Register</Link>
        <Link to="/dashboard" className='cursor-pointer'>DashBoard</Link>
        <Link to="/profile" className='cursor-pointer'>Profile</Link>

    </div>
  )
}

export default Navbar