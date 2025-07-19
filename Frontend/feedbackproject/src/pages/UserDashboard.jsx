import React, { useEffect, useState } from "react";
import axios from "axios";
import { useAuth } from "../context/AuthContext";

const UserDashboard = () => {
  const { token } = useAuth();
  const [feedbackText, setFeedbackText] = useState("");
  const [myFeedback, setMyFeedback] = useState(null);
  const [isEditing, setIsEditing] = useState(false);
  const [editText, setEditText] = useState("");

  const fetchMyFeedback = async () => {
    try {
      const res = await axios.get(
        "http://localhost:8080/api/user/feedback/my-feedback",
        {
          headers: {
            Authorization: `Bearer ${token}`,
          },
        }
      );
      setMyFeedback(res.data);
    } catch (err) {
      setMyFeedback(null);
    }
  };

  const handleAddFeedback = async () => {
    try {
      await axios.post(
        "http://localhost:8080/api/user/feedback/add",
        { message: feedbackText },
        {
          headers: {
            Authorization: `Bearer ${token}`,
          },
        }
      );
      setFeedbackText("");
      fetchMyFeedback();
    } catch (err) {}
  };

  const handleEdit = () => {
    setEditText(myFeedback.message);
    setIsEditing(true);
  };

  const handleEditSave = async () => {
    try {
      await axios.put(
        "http://localhost:8080/api/user/feedback/update",
        { id: myFeedback.id, message: editText },
        {
          headers: {
            Authorization: `Bearer ${token}`,
          },
        }
      );
      setIsEditing(false);
      setEditText("");
      fetchMyFeedback();
    } catch (err) {}
  };

  const handleEditCancel = () => {
    setIsEditing(false);
    setEditText("");
  };

  useEffect(() => {
    fetchMyFeedback();
  }, []);

  return (
    <div className="container mt-5">
      <h2 className="text-center mb-4">User Dashboard</h2>

      {!myFeedback || !myFeedback.message ? (
        <div className="card p-4">
          <h4 className="mb-3">Submit Your Feedback</h4>
          <textarea
            className="form-control mb-3"
            placeholder="Write your feedback here"
            value={feedbackText}
            onChange={(e) => setFeedbackText(e.target.value)}
            rows="4"
          />
          <button className="btn btn-primary" onClick={handleAddFeedback}>
            Submit Feedback
          </button>
        </div>
      ) : (
        <div className="card p-4">
          <h4>Your Feedback</h4>
          {isEditing ? (
            <>
              <input
                type="text"
                className="form-control mb-2"
                value={editText}
                onChange={(e) => setEditText(e.target.value)}
              />
              <button className="btn btn-success me-2" onClick={handleEditSave}>
                Save
              </button>
              <button className="btn btn-secondary" onClick={handleEditCancel}>
                Cancel
              </button>
            </>
          ) : (
            <>
              <p className="mb-2">
                <strong>Message:</strong> {myFeedback.message}
              </p>
              <p className="text-muted">
                <strong>Date:</strong> {myFeedback.date}
              </p>
              <button className="btn btn-warning" onClick={handleEdit}>
                Edit
              </button>
            </>
          )}
        </div>
      )}
    </div>
  );
};

export default UserDashboard;
