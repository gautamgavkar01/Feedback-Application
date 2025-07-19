import React, { useEffect, useState } from "react";
import axios from "axios";
import { useAuth } from "../context/AuthContext";

const AdminDashboard = () => {
  const { token } = useAuth();
  const [allFeedback, setAllFeedback] = useState([]);
  const [editId, setEditId] = useState(null);
  const [editMessage, setEditMessage] = useState("");

  // for admin I implement this function, basically it will perform all the CRUD operations
  const fetchAllFeedback = async () => {
    try {
      const res = await axios.get(
        "http://localhost:8080/api/admin/feedback/all",
        {
          headers: {
            Authorization: `Bearer ${token}`,
          },
        }
      );
      setAllFeedback(res.data);
    } catch (err) {
      console.error("Error fetching feedback", err);
    }
  };

  const handleDelete = async (id) => {
    try {
      await axios.delete(
        `http://localhost:8080/api/admin/feedback/delete/${id}`,
        {
          headers: {
            Authorization: `Bearer ${token}`,
          },
        }
      );
      fetchAllFeedback(); // refresh our list
    } catch (err) {
      console.error("Error deleting", err);
    }
  };

  const handleView = (fb) => {
    alert(
      `ID: ${fb.id}\nUsername: ${fb.username}\nFeedback: ${fb.message}\nDate: ${fb.date}`
    );
  };

  const handleEdit = (fb) => {
    setEditId(fb.id);
    setEditMessage(fb.message || ""); //this is for just store the current message
  };

  const handleEditSave = async (fb) => {
    try {
      await axios.put(
        `http://localhost:8080/api/admin/feedback/update/${fb.id}`,
        { ...fb, message: editMessage },
        {
          headers: {
            Authorization: `Bearer ${token}`,
          },
        }
      );
      setEditId(null);
      setEditMessage("");
      fetchAllFeedback();
    } catch (err) {
      alert("Failed to update feedback.");
    }
  };

  const handleEditCancel = () => {
    setEditId(null);
    setEditMessage("");
  };

  useEffect(() => {
    fetchAllFeedback();
  }, []);

  return (
    <div className="container mt-4">
      <h2 className="mb-4">Admin Feedback Dashboard</h2>
      <table className="table table-bordered table-striped">
        <thead className="table-dark">
          <tr>
            <th>ID</th>
            <th>Username</th>
            <th>Feedback</th>
            <th>Date</th>
            <th>Action</th>
          </tr>
        </thead>
        <tbody>
          {allFeedback.map((fb) => (
            <tr key={fb.id}>
              <td>{fb.id}</td>
              <td>{fb.username}</td>
              <td>
                {editId === fb.id ? (
                  <input
                    type="text"
                    className="form-control"
                    value={editMessage}
                    onChange={(e) => setEditMessage(e.target.value)}
                  />
                ) : (
                  fb.message
                )}
              </td>
              <td>{fb.date}</td>
              <td>
                {editId === fb.id ? (
                  <>
                    <button
                      className="btn btn-success btn-sm me-2"
                      onClick={() => handleEditSave(fb)}
                    >
                      Save
                    </button>
                    <button
                      className="btn btn-secondary btn-sm"
                      onClick={handleEditCancel}
                    >
                      Cancel
                    </button>
                  </>
                ) : (
                  <>
                    <button
                      className="btn btn-primary btn-sm me-2"
                      onClick={() => handleEdit(fb)}
                    >
                      Edit
                    </button>
                    <button
                      className="btn btn-info btn-sm me-2"
                      onClick={() => handleView(fb)}
                    >
                      View
                    </button>
                    <button
                      className="btn btn-danger btn-sm"
                      onClick={() => handleDelete(fb.id)}
                    >
                      Delete
                    </button>
                  </>
                )}
              </td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
};

export default AdminDashboard;
