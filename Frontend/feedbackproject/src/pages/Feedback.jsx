import React, { useEffect, useState } from "react";
import axios from "axios";
import { useNavigate } from "react-router-dom";
import { useAuth } from "../context/AuthContext";

function Feedback() {
  const navigate = useNavigate();
  const [feedback, setFeedback] = useState({ content: "" });
  const [isEdit, setIsEdit] = useState(false);
  const { token } = useAuth();

  useEffect(() => {
    if (!token) {
      navigate("/login");
    } else {
      fetchMyFeedback();
    }
  }, []);

  const fetchMyFeedback = async () => {
    try {
      const res = await axios.get(
        "http://localhost:8080/api/user/feedback/my-feedback",
        {
          headers: { Authorization: `Bearer ${token}` },
        }
      );
      if (res.data && res.data.content) {
        setFeedback({ content: res.data.content });
        setIsEdit(true);
      }
    } catch (err) {
      setIsEdit(false);
      setFeedback({ content: "" });
    }
  };

  const handleChange = (e) => {
    setFeedback({ ...feedback, [e.target.name]: e.target.value });
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    const url = isEdit
      ? "http://localhost:8080/api/user/feedback/update"
      : "http://localhost:8080/api/user/feedback/add";

    try {
      await axios({
        method: isEdit ? "put" : "post",
        url: url,
        data: feedback,
        headers: { Authorization: `Bearer ${token}` },
      });

      alert(isEdit ? "Feedback updated" : "Feedback submitted");
    } catch (err) {
      console.error(err);
      alert("Error submitting feedback");
    }
  };

  return (
    <div className="container mt-5">
      <div className="card shadow">
        <div className="card-body">
          <h3 className="card-title text-center mb-4">
            {isEdit ? "Update" : "Add"} Feedback
          </h3>
          <form onSubmit={handleSubmit}>
            <div className="mb-3">
              <label htmlFor="content" className="form-label">
                Your Feedback
              </label>
              <textarea
                id="content"
                name="content"
                className="form-control"
                rows="5"
                value={feedback.content}
                onChange={handleChange}
                placeholder="Enter your feedback"
                required
              />
            </div>
            <button type="submit" className="btn btn-primary w-100">
              {isEdit ? "Update" : "Submit"} Feedback
            </button>
          </form>
        </div>
      </div>
    </div>
  );
}

export default Feedback;
