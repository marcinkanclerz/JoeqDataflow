package submit;

import joeq.Compiler.Quad.Operator;
import joeq.Compiler.Quad.Quad;
import joeq.Compiler.Quad.QuadVisitor;
import joeq.Compiler.Quad.Operand.RegisterOperand;

/* The QuadVisitor that actually does the computation */
public class FaintnessTransferFunction extends QuadVisitor.EmptyVisitor {
    
    public VarSet val;
    
    /**
     * TODO:
     * - In general the work that's not done is propagation of uses.
     * 	 e.g.: I'd expect that return x; would mark "x" as used.
     * - R0 could be managed with getField, setField, getStatic. 
     */
    
    /**
     * visitMove and visitBinary are the only operations actually propagating faintness. 
     */
    
    @Override
    public void visitMove (Quad q) {
    	// Add register operands to set of operands.
    	VarSet operands = new VarSet();
    	
    	operands.addIfOperand(Operator.Move.getSrc(q));
        String lhs = Operator.Move.getDest(q).getRegister().toString();

        // If lhs was not not faint up until here, then remove operands from the list of faint vars.
        // lhs is never in the list of operands.
        if (!val.containsVar(lhs)) {
        	val.removeSet(operands);
        }
    	
        // We have just redefined lhs, therefore it's faint.
        val.addVar(lhs);
    }
    
    @Override
    public void visitBinary (Quad q) {
    	// Add register operands to set of operands.
    	VarSet operands = new VarSet();
    	operands.addIfOperand(Operator.Binary.getSrc1(q));
    	operands.addIfOperand(Operator.Binary.getSrc2(q));
    	
        String lhs = Operator.Binary.getDest(q).getRegister().toString();

        // If lhs was not not faint up until here, then remove operands from the list of faint vars.
        if (!val.containsVar(lhs)) {
        	val.removeSet(operands);
        }
        
        // If lhs is not in operands, then add it to the list of faint vars.
        if (!operands.containsVar(lhs))
        {
        	val.addVar(lhs);
        }
    }
    
    /**
     * X x = new X();
     * 
     * NEW                     T1 TestFaintness$X,	submit.TestFaintness$X
     * 
     * T1 is used
     */
    @Override
    public void visitNew(Quad q) {
    	VarSet operands = new VarSet();
    	operands.addIfOperand(Operator.New.getDest(q));
    	
    	val.removeSet(operands);
    }

    /**
     * int[] a = new int[x];
     * 
     * NEWARRAY                T4 int[],	R3 int,	int[]
     * 
     * x is used
     */
    @Override
    public void visitNewArray(Quad q) {
    	VarSet operands = new VarSet();
    	operands.addIfOperand(Operator.NewArray.getSize(q));
    	
    	val.removeSet(operands);
    }

    @Override
    public void visitALength(Quad q) {
         VarSet operands = new VarSet();
         //operands.addIfOperand();
         val.removeSet(operands);
    }

    @Override
    public void visitAllocation(Quad q) {
         VarSet operands = new VarSet();
         //operands.addIfOperand();
         val.removeSet(operands);
    }

    @Override
    public void visitALoad(Quad q) {
         VarSet operands = new VarSet();
         //operands.addIfOperand();
         val.removeSet(operands);
    }

    @Override
    public void visitArray(Quad q) {
         VarSet operands = new VarSet();
//         operands.addIfOperand(Operator.);
         val.removeSet(operands);
    }

    @Override
    public void visitAStore(Quad q) {
         VarSet operands = new VarSet();
         //operands.addIfOperand();
         val.removeSet(operands);
    }

    @Override
    public void visitBoundsCheck(Quad q) {
         VarSet operands = new VarSet();
         //operands.addIfOperand();
         val.removeSet(operands);
    }

    @Override
    public void visitBranch(Quad q) {
         VarSet operands = new VarSet();
         //operands.addIfOperand();
         val.removeSet(operands);
    }

    @Override
    public void visitCheck(Quad q) {
         VarSet operands = new VarSet();
         //operands.addIfOperand();
         val.removeSet(operands);
    }

    @Override
    public void visitCheckCast(Quad q) {
         VarSet operands = new VarSet();
         //operands.addIfOperand();
         val.removeSet(operands);
    }

    @Override
    public void visitCondBranch(Quad q) {
         VarSet operands = new VarSet();
         //operands.addIfOperand();
         val.removeSet(operands);
    }

    @Override
    public void visitExceptionThrower(Quad q) {
         VarSet operands = new VarSet();
         //operands.addIfOperand();
         val.removeSet(operands);
    }

    @Override
    public void visitGetfield(Quad q) {
         VarSet operands = new VarSet();
         //operands.addIfOperand();
         val.removeSet(operands);
    }

    @Override
    public void visitGetstatic(Quad q) {
         VarSet operands = new VarSet();
         //operands.addIfOperand();
         val.removeSet(operands);
    }

    @Override
    public void visitGoto(Quad q) {
         VarSet operands = new VarSet();
         //operands.addIfOperand();
         val.removeSet(operands);
    }

    @Override
    public void visitInstanceField(Quad q) {
         VarSet operands = new VarSet();
         //operands.addIfOperand();
         val.removeSet(operands);
    }

    @Override
    public void visitInstanceOf(Quad q) {
         VarSet operands = new VarSet();
         //operands.addIfOperand();
         val.removeSet(operands);
    }

    @Override
    public void visitIntIfCmp(Quad q) {
         VarSet operands = new VarSet();
         //operands.addIfOperand();
         val.removeSet(operands);
    }

    @Override
    public void visitInvoke(Quad q) {
         VarSet operands = new VarSet();
         //operands.addIfOperand();
         val.removeSet(operands);
    }

    @Override
    public void visitJsr(Quad q) {
         VarSet operands = new VarSet();
         //operands.addIfOperand();
         val.removeSet(operands);
    }

    @Override
    public void visitLoad(Quad q) {
         VarSet operands = new VarSet();
         //operands.addIfOperand();
         val.removeSet(operands);
    }

    @Override
    public void visitLookupSwitch(Quad q) {
         VarSet operands = new VarSet();
         //operands.addIfOperand();
         val.removeSet(operands);
    }

    @Override
    public void visitMemLoad(Quad q) {
         VarSet operands = new VarSet();
         //operands.addIfOperand();
         val.removeSet(operands);
    }

    @Override
    public void visitMemStore(Quad q) {
         VarSet operands = new VarSet();
         //operands.addIfOperand();
         val.removeSet(operands);
    }

    @Override
    public void visitMonitor(Quad q) {
         VarSet operands = new VarSet();
         //operands.addIfOperand();
         val.removeSet(operands);
    }

    @Override
    public void visitNullCheck(Quad q) {
         VarSet operands = new VarSet();
         //operands.addIfOperand();
         val.removeSet(operands);
    }

    @Override
    public void visitPhi(Quad q) {
         VarSet operands = new VarSet();
         //operands.addIfOperand();
         val.removeSet(operands);
    }

    @Override
    public void visitPutfield(Quad q) {
         VarSet operands = new VarSet();
         //operands.addIfOperand();
         val.removeSet(operands);
    }

    @Override
    public void visitPutstatic(Quad q) {
         VarSet operands = new VarSet();
         //operands.addIfOperand();
         val.removeSet(operands);
    }

    @Override
    public void visitQuad(Quad q) {
         VarSet operands = new VarSet();
         //operands.addIfOperand();
         val.removeSet(operands);
    }

    @Override
    public void visitRet(Quad q) {
         VarSet operands = new VarSet();
         //operands.addIfOperand();
         val.removeSet(operands);
    }

    @Override
    public void visitReturn(Quad q) {
    	q.getUsedRegisters();
         VarSet operands = new VarSet();
         operands.addIfOperand(Operator.Return.getSrc(q));
         val.removeSet(operands);
    }

    @Override
    public void visitSpecial(Quad q) {
         VarSet operands = new VarSet();
         //operands.addIfOperand();
         val.removeSet(operands);
    }

    @Override
    public void visitStaticField(Quad q) {
         VarSet operands = new VarSet();
         //operands.addIfOperand();
         val.removeSet(operands);
    }

    @Override
    public void visitStore(Quad q) {
         VarSet operands = new VarSet();
         //operands.addIfOperand();
         val.removeSet(operands);
    }

    @Override
    public void visitStoreCheck(Quad q) {
         VarSet operands = new VarSet();
         //operands.addIfOperand();
         val.removeSet(operands);
    }

    @Override
    public void visitTableSwitch(Quad q) {
         VarSet operands = new VarSet();
         //operands.addIfOperand();
         val.removeSet(operands);
    }

    @Override
    public void visitTypeCheck(Quad q) {
         VarSet operands = new VarSet();
         //operands.addIfOperand();
         val.removeSet(operands);
    }

    @Override
    public void visitUnary(Quad q) {
         VarSet operands = new VarSet();
         //operands.addIfOperand();
         val.removeSet(operands);
    }

}