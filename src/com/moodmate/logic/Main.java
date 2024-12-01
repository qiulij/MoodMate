package com.moodmate.logic;

import jess.JessException;
import jess.Rete;
import jess.*;

public class Main {
	public static void main(String[] args) {
		String file1 = "src/com/moodmate/logic/templates.clp";
		String file2 = "src/com/moodmate/logic/data.clp";
		String file3 ="src/com/moodmate/logic/primary_reason_rules.clp";
		String file4 ="src/com/moodmate/logic/food_rules.clp";


		
		try {
			Rete r = new Rete();
			
//			r.eval("(watch facts)");
//			r.eval("(watch rules)");
			r.eval("(reset)");
			
			r.eval("(batch " + file1 + ")");
			r.eval("(batch " + file2 + ")");
			r.eval("(batch " + file3 + ")");
			r.eval("(batch " + file4 + ")");
			
//			r.eval("(facts)");
			r.eval("( run)");
//			r.eval("(facts)");
		
			
			
			
		}
		catch (JessException j) {
			j.printStackTrace();
		}
	}


}