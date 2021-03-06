// Node for a cell or neighbor expression

public class CellCheckExpr extends Expr {

	private String type;
	private boolean relative;

	public CellCheckExpr(String type, CoordExpr location, boolean relative)
	{
		this.addAST(location);
		this.type = type;
		this.relative = relative;
	}

	public String type() {
		return type;
	}

	public boolean relative() {
		return relative;
	}
}
