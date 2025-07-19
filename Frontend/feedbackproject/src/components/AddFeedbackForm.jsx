import React, { useState } from "react";
import axios from "axios";

const AddFeedbackForm = () => {
  const [feedback, setFeedback] = useState(""); //this state basically hold the whatever feedback text

  const handleSubmit = async (event) => {
    event.preventDefault(); //to change the default behaiour
    const token = localStorage.getItem("token");

    try {
      await axios.post(
        "http://localhost:8080/api/user/feedback/add",
        // from request body we can send data
        { content: feedback },
        {
          headers: {
            Authorization: `Bearer ${token}`,
          },
        }
      );
      alert("Feedback submitted");
      setFeedback("");
    } catch (error) {
      console.error("Something went wrong!!!", error);
    }
  };

  return (
    <form onSubmit={handleSubmit} className="container mt-4">
      <div className="mb-3">
        <label htmlFor="feedbackTextarea" className="form-label">
          Write your feedback:
        </label>
        <textarea
          id="feedbackTextarea"
          className="form-control"
          placeholder="Please write feedback..."
          value={feedback}
          onChange={(event) => setFeedback(event.target.value)}
          rows="4"
        />
      </div>
      <button type="submit" className="btn btn-primary">
        Submit
      </button>
    </form>
  );
};

export default AddFeedbackForm;
