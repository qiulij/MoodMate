(defrule check-valid-username-and-password
	 (declare (salience 100))
    (user-input (username ?u) (password ?p))
    (test (neq ?u ""))
    (test (neq ?p ""))
    (test (neq ?u ?p)) ;; Username and password must not match
=>
    (assert (validation-result (valid TRUE) (message "Validation successful!"))))

(defrule invalid-username-or-password
	 (declare (salience 100))
    (user-input (username ?u) (password ?p))
    (or (test (eq ?u ""))
        (test (eq ?p ""))
        (test (eq ?u ?p))) ;; Username and password must not be empty or the same
=>
    (assert (validation-result (valid FALSE) (message "Invalid input: Username and password must not be the same or empty!"))))

(defrule print-validation-result
 (declare (salience 99))
    ?result <- (validation-result (valid ?valid) (message ?message))
=>
    (printout t ?message crlf)
    (retract ?result)) ;; Retract after printing to avoid duplicate messages


(defrule validate-profile-basic
    (declare (salience 98))
    ?input <- (profile-input (name ?name) (age ?age) (gender ?gender))
    (not (profile-validated))  ; Add this line to prevent multiple firings
    (test (neq ?name ""))
    (test (> ?age 0))
    (test (<= ?age 120))
    (test (or (eq ?gender 0)
              (eq ?gender 1) 
              (eq ?gender 2)))
=>
    (assert (profile-validated))  ; Add this line to indicate validation is done
    (printout t "Basic profile validated successfully for user: " ?name crlf))

(defrule check-mbti
    (declare (salience 97))
    ?input <- (profile-input (user_id ?id) (name ?name) (mbti ?mbti))
    (not (mbti-checked))  ; Control fact
    (test (member$ ?mbti (create$ "INTJ" "ENTJ" "INFJ" "ENFJ" 
                                 "INTP" "ENTP" "INFP" "ENFP" 
                                 "ISTJ" "ESTJ" "ISFJ" "ESFJ" 
                                 "ISTP" "ESTP" "ISFP" "ESFP" "unknown")))
=>
    (assert (mbti-checked))  ; Prevent multiple firings
    (if (eq ?mbti "unknown") 
        then (printout t "MBTI is unknown for user: " ?name ". Assigning test for MBTI classification." crlf))
    (if (neq ?mbti "unknown") 
        then (printout t "MBTI provided for user: " ?name " is valid: " ?mbti crlf)))

(defrule calculate-mbti
    (declare (salience 96)) ;; Ensure MBTI is calculated before printing
    ?profile <- (profile-input (user_id ?id) (mbti "unknown"))
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


(defrule validate-notification-frequency
	(declare (salience 95))
    (profile-input (user_id ?id) (notification-frequency ?freq))
    (not (notification-validated))  ; Control fact
    (test (member$ ?freq (create$ 1 2 3)))
=>
    (assert (notification-validated))  ; Prevent multiple firings
    (printout t "User " ?id " will receive notifications every " ?freq " hours." crlf))

(defrule print-profile
    (declare (salience 94)) ;; Ensure this executes last
    ?profile <- (profile-input (user_id ?id)
                               (name ?name)
                               (gender ?gender)
                               (age ?age)
                               (mbti ?mbti&:(neq ?mbti nil))
                               (hobbies ?hobbies))
=>
    (printout t "User Profile for ID: " ?id crlf
              "Name: " ?name crlf
              "Gender: " ?gender crlf
              "Age: " ?age crlf
              "MBTI: " ?mbti crlf
              "Hobbies: " ?hobbies crlf))

; Validation rule for emotion intensity
(defrule validate-emotion-intensity
    (declare (salience 93))
    (user-emotion (intensity ?i))
    (test (or (< ?i 0) (> ?i 100)))
=>
    (assert (validation-result 
        (valid FALSE)
        (message "Emotion intensity must be between 0 and 100"))))
        
; Calculate total intensity with user_id
(defrule calculate-total-intensity
    (declare (salience 92))
    (profile-input (user_id ?id))
    (not (total-intensity (user_id ?id)))  ; Only calculate once per user

    (or (and (not (user-emotion (user_id ?id) (emotion-name "happy")))
             (bind ?hi 0))
        (user-emotion (user_id ?id) (emotion-name "happy") (intensity ?hi)))
    (or (user-emotion (user_id ?id) (emotion-name "sad") (intensity ?si))
        (and (not (user-emotion (user_id ?id) (emotion-name "sad")))
             (bind ?si 0)))
    (or (user-emotion (user_id ?id) (emotion-name "angry") (intensity ?ai))
        (and (not (user-emotion (user_id ?id) (emotion-name "angry")))
             (bind ?ai 0)))
    (or (user-emotion (user_id ?id) (emotion-name "scared") (intensity ?sci))
        (and (not (user-emotion (user_id ?id) (emotion-name "scared")))
             (bind ?sci 0)))
    (or (user-emotion (user_id ?id) (emotion-name "confused") (intensity ?ci))
        (and (not (user-emotion (user_id ?id) (emotion-name "confused")))
             (bind ?ci 0)))
=>
    ; Calculate total using the matched values
    (bind ?total (+ ?hi ?si ?ai ?sci ?ci))
                    
    (assert (total-intensity (user_id ?id) (value ?total)))
    (printout t "Total intensity for user " ?id ": " ?total crlf))

    
; Calculate EFT percentages with user_id
(defrule normalize-emotions
    (declare (salience 91))
    (total-intensity (user_id ?id) (value ?total))
    (user-emotion (user_id ?id) (emotion-name ?ename) (intensity ?i))
    (not (normalized-emotion (user_id ?id) (emotion-name ?ename)))  ; Don't normalize twice
    (test (> ?total 0))  ; Prevent division by zero
=>
    (bind ?percentage (* (/ ?i ?total) 100))
    (assert (normalized-emotion 
        (user_id ?id)
        (emotion-name ?ename)
        (percentage ?percentage)))
    (printout t "User " ?id " emotion " ?ename " normalized: " (round ?percentage) "%" crlf))

; Rules for different combinations
(defrule trigger-yes-rses-high
	(declare (salience 90))
   	(profile-input (user_id ?id))
    (trigger-status (user_id ?id) (has-trigger TRUE))
    (rses-level (user_id ?id) (level "high"))
=>
    (assert (recommendation 
        (user_id ?id)
        (message "It's great that you have high self-esteem! Reflecting on this trigger might help you learn more about what challenges you and how you can grow stronger from it.")))
    (assert (recommendation 
        (user_id ?id)
        (message "Your confidence is your strength—use it to address this trigger constructively and focus on maintaining a positive mindset.")))
    (assert (recommendation 
        (user_id ?id)
        (message "You seem resilient! Consider journaling about this experience to process your emotions and reinforce your self-image."))))


(defrule trigger-yes-rses-low
	(declare (salience 90))
	(profile-input (user_id ?id))
    (trigger-status (user_id ?id) (has-trigger TRUE))
    (rses-level (user_id ?id) (level "low"))
=>
    (assert (recommendation 
        (user_id ?id)
        (message "Even though this trigger has affected you, remember that setbacks don't define your worth. Try focusing on one small, positive action today.")))
    (assert (recommendation 
        (user_id ?id)
        (message "It's okay to feel shaken by this situation. Speaking to someone you trust might help you gain perspective and build your self-esteem.")))
    (assert (recommendation 
        (user_id ?id)
        (message "Be kind to yourself. Triggers can be tough, but recognizing them is a step toward growth. Practice self-compassion and celebrate small wins."))))

(defrule trigger-no-rses-high
	(declare (salience 90))	
	(profile-input (user_id ?id))
    (trigger-status (user_id ?id) (has-trigger FALSE))
    (rses-level (user_id ?id) (level "high"))
=>
    (assert (recommendation 
        (user_id ?id)
        (message "Your high self-esteem shows! Without a trigger present, take this time to appreciate how you've been managing your emotions effectively.")))
    (assert (recommendation 
        (user_id ?id)
        (message "Enjoy the calm moments and consider reinforcing your positivity by engaging in an activity that brings you joy or helps others.")))
    (assert (recommendation 
        (user_id ?id)
        (message "Your self-image is strong, which is a fantastic foundation. Use this peaceful time to set goals that align with your strengths."))))

(defrule trigger-no-rses-low
	(declare (salience 90))
	(profile-input (user_id ?id))
    (trigger-status (user_id ?id) (has-trigger FALSE))
    (rses-level (user_id ?id) (level "low"))
=>
    (assert (recommendation 
        (user_id ?id)
        (message "This is a good time to focus on yourself. Reflecting on positive experiences or achievements might help strengthen your self-esteem.")))
    (assert (recommendation 
        (user_id ?id)
        (message "You're in a calm space, so take a moment to practice self-affirmation or mindfulness to nurture your self-worth.")))
    (assert (recommendation 
        (user_id ?id)
        (message "Without a trigger affecting you, this could be an opportunity to explore activities that help you feel accomplished and valued."))))

; Check for moderate RSES and no trigger for second factors
(defrule check-second-factors
	(declare (salience 89))
	(profile-input (user_id ?id))
    (trigger-status (user_id ?id) (has-trigger FALSE))
    (rses-level (user_id ?id) (level "moderate"))
=>
    (assert (need-second-factors (user_id ?id) (need TRUE)))
    (printout t "RSES is moderate and no trigger present. Checking secondary factors..." crlf))



; Calculate RSES score and categorize it
(defrule calculate-rses-score
    (declare (salience 88))
    (profile-input (user_id ?id))
    (not (rses-score (user_id ?id)))  ; Haven't calculated for this user
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
    
    ; Determine level based on score
    (bind ?level (if (< ?score 15) then "low"
                     else (if (> ?score 25) then "high"
                             else "moderate")))
    
    ; Assert RSES score and level
    (assert (rses-score 
        (user_id ?id)
        (score ?score)
        (level ?level)))
        
    (printout t "RSES Score for user " ?id ": " ?score crlf)
    (printout t "RSES Level: " ?level crlf))

; Print recommendation with user_id
(defrule print-recommendation
    (declare (salience 87))
    (profile-input (user_id ?id))
    (recommendation (user_id ?id) (message ?m))
    (not (printed-recommendation (user_id ?id)))
=>
    (assert (printed-recommendation (user_id ?id)))
    (printout t crlf "Recommendation for user " ?id ":" crlf)
    (printout t ?m crlf))

; Rules for sleep assessment
(defrule check-sleep-factors
    (declare (salience 86))
    (need-second-factors (user_id ?id) (need TRUE))
    (sleepiness (user_id ?id) (sleepy TRUE))
=>
    (printout t crlf "Checking sleep factors for secondary analysis..." crlf))

; Rule to analyze sleep quality when user is sleepy
(defrule analyze-sleep-quality
    (declare (salience 85))
    (need-second-factors (user_id ?id) (need TRUE))
    (sleepiness (user_id ?id) (sleepy TRUE))
    (sleep-quality (user_id ?id) 
                  (satisfaction ?sat)
                  (sleep-time ?st)
                  (wake-time ?wt))
=>
    ; Determine quality message based on satisfaction level
    (bind ?quality-message
        (if (= ?sat 0) 
            then "Your sleep quality is very good, but you're feeling sleepy now. "
            else (if (= ?sat 2)
                    then "Your sleep quality is fairly good, though you're experiencing sleepiness. "
                    else (if (= ?sat 1)
                            then "Your sleep quality needs improvement. "
                            else (if (= ?sat 0)
                                    then "Your sleep quality is poor. "
                                    else "Unable to determine sleep quality. ")))))
            
    (bind ?schedule-message
        (str-cat "Your current sleep schedule is from " ?st " to " ?wt ". "))
        
    (bind ?recommendation
        (if (= ?sat 3) 
            then "Consider taking a short power nap."
            else (if (= ?sat 2)
                    then "Try to maintain consistent sleep-wake times."
                    else (if (= ?sat 1)
                            then "Focus on sleep hygiene and consistent bedtime routine."
                            else (if (= ?sat 0)
                                    then "Consider consulting a healthcare provider about your sleep quality."
                                    else "Try to identify what affects your sleep quality.")))))
            
    (assert (sleep-recommendation 
        (user_id ?id)
        (message (str-cat ?quality-message ?schedule-message ?recommendation))))
        
    (printout t "Sleep Quality Analysis Complete" crlf))

; Rule when user is not sleepy
(defrule analyze-non-sleepy
    (declare (salience 84))
    (need-second-factors (user_id ?id) (need TRUE))
    (sleepiness (user_id ?id) (sleepy FALSE))
=>
    (assert (sleep-recommendation 
        (user_id ?id)
        (message "Your alertness is good. Keep maintaining your current sleep schedule.")))
    
    (printout t "Sleep Analysis Complete" crlf))

; Rule to print sleep recommendation
(defrule print-sleep-recommendation
    (declare (salience 83))  ; Lower than analysis rules
    ?rec <- (sleep-recommendation (user_id ?id) (message ?m))
    (not (printed-sleep-recommendation (user_id ?id)))  ; Prevent multiple prints
=>
    (assert (printed-sleep-recommendation (user_id ?id)))
    (printout t crlf "Sleep Recommendation:" crlf)
    (printout t ?m crlf))

; Rule to check if physical activity assessment is needed
(defrule check-physical-activity
    (declare (salience 82))
    (need-second-factors (user_id ?id) (need TRUE))
    (activity (user_id ?id) (has-activity TRUE))
=>
    (printout t crlf "Checking physical activity factors..." crlf))

; Rule for no physical activity
(defrule evaluate-no-activity
    (declare (salience 81))
    (need-second-factors (user_id ?id) (need TRUE))
    (activity (user_id ?id) (has-activity FALSE))
=>
    (assert (physical-activity-recommendation 
        (user_id ?id)
        (message "Consider incorporating some light physical activity into your day. Even a short walk can help improve your mood.")))
    (printout t "Physical Activity Analysis Complete" crlf))


; Rule to calculate physical activity score
(defrule calculate-activity-score
    (declare (salience 80))
    (need-second-factors (user_id ?id) (need TRUE))
    ?activity <- (physical-activity (user_id ?id) 
                                  (has-activity TRUE)
                                  (duration ?dur)
                                  (intensity ?int))
    (not (activity-score-calculated (user_id ?id)))
=>
    ; Calculate base score
    (bind ?score 0)
    
    (if (< ?dur 10)
        then 
        (bind ?score 0)
        else 
        (if (< ?dur 15)
            then 
            (bind ?score 
                (if (eq ?int "high") 
                    then 3
                    else (if (eq ?int "moderate")
                            then 2
                            else 1)))
            else 
            (if (< ?dur 30)
                then 
                (bind ?score 
                    (if (eq ?int "high")
                        then 4
                        else (if (eq ?int "moderate")
                                then 3
                                else 2)))
                else 
                (if (< ?dur 60)
                    then 
                    (bind ?score 
                        (if (eq ?int "high")
                            then 4
                            else (if (eq ?int "moderate")
                                    then 4
                                    else 3)))
                    else 
                    (bind ?score 4)))))  ; Maximum score for duration >= 60 mins regardless of intensity
    
    ; Normalize score to 0-100
    (bind ?normalized-score (* (/ ?score 4) 100))
    
    ; Modify fact with calculated score
    (modify ?activity (score ?normalized-score))
    
    ; Assert control fact to prevent multiple calculations
    (assert (activity-score-calculated (user_id ?id)))
    
    ; Generate recommendation based on normalized score
    (if (= ?normalized-score 0)
        then 
        (assert (physical-activity-recommendation 
            (user_id ?id)
            (message "Your physical activity duration is too short. Try to exercise for at least 10 minutes continuously.")))
        else 
        (if (< ?normalized-score 50)
            then 
            (assert (physical-activity-recommendation 
                (user_id ?id)
                (message "You've made a start with physical activity. Try to gradually increase your duration and intensity.")))
            else 
            (if (< ?normalized-score 75)
                then 
                (assert (physical-activity-recommendation 
                    (user_id ?id)
                    (message "Good effort with physical activity! Consider increasing either duration or intensity.")))
                else 
                (assert (physical-activity-recommendation 
                    (user_id ?id)
                    (message "Excellent physical activity level! Keep maintaining this level. Your duration and intensity are ideal for health benefits."))))))
    
    (printout t "Physical Activity Score: " ?normalized-score "%" crlf))


; Rule to print physical activity recommendation
(defrule print-activity-recommendation
    (declare (salience 79))
    ?rec <- (physical-activity-recommendation (user_id ?id) (message ?m))
    (not (printed-activity-recommendation (user_id ?id)))
=>
    (assert (printed-activity-recommendation (user_id ?id)))
    (printout t crlf "Physical Activity Recommendation:" crlf)
    (printout t ?m crlf))
