// implementation of rhsvar expression

public class ColorExpr extends AST {

    public ColorExpr(Expr r, Expr g, Expr b) {
	this.addAST(r);
	this.addAST(g);
	this.addAST(b);
    }
}