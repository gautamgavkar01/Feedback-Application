import React, { useEffect, useState } from "react";
import axios from "axios";

const AdminFeedbackTable = () => {
  const [feedbacks, setFeedbacks] = useState([]);

  const fetchAllFeedbacks = async () => {
    const token = localStorage.getItem("token");

    // Extract all the feedbackd from all the users
    try {
      const response = await axios.get(
        // This is my admin side backend token
        "http://localhost:8080/api/auth/admin/feedback/all",
        {
          headers: {
            Authorization: `Bearer ${token}`,
          },
        }
      );
      setFeedbacks(response.data);
    } catch (error) {
      console.error("Error fetching all feedbacks:", error);
    }
  };

  // We can delete the perticular feedback, so I implemented this one
  const handleDelete = async (id) => {
    const token = localStorage.getItem("token");
    try {
      await axios.delete(
        `http://localhost:8080/api/auth/admin/feedback/delete/${id}`,
        {
          headers: {
            Authorization: `Bearer ${token}`,
          },
        }
      );
      fetchAllFeedbacks();
    } catch (error) {
      console.error("Failed to delete", error);
    }
  };

  useEffect(() => {
    fetchAllFeedbacks();
  }, []);

  return (
    <div className="container mt-4">
      <h2 className="mb-4">All Feedbacks</h2>
      <table className="table table-bordered table-striped">
        <thead className="table-dark">
          <tr>
            <th>ID</th>
            <th>Feedback</th>
            <th>Date</th>
            <th>Action</th>
          </tr>
        </thead>
        <tbody>
          {feedbacks.map((dt) => (
            <tr key={dt.id}>
              <td>{dt.id}</td>
              <td>{dt.content}</td>
              <td>{dt.createdAt}</td>
              <td>
                <button
                  className="btn btn-info btn-sm me-2"
                  onClick={() => alert(dt.content)}
                >
                  View
                </button>{" "}
                <button
                  className="btn btn-warning btn-sm me-2"
                  onClick={() => alert("Open edit form")}
                >
                  Edit
                </button>{" "}
                <button
                  className="btn btn-danger btn-sm"
                  onClick={() => handleDelete(dt.id)}
                >
                  Delete
                </button>
              </td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
};

export default AdminFeedbackTable;
