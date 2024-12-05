; Rule to check sleep factors for secondary analysis
(defrule check-sleep-factors
    (declare (salience 86))
    (need-second-factors (user_id ?id) (need TRUE))
    (sleepiness (user_id ?id) (sleepy TRUE))
=>
    (printout t crlf "Checking sleep factors for secondary analysis..." crlf))

; Rule to analyze sleep quality when user is sleepy
(defrule analyze-sleep-quality
    (declare (salience 85))
    (sleepiness (user_id ?id) (sleepy TRUE))
    ?sleep <- (sleep-quality (user_id ?id) 
                          (satisfaction ?sat)
                          (sleep-time ?st)
                          (wake-time ?wt)
                          (sleep-decimal ?sd)
                          (wake-decimal ?wd))
    (not (sleep-score-calculated (user_id ?id)))
=>
    ; Calculate duration considering 24-hour cycle
    (bind ?duration 
        (if (< ?wd ?sd)
            then (+ (- 24 ?sd) ?wd)
            else (- ?wd ?sd)))
    
    ; Calculate satisfaction score (0-40 points)
    (bind ?satisfaction-score (* (/ ?sat 3) 40))
    
    ; Calculate duration score (0-40 points)
    (bind ?duration-score
        (if (< ?duration 6) 
            then 20
            else (if (< ?duration 7)
                    then 30
                    else (if (<= ?duration 9)
                            then 40
                            else 25))))
    
    ; Calculate timing score (0-20 points)
    (bind ?timing-score
        (if (and (>= ?sd 22.0) (<= ?sd 23.5)
                 (>= ?wd 6.0) (<= ?wd 7.5))
            then 20
            else (if (and (>= ?sd 21.0) (<= ?sd 24.0)
                         (>= ?wd 5.0) (<= ?wd 8.0))
                    then 15
                    else 10)))
    
    ; Calculate midpoint
    (bind ?midpoint 
        (if (< ?wd ?sd)
            then (/ (+ (- 24 ?sd) ?wd) 2)
            else (/ (+ ?sd ?wd) 2)))
    
    ; Calculate midpoint score (0-20 points)
    (bind ?midpoint-score
        (if (and (>= ?midpoint 3.0) (<= ?midpoint 4.0))
            then 20
            else (if (and (>= ?midpoint 2.5) (<= ?midpoint 4.5))
                    then 15
                    else 10)))
    
    ; Calculate total score
    (bind ?total-score (round (+ ?satisfaction-score ?duration-score ?timing-score ?midpoint-score)))
    
    ; Finalize analysis
    (modify ?sleep (score ?total-score))
    (assert (sleep-score-calculated (user_id ?id)))
    (printout t "Sleep analysis completed. Total score: " ?total-score crlf))

; Rule when user is not sleepy
(defrule analyze-non-sleepy
    (declare (salience 84))
    (sleepiness (user_id ?id) (sleepy FALSE))
=>
    (assert (sleep-recommendation 
        (user_id ?id)
        (message "Your alertness is good. Keep maintaining your current sleep schedule.")))
    (printout t "Sleep Analysis Complete" crlf))

; Rule to generate a single comprehensive sleep recommendation
(defrule generate-sleep-recommendation
    (declare (salience 83))  ; Lower than analysis rules
    ?sleep <- (sleep-quality (user_id ?id) 
                          (satisfaction ?sat)
                          (sleep-time ?st)
                          (wake-time ?wt)
                          (sleep-decimal ?sd)
                          (wake-decimal ?wd)
                          (score ?total-score))
    (not (sleep-recommendation (user_id ?id)))  ; Ensure only one consolidated recommendation
=>
    ; Initialize the comprehensive recommendation message
    (bind ?message "")

    ; Analyze duration
    (bind ?duration 
        (if (< ?wd ?sd)
            then (+ (- 24 ?sd) ?wd)
            else (- ?wd ?sd)))

    (if (< ?duration 6)
        then (bind ?message (str-cat ?message
            "Your sleep duration is too short. Try to go to bed earlier or wake up later.\n")))
    (if (> ?duration 9)
        then (bind ?message (str-cat ?message
            "Your sleep duration is excessive. Consider reducing sleep time for better energy levels.\n")))

    ; Analyze timing
    (if (not (and (>= ?sd 21.0) (<= ?sd 24.0)
                  (>= ?wd 5.0) (<= ?wd 8.0)))
        then (bind ?message (str-cat ?message
            "Your sleep timing could be improved. Aim for a sleep window of 22:00 to 23:00 and wake between 6:00 to 7:00.\n")))

    ; Analyze satisfaction
    (bind ?satisfaction-score (* (/ ?sat 3) 40))
    (if (< ?satisfaction-score 30)
        then (bind ?message (str-cat ?message
            "Your sleep quality is below optimal. Consider improving your sleep hygiene.\n")))

    ; Analyze mid-sleep time
    (bind ?midpoint 
        (if (< ?wd ?sd)
            then (/ (+ (- 24 ?sd) ?wd) 2)
            else (/ (+ ?sd ?wd) 2)))
    (if (not (and (>= ?midpoint 3.0) (<= ?midpoint 4.0)))
        then (bind ?message (str-cat ?message
            "Your mid-sleep time is not optimal. Aim for a midpoint between 3:00 and 4:00 AM.\n")))

    ; Add overall positive or constructive feedback
    (if (>= ?total-score 75)
        then (bind ?message (str-cat ?message
            "Overall, your sleep patterns are good. Keep maintaining your current routine.\n"))
        else (bind ?message (str-cat ?message
            "Overall, there is room for improvement in your sleep patterns. Focus on the areas highlighted above.\n")))

    ; Assert the consolidated sleep recommendation
    (assert (sleep-recommendation 
        (user_id ?id)
        (message ?message)))

    ; Print the comprehensive recommendation
    (printout t crlf "Comprehensive Sleep Recommendation:" crlf)
    (printout t ?message crlf))
