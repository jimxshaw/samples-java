public class Tree {

	protected class Node {

		String data;
		Node right, left;

		public Node(String s) {
			data = s;
			right = left = null;
		}

		public Node(char ch) {
			this(String.valueOf(ch));
		}

	}

	Node root;
	String eqn;
	int pos;
	char ch;

	public void parse(String e) {
		eqn = e;
		pos = 0;
		getChar();
		root = findExpr();
	}

	protected char getChar() {
		if (pos < eqn.length()) {
			return ch = eqn.charAt(pos++);
		}
		return ch = '\0';
	}

	protected Node findExpr() {
		Node tmproot = findProd();
		while (ch == '+' || ch == '-') {
			Node tmp = new Node(ch);
			getChar();
			tmp.left = tmproot;
			tmp.right = findProd();
			tmproot = tmp;
		}
		return tmproot;
	}

	protected Node findProd() {
		Node tmproot = findTerm();
		while (ch == '*' || ch == '/') {
			Node tmp = new Node(ch);
			getChar();
			tmp.left = tmproot;
			tmp.right = findTerm();
			tmproot = tmp;
		}
		return tmproot;
	}

	protected Node findTerm() {
		if (ch == 'x') {
			Node tmp = new Node(ch);
			getChar();
			return tmp;
		}
		if (ch >= '0' && ch <= '9') {
			StringBuilder buffer = new StringBuilder();
			for (int j = 0; ch >= '0' && ch <= '9'; j++) {
				buffer.append(ch);
				getChar();
			}
			return new Node(buffer.toString());
		}
		if (ch == '(') {
			getChar();
			Node tmp = findExpr();
			getChar();
			return tmp;
		}
		if (ch == 's' && eqn.substring(pos).startsWith("in")) {
			Node tmp = new Node(ch);
			pos += 2;
			getChar();
			tmp.right = findExpr();
			getChar();
			return tmp;
		}
		if (ch == 'c' && eqn.substring(pos).startsWith("os")) {
			Node tmp = new Node(ch);
			pos += 2;
			getChar();
			tmp.right = findExpr();
			getChar();
			return tmp;
		}
		if (ch == '-') {
			Node tmp = new Node(ch);
			getChar();
			tmp.right = findTerm();
			return tmp;
		}
		return null;
	}

	protected float x;

	public float calc(float x) {
		this.x = x;
		return calc(root);
	}

	protected float calc(Node root) {
		switch (root.data.charAt(0)) {
		case '+':
			return calc(root.left) + calc(root.right);
		case '-':
			if (root.left == null) {
				return -calc(root.right);
			}
			return calc(root.left) - calc(root.right);
		case '*':
			return calc(root.left) * calc(root.right);
		case '/':
			return calc(root.left) / calc(root.right);
		case 's':
			return (float) Math.sin(calc(root.right));
		case 'c':
			return (float) Math.cos(calc(root.right));
		case 'x':
			return x;
		}
		return Float.parseFloat(root.data);
	}
}

/*
 * E -> P + E 
 * E -> P 
 * p -> T * P 
 * p -> T 
 * T -> <x> 
 * T -> <numbers>
 */
