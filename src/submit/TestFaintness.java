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
	
//	void test2() {
//		int x = new Random().nextInt();
//		int y = 2 + x;
//		int[] a = new int[y];
//		return;
//	}
	
//	class X {}
//	void test3() {
//		int y = 2;
//		X[] x = new X[y];
//	}

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

    /*
    *   x is faint after every loop iteration since it is dead 
    *   after the loop and live for each iteration (next iteration is dependent)
    */
    void faintloop(){
        int x=0;
        for(int i=0; i<10; i++){
            x=x+1; 
        }
        x=3; // x is dead after this
    }


    /*
    *   x is faint after every out loop iteration since it is dead 
    *   after the loop and live for each iteration (next iteration is dependent)
    *   Since y is used to calculate the faint x (at the end of the inner loop iteration),
    *   y is also faint there. By the same logic as x y is faint at the end every inner loop iteration too
    */
    void faintnestedloops(){
        int x=0;
        int y=0;
        for(int i=0; i<10; i++){
            for(int j=0; j<10; j++){
                y=y+1;
            }
            x=x+y;
        }
        x=3; // x is dead after this
    }

    /*
    *   return statement ensures control flow will not y=x+x
    *   causing x to be dead at the end of x=x+1 causing x to be going into x=x+1
    */
    void nullexception(){
        int x=3; //x is live but faint
        x = x+1; //x is dead going out due to null poiinter exception
        return;
        int y=x+x; //x is alive when entering here
    }



    /*
    *   null pointer exception ensure control flow will not y=x+x
    *   causing x to be dead at the end of x=x+1 causing x to be going into x=x+1
    */
    void nullexception(){
        int x=3; //x is live but faint
        x = x+1; //x is dead going out due to null poiinter exception
        int[] twoArr = new int[2];
        int z = twoArr[2]; //null pointer exception here
        int y=x+x; //x is alive when entering here
    }

}

