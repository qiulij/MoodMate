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
    (slot satisfaction)    ; 0-3 (0=very poor, 3=very good)
    (slot sleep-time)      ; e.g., "23:30" (for display)
    (slot wake-time)       ; e.g., "07:00" (for display)
    (slot sleep-decimal)   ; e.g., 23.5 (for calculation)
    (slot wake-decimal)    ; e.g., 7.0 (for calculation)
    (slot score))          ; final calculated score 0-100

(deftemplate sleep-score-calculated
	(slot user_id))

(deftemplate sleep-recommendation
    (slot user_id)
    (slot message))
    
(deftemplate printed-sleep-recommendation
    (slot user_id))
 
(deftemplate activity
    (slot user_id)
    (slot has-activity)) ; TRUE or FALSE
   
; Template for physical activity
(deftemplate physical-activity
    (slot user_id)
    (slot has-activity (type SYMBOL))    ; TRUE or FALSE
    (slot duration (type INTEGER))       ; in minutes
    (slot intensity)                     ; "high", "moderate", "light"
    (slot score (type INTEGER) (default 0)))

; Add a template for tracking score calculation
(deftemplate activity-score-calculated
    (slot user_id))

; Template for physical activity recommendation
(deftemplate physical-activity-recommendation
    (slot user_id)
    (slot message))

; Template for tracking printed recommendations
(deftemplate printed-activity-recommendation
    (slot user_id))
    
; Template for tracking appetite score calculation
(deftemplate appetite-score-calculated
    (slot user_id))
    
; Templates for food scoring
(deftemplate appetite-status
    (slot user_id)
    (slot option)        ; "3", "2a", "2b", "1a", "1b", "0a", "0b"
    (slot score))        ; calculated score 0-3

(deftemplate macronutrient-score-calculated
    (slot user_id))

(deftemplate macronutrient-intake
    (slot user_id)
    (slot carbs)         ; percentage 0-100
    (slot protein)       ; percentage 0-100
    (slot fat)           ; percentage 0-100
    (slot minerals)      ; percentage 0-100
    (slot vitamins)      ; percentage 0-100
    (slot water)         ; percentage 0-100
    (slot score (type INTEGER) (default 0)))    ; percentage 0-100

(deftemplate meal-info
    (slot user_id)
    (slot meals-per-day)
    (slot meal-score))    
    
(deftemplate meal-pattern-evaluated
    (slot user_id))
    
(deftemplate food-score
    (slot user_id)
    (slot total-score)        ; normalized 0-100
    (slot appetite-score)     ; component scores
    (slot nutrient-score)
    (slot meal-score))

(deftemplate food-warning
    (slot user_id)
    (slot warning-type)   ; "macronutrient" or "meal"
    (slot message))

(deftemplate appetite-recommendation
    (slot user_id)
    (slot message))