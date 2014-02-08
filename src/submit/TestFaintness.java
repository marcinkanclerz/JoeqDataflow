package submit;

import java.util.Random;

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
	
    /**
     * Because only binary operator and move operator propagate faintness, y is considered used (live). Thus x as well.
     * a is dead though.
     */
	void test2() {
		int x = 1;
		int y = 2 + x;
		int[] a = new int[y];
	}
	
	/**
	 * a, i, j are used in control flow, thus used (alive).
	 * d is returned, thus alive.
	 * b, c, e are faint.
	 */
	int verySneakyTranstiveTest() {
		int a = 1, b = 1, c = 1, d = 1, e = 1;
		
		for (int i = 0; i < a; ++i) {
			b = c + d;
			
			for (int j = 0; j < e; j++) {
				j = j + j;
				e = b;
			}
		}
		
		return d;
	}

	/**
	 * b is dead. All other variables (but x in f()) are alive.
	 * c is used in f(c), thus is used.
	 * d is used for control flow, thus used.
	 * a is returned, so it's pretty alive.
	 */
	void f(int x) {	}
    int test3() {
    	int d = 0, a = 1, b = 1, c = 1;
    	
    	while (d < 3) {
	    	if (d >= 2) {
	    		a = b;
	    	} else if (d >= 1) {
				b = c;
			} else {
				f(c);
			}

	    	d = d + 1;
    	}
    	
    	return a;
    }
    
    /**
     * z is alive, x is used in control flow (thus used). x is dead.
     */
    int diamondTest() {
    	int x = 2, y = 0, z = 0;
    	
    	if (x == 2) {
    		x = z + 1;
    	} else {
    		y = z;
    	}
    	
    	return z;
    }
    
    /**
     * x is dead, y alive. 
     */
    int sneakyTest() {
    	int x = 0, y = 1;
    	x = x;
    	return y;
    }
    
    void sneakyTest2() {
    	int x = 0;
    	x = x;
    }

    /*
    *   x is faint after every loop iteration since it is dead 
    *   after the loop and live for each iteration (next iteration is dependent)
    */
    void faintloop() {
        int x = 0;
        for(int i = 0; i < 10; i++){
            x = x + 1; 
        }
        x = 3; // x is dead after this
    }

   /**
    *   x is faint after every out loop iteration since it is dead 
    *   after the loop and live for each iteration (next iteration is dependent)
    *   Since y is used to calculate the faint x (at the end of the inner loop iteration),
    *   y is also faint there. By the same logic as x y is faint at the end every inner loop iteration too
    */
    void faintnestedloops() {
        int x = 0;
        int y = 0;
        for(int i = 0; i < 10; i++){
            for(int j = 0; j < 10; j++){
                y = y + 1;
            }
            x = x + y;
        }
        x = 3; // x is dead after this
    }
}

