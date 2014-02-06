package submit;

class TestFaintness {
    /**
     * In this method all variables are faint because the final value is never used.
     * Sample out is at src/test/Faintness.out
     */
    void test1() {
        int x = 2;
        int y = x + 2;
        int z = x + y;
        return;
    }

    /**
     * Write your test cases here. Create as many methods as you want.
     * Run the test from root dir using
     * bin/parun flow.Flow submit.MySolver submit.Faintness submit.TestFaintness
     */
    
    // TODO Write tests outputs and test against them.
    // TODO Write documentation about tests as part of the submission.
    // TODO Write test for move operation.
    // TODO What about arrays, other types, etc?
//    int articleTest() {
//    	int d = 0, a = 1, b = 1, c = 1;
//    	
//    	while (d < 3) {
//	    	if (d >= 2) {
//	    		a = b;
//	    	} else if (d >= 1) {
//				b = c;
//			} else {
//				c = 1;
//			}
//
//	    	d = d + 1;
//    	}
//    	
//    	return a;
//    }
//    
//    /**
//     * x and y are obviously alive. z is somewhere around Ballmer's Peak.
//     */
//    int diamondTest() {
//    	int x = 2, y = 0, z = 0;
//    	
//    	if (x == 2) {
//    		x = y + 1;
//    	} else {
//    		y = z;
//    	}
//    	
//    	return x;
//    }
//    
//    /**
//     * x and y are obviously alive. z is not alive, therefore it is faint.
//     */
//    int diamondTest2() {
//    	int x = 2, y = 0, z = 0;
//    	
//    	if (x == 1) {
//    		x = y;
//    	} else {
//    		z = y;
//    	}
//    	
//    	return x;
//    }
//    
//    int sneakyTest() {
//    	int x = 0;
//    	x = x;
//    	return x;
//    }
//    
//    void sneakyTest2() {
//    	int x = 0;
//    	x = x;
//    }
}
