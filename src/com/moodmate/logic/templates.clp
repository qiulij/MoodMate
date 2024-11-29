(deftemplate user-input
    (slot username)
    (slot password)
)

(deftemplate validation-result
    (slot valid)
    (slot message)
)

(deftemplate profile-input
    (slot user_id)
    (slot name)
    (slot gender)    ; 0 for male, 1 for female, 2 for other
    (slot age)
    (slot mbti)      ; Can be one of 16 types or "unknown"
    (slot hobbies)   ; List of hobbies
    (slot self-image-score)) ; Calculated from a test

(deftemplate mbti-answer
    (slot user_id)
    (slot dimension) ; EI, SN, TF, JP
    (slot question_id)
    (slot score))    ; Score from -5 to 5

(deftemplate self-image-answer
    (slot user_id)
    (slot question_id)
    (slot answer))   ; Answer from 1 to 4

(deftemplate profile-result
    (slot user_id)
    (slot mbti)              ; Calculated MBTI type
    (slot self-image-score)) ; Calculated self-image score

