public class Fibbo {
    Node min;
    int n;
    boolean trace;
    Node found;

    public boolean get_trace() {
        return trace;
    }

    public void set_trace(boolean t) {
        this.trace = t;
    }

    public static Fibbo create_heap() {
        return new Fibbo();
    }

    Fibbo() {
        min = null;
        n = 0;
        trace = false;
    }

    public double getTotalOperationsInsert() {
        return (double) totalOperationsInsert/10000;
    }

    int totalOperationsInsert = 0;
    private void insert(Node x) {
        int op = 0;
        if (min == null) {
            min = x;
            x.set_left(min);
            x.set_right(min);
            op+=4;
        } else {
            x.set_right(min);
            x.set_left(min.get_left());
            min.get_left().set_right(x);
            min.set_left(x);
            op+=7;
            if (x.get_key() < min.get_key()) {
                min = x;
                op+=4;
            }
        }
        n += 1;
        op++;
        totalOperationsInsert+=op;
    }

    public void insert(int key) {
        insert(new Node(key));
        totalOperationsInsert+=2;
    }


    public int find_min() {
        if (min == null) {
            System.out.println("Куча пуста");
            return -1;
        } else {
            return this.min.get_key();
        }
    }

    public double getTotalOperationsDelete() {
        return (double)totalOperationsDelete/1000;
    }

    int totalOperationsDelete = 0;
    public int extractMin() {
        int op = 0;
        Node z = this.min;
        op+=1;
        if (z != null) {
            Node c = z.get_child();
            Node k = c, p;
            op+=3;
            if (c != null) {
                op+=1;
                do {
                    p = c.get_right();
                    insert(c);
                    c.set_parent(null);
                    c = p;
                    op+=7;
                } while (c != null && c != k);
            }
            z.get_left().set_right(z.get_right());
            z.get_right().set_left(z.get_left());
            z.set_child(null);
            op+=3;
            if (z == z.get_right()) {
                this.min = null;
                op += 2;
            } else {
                this.min = z.get_right();
                this.consolidate();
                op+=3;
            }
            this.n -= 1;
            op++;
            totalOperationsDelete+=op;
            return z.get_key();
        }
        return Integer.MAX_VALUE;
    }

    public void consolidate() {
        int op = 0;
        double phi = (1 + Math.sqrt(5)) / 2;
        int Dofn = (int) (Math.log(this.n) / Math.log(phi));
        Node[] A = new Node[Dofn + 1];
        op+=8;
        for (int i = 0; i <= Dofn; ++i) {
            A[i] = null;
            op+=2;
        }
        Node w = min;
        op++;
        if (w != null) {
            Node check = min;
            do {
                Node x = w;
                int d = x.get_degree();
                while (A[d] != null) {
                    Node y = A[d];
                    if (x.get_key() > y.get_key()) {
                        Node temp = x;
                        x = y;
                        y = temp;
                        w = x;
                        op+=7;
                    }
                    fib_heap_link(y, x);
                    check = x;
                    A[d] = null;
                    d += 1;
                    op+=5;
                }
                A[d] = x;
                w = w.get_right();
                op+=4;
            } while (w != null && w != check);
            this.min = null;
            op+=6;
            for (int i = 0; i <= Dofn; ++i) {
                if (A[i] != null) {
                    insert(A[i]);
                    op+=4;
                }
            }
        }
        totalOperationsDelete += op;
    }
    private void fib_heap_link(Node y, Node x) {
        int op = 0;
        y.get_left().set_right(y.get_right());
        y.get_right().set_left(y.get_left());

        Node p = x.get_child();
        op+=8;
        if (p == null) {
            y.set_right(y);
            y.set_left(y);
            op+=3;
        } else {
            y.set_right(p);
            y.set_left(p.get_left());
            p.get_left().set_right(y);
            p.set_left(y);
            op+=7;
        }
        y.set_parent(x);
        x.set_child(y);
        x.set_degree(x.get_degree() + 1);
        y.set_mark(false);
        op+=7;
        totalOperationsDelete+=op;
    }
}