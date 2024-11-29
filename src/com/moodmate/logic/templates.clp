; Templates for signup
(deftemplate user-input
    (slot username)
    (slot password)
)

(deftemplate validation-result
    (slot valid)
    (slot message)
)

; Templates for user-profile
(deftemplate profile-input
    (slot user_id)
    (slot name)
    (slot gender)    ; 0 for male, 1 for female, 2 for other
    (slot age)
    (slot mbti)
    (slot hobbies)
    (slot self-image-score))

; Template for scaled MBTI answers
(deftemplate mbti-answer
    (slot question-id)
    (slot dimension)    ; EI, SN, TF, or JP
    (slot value)        ; -5 to 5 scale
)

; Template for dimension scores
(deftemplate dimension-score
    (slot dimension)
    (slot score)
    (slot strength)     ; How strong the preference is (1-100%)
)

; Template for final MBTI result
(deftemplate mbti-result
    (slot type)
 	(slot details)
)

; For self-image test
(deftemplate self-image-answer
    (slot question-id)
    (slot value))       ; 1-5 scale
