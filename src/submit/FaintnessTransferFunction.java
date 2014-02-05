package submit;

import joeq.Compiler.Quad.Operand;
import joeq.Compiler.Quad.Operator;
import joeq.Compiler.Quad.Quad;
import joeq.Compiler.Quad.QuadVisitor;
import joeq.Compiler.Quad.Operand.IConstOperand;
import joeq.Compiler.Quad.Operand.RegisterOperand;

/* The QuadVisitor that actually does the computation */
public class FaintnessTransferFunction extends QuadVisitor.EmptyVisitor {
    
    public VarSet val;
    
    @Override
    public void visitMove (Quad q) {
        Operand op = Operator.Move.getSrc(q);
        String key = Operator.Move.getDest(q).getRegister().toString();

//        if (isUndef(op)) {
//            val.setUndef(key);
//        } else if (isConst(op)) {
//            val.setConst(key, getConst(op));
//        } else {
//            val.setNAC(key);
//        }
    }
    
    @Override
    public void visitBinary (Quad q) {
        Operand op1 =  Operator.Binary.getSrc1(q);
        Operand op2 =  Operator.Binary.getSrc2(q);
        String key =   Operator.Binary.getDest(q).getRegister().toString();
        Operator opr = q.getOperator();

//        if (opr == Operator.Binary.ADD_I.INSTANCE) {
//            if (isNAC(op1) || isNAC(op2)) {
//                val.setNAC(key);
//            } else if (isUndef(op1) || isUndef(op2)) {
//                val.setUndef(key);
//            } else { // both must be constant!
//                val.setConst(key, getConst(op1)+getConst(op2));
//            }
//        } else {
//            val.setNAC(key);
//        }
    }
}