package CelloGraph;

import netsynth.*;

import java.lang.Math;
import java.util.Arrays;
import java.util.ArrayList;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.math.*;
import CelloGraph.Wire;
//import org.python.core.PyFloat;
//import org.python.core.PyObject;
//import org.python.core.PyString;
//import org.python.core.PyList;
//import org.python.core.PyInteger;
//import org.python.util.PythonInterpreter;


public class Gate {
    public enum GateType{
        INPUT,
        OUTPUT,
        OUTPUT_OR,
        NOR,
        NOT;
    }
    
    public DWire outW = new DWire();
    public int Index;
    public String Name;
    public String Type;
    public Wire Outgoing; //toward INPUTS. note that a gate is not aware of its wire(s) going toward OUTPUT

    public ArrayList<Integer> logics;
    //public static ArrayList<InputLogic> CIRCUIT_INPUT_LOGICS;

    public ArrayList<Double> reus;
    //public static ArrayList<InputREU> CIRCUIT_INPUT_REUS;

    // in case of input gates have different on_off_reus;
    public ArrayList<Double> input_gate_reus;

    //public static final double[] ON_OFF_REUS = {0.05, 5.0}; // for NOT w/o riboz
    public static final double[] ON_OFF_REUS = {0.25, 50.0};

    public boolean simulate_logic; // need to re-simulate logic
    public boolean simulate_reu;   // need to re-simulate reu
    public double tir_ratio;
    public double score; // 1 - hightest_OFF/lowest_ON
    public int distance_to_input; 
    int farthest_dist2in = 1;
    int distance_to_output = 0;

    //public ArrayList<Part> gate_parts;  // parts for this gate
    //public ArrayList<Part> transcription_parts; // parts for a transcripion unit

    public int id;
    public int part_id;
    public int gatetype_id;
    public int input1_id;
    public int input2_id; 
    public int output_id;
    public String source;

    //public Part input1;
    //public Part input2;
    //public Part output;
    //public Model model;

    public String direction;

    

    public Gate(){
	this.Index = 0;
	this.Name = "";
	this.Type = "";
        this.Outgoing = null;
	this.tir_ratio =  -1.0;
	this.score = -1.0;
	this.distance_to_input = -1;
	this.simulate_logic = true;
	this.simulate_reu = true;
	this.direction = "+";
        this.outW = new DWire();
    }

    ///////////prashant's///////////
    public Gate(int ind,String dType)
    {
        this.Index = ind;
        this.Type = dType;
        if(dType.equals(GateType.NOR.toString()))
        {
            this.Name = "~|";
        }
        else if(dType.equals(GateType.NOT.toString()))
        {
            this.Name = "~";
        }
        else
        { 
            this.Name = "";
        }
       
        this.outW = new DWire();
        this.Outgoing = null;

	this.tir_ratio =  -1.0;
	this.score = -1.0;
	this.distance_to_input = -1;
	this.simulate_logic = true;
	this.simulate_reu = true;
	this.direction = "+";
    }

    public Gate(int ind,String dType,Wire de)
    {
        this.Index = ind;
        this.Type = dType;
        if(dType.equals(GateType.NOR.toString()))
        {
            this.Name = "~|";
        }
        else if(dType.equals(GateType.NOT.toString()))
        {
            this.Name = "~";
        }
        else
        { 
            this.Name = "";
        }
        this.Outgoing = de;
        
        this.outW = new DWire();

	this.tir_ratio =  -1.0;
	this.score = -1.0;
	this.distance_to_input = -1;
	this.simulate_logic = true;
	this.simulate_reu = true;
	this.direction = "+";
    
    }
    ////////////////////////////////


    public Gate(String gate_name) {
	//super();
	//this.constructFromDB(gate_name);
	//		this.id = dbi.rs.getInt("ID");
	//		this.Name = dbi.rs.getString("Name");
	//              this.part_id = dbi.rs.getInt("PartID");
	//		this.gatetype_id = dbi.rs.getInt("GateTypeID");
	//		this.input1_id = dbi.rs.getInt("Input1");
	//		this.input2_id = dbi.rs.getInt("Input2");
	//		this.output_id = dbi.rs.getInt("Output");
	//		this.source = dbi.rs.getString("Source");

	//Part gate_part = new Part(this.part_id);
	//this.gate_parts = gate_part.basic_parts;
	//this.getModel();
	this.tir_ratio = -1.0;
	this.score = -1.0;
	this.distance_to_input = -1;
	this.simulate_logic = true;
	this.simulate_reu = true;
	this.direction = "+";

	this.Index = 0;
	//this.Name = "";
	this.Type = "";
        this.Outgoing = null;
	this.outW = null;


    }

    // deep copy
    public Gate(Gate gate){
	simulate_logic = gate.simulate_logic;
	simulate_reu = gate.simulate_reu;
	tir_ratio    = gate.tir_ratio;
	score        = gate.score;
	distance_to_input = gate.distance_to_input;
	direction    = gate.direction;

	id           = gate.id;
	part_id      = gate.part_id;
	gatetype_id  = gate.gatetype_id;
	input1_id    = gate.input1_id;
	input2_id    = gate.input2_id; 
	output_id    = gate.output_id;
	if (gate.source != null){
	    source      = new String(gate.source);
	}
    
	//input1       = gate.input1;
	//input2       = gate.input2;
	//output       = gate.output;
	//model        = gate.model;

	//if (gate.gate_parts != null){
	//    this.gate_parts = gate.gate_parts;
	//}

	if (gate.reus != null) {
	    reus = new ArrayList<Double>();
	    for(Double reu: gate.reus ) {
		reus.add( new Double(reu) );
	    } 	
	}
	else
	    reus = new ArrayList<Double>();

	if (gate.logics != null ) {
	    logics = new ArrayList<Integer>();
	    for(Integer logic: gate.logics ) {
		logics.add( new Integer(logic) );
	    }
	}
	else
	    logics = new ArrayList<Integer>();

	this.outW = gate.outW;
	this.Index = gate.Index;
	this.Name = gate.Name;
	this.Type = gate.Type;
	this.Outgoing = gate.Outgoing;
	//this.Outgoing = new Wire(gate.Outgoing);
    }









  



 


}
