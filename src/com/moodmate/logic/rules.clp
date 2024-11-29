; Rule to validate username and password
(defrule check-valid-username-and-password
    (user-input (username ?u) (password ?p))
    (test (neq ?u ""))
    (test (neq ?p ""))
    (test (neq ?u ?p)) ;; Username and password must not match
=>
    (assert (validation-result (valid TRUE) (message "Validation successful!"))))

; Rule to find invalidate username or password
(defrule invalid-username-or-password
    (user-input (username ?u) (password ?p))
    (or (test (eq ?u ""))
        (test (eq ?p ""))
        (test (eq ?u ?p))) ;; Username and password must not be empty or the same
=>
    (assert (validation-result (valid FALSE) (message "Invalid input: Username and password must not be the same or empty!"))))

; Rules for name validation
(defrule validate-name
    (profile-input (name ?n))
    (test (and (> (str-length ?n) 0) 
               (< (str-length ?n) 50)))
=>
    (assert (validation-result (valid TRUE) 
            (message "Name validation passed"))))

(defrule invalid-name
    (profile-input (name ?n))
    (or (test (<= (str-length ?n) 0))
        (test (>= (str-length ?n) 50)))
=>
    (assert (validation-result (valid FALSE) 
            (message "Name must be between 1 and 50 characters"))))

; Rule for MBTI validation
(defrule validate-mbti
    (profile-input (mbti ?m))
    (test (member$ ?m (create$ "INTJ" "INTP" "ENTJ" "ENTP" 
                              "INFJ" "INFP" "ENFJ" "ENFP" 
                              "ISTJ" "ISFJ" "ESTJ" "ESFJ" 
                              "ISTP" "ISFP" "ESTP" "ESFP"
                              "unknown")))
=>
    (assert (validation-result (valid TRUE) (message "Valid MBTI type"))))

; Rule to validate self-image score
(defrule validate-self-image
    (profile-input (self-image-score ?s))
    (test (and (>= ?s 1.0) (<= ?s 10.0)))
=>
    (assert (validation-result (valid TRUE) (message "Self-image score validation passed"))))

; Rule to check for invalid data
(defrule invalid-profile-data
    (profile-input (name ?n) (age ?a) (gender ?g) (mbti ?m) (self-image-score ?s))
    (or (test (<= (str-length ?n) 0))
        (test (>= (str-length ?n) 50))
        (test (< ?a 13))
        (test (> ?a 100))
        (test (< ?g 0))
        (test (> ?g 2))
        (test (< ?s 1.0))
        (test (> ?s 10.0)))
=>
    (assert (validation-result (valid FALSE) (message "Invalid profile data provided!"))))

; Calculate dimension scores with strength
(defrule calculate-dimension-score
    ;; Get values for a specific dimension
    (mbti-answer (dimension ?d) (value ?v))
=>
    ; Convert to percentage (-5 to 5 scale to 0-100%)
    (bind ?strength (* (abs (/ ?v 5)) 100))
    
    ; Score is positive or negative based on value
    (assert (dimension-score 
        (dimension ?d)
        (score (if (> ?v 0) then 1 else -1))
        (strength ?strength)))
    
)

; Determine MBTI type with confidence levels
(defrule determine-mbti-type
    (dimension-score (dimension "EI") (score ?ei) (strength ?ei-str))
    (dimension-score (dimension "SN") (score ?sn) (strength ?sn-str))
    (dimension-score (dimension "TF") (score ?tf) (strength ?tf-str))
    (dimension-score (dimension "JP") (score ?jp) (strength ?jp-str))
    =>
    (bind ?type "")
    ; Build MBTI type with strength indicators
    (bind ?type (str-cat ?type 
        (if (> ?ei 0) then "E" else "I") "(" (round ?ei-str) "%) "))
    (bind ?type (str-cat ?type 
        (if (> ?sn 0) then "S" else "N") "(" (round ?sn-str) "%) "))
    (bind ?type (str-cat ?type 
        (if (> ?tf 0) then "T" else "F") "(" (round ?tf-str) "%) "))
    (bind ?type (str-cat ?type 
        (if (> ?jp 0) then "J" else "P") "(" (round ?jp-str) "%) "))
    
    ; Assert result with detailed breakdown
    (assert (mbti-result 
        (type (str-cat 
            (if (> ?ei 0) then "E" else "I")
            (if (> ?sn 0) then "S" else "N")
            (if (> ?tf 0) then "T" else "F")
            (if (> ?jp 0) then "J" else "P")))
        (details ?type)))
)

; Rule to print validation results
(defrule print-validation-result
    (validation-result (valid ?v) (message ?m))
=>
    (printout t "Validation Result: " ?m crlf))

; Rule to print MBTI results
(defrule print-mbti-result
    (mbti-result (type ?t) (details ?d))
=>
    (printout t "MBTI Type: " ?t crlf)
)
    