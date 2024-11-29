(assert (dimension-score (dimension "EI") (score 1) (strength 50)))
(assert (dimension-score (dimension "SN") (score 1) (strength 90)))
(assert (dimension-score (dimension "TF") (score 1) (strength 65)))
(assert (dimension-score (dimension "JP") (score -1) (strength 70)))

(assert (profile-input 
    (name "John Doe")
    (age 25)
    (gender 0)
    (mbti "INTJ")
    (self-image-score 7.5)))

(assert (user-input (username "john") (password "pass123")))
(assert (user-input (username "john") (password "pass123")))