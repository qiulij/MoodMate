(deftemplate user-input
    (slot username)  ;; String, must not be empty
    (slot password)) ;; String, must not be empty

(deftemplate validation-result
    (slot valid)     ;; Boolean, TRUE or FALSE
    (slot message))  ;; String, validation message

(deftemplate profile-input
    (slot user_id)              ;; Integer, unique identifier
    (slot name)                 ;; String, user name
    (slot gender)               ;; Integer, 0 for male, 1 for female, 2 for other
    (slot age)                  ;; Integer, age in years
    (slot mbti (default nil))   ;; String, one of 16 MBTI types or "unknown"
    (slot hobbies)              ;;list of hobbies
    (slot notification-frequency (default 1))) ;; Integer, hours between notifications

(deftemplate mbti-answer
    (slot user_id)       ;; Integer, references profile-input user_id
    (slot dimension)     ;; String, MBTI dimension: EI, SN, TF, JP
    (slot question_id)   ;; Integer, question identifier
    (slot score))        ;; Integer, score for the question (-5 to 5)

(deftemplate self-image-answer
    (slot user_id)       ;; Integer, references profile-input user_id
    (slot question_id)   ;; Integer, question identifier
    (slot answer))       ;; Integer, answer for the question (1 to 4)

(deftemplate user-emotion
    (slot user_id)
    (slot emotion-name)
    (slot intensity (type INTEGER) (default 0)))

(deftemplate normalized-emotion
    (slot user_id)
    (slot emotion-name)
    (slot percentage))  ; normalized percentage
    
(deftemplate total-intensity
    (slot user_id)
    (slot value))
    
(deftemplate trigger-status
    (slot user_id)
    (slot has-trigger))  ; TRUE or FALSE

(deftemplate rses-level
    (slot user_id)    
    (slot level))     ; "high", "low", "moderate"

(deftemplate recommendation
    (slot user_id)
    (slot message))

; Template for RSES (Rosenberg Self-Esteem Scale)
(deftemplate rses-score
    (slot user_id)
    (slot score)
    (slot level))  ; "low", "moderate", "high"

(deftemplate need-second-factors
    (slot user_id)
    (slot need)) ; TRUE or FALSE

(deftemplate printed-recommendation
    (slot user_id))

(deftemplate sleepiness
    (slot user_id)
    (slot sleepy)) ; TRUE or FALSE

(deftemplate sleep-quality
    (slot user_id)
    (slot satisfaction (type INTEGER))  ; 0 (Very Good) to 3 (Very Bad)
    (slot sleep-time)                   ; e.g., "23:00"
    (slot wake-time))                   ; e.g., "07:00"

(deftemplate sleep-recommendation
    (slot user_id)
    (slot message))
    
(deftemplate printed-sleep-recommendation
    (slot user_id))