package CelloGraph;

public class _DAGEdge_PV {

    protected  int Index;

    public _DAGVertex_PV From;

    public _DAGVertex_PV To;

    public _DAGEdge_PV Next;
    public String name="";
    public static int numberofedges;
    
    
    public _DAGEdge_PV()
    {
        this.Index = ++numberofedges;
        this.From = new _DAGVertex_PV();
        this.To = new _DAGVertex_PV();
        this.Next = null;
    }

    public _DAGEdge_PV(_DAGVertex_PV From, _DAGVertex_PV To, _DAGEdge_PV Next)
    {
        this.Index = ++numberofedges;
        this.From = From;
        this.To = To;
        this.Next = Next;

        if (From.Outgoing == null)
            From.Outgoing = this;
        else if (From.Outgoing.From == null)
            From.Outgoing = this;
        else
        {
            _DAGEdge_PV E = From.Outgoing;
            while (E.Next != null)
                E = E.Next;
            E.Next = this;
        }

    }


    public _DAGEdge_PV (_DAGEdge_PV e)
    {
       // this.From = e.From.Copy();
       // this.To = e.To.Copy();
       // if (e.Next != null)
       //     this.Next = e.Next.Copy();
        this.Index = e.Index;
    }

    public _DAGEdge_PV Copy ()
    {
        _DAGEdge_PV e = new _DAGEdge_PV(this);
        return e;
    }

    @Override
    public String toString ()
    {
        return this.name;
                
    }

}