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
