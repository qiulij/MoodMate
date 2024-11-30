(assert (user-input (username "john") (password "pass123")))
(assert (profile-input (user_id 1) (name "Alice") (gender 1)
                       (age 25) (mbti "unknown") (hobbies (create$ "reading" "hiking"))
                       (notification-frequency 2))) 

(assert (mbti-answer (user_id 1) (dimension "EI") (question_id 1) (score 3)))
(assert (mbti-answer (user_id 1) (dimension "EI") (question_id 2) (score -2)))
(assert (mbti-answer (user_id 1) (dimension "SN") (question_id 1) (score 4)))
(assert (mbti-answer (user_id 1) (dimension "SN") (question_id 2) (score 1)))
(assert (mbti-answer (user_id 1) (dimension "TF") (question_id 1) (score -3)))
(assert (mbti-answer (user_id 1) (dimension "TF") (question_id 2) (score -4)))
(assert (mbti-answer (user_id 1) (dimension "JP") (question_id 1) (score 5)))
(assert (mbti-answer (user_id 1) (dimension "JP") (question_id 2) (score -1)))

(assert (user-emotion (user_id 1) (emotion-name "happy") (intensity 60)))
(assert (user-emotion (user_id 1) (emotion-name "sad") (intensity 40)))
(assert (user-emotion (user_id 1) (emotion-name "angry") (intensity 80)))
(assert (user-emotion (user_id 1) (emotion-name "scared") (intensity 10)))
(assert (user-emotion (user_id 1) (emotion-name "confused") (intensity 80)))

(assert (trigger-status (user_id 1) (has-trigger FALSE)))
(assert (rses-level (user_id 1) (level "moderate")))

(assert (self-image-answer (user_id 1) (question_id 1) (answer 3)))
(assert (self-image-answer (user_id 1) (question_id 2) (answer 4)))
(assert (self-image-answer (user_id 1) (question_id 3) (answer 2)))
(assert (self-image-answer (user_id 1) (question_id 4) (answer 1)))
(assert (self-image-answer (user_id 1) (question_id 5) (answer 4)))
(assert (self-image-answer (user_id 1) (question_id 6) (answer 3)))
(assert (self-image-answer (user_id 1) (question_id 7) (answer 2)))
(assert (self-image-answer (user_id 1) (question_id 8) (answer 1)))
(assert (self-image-answer (user_id 1) (question_id 9) (answer 3)))
(assert (self-image-answer (user_id 1) (question_id 10) (answer 4)))

(assert (sleepiness (user_id 1) (sleepy TRUE)))
; Based on satisfaction level (0-3):
; 3 = Very good quality -> recommend power nap
; 2 = Fairly good -> recommend consistent schedule
; 1 = Needs improvement -> recommend sleep hygiene
; 0 = Poor quality -> recommend medical consultation
(assert (sleep-quality 
    (user_id 1)
    (satisfaction 2)    
    (sleep-time "23:00")
    (wake-time "08:00")))

(assert (activity (user_id 1) (has-activity TRUE)))
(assert (physical-activity  ; Example 1: Short duration
    (user_id 1)
    (has-activity TRUE)
    (duration 100)            
    (intensity "light")))

(assert (appetite-status (user_id 1) (option "2a")))
(assert (macronutrient-intake 
    (user_id 1)
    (carbs 30)       ; Lower than needed
    (protein 45)     ; Good amount
    (fat 50)         ; Good amount
    (minerals 40)    ; Borderline
    (vitamins 45)    ; Good amount
    (water 20)))     ; Too low
