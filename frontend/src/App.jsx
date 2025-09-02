import { useState } from 'react'
import { BrowserRouter, Route, Routes } from 'react-router'
import HomePage from './pages/HomePage'

import DashBoardPage from './pages/DashBoardPage'
import ProfilePage from './pages/ProfilePage'
import Navbar from './components/Navbar'
import LoginPage from './pages/LoginPage'
import RegisterPage from './pages/RegisterPage'


function App() {
  

  return (
    <BrowserRouter>
      <Navbar />

      <Routes>
        <Route path="/" element={<HomePage />} />
        <Route path="/home" element={<HomePage />} />
        <Route path="/login" element={<LoginPage />} />
        <Route path="/register" element={<RegisterPage />} />
        <Route path="/dashboard" element={<DashBoardPage />} />
        <Route path="/profile/:userId" element={<ProfilePage />} />
      </Routes>
      
    </BrowserRouter>
  )
}

export default App
