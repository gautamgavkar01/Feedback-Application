import React, { useEffect, useState } from "react";
import axios from "axios";

const MyFeedbackList = () => {
  const [feedback, setFeedback] = useState(null);

  const fetchFeedback = async () => {
    const token = localStorage.getItem("token");
    try {
      const response = await axios.get(
        "http://localhost:8080/api/user/feedback/my-feedback",
        {
          headers: {
            Authorization: `Bearer ${token}`,
          },
        }
      );
      setFeedback(response.data);
    } catch (error) {
      console.error("Failed to fetch feedback", error);
    }
  };

  useEffect(() => {
    fetchFeedback();
  }, []);

  if (!feedback) return <p className="text-muted">No feedback found.</p>;

  return (
    <div className="container mt-4">
      <div className="card shadow-sm">
        <div className="card-body">
          <h5 className="card-title">My Feedback</h5>
          <p className="card-text">{feedback.content}</p>
        </div>
      </div>
    </div>
  );
};

export default MyFeedbackList;
