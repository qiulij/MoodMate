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
    
    ; Calculate timing score (0-20 points) - with more precise ranges
    (bind ?timing-score
        (if (and (>= ?sd 22.0) (<= ?sd 23.5)
                 (>= ?wd 6.0) (<= ?wd 7.5))
            then 20
            else (if (and (>= ?sd 21.0) (<= ?sd 24.0)
                         (>= ?wd 5.0) (<= ?wd 8.0))
                    then 15
                    else 10)))
    
    ; Calculate midpoint and evaluate it
	(bind ?midpoint 
	    (if (< ?wd ?sd)
	        then (/ (+ (+ (- 24 ?sd) ?wd)) 2)  ; Cross midnight case
	        else (/ (+ ?sd ?wd) 2)))           ; Same day case
    
    ; Adjust midpoint if it's after noon (for display)
	(if (> ?midpoint 12)
	    then (bind ?display-midpoint (- ?midpoint 24))
	    else (bind ?display-midpoint ?midpoint))
    
    ; Calculate midpoint score (0-20 points)
	(bind ?midpoint-score
	    (if (and (>= ?midpoint 3.0) (<= ?midpoint 4.0))
	        then 20    ; Ideal midpoint (3-4 AM)
	        else (if (and (>= ?midpoint 2.5) (<= ?midpoint 4.5))
	                then 15    ; Acceptable range
	                else 10))) ; Outside optimal range
	
    ; Calculate total score
    (bind ?total-score (round (+ (* (/ ?sat 3) 30)     ; satisfaction (0-30)
                            ?duration-score          ; duration (0-30)
                            ?timing-score            ; timing (0-20)
                            ?midpoint-score)))       ; midpoint (0-20)
    
    ; Modify fact with calculated score
    (modify ?sleep (score ?total-score))
    (assert (sleep-score-calculated (user_id ?id)))
    
    ; Print detailed analysis
    (printout t crlf "Sleep Quality Analysis:" crlf)
    
    ; Print duration analysis
    (printout t "Sleep Duration (" ?duration " hours): "
        (if (< ?duration 6) 
            then "Too short - Consider going to bed earlier or waking up later"
            else (if (< ?duration 7)
                    then "Slightly short - Try to get a bit more sleep"
                    else (if (<= ?duration 9)
                            then "Optimal duration - Keep maintaining this amount of sleep"
                            else "Longer than recommended - Excessive sleep might affect your energy"))) crlf)
    
    ; Print timing analysis using original time strings
    (printout t "Sleep Schedule (" ?st " to " ?wt "): "
        (if (and (>= ?sd 22.0) (<= ?sd 23.5)
                 (>= ?wd 6.0) (<= ?wd 7.5))
            then "Ideal sleep-wake timing - This schedule aligns well with natural circadian rhythms"
            else (if (and (>= ?sd 21.0) (<= ?sd 24.0)
                         (>= ?wd 5.0) (<= ?wd 8.0))
                    then "Acceptable timing - Minor adjustments might help optimize your sleep"
                    else "Could be improved - Consider adjusting your schedule closer to 22:00-23:00 sleep and 6:00-7:00 wake")) crlf)
    
    ; Print satisfaction analysis
    (printout t "Sleep Quality: "
        (if (= ?sat 3)
            then "Excellent - You're experiencing high-quality sleep"
            else (if (= ?sat 2)
                    then "Good - Your sleep quality is satisfactory but could be improved"
                    else (if (= ?sat 1)
                            then "Fair - You might benefit from better sleep hygiene practices"
                            else "Poor - Consider addressing factors that might be affecting your sleep quality"))) crlf)
    
    ; Print timing analysis with midpoint
	(printout t "Mid-sleep time: " (round ?display-midpoint) ":00" crlf)
	(printout t "Schedule Quality: "
	    (if (and (>= ?sd 22.0) (<= ?sd 23.5)
	             (>= ?wd 6.0) (<= ?wd 7.5)
	             (>= ?midpoint 3.0) (<= ?midpoint 4.0))
	        then "Ideal - Your sleep timing aligns perfectly with natural circadian rhythms"
	        else (if (and (>= ?sd 21.0) (<= ?sd 24.0)
	                     (>= ?wd 5.0) (<= ?wd 8.0)
	                     (>= ?midpoint 2.5) (<= ?midpoint 4.5))
	                then "Good - Your sleep timing is within an acceptable range"
	                else "Could be improved - Your sleep timing might not be optimal for your circadian rhythm")) crlf)
	
	; Add midpoint-specific advice
	(if (< ?midpoint 2.5)
	    then (printout t "   Note: Your sleep timing is quite early. Consider going to bed a bit later." crlf)
	    else (if (> ?midpoint 4.5)
	            then (printout t "   Note: Your sleep timing is quite late. Consider going to bed earlier." crlf)))
    (printout t crlf "Overall Sleep Score: " ?total-score "%" crlf))

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

