const questionWeights = {
  q1: { "42": 14, "56": 5, "72": 5, "12": 1 },
  q2: { "square": 5, "circle": 14, "triangle": 5, "pentagon": 1 },
  q3: { "Mary": 5, "John": 1, "Steve": 14, "js": 5 },
  q4: { "Minimize": 14, "Amplify": 5, "Enhance": 1, "Increase": 5 },
  q5: { "41": 14, "36": 6, "45": 1, "42": 5 },
  q6: { "All roses are red.": 5, "Some flowers are roses.": 5, "Some red things are flowers.": 0, "Some flowers are not red.": 14 },
  q7: { "20": 4, "19": 2, "21": 14, "18": 1 },
  q8: { "INVEST": 6, "VESTINE": 5, "INVITES": 14, "INTIVES": 1 },
  q9: { "35": 1, "40": 5, "36": 14, "30": 6 },
  q10: { "64": 14, "68": 5, "62": 5, "58": 1 },
  q11: { "25": 5, "50": 14, "75": 5, "100": 1 },
  q12: { "0.2": 5, "2/5": 5, "20%": 14, "1": 0 },
  q13: { "V": 5, "S": 0, "T": 4, "U": 14 },
  q14: { "Yes": 5, "No": 5, "Cannot be determined": 14, "Some Bloops are Quirks8": 0 },
  q15: { "15": 14, "45": 0, "30": 14, "90": 5 },
};

window.onload = function () {

  const userInfoPopup = document.getElementById("user-info");
  const quizContainer = document.querySelector(".container");
  const timerElement = document.getElementById("timer");
  const timeElapsedElement = document.getElementById("time-elapsed");

  let elapsedSeconds = 0; // Timer elapsed time in seconds


  quizContainer.classList.add("blur");
  userInfoPopup.style.display = "block";

   // Handle user info input and start quiz
   document.getElementById("user-info-form").addEventListener("submit", function (event) {
    event.preventDefault();
    const name = document.getElementById("name").value;
    const age = document.getElementById("age").value;

    if (name  && age>0) {
      // Store user name and age
      sessionStorage.setItem("userName", name);
      sessionStorage.setItem("userAge", age);

      // Hide the user info popup and unblur the quiz
      userInfoPopup.style.display = "none";
      quizContainer.classList.remove("blur");

      // Show the timer and start the quiz
      timerElement.classList.remove("hidden");
      startElapsedTimer();
    } else {
      if(!name)){
        alert("Name should not be in Numbers!")
      }
      else if(age<=0){
        alert("Please enter your correct age.");
      }
    }
  });

  // Timer logic
  function startElapsedTimer() {
    setInterval(() => {
      elapsedSeconds++;
      const minutes = Math.floor(elapsedSeconds / 60);
      const seconds = elapsedSeconds % 60;
      timeElapsedElement.textContent = `${minutes}:${seconds < 10 ? "0" : ""}${seconds}`;
    }, 1000);
  }

  

  // Form submission logic for IQ Test
  document.getElementById("iq-test-form").addEventListener("submit", function (event) {
    event.preventDefault();

    const formData = new FormData(event.target);
    const totalQuestions = 15;
    let allAnswered = true;

    // Check if all questions are answered
    for (let i = 1; i <= totalQuestions; i++) {
      if (!formData.has(`q${i}`)) {
        allAnswered = false;

        // Scroll to the first unanswered question
        const questionElement = document.querySelector(`[name="q${i}"]`).closest(".question");
        questionElement.scrollIntoView({ behavior: "smooth", block: "center" });

        // Highlight the unanswered question
        questionElement.style.border = "2px solid red";
        setTimeout(() => {
          questionElement.style.border = "1px solid #ddd";
        }, 3000);

        break;
      }
    }

    if (allAnswered) {
      // Calculate the score
      let totalScore = 0;
      formData.forEach((value, key) => {
        if (questionWeights[key] && questionWeights[key][value]) {
          totalScore += questionWeights[key][value];
        }
      });

      // Pass name, age, and score to result page via session storage
      sessionStorage.setItem('iqScore', totalScore);

      // Redirect to result page
      window.location.href = "resultpage.html";
    } else {
      alert("Please answer all the questions before submitting!");
    }
  });
};
