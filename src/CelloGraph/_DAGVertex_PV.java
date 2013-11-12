package CelloGraph;

public class _DAGVertex_PV {

    protected  int Index;

    public String Name;

    public String Type;

    public _DAGEdge_PV Outgoing;


    public int Cover  = -1;
    public int subCover  = -1;

    public static int numberofvertex;
   // public int id;

    public _DAGVertex_PV ()
    {
        this.Index = ++numberofvertex;//++;
        this.Name = "";
        this.Type = "";
       this.Outgoing = null;

      // this.id = ++numberofvertex;
    }

    public _DAGVertex_PV(String Name, String Type, _DAGEdge_PV Outgoing)
    {
        this.Index= ++numberofvertex;//++;
        this.Name = Name;
        this.Type = Type;
        this.Outgoing = Outgoing;
    }

    
public _DAGVertex_PV (_DAGVertex_PV v)
    {
        this.Index = v.Index;
        this.Name = v.Name;
        //if (v.Outgoing != null)
        //    this.Outgoing = v.Outgoing.Copy();
        this.Type = v.Type;
        this.Cover = v.Cover;
        this.subCover = v.subCover;
    }

    public _DAGVertex_PV Copy ()
    {
        _DAGVertex_PV v = new _DAGVertex_PV(this);
        return v;
    }

    public void AddEdge(_DAGEdge_PV Edge)
    {
        if (this.Outgoing == null)
            this.Outgoing = Edge;
        else
        {
            _DAGEdge_PV e = this.Outgoing.Next;
            while (e != null)
                e = e.Next;
            e =  Edge;
        }
    }

    @Override
    public String toString()
    {
        String s = "";
        s += this.Name;
        /*if (this.Feature != null )
            if (this.Feature._feature != null)
                s+= this.Feature._feature.getName();
         *
         */
        return s;

    }



}

