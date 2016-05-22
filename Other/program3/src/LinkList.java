import java.io.*;

public class LinkList {

	private class Node {

		File f;
		Node next;

		public Node(File f) {
			this.f = f;
			next = null;
		}

		public int compareTo(Node node) {
			return f.getName().compareTo(node.f.getName());
		}

		public String toString() {
			return f.getAbsolutePath();
		}
	}

	Node top;

	private void add(Node node) {
		// 1. top is null
		if (top == null) {
			top = node;
		} // 2. new node goes prior to top
		else if (node.compareTo(top) < 0) {
			node.next = top;
			top = node;
		} // 3. new node goes somewhere in the middle
			// 4. new node goes at the end
		else {
			Node curr = top, prev = null;
			while (curr != null && curr.compareTo(node) < 0) {
				prev = curr;
				curr = curr.next;
			}
			prev.next = node;
			node.next = curr;
		}
	}

	public void add(File f) {
		add(new Node(f));
	}

	public String toString() {
		String result = "";
		for (Node curr = top; curr != null; curr = curr.next) {
			result += curr + "\n";
		}
		return result;
	}

	void deleteNode(Node n) {
		if (n.compareTo(top) == 0) {
			top = top.next;
		} else {
			Node curr = top, prev = null;
			while (curr != null && curr.compareTo(n) < 0) {
				prev = curr;
				curr = curr.next;
			}
			if (curr != null && curr.compareTo(n) == 0) {
				prev.next = curr.next;
			}
		}

	}
}
