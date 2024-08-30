class BigNumberNode {

    private int data;
    private BigNumberNode next;

    public BigNumberNode(int data) {
        this.data = data;
        this.next = null;
    }
    public void setData(int data) {
        this.data = data;
    }
    public int getData() {
        return data;
    }
    public void setNext(BigNumberNode n) { this.next = n; }
    public BigNumberNode getNext() { return next; }
}