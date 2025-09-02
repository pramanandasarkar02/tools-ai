import React, { useState, useEffect } from 'react';
import { useParams } from 'react-router-dom';
import { FaUser, FaEnvelope, FaCalendar, FaSave, FaEdit, FaBan, FaCheckCircle } from 'react-icons/fa';

const UserProfile = () => {
  const { userId } = useParams();
  const [user, setUser] = useState(null);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState('');
  const [isEditing, setIsEditing] = useState(false);
  const [formData, setFormData] = useState({
    firstName: '',
    lastName: '',
    username: '',
    email: ''
  });

  useEffect(() => {
    fetchUser();
  }, [userId]);

  const fetchUser = async () => {
    try {
      setLoading(true);
      const response = await fetch(`http://localhost:8081/api/v1/user/${userId}`);
      
      if (!response.ok) {
        throw new Error(`HTTP error! status: ${response.status}`);
      }
      
      const userData = await response.json();
      setUser(userData);
      setFormData({
        firstName: userData.firstName,
        lastName: userData.lastName,
        username: userData.username,
        email: userData.email
      });
      setError('');
    } catch (err) {
      setError('Failed to fetch user data. Please try again.');
      console.error('Error fetching user:', err);
    } finally {
      setLoading(false);
    }
  };

  const handleInputChange = (e) => {
    const { name, value } = e.target;
    setFormData({
      ...formData,
      [name]: value
    });
  };

  const handleSave = async () => {
    try {
      setLoading(true);
      const response = await fetch(`http://localhost:8081/api/v1/user/${userId}`, {
        method: 'PUT',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify(formData),
      });
      
      if (!response.ok) {
        throw new Error(`HTTP error! status: ${response.status}`);
      }
      
      const updatedUser = await response.json();
      setUser(updatedUser);
      setIsEditing(false);
      setError('');
    } catch (err) {
      setError('Failed to update user. Please try again.');
      console.error('Error updating user:', err);
    } finally {
      setLoading(false);
    }
  };

  const handleBanUser = async () => {
    try {
      const response = await fetch(`http://localhost:8081/api/v1/user/admin/${userId}/ban/${userId}`, {
        method: 'POST',
      });
      
      if (!response.ok) {
        throw new Error(`HTTP error! status: ${response.status}`);
      }
      
      // Refresh user data
      fetchUser();
    } catch (err) {
      setError('Failed to ban user. Please try again.');
      console.error('Error banning user:', err);
    }
  };

  const handleUnbanUser = async () => {
    try {
      const response = await fetch(`http://localhost:8081/api/v1/user/admin/${userId}/unban/${userId}`, {
        method: 'POST',
      });
      
      if (!response.ok) {
        throw new Error(`HTTP error! status: ${response.status}`);
      }
      
      // Refresh user data
      fetchUser();
    } catch (err) {
      setError('Failed to unban user. Please try again.');
      console.error('Error unbanning user:', err);
    }
  };

  if (loading) {
    return (
      <div className="min-h-screen bg-gray-50 flex items-center justify-center">
        <div className="text-center">
          <div className="animate-spin rounded-full h-12 w-12 border-b-2 border-blue-600 mx-auto"></div>
          <p className="mt-4 text-gray-600">Loading user profile...</p>
        </div>
      </div>
    );
  }

  if (error) {
    return (
      <div className="min-h-screen bg-gray-50 flex items-center justify-center">
        <div className="bg-red-100 border border-red-400 text-red-700 px-4 py-3 rounded max-w-md">
          <p>{error}</p>
          <button 
            onClick={fetchUser}
            className="mt-4 bg-red-600 hover:bg-red-700 text-white font-bold py-2 px-4 rounded"
          >
            Try Again
          </button>
        </div>
      </div>
    );
  }

  return (
    <div className="min-h-screen bg-gray-50 py-8">
      <div className="max-w-4xl mx-auto px-4 sm:px-6 lg:px-8">
        <div className="bg-white shadow rounded-lg overflow-hidden">
          {/* Profile Header */}
          <div className="bg-gradient-to-r from-blue-600 to-indigo-700 px-6 py-8 text-white">
            <div className="flex flex-col md:flex-row items-center">
              <div className="flex-shrink-0 h-24 w-24 rounded-full bg-white bg-opacity-20 flex items-center justify-center">
                <FaUser className="h-12 w-12 text-white" />
              </div>
              <div className="mt-4 md:mt-0 md:ml-6 text-center md:text-left">
                <h1 className="text-2xl font-bold">
                  {user.firstName} {user.lastName}
                </h1>
                <p className="text-blue-100">@{user.username}</p>
                <div className="flex items-center mt-2">
                  <span className={`px-2 py-1 rounded-full text-xs font-medium ${
                    user.banned ? 'bg-red-200 text-red-800' : 'bg-green-200 text-green-800'
                  }`}>
                    {user.banned ? 'Banned' : 'Active'}
                  </span>
                  <span className="ml-2 px-2 py-1 rounded-full bg-blue-200 text-blue-800 text-xs font-medium">
                    {user.role}
                  </span>
                </div>
              </div>
            </div>
          </div>

          {/* Profile Content */}
          <div className="px-6 py-6">
            <div className="flex justify-between items-center mb-6">
              <h2 className="text-lg font-medium text-gray-900">Profile Information</h2>
              <div className="flex space-x-2">
                {user.banned ? (
                  <button
                    onClick={handleUnbanUser}
                    className="flex items-center px-4 py-2 bg-green-600 text-white rounded-md hover:bg-green-700"
                  >
                    <FaCheckCircle className="mr-2" /> Unban User
                  </button>
                ) : (
                  <button
                    onClick={handleBanUser}
                    className="flex items-center px-4 py-2 bg-red-600 text-white rounded-md hover:bg-red-700"
                  >
                    <FaBan className="mr-2" /> Ban User
                  </button>
                )}
                {isEditing ? (
                  <button
                    onClick={handleSave}
                    className="flex items-center px-4 py-2 bg-blue-600 text-white rounded-md hover:bg-blue-700"
                  >
                    <FaSave className="mr-2" /> Save Changes
                  </button>
                ) : (
                  <button
                    onClick={() => setIsEditing(true)}
                    className="flex items-center px-4 py-2 bg-gray-600 text-white rounded-md hover:bg-gray-700"
                  >
                    <FaEdit className="mr-2" /> Edit Profile
                  </button>
                )}
              </div>
            </div>

            <div className="grid grid-cols-1 md:grid-cols-2 gap-6">
              <div className="col-span-1">
                <label className="block text-sm font-medium text-gray-700 mb-1">First Name</label>
                {isEditing ? (
                  <input
                    type="text"
                    name="firstName"
                    value={formData.firstName}
                    onChange={handleInputChange}
                    className="w-full px-4 py-2 border border-gray-300 rounded-md focus:ring-blue-500 focus:border-blue-500"
                  />
                ) : (
                  <p className="px-4 py-2 bg-gray-50 rounded-md">{user.firstName}</p>
                )}
              </div>

              <div className="col-span-1">
                <label className="block text-sm font-medium text-gray-700 mb-1">Last Name</label>
                {isEditing ? (
                  <input
                    type="text"
                    name="lastName"
                    value={formData.lastName}
                    onChange={handleInputChange}
                    className="w-full px-4 py-2 border border-gray-300 rounded-md focus:ring-blue-500 focus:border-blue-500"
                  />
                ) : (
                  <p className="px-4 py-2 bg-gray-50 rounded-md">{user.lastName}</p>
                )}
              </div>

              <div className="col-span-1">
                <label className="block text-sm font-medium text-gray-700 mb-1">Username</label>
                {isEditing ? (
                  <input
                    type="text"
                    name="username"
                    value={formData.username}
                    onChange={handleInputChange}
                    className="w-full px-4 py-2 border border-gray-300 rounded-md focus:ring-blue-500 focus:border-blue-500"
                  />
                ) : (
                  <p className="px-4 py-2 bg-gray-50 rounded-md">@{user.username}</p>
                )}
              </div>

              <div className="col-span-1">
                <label className="block text-sm font-medium text-gray-700 mb-1">Email</label>
                {isEditing ? (
                  <input
                    type="email"
                    name="email"
                    value={formData.email}
                    onChange={handleInputChange}
                    className="w-full px-4 py-2 border border-gray-300 rounded-md focus:ring-blue-500 focus:border-blue-500"
                  />
                ) : (
                  <div className="flex items-center px-4 py-2 bg-gray-50 rounded-md">
                    <FaEnvelope className="text-gray-400 mr-2" />
                    <span>{user.email}</span>
                  </div>
                )}
              </div>

              <div className="col-span-1">
                <label className="block text-sm font-medium text-gray-700 mb-1">User ID</label>
                <p className="px-4 py-2 bg-gray-50 rounded-md text-sm font-mono">{user.id}</p>
              </div>

              <div className="col-span-1">
                <label className="block text-sm font-medium text-gray-700 mb-1">Role</label>
                <p className="px-4 py-2 bg-gray-50 rounded-md capitalize">{user.role.toLowerCase()}</p>
              </div>

              <div className="col-span-1">
                <label className="block text-sm font-medium text-gray-700 mb-1">Status</label>
                <p className={`px-4 py-2 rounded-md ${
                  user.banned ? 'bg-red-100 text-red-800' : 'bg-green-100 text-green-800'
                }`}>
                  {user.banned ? 'Banned' : 'Active'}
                </p>
              </div>

              <div className="col-span-1">
                <label className="block text-sm font-medium text-gray-700 mb-1">Member Since</label>
                <div className="flex items-center px-4 py-2 bg-gray-50 rounded-md">
                  <FaCalendar className="text-gray-400 mr-2" />
                  <span>January 2023</span>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
};

export default UserProfile;