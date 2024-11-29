(defrule check-valid-username-and-password
    (user-input (username ?u) (password ?p))
    (test (neq ?u ""))
    (test (neq ?p ""))
    (test (neq ?u ?p)) ;; Username and password must not match
=>
    (assert (validation-result (valid TRUE) (message "Validation successful!"))))

(defrule invalid-username-or-password
    (user-input (username ?u) (password ?p))
    (or (test (eq ?u ""))
        (test (eq ?p ""))
        (test (eq ?u ?p))) ;; Username and password must not be empty or the same
=>
    (assert (validation-result (valid FALSE) (message "Invalid input: Username and password must not be the same or empty!"))))

(defrule print-validation-result
    ?result <- (validation-result (valid ?valid) (message ?message))
=>
    (printout t ?message crlf)
    (retract ?result)) ;; Retract after printing to avoid duplicate messages

(defrule initialize-profile
   (profile-input (user_id ?id) (name ?name))
   (not (profile-result (user_id ?id))) ;; Only initialize if it doesn't already exist
=>
   (assert (profile-result (user_id ?id) (mbti nil) (self-image-score nil)))
   (printout t "Profile initialized for user: " ?name crlf))

(defrule validate-profile-basic
	(declare (salience 100))
    ?input <- (profile-input (name ?name) (age ?age) (gender ?gender))
    (test (neq ?name "")) ;; Name must not be empty
    (test (> ?age 0))     ;; Age must be a positive number
    (test (<= ?age 120))  ;; Age should be realistic
    (test (or (eq ?gender 0) (eq ?gender 1) (eq ?gender 2))) ;; Valid gender
=>
    (printout t "Basic profile validated successfully for user: " ?name crlf))

(defrule check-mbti
    ?input <- (profile-input (user_id ?id) (name ?name) (mbti ?mbti))
    (test (member$ ?mbti (create$ "INTJ" "ENTJ" "INFJ" "ENFJ" 
                                  "INTP" "ENTP" "INFP" "ENFP" 
                                  "ISTJ" "ESTJ" "ISFJ" "ESFJ" 
                                  "ISTP" "ESTP" "ISFP" "ESFP" "unknown")))
=>
    (if (eq ?mbti "unknown") 
        then (printout t "MBTI is unknown for user: " ?name ". Assigning test for MBTI classification." crlf))
    (if (neq ?mbti "unknown") 
        then (printout t "MBTI provided for user: " ?name " is valid: " ?mbti crlf)))


(defrule calculate-mbti
   (declare (salience 80))
   ?profile <- (profile-result (user_id ?id) (mbti nil))
   (mbti-answer (user_id ?id) (dimension "EI") (question_id 1) (score ?q1))
   (mbti-answer (user_id ?id) (dimension "EI") (question_id 2) (score ?q2))
   (mbti-answer (user_id ?id) (dimension "SN") (question_id 1) (score ?q3))
   (mbti-answer (user_id ?id) (dimension "SN") (question_id 2) (score ?q4))
   (mbti-answer (user_id ?id) (dimension "TF") (question_id 1) (score ?q5))
   (mbti-answer (user_id ?id) (dimension "TF") (question_id 2) (score ?q6))
   (mbti-answer (user_id ?id) (dimension "JP") (question_id 1) (score ?q7))
   (mbti-answer (user_id ?id) (dimension "JP") (question_id 2) (score ?q8))
=>
   (bind ?EI (if (> (+ ?q1 ?q2) 0) then "E" else "I"))
   (bind ?SN (if (> (+ ?q3 ?q4) 0) then "S" else "N"))
   (bind ?TF (if (> (+ ?q5 ?q6) 0) then "T" else "F"))
   (bind ?JP (if (> (+ ?q7 ?q8) 0) then "J" else "P"))
   (bind ?mbti (str-cat ?EI ?SN ?TF ?JP))
   (modify ?profile (mbti ?mbti))
   (printout t "Calculated MBTI for user " ?id ": " ?mbti crlf))

(defrule calculate-self-image-score
   (declare (salience 70))
   ?profile <- (profile-result (user_id ?id) (self-image-score nil))
   (self-image-answer (user_id ?id) (question_id 1) (answer ?a1))
   (self-image-answer (user_id ?id) (question_id 2) (answer ?a2))
   (self-image-answer (user_id ?id) (question_id 3) (answer ?a3))
   (self-image-answer (user_id ?id) (question_id 4) (answer ?a4))
   (self-image-answer (user_id ?id) (question_id 5) (answer ?a5))
   (self-image-answer (user_id ?id) (question_id 6) (answer ?a6))
   (self-image-answer (user_id ?id) (question_id 7) (answer ?a7))
   (self-image-answer (user_id ?id) (question_id 8) (answer ?a8))
   (self-image-answer (user_id ?id) (question_id 9) (answer ?a9))
   (self-image-answer (user_id ?id) (question_id 10) (answer ?a10))
=>
   (bind ?score (+ ?a1 
                   (- 5 ?a2) 
                   ?a3 
                   ?a4 
                   (- 5 ?a5) 
                   (- 5 ?a6) 
                   ?a7 
                   (- 5 ?a8) 
                   (- 5 ?a9) 
                   ?a10))
   (modify ?profile (self-image-score ?score))
   (printout t "Calculated self-image score for user " ?id ": " ?score crlf))


(defrule print-profile
   (declare (salience 50))
   (profile-result (user_id ?id) (mbti ?mbti&:(neq ?mbti nil)) (self-image-score ?score&:(neq ?score nil)))
=>
   (printout t "User Profile for ID: " ?id crlf
             "MBTI: " ?mbti crlf
             "Self-Image Score: " ?score crlf))
