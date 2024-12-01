;;;;;;;;;;;;;;;;;;;;;;;;CHECK THE USERNAME ;;;;;;;;;;;;; 
(defrule check-valid-username-and-password
    (user-input (username ?u) (password ?p))
    (test (neq ?u ""))
    (test (neq ?p ""))
    (test (neq ?u ?p)) ;; Username and password must not match
    (test (>= (str-length ?u) 3))   ;; Username must have at least 3 characters
=>
    (assert (validation-result (valid TRUE) (message "Validation successful!"))))

(defrule invalid-username-or-password
    (user-input (username ?u) (password ?p))
    (or (test (eq ?u ""))
        (test (eq ?p ""))
        (test (eq ?u ?p)) ;; Username and password must not be empty or the same
        (test (< (str-length ?u) 3)))      ;; Username has fewer than 3 characters
=>
    (assert (validation-result (valid FALSE) (message "Invalid input: Username and password must not be the same or empty or username characters must be atleast 3!"))))

;;;;;;;;;;;;;;;;;;;;;;CHECK THE VALIDITY OF PASSWORD;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

(deffunction isValidPassword (?password)
   (bind ?pattern "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d).{8,}$") ; Password regex pattern
   (bind ?matcher (call "java.util.regex.Pattern" "compile" ?pattern))
   (bind ?matcherResult (call ?matcher "matcher" ?password))
   (bind ?matches (call ?matcherResult "matches"))
   ?matches ; Returns true if the password matches the regex pattern, false otherwise
)

(defrule enforce-password-policy
   (user-input (password ?p))
   (test (isValidPassword ?p)) ; Check if the password meets the policy
=>
   (assert (password-validation-result (valid TRUE) (message "Password is valid.")))
)

(defrule invalid-password-policy
   (user-input (password ?p))
   (test (not (isValidPassword ?p))) ; If the password does not meet the policy
=>
   (assert (password-validation-result (valid FALSE) (message "Password does not meet the policy requirements!")))
)

;;;;;;;;;;;;;;;;CHECK THE VALIDITY OF EMAIL;;;;;;;;;;;;;;;;;;;;

(deffunction isValidEmail (?email)
   (bind ?pattern "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[a-zA-Z]{2,}$") ; Regular expression for basic email validation
   (bind ?matcher (call "java.util.regex.Pattern" "compile" ?pattern))
   (bind ?matcherResult (call ?matcher "matcher" ?email))
   (bind ?matches (call ?matcherResult "matches"))
   ?matches ; Returns true if the email matches the regex pattern, false otherwise
)

(defrule check-valid-email
   (user-input (email ?e))
   (test (isValidEmail ?e)) ; Check if the email matches the regex pattern
=>
   (assert (email-validation-result (valid TRUE) (message "Email is valid.")))
)

(defrule invalid-email
   (user-input (email ?e))
   (test (not (isValidEmail ?e))) ; If the email does not match the regex pattern
=>
   (assert (email-validation-result (valid FALSE) (message "Invalid email format!")))
)

