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
    (slot self-image-score (default nil)) ;; Integer, calculated score (nil initially)
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
