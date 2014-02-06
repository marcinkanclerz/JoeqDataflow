package submit;

import joeq.Compiler.Quad.Operator;
import joeq.Compiler.Quad.Quad;
import joeq.Compiler.Quad.QuadVisitor;
import joeq.Compiler.Quad.Operand.RegisterOperand;

/* The QuadVisitor that actually does the computation */
public class FaintnessTransferFunction extends QuadVisitor.EmptyVisitor {
    
    public VarSet val;
    
    /**
     * visitMove and visitBinary are the only operations actually propagating faintness. 
     */
    
    @Override
    public void visitMove (Quad q) {
    	// Add register operands to set of operands.
    	// TODO Maybe this could be short-circuited?
    	VarSet operands = new VarSet();
    	if (Operator.Move.getSrc(q) instanceof RegisterOperand)
    	{
    		operands.addVar(((RegisterOperand)Operator.Move.getSrc(q)).getRegister().toString());
    	}

        String lhs = Operator.Move.getDest(q).getRegister().toString();

        // TODO Comment
        if (!val.containsVar(lhs)) {
        	val.removeSet(operands);
        }
    	
        val.addVar(lhs);
    }
    
    @Override
    public void visitBinary (Quad q) {
    	// Add register operands to set of operands.
    	VarSet operands = new VarSet();
    	if (Operator.Binary.getSrc1(q) instanceof RegisterOperand)
    	{
    		operands.addVar(((RegisterOperand)Operator.Binary.getSrc1(q)).getRegister().toString());
    	}
    	
    	if (Operator.Binary.getSrc2(q) instanceof RegisterOperand)
    	{
    		operands.addVar(((RegisterOperand)Operator.Binary.getSrc2(q)).getRegister().toString());
    	}
    	
        String lhs =   Operator.Binary.getDest(q).getRegister().toString();
        
        // TODO Comment
        if (!val.containsVar(lhs)) 
        {
        	val.removeSet(operands);
        }
        
        // TODO Comment
        if (!operands.containsVar(lhs))
        {
        	val.addVar(lhs);
        }
    }
    
    /**
     * The following operations propagate liveness.
     */

    public void visitALoad(Quad q) {
        String key = Operator.ALoad.getDest(q).getRegister().toString();
    }

    @Override
    public void visitALength(Quad q) {
        String key = Operator.ALength.getDest(q).getRegister().toString();
    }

    @Override
    public void visitGetstatic(Quad q) {
        String key = Operator.Getstatic.getDest(q).getRegister().toString();
    }

    @Override
    public void visitGetfield(Quad q) {
        String key = Operator.Getfield.getDest(q).getRegister().toString();
    }

    @Override
    public void visitInstanceOf(Quad q) {
        String key = Operator.InstanceOf.getDest(q).getRegister().toString();
    }

    @Override
    public void visitNew(Quad q) {
        String key = Operator.New.getDest(q).getRegister().toString();
    }

    @Override
    public void visitNewArray(Quad q) {
        String key = Operator.NewArray.getDest(q).getRegister().toString();
    }

    @Override
    public void visitInvoke(Quad q) {
        RegisterOperand op = Operator.Invoke.getDest(q);
        if (op != null) {
            String key = op.getRegister().toString();
        }
    }

    @Override
    public void visitJsr(Quad q) {
        String key = Operator.Jsr.getDest(q).getRegister().toString();
    }

    @Override
    public void visitCheckCast(Quad q) {
        String key = Operator.CheckCast.getDest(q).getRegister().toString();
    }
}