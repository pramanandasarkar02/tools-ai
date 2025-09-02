import React, { useState, useEffect } from 'react';
import axios from 'axios';
import { FaUsers, FaBan, FaCheckCircle, FaSearch, FaSpinner } from 'react-icons/fa';

const AdminDashboard = () => {
  const [users, setUsers] = useState([]);
  const [filteredUsers, setFilteredUsers] = useState([]);
  const [searchTerm, setSearchTerm] = useState('');
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState('');
  const [actionLoading, setActionLoading] = useState(null);
  const adminID = "10856bf8-37dc-4d43-a9b1-e7aa51d43b1e";

  useEffect(() => {
    fetchUsers();
  }, []);

  useEffect(() => {
    const filtered = users.filter(user => 
      user.username.toLowerCase().includes(searchTerm.toLowerCase()) ||
      user.email.toLowerCase().includes(searchTerm.toLowerCase()) ||
      `${user.firstName} ${user.lastName}`.toLowerCase().includes(searchTerm.toLowerCase())
    );
    setFilteredUsers(filtered);
  }, [searchTerm, users]);

  const fetchUsers = async () => {
    try {
      setLoading(true);
      const response = await axios.get(`http://localhost:8081/api/v1/user/admin/${adminID}`, {
        headers: {
          'Content-Type': 'application/json',
          // Add Authorization header if required, e.g.:
          // 'Authorization': 'Bearer <your-token-here>'
        },
      });

      setUsers(response.data);
      setFilteredUsers(response.data);
      setError('');
    } catch (err) {
      const errorMessage = err.response
        ? `Failed to fetch users: ${err.response.status} - ${err.response.data.message || err.message}`
        : `Failed to fetch users: Network error or CORS issue - ${err.message}`;
      setError(errorMessage);
      console.error('Error fetching users:', err);
    } finally {
      setLoading(false);
    }
  };

  const handleBanUser = async (userId) => {
    try {
      setActionLoading(userId);
      const response = await axios.patch(
        `http://localhost:8081/api/v1/user/admin/${adminID}/ban/${userId}`,
        {}, // Body (empty if not required by backend)
        {
          headers: {
            'Content-Type': 'application/json',
            // Add Authorization header if required, e.g.:
            // 'Authorization': 'Bearer <your-token-here>'
          },
        }
      );

      console.log('Ban user response:', response.data);

      // Update the user's banned status in the local state
      setUsers(users.map(user =>
        user.id === userId ? { ...user, banned: true } : user
      ));
      setError('');
    } catch (err) {
      const errorMessage = err.response
        ? `Failed to ban user: ${err.response.status} - ${err.response.data.message || err.message}`
        : `Failed to ban user: Network error or CORS issue - ${err.message}`;
      setError(errorMessage);
      console.error('Error banning user:', err);
    } finally {
      setActionLoading(null);
    }
  };

  const handleUnbanUser = async (userId) => {
    try {
      setActionLoading(userId);
      const response = await axios.patch(
        `http://localhost:8081/api/v1/user/admin/${adminID}/unban/${userId}`,
        {}, // Body (empty if not required by backend)
        {
          headers: {
            'Content-Type': 'application/json',
            // Add Authorization header if required, e.g.:
            // 'Authorization': 'Bearer <your-token-here>'
          },
        }
      );

      console.log('Unban user response:', response.data);

      // Update the user's banned status in the local state
      setUsers(users.map(user =>
        user.id === userId ? { ...user, banned: false } : user
      ));
      setError('');
    } catch (err) {
      const errorMessage = err.response
        ? `Failed to unban user: ${err.response.status} - ${err.response.data.message || err.message}`
        : `Failed to unban user: Network error or CORS issue - ${err.message}`;
      setError(errorMessage);
      console.error('Error unbanning user:', err);
    } finally {
      setActionLoading(null);
    }
  };

  if (loading) {
    return (
      <div className="min-h-screen bg-gray-50 flex items-center justify-center">
        <div className="text-center">
          <FaSpinner className="animate-spin text-4xl text-blue-600 mx-auto mb-4" />
          <p className="text-gray-600">Loading users...</p>
        </div>
      </div>
    );
  }

  return (
    <div className="min-h-screen bg-gray-50">
      {/* Header */}
      <header className="bg-white shadow-sm">
        <div className="max-w-7xl mx-auto px-4 py-4 sm:px-6 lg:px-8 flex justify-between items-center">
          <h1 className="text-xl font-semibold text-gray-900">Admin Dashboard</h1>
          <div className="flex items-center space-x-4">
            <div className="relative">
              <div className="absolute inset-y-0 left-0 pl-3 flex items-center pointer-events-none">
                <FaSearch className="text-gray-400" />
              </div>
              <input
                type="text"
                placeholder="Search users..."
                value={searchTerm}
                onChange={(e) => setSearchTerm(e.target.value)}
                className="pl-10 pr-4 py-2 border border-gray-300 rounded-md focus:ring-blue-500 focus:border-blue-500"
              />
            </div>
          </div>
        </div>
      </header>

      {/* Main content */}
      <main className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 py-8">
        {error && (
          <div className="bg-red-100 border border-red-400 text-red-700 px-4 py-3 rounded mb-6">
            {error}
          </div>
        )}

        {/* Users table */}
        <div className="bg-white shadow-md rounded-lg overflow-hidden">
          <div className="px-6 py-4 border-b border-gray-200 flex items-center">
            <FaUsers className="text-blue-600 mr-2" />
            <h2 className="text-lg font-medium text-gray-900">User Management</h2>
            <span className="ml-2 bg-gray-200 text-gray-800 text-xs px-2 py-1 rounded-full">
              {filteredUsers.length} users
            </span>
          </div>

          <div className="overflow-x-auto">
            <table className="min-w-full divide-y divide-gray-200">
              <thead className="bg-gray-50">
                <tr>
                  <th scope="col" className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">
                    User
                  </th>
                  <th scope="col" className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">
                    Email
                  </th>
                  <th scope="col" className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">
                    Role
                  </th>
                  <th scope="col" className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">
                    Status
                  </th>
                  <th scope="col" className="px-6 py-3 text-right text-xs font-medium text-gray-500 uppercase tracking-wider">
                    Actions
                  </th>
                </tr>
              </thead>
              <tbody className="bg-white divide-y divide-gray-200">
                {filteredUsers.length > 0 ? (
                  filteredUsers.map((user) => (
                    <tr key={user.id}>
                      <td className="px-6 py-4 whitespace-nowrap">
                        <div className="flex items-center">
                          <div className="flex-shrink-0 h-10 w-10 bg-blue-500 rounded-full flex items-center justify-center">
                            <span className="text-white font-medium">
                              {user.firstName.charAt(0)}{user.lastName.charAt(0)}
                            </span>
                          </div>
                          <div className="ml-4">
                            <div className="text-sm font-medium text-gray-900">
                              {user.firstName} {user.lastName}
                            </div>
                            <div className="text-sm text-gray-500">
                              @{user.username}
                            </div>
                          </div>
                        </div>
                      </td>
                      <td className="px-6 py-4 whitespace-nowrap text-sm text-gray-500">
                        {user.email}
                      </td>
                      <td className="px-6 py-4 whitespace-nowrap">
                        <span className={`px-2 inline-flex text-xs leading-5 font-semibold rounded-full 
                          ${user.role === 'MODERATOR' ? 'bg-purple-100 text-purple-800' : 'bg-green-100 text-green-800'}`}>
                          {user.role}
                        </span>
                      </td>
                      <td className="px-6 py-4 whitespace-nowrap">
                        <span className={`px-2 inline-flex text-xs leading-5 font-semibold rounded-full 
                          ${user.banned ? 'bg-red-100 text-red-800' : 'bg-green-100 text-green-800'}`}>
                          {user.banned ? 'Banned' : 'Active'}
                        </span>
                      </td>
                      <td className="px-6 py-4 whitespace-nowrap text-right text-sm font-medium">
                        {actionLoading === user.id ? (
                          <FaSpinner className="animate-spin text-gray-500 inline-block" />
                        ) : user.banned ? (
                          <button
                            onClick={() => handleUnbanUser(user.id)}
                            className="text-green-600 hover:text-green-900 mr-3"
                            title="Unban User"
                          >
                            <FaCheckCircle />
                          </button>
                        ) : (
                          <button
                            onClick={() => handleBanUser(user.id)}
                            className="text-red-600 hover:text-red-900 mr-3"
                            title="Ban User"
                          >
                            <FaBan />
                          </button>
                        )}
                      </td>
                    </tr>
                  ))
                ) : (
                  <tr>
                    <td colSpan="5" className="px-6 py-4 text-center text-sm text-gray-500">
                      No users found
                    </td>
                  </tr>
                )}
              </tbody>
            </table>
          </div>
        </div>
      </main>
    </div>
  );
};

export default AdminDashboard;