; Rules for sleep assessment
(defrule check-sleep-factors
    (declare (salience 86))
    (need-second-factors (user_id ?id) (need TRUE))
    (sleepiness (user_id ?id) (sleepy TRUE))
=>
    (printout t crlf "Checking sleep factors for secondary analysis..." crlf))

; Rule to analyze sleep quality when user is sleepy
(defrule analyze-sleep-quality
    (declare (salience 85))
    (need-second-factors (user_id ?id) (need TRUE))
    (sleepiness (user_id ?id) (sleepy TRUE))
    (sleep-quality (user_id ?id) 
                  (satisfaction ?sat)
                  (sleep-time ?st)
                  (wake-time ?wt))
=>
    ; Determine quality message based on satisfaction level
    (bind ?quality-message
        (if (= ?sat 0) 
            then "Your sleep quality is very good, but you're feeling sleepy now. "
            else (if (= ?sat 2)
                    then "Your sleep quality is fairly good, though you're experiencing sleepiness. "
                    else (if (= ?sat 1)
                            then "Your sleep quality needs improvement. "
                            else (if (= ?sat 0)
                                    then "Your sleep quality is poor. "
                                    else "Unable to determine sleep quality. ")))))
            
    (bind ?schedule-message
        (str-cat "Your current sleep schedule is from " ?st " to " ?wt ". "))
        
    (bind ?recommendation
        (if (= ?sat 3) 
            then "Consider taking a short power nap."
            else (if (= ?sat 2)
                    then "Try to maintain consistent sleep-wake times."
                    else (if (= ?sat 1)
                            then "Focus on sleep hygiene and consistent bedtime routine."
                            else (if (= ?sat 0)
                                    then "Consider consulting a healthcare provider about your sleep quality."
                                    else "Try to identify what affects your sleep quality.")))))
            
    (assert (sleep-recommendation 
        (user_id ?id)
        (message (str-cat ?quality-message ?schedule-message ?recommendation))))
        
    (printout t "Sleep Quality Analysis Complete" crlf))

; Rule when user is not sleepy
(defrule analyze-non-sleepy
    (declare (salience 84))
    (need-second-factors (user_id ?id) (need TRUE))
    (sleepiness (user_id ?id) (sleepy FALSE))
=>
    (assert (sleep-recommendation 
        (user_id ?id)
        (message "Your alertness is good. Keep maintaining your current sleep schedule.")))
    
    (printout t "Sleep Analysis Complete" crlf))

; Rule to print sleep recommendation
(defrule print-sleep-recommendation
    (declare (salience 83))  ; Lower than analysis rules
    ?rec <- (sleep-recommendation (user_id ?id) (message ?m))
    (not (printed-sleep-recommendation (user_id ?id)))  ; Prevent multiple prints
=>
    (assert (printed-sleep-recommendation (user_id ?id)))
    (printout t crlf "Sleep Recommendation:" crlf)
    (printout t ?m crlf))

