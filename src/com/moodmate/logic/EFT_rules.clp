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

