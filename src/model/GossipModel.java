package model;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import parser.State;
import parser.ast.DeclarationInt;
import parser.ast.DeclarationType;
import parser.ast.Expression;
import parser.type.Type;
import parser.type.TypeBool;
import parser.type.TypeInt;
import prism.DefaultModelGenerator;
import prism.ModelType;
import prism.PrismException;
import org.json.*;


import utils.*;


public class GossipModel extends DefaultModelGenerator
{

	public enum GSE { //Gossip States Enumeration
		b1,
		b2,
		b3,
		b4,
		s1,
		v1_1_a,
		v1_2_a,
		v1_1_h,
		v1_2_h,
		a1,
		h1,
		i1,
		send1,
		s2,
		v2_1_a,
		v2_2_a,
		v2_1_h,
		v2_2_h,
		a2,
		h2,
		i2,
		send2,
		s3,
		v3_1_a,
		v3_2_a,
		v3_1_h,
		v3_2_h,
		a3,
		h3,
		i3,
		send3,
		s4,
		v4_1_a,
		v4_2_a,
		v4_1_h,
		v4_2_h,
		a4,
		h4,
		i4,
		send4,
		
	};
	
	
	public enum AE{ //Action Enumeration
		start1,
		start2,
		start3,
		start4,
		
		push2_1_0,
		push3_1_0,
		push4_1_0,
		push2_1_1,
		push3_1_1,
		push4_1_1,
		push2_1_end,
		push3_1_end,
		push4_1_end,
		discard_received_hop_for_this_address1,
		received_hop_count_for_address_which_we_have_already_lower_hop_count_1,
		received_hop_count_lower_than_v_1_1_h_store_the_newest_hop_1,
		old_value_with_new_hop_store_to_position_2_for_hop_less_than_v1_2h_1,
		old_value_with_new_hop_store_to_position_1_for_hop_less_than_v1_1h_1,
		discard_new_address_hop_too_big_1,
		new_entry_store_to_2_position_1,
		new_entry_store_to_1_position_1,
		dont_send_view_0_1,
		send_the_first_view_1,
		send_1_or_2_view_1,
		push1_2_0,
		push1_2_1,
		push1_2_end,
		push1_3_0,
		push1_3_1,
		push1_3_end,
		push1_4_0,
		push1_4_1,
		push1_4_end,
		
		push3_2_0,
		push4_2_0,
		push3_2_1,
		push4_2_1,
		push3_2_end,
		push4_2_end,
		discard_received_hop_for_this_address2,
		received_hop_count_for_address_which_we_have_already_lower_hop_count_2,
		received_hop_count_lower_than_v_1_1_h_store_the_newest_hop_2,
		old_value_with_new_hop_store_to_position_2_for_hop_less_than_v1_2h_2,
		old_value_with_new_hop_store_to_position_1_for_hop_less_than_v1_1h_2,
		discard_new_address_hop_too_big_2,
		new_entry_store_to_2_position_2,
		new_entry_store_to_1_position_2,
		dont_send_view_0_2,
		send_the_first_view_2,
		send_1_or_2_view_2,
		push2_3_0,
		push2_3_1,
		push2_3_end,
		push2_4_0,
		push2_4_1,
		push2_4_end,
		

		push4_3_0,
		push4_3_1,
		push4_3_end,
		discard_received_hop_for_this_address3,
		received_hop_count_for_address_which_we_have_already_lower_hop_count_3,
		received_hop_count_lower_than_v_1_1_h_store_the_newest_hop_3,
		old_value_with_new_hop_store_to_position_2_for_hop_less_than_v1_2h_3,
		old_value_with_new_hop_store_to_position_1_for_hop_less_than_v1_1h_3,
		discard_new_address_hop_too_big_3,
		new_entry_store_to_2_position_3,
		new_entry_store_to_1_position_3,
		dont_send_view_0_3,
		send_the_first_view_3,
		send_1_or_2_view_3,
		push3_4_0,
		push3_4_1,
		push3_4_end,
		
		
		discard_received_hop_for_this_address4,
		received_hop_count_for_address_which_we_have_already_lower_hop_count_4,
		received_hop_count_lower_than_v_1_1_h_store_the_newest_hop_4,
		old_value_with_new_hop_store_to_position_2_for_hop_less_than_v1_2h_4,
		old_value_with_new_hop_store_to_position_1_for_hop_less_than_v1_1h_4,
		discard_new_address_hop_too_big_4,
		new_entry_store_to_2_position_4,
		new_entry_store_to_1_position_4,
		dont_send_view_0_4,
		send_the_first_view_4,
		send_1_or_2_view_4
		
	}
	
	// Current state being explored
	private State exploreState;
	
	// size of gossip model
	private int n;

	// local actions
	private Action av_actions[];
	
	// Labels for 4 actions
	private Variable variables[];
	
	//debug: print useful messages
	private boolean debug = true;

	public GossipModel(int n)
	{
		this.n = n;
		this.variables = new Variable[GSE.values().length];
		//this.actions = new Action[];
		
		// Scheduler
		variables[GSE.b1.ordinal()] = new Variable("b1","int",0,1,0);
		variables[GSE.b2.ordinal()] = new Variable("b2","int",0,1,0);
		variables[GSE.b3.ordinal()] = new Variable("b3","int",0,1,0);
		variables[GSE.b4.ordinal()] = new Variable("b4","int",0,1,0);
		
		
		// Node 1 variables
		variables[GSE.s1.ordinal()] = new Variable("s1","int",0,3,0);
		
		
		variables[GSE.v1_1_a.ordinal()] = new Variable("v1_1_a","int",0,4,2);
		variables[GSE.v1_2_a.ordinal()] = new Variable("v1_2_a","int",0,4,0);
		variables[GSE.v1_1_h.ordinal()] = new Variable("v1_1_h","int",0,4,1);
		variables[GSE.v1_2_h.ordinal()] = new Variable("v1_2_h","int",0,4,4);
		variables[GSE.a1.ordinal()] = new Variable("a1","int",0,4,0);
		variables[GSE.h1.ordinal()] = new Variable("h1","int",0,4,0);
		variables[GSE.i1.ordinal()] = new Variable("i1","int",0,2,0);
		variables[GSE.send1.ordinal()] = new Variable("send1","int",0,this.n,0);
		
		
		
		// Node 2 variables
		variables[GSE.s2.ordinal()] = new Variable("s2","int",0,3,0);
		variables[GSE.v2_1_a.ordinal()] = new Variable("v2_1_a","int",0,4,0);
		variables[GSE.v2_2_a.ordinal()] = new Variable("v2_2_a","int",0,4,0);
		variables[GSE.v2_1_h.ordinal()] = new Variable("v2_1_h","int",0,4,4);
		variables[GSE.v2_2_h.ordinal()] = new Variable("v2_2_h","int",0,4,4);
		variables[GSE.a2.ordinal()] = new Variable("a2","int",0,4,0);
		variables[GSE.h2.ordinal()] = new Variable("h2","int",0,4,0);
		variables[GSE.i2.ordinal()] = new Variable("i2","int",0,2,0);
		variables[GSE.send2.ordinal()] = new Variable("send2","int",0,this.n,0);
		
		// Node 3 variables
		variables[GSE.s3.ordinal()] = new Variable("s3","int",0,3,0);
		variables[GSE.v3_1_a.ordinal()] = new Variable("v3_1_a","int",0,4,2);
		variables[GSE.v3_2_a.ordinal()] = new Variable("v3_2_a","int",0,4,0);
		variables[GSE.v3_1_h.ordinal()] = new Variable("v3_1_h","int",0,4,1);
		variables[GSE.v3_2_h.ordinal()] = new Variable("v3_2_h","int",0,4,4);
		variables[GSE.a3.ordinal()] = new Variable("a3","int",0,4,0);
		variables[GSE.h3.ordinal()] = new Variable("h3","int",0,4,0);
		variables[GSE.i3.ordinal()] = new Variable("i3","int",0,2,0);
		variables[GSE.send3.ordinal()] = new Variable("send3","int",0,this.n,0);
		
		// Node 4 variables
		variables[GSE.s4.ordinal()] = new Variable("s4","int",0,3,0);
		variables[GSE.v4_1_a.ordinal()] = new Variable("v4_1_a","int",0,4,2);
		variables[GSE.v4_2_a.ordinal()] = new Variable("v4_2_a","int",0,4,0);
		variables[GSE.v4_1_h.ordinal()] = new Variable("v4_1_h","int",0,4,1);
		variables[GSE.v4_2_h.ordinal()] = new Variable("v4_2_h","int",0,4,4);
		variables[GSE.a4.ordinal()] = new Variable("a4","int",0,4,0);
		variables[GSE.h4.ordinal()] = new Variable("h4","int",0,4,0);
		variables[GSE.i4.ordinal()] = new Variable("i4","int",0,2,0);
		variables[GSE.send4.ordinal()] = new Variable("send4","int",0,this.n,0);
		
		
		
	}
	
	
	// i index action
	// p primary
	// s secondary
	private void processStartX(Action[] act, int i, int p, int s2, int s3, int s4, String vn, String uv) {
		/*
		 * 
		 * 	[start1] b1=0 & (b2+b3+b4<3) -> (b1'=1); // schedule node 1 and stay in current round
			[start2] b2=0 & (b1+b3+b4<3) -> (b2'=1); // schedule node 2 and stay in current round
			[start3] b3=0 & (b1+b2+b4<3) -> (b3'=1); // schedule node 3 and stay in current round
			[start4] b4=0 & (b1+b2+b3<3) -> (b4'=1); // schedule node 4 and stay in current round
		
			[start1] b1=0 & (b2+b3+b4=3) -> (b1'=0) & (b2'=0) & (b3'=0) & (b4'=0); // schedule node 1 move to next round
			[start2] b2=0 & (b1+b3+b4=3) -> (b1'=0) & (b2'=0) & (b3'=0) & (b4'=0); // schedule node 2 move to next round
			[start3] b3=0 & (b1+b2+b4=3) -> (b1'=0) & (b2'=0) & (b3'=0) & (b4'=0); // schedule node 3 move to next round
			[start4] b4=0 & (b1+b2+b3=3) -> (b1'=0) & (b2'=0) & (b3'=0) & (b4'=0); // schedule node 4 move to next round
		 * 
		 */
		
		if( ((Integer) exploreState.varValues[p]).intValue() == 0 &&
				(((Integer) exploreState.varValues[s2]).intValue() + 
				((Integer) exploreState.varValues[s3]).intValue()	+
				((Integer) exploreState.varValues[s4]).intValue() ) < 3) {
			act[i].setEnable(true);
			act[i].setUpdateVarName(vn);
			act[i].setUpdateValue(uv);
		}
		
		if( ((Integer) exploreState.varValues[p]).intValue() == 0 &&
				(((Integer) exploreState.varValues[s2]).intValue() + 
				((Integer) exploreState.varValues[s3]).intValue()	+
				((Integer) exploreState.varValues[s4]).intValue() ) == 3) {
			act[i].setEnable(true);
			act[i].setUpdateVarName("b1;b2;b3;b4");
			act[i].setUpdateValue("0;0;0;0");
		}
	}
	
	// process the machine module startX
	// i index action
	// p primary
	// s secondary
	private void processMachineStartX(Action[] act, int i, int p, int s2, int s3, int s4, String vn, String uv) {
		//[start1] s1=0 & !(s2>0 | s3>0 | s4>0) -> (s1'=2);	
		if( act[i].isEnable() && ((Integer) exploreState.varValues[p]).intValue() == 0 &&
				!(((Integer) exploreState.varValues[s2]).intValue() > 0 ||
				((Integer) exploreState.varValues[s3]).intValue()	> 0 ||
				((Integer) exploreState.varValues[s4]).intValue() > 0 )) {
			act[i].setEnable(true);
			act[i].appendUpdateVarName(vn);
			act[i].appendUpdateValue(uv);
		}else {
			act[i].setEnable(false);
			act[i].setUpdateValue("");
			act[i].setUpdateVarName("");
		}
	}
	
	private int getSizeOfView(int v1_1_a, int v1_2_a) {
		int v1 = ((Integer) exploreState.varValues[v1_1_a]).intValue() > 0 ? 1 : 0;
		int v2 = ((Integer) exploreState.varValues[v1_2_a]).intValue() > 0 ? 1 : 0;
		return v1 + v2;
	}
	
	private void processDontSend(Action[] act, int i, int s, int v1_1_a, int v1_2_a, String vn, String uv) {
		
		//[dont_send_view_0_1] s1=2 & size_of_view1=0 -> (s1'=0);
		if(((Integer) exploreState.varValues[s]).intValue() == 2 && 
				this.getSizeOfView(v1_1_a, v1_2_a) == 0) {
			act[i].setEnable(true);
			act[i].appendUpdateVarName(vn);
			act[i].appendUpdateValue(uv);
		}
		
		
	}
	
	private void processDecideSendView1(Action[] act, int i, int s, int v1_1_a, int v1_2_a, String vn, String uv) {
		//[send_the_first_view_1] s1=2 & size_of_view1=1 -> (s1'=3) & (send1'=v1_1_a);
		if(((Integer) exploreState.varValues[s]).intValue() == 2 && 
				this.getSizeOfView(v1_1_a, v1_2_a) == 1) {
			act[i].setEnable(true);
			act[i].appendUpdateVarName(vn);
			act[i].appendUpdateValue(uv);
		}	
	
	}
	
	private void processDecideSendView1OrView2(Action[] act, int i, int s, int v1_1_a, int v1_2_a, String json) {
		//[send_1_or_2_view_1] s1=2 & size_of_view1=2 -> 0.5 : (s1'=3) & (send1'=v1_1_a) + 0.5 : (s1'=3) & (send1'=v1_2_a);
		if(((Integer) exploreState.varValues[s]).intValue() == 2 && 
				this.getSizeOfView(v1_1_a, v1_2_a) == 2) {
			act[i].setEnable(true);
			act[i].setJsonUpdate(json);
		}
	
	}
	
	private void processPushX_Y_0(Action[] act, int i, int s, int s2, int send, int id, int ii, String vn, String uv) {
		//[push1_2_0] s1=3 & send1=id2 & i1=0 -> (i1'=i1+1);
		// Receive first new address/hop-count pair
		//[push2_3_0] s2=3 & send2=id3 & i2=0 -> (i2'=i2+1);
		if(returnIntValueById(s) == 3 && returnIntValueById(send) == id /*idendita' del nodo 2*/ 
				&&
				returnIntValueById(ii) == 0) {
			act[i].setEnable(true);
			act[i].appendUpdateVarName(vn);
			act[i].appendUpdateValue(uv);
		}
		
		//[push1_2_0] s2=0 -> (s2'=1) & (a2'=id2) & (h2'=0);
		//[push2_3_0] s3=0 ->  s3=1 & a3=id3 & (h3'=0);
		if( act[i].isEnable() && returnIntValueById(s2) == 0) {
			act[i].setEnable(true);
			//act[i].appendUpdateVarName(vn);
			//act[i].appendUpdateValue(uv);
		}else {
			act[i].setEnable(false);
			act[i].setUpdateValue("");
			act[i].setUpdateVarName("");
		}
	}
	
	private void processPushX_Y_1(Action[] act, int i, int s,
			int s2, int send, int id, int ii, int v1_1_h, String vn, String uv) {
		//[push1_2_1] s1=3 & send1=id2 & i1=1 & v1_1_h<4 -> (s1'=0) & (i1'=0) & (send1'=0);
		if(returnIntValueById(s) == 3 &&
				returnIntValueById(send) == id /*idendita' del nodo 2*/ &&
				returnIntValueById(ii) == 1 &&
				returnIntValueById(v1_1_h) < 4) {
			act[i].setEnable(true);
			act[i].appendUpdateVarName(vn);
			act[i].appendUpdateValue(uv);
		}
		
		// Receive subsequent address/hop-count pair from same sender
		//[push1_2_1] s2=0 -> (s2'=1) & (a2'=v1_1_a) & (h2'=v1_1_h);
		if( act[i].isEnable() && returnIntValueById(s2) == 0) {
			act[i].setEnable(true);
			//act[i].appendUpdateVarName(vn);
			//act[i].appendUpdateValue(uv);
		}else {
			act[i].setEnable(false);
			act[i].setUpdateValue("");
			act[i].setUpdateVarName("");
		}
	}
	
	private void processPushX_Y_end(Action[] act, int i, int s,
			int s2, int send, int id, int ii, int v1_1_h, int v1_2_h, String vn, String uv) {
		//[push1_2_end] s1=3 & send1=id2 & ((i1=1&v1_1_h=4) | (i1=2&v1_2_h=4)) -> (s1'=0) & (i1'=0) & (send1'=0); 
		if(returnIntValueById(s) == 3 &&
				returnIntValueById(send) == id /*idendita' del nodo 2*/ &&
				( (returnIntValueById(ii) == 1 &&
				returnIntValueById(v1_1_h) == 4) || (returnIntValueById(ii) == 2 &&
				returnIntValueById(v1_2_h) == 4) )) {
			act[i].setEnable(true);
			act[i].appendUpdateVarName(vn);
			act[i].appendUpdateValue(uv);
		}
		
		// Receive subsequent address/hop-count pair from same sender
		//[push1_2_end] s2=0 -> (s2'=0) & (a2'=0) & (h2'=0);;
		if( act[i].isEnable() && returnIntValueById(s2) == 0) {
			act[i].setEnable(true);
			//act[i].appendUpdateVarName(vn);
			//act[i].appendUpdateValue(uv);
		}else {
			act[i].setEnable(false);
			act[i].setUpdateValue("");
			act[i].setUpdateVarName("");
		}
	}
	
	private void processDiscardHopForSelfAddress(Action[] act, int i, int s, int a, int id, String vn, String uv) {
		
		//[discard_received_hop_for_this_address1] s1=1 & a1=id1 -> (s1'=0) & (a1'=0) & (h1'=0);
		if(returnIntValueById(s) == 1 &&
				returnIntValueById(a) == id) {
			act[i].setEnable(true);
			act[i].appendUpdateVarName(vn);
			act[i].appendUpdateValue(uv);
		}
	}
	
	private void processHopVSLowerHopCount(Action[] act, int i, int s, int a, int id, int h, int v1_1_a, int v1_1_h,
			int v1_2_a, int v1_2_h,String vn, String uv) {
		// Received hop count for address for which we already have a lower hop count so discard
		//[received_hop_count_for_address_which_we_have_already_lower_hop_count_1] s1=1 & !(a1=id1) & already_have_lower1 -> (s1'=0) & (a1'=0) & (h1'=0);
		if(returnIntValueById(s) == 1 &&
				(returnIntValueById(a) != id) 
				&& ( (returnIntValueById(a)==returnIntValueById(v1_1_a) && (returnIntValueById(h)+1) >= returnIntValueById(v1_1_h))
						|| (returnIntValueById(a)==returnIntValueById(v1_2_a) && (returnIntValueById(h)+1) >= returnIntValueById(v1_2_h)) )
				) {
			act[i].setEnable(true);
			act[i].appendUpdateVarName(vn);
			act[i].appendUpdateValue(uv);
		}
	}
	
	private void processRefreshHopCount(Action[] act, int i, int s, int a, int id, int h, int v1_1_a, int v1_1_h,
			String vn, String uv) {
		// Received hop count for address for which we already have a value but new one is lower
		// (need to store new pair in position "where_to_put1" and shift some pairs to the right)
		// (i.e.: pairs at position i for which i>=where_to_put1 and i<position of old value)
		// Old value stored in position 1
		//[received_hop_count_lower_than_v_1_1_h_store_the_newest_hop_1] s1=1 & !(a1=id1) & a1=v1_1_a & h1+1<v1_1_h -> (s1'=0) & (v1_1_h'=h1+1) & (a1'=0) & (h1'=0);
		if(returnIntValueById(s) == 1 &&
				(returnIntValueById(a) != id) 
				&& returnIntValueById(a)==returnIntValueById(v1_1_a) && (returnIntValueById(h)+1) < returnIntValueById(v1_1_h)
						 ) {
			act[i].setEnable(true);
			act[i].appendUpdateVarName(vn);
			act[i].appendUpdateValue(uv);
		}
	}
	
	private void processRefreshAddress2Position(Action[] act, int i, int s, int a, int id, int h, int v1_1_a, int v1_1_h,
			int v1_2_a, int v1_2_h, String vn, String uv) {

		//[old_value_with_new_hop_store_to_position_2_for_hop_less_than_v1_2h_1] s1=1 & !(a1=id1) & !(a1=v1_1_a) & a1=v1_2_a & h1+1<v1_2_h & where_to_put1=2 -> (s1'=0) & (v1_2_a'=a1) & (v1_2_h'=h1+1) & (a1'=0) & (h1'=0);
		if(returnIntValueById(s) == 1 &&
				!(returnIntValueById(a) == id) 
				&&  (returnIntValueById(a)!=returnIntValueById(v1_1_a))
				&& (returnIntValueById(a) == returnIntValueById(v1_2_a)
				&& ((returnIntValueById(h)+1) < returnIntValueById(v1_2_h))
				&& ( whereToPut(h, v1_1_h, v1_2_h)  == 2 )
				)) {
			act[i].setEnable(true);
			act[i].appendUpdateVarName(vn);
			act[i].appendUpdateValue(uv);
		}
	}
	
	private void processRefreshAddress1Position(Action[] act, int i, int s, int a, int id, int h, int v1_1_a, int v1_1_h,
			int v1_2_a, int v1_2_h, String vn, String uv) {

		//[old_value_with_new_hop_store_to_position_1_for_hop_less_than_v1_1h_1] s1=1 &
		//!(a1=id1) & !(a1=v1_1_a) & a1=v1_2_a & h1+1<v1_2_h & where_to_put1=1 ->
		//(s1'=0) & (v1_1_a'=a1) & (v1_1_h'=h1+1) & (v1_2_a'=v1_1_a) & (v1_2_h'=v1_1_h) & (a1'=0) & (h1'=0);

		if(returnIntValueById(s) == 1 &&
				(returnIntValueById(a) != id) 
				&&  (returnIntValueById(a)!=returnIntValueById(v1_1_a))
				&& (returnIntValueById(a) == returnIntValueById(v1_2_a)
				&& ((returnIntValueById(h)+1) < returnIntValueById(v1_2_h))
				&& ( whereToPut(h, v1_1_h, v1_2_h)  == 1 )
				)) {
			
			act[i].setEnable(true);
			act[i].appendUpdateVarName(vn);
			act[i].appendUpdateValue(uv);
		}
	}
	// new entry
	private void processDiscardNewAddressHopTooBig(Action[] act, int i, int s, int a, int id, int h, int v1_1_a, int v1_1_h,
			int v1_2_a, int v1_2_h, String vn, String uv) {

		// Received hop count for address for which we have no existing value
		// (need to shift existing pair one to the right)
		//[discard_new_address_hop_too_big_1] s1=1 & !(a1=id1 | already_have_addr1) & where_to_put1=3 -> (s1'=0) & (a1'=0) & (h1'=0);
		
		if(returnIntValueById(s) == 1 &&
				!(returnIntValueById(a) == id || alreadyHaveAddr(a,v1_1_a,v1_2_a)) 
				&& (whereToPut(h, v1_1_h, v1_2_h) == 3 )
				) {
			
			act[i].setEnable(true);
			act[i].appendUpdateVarName(vn);
			act[i].appendUpdateValue(uv);
		}
	}
	
	// new entry
	private void processAddEntryToSecondPosition(Action[] act, int i, int s, int a, int id, int h, int v1_1_a, int v1_1_h,
			int v1_2_a, int v1_2_h, String vn, String uv) {

		// Received hop count for address for which we have no existing value
		// (need to shift existing pair one to the right)
		//[new_entry_store_to_2_position_1] s1=1 & !(a1=id1 | already_have_addr1) & where_to_put1=2 -> (s1'=0) & (v1_2_a'=a1) & (v1_2_h'=h1+1) & (a1'=0) & (h1'=0);
		
		
		if(returnIntValueById(s) == 1 &&
				!(returnIntValueById(a) == id || alreadyHaveAddr(a,v1_1_a,v1_2_a)) 
				&& (whereToPut(h, v1_1_h, v1_2_h) == 2 )
				) {
			
			act[i].setEnable(true);
			act[i].appendUpdateVarName(vn);
			act[i].appendUpdateValue(uv);
		}
	}
	
	// new entry
	private void processAddEntryToFirstPosition(Action[] act, int i, int s, int a, int id, int h, int v1_1_a, int v1_1_h,
			int v1_2_a, int v1_2_h, String vn, String uv) {

		// Received hop count for address for which we have no existing value
		// (need to shift existing pair one to the right)
		//shift the previous to v_1_1_a and v_1_1_h to v_1_2_a and v_1_2_h resp
		//[new_entry_store_to_1_position_1] s1=1 & !(a1=id1 | already_have_addr1) & where_to_put1=1 -> (s1'=0) & (v1_1_a'=a1) & (v1_1_h'=h1+1) & (v1_2_a'=v1_1_a) & (v1_2_h'=v1_1_h) & (a1'=0) & (h1'=0);
		
		
		if(returnIntValueById(s) == 1 &&
				!(returnIntValueById(a) == id || alreadyHaveAddr(a,v1_1_a,v1_2_a)) 
				&& (whereToPut(h, v1_1_h, v1_2_h) == 1 )
				) {
			
			act[i].setEnable(true);
			act[i].appendUpdateVarName(vn);
			act[i].appendUpdateValue(uv);
		}
	}
	
	private int whereToPut(int h, int vx_1_h, int vx_2_h) {
		int final_value = 0;
		int temp_value = 0;
		
		if( (returnIntValueById(h)+1) <= returnIntValueById(vx_2_h)) {
			temp_value = 2;
		}else {
			temp_value = 3;
		}
		
		if( (returnIntValueById(h)+1) <= returnIntValueById(vx_1_h)) {
			final_value = 1;
		}else {
			final_value = temp_value;
		}
		
		return final_value;
	}
	
	private Boolean alreadyHaveAddr(int a, int vx_1_a, int vx_2_a) {
		return (returnIntValueById(a)==returnIntValueById(vx_1_a) || returnIntValueById(a)==returnIntValueById(vx_2_a));
	}
	
	private String returnStateValueById(int i) {
		return Integer.toString((((Integer) exploreState.varValues[i]).intValue()));
	}
	
	private int returnIntValueById(int i) {
		return ((((Integer) exploreState.varValues[i]).intValue()));
	}
	
	
	
	
	public Action[] calculateTransitions() {
		
		Action[] actions = new Action[AE.values().length];

		// Initializing all actions by its name
		for(int i = 0; i < AE.values().length; i++) {
			actions[i] = new Action(AE.values()[i].toString(),"","");
		}
		
		/**** 
		 * 	
		 *  [start1] b1=0 & (b2+b3+b4<3) -> (b1'=1); // schedule node 1 and stay in current round
			[start2] b2=0 & (b1+b3+b4<3) -> (b2'=1); // schedule node 2 and stay in current round
			[start3] b3=0 & (b1+b2+b4<3) -> (b3'=1); // schedule node 3 and stay in current round
			[start4] b4=0 & (b1+b2+b3<3) -> (b4'=1); // schedule node 4 and stay in current round
		 * 
		 * 
		 * ****/
		
		/**** 
		 * 	
			[start1] b1=0 & (b2+b3+b4=3) -> (b1'=0) & (b2'=0) & (b3'=0) & (b4'=0); // schedule node 1 move to next round
			[start2] b2=0 & (b1+b3+b4=3) -> (b1'=0) & (b2'=0) & (b3'=0) & (b4'=0); // schedule node 2 move to next round
			[start3] b3=0 & (b1+b2+b4=3) -> (b1'=0) & (b2'=0) & (b3'=0) & (b4'=0); // schedule node 3 move to next round
			[start4] b4=0 & (b1+b2+b3=3) -> (b1'=0) & (b2'=0) & (b3'=0) & (b4'=0); // schedule node 4 move to next round

		 * 
		 * 
		 * ****/
		processStartX(actions,AE.start1.ordinal(),GSE.b1.ordinal(), GSE.b2.ordinal(), GSE.b3.ordinal(), GSE.b4.ordinal(), "b1", "1" );
		processStartX(actions,AE.start2.ordinal(),GSE.b2.ordinal(), GSE.b1.ordinal(), GSE.b3.ordinal(), GSE.b4.ordinal(), "b2", "1" );
		processStartX(actions,AE.start3.ordinal(),GSE.b3.ordinal(), GSE.b2.ordinal(), GSE.b1.ordinal(), GSE.b4.ordinal(), "b3", "1" );
		processStartX(actions,AE.start4.ordinal(),GSE.b4.ordinal(), GSE.b2.ordinal(), GSE.b3.ordinal(), GSE.b1.ordinal(), "b4", "1" );
		
		
		
		//[start1] s1=0 & !(s2>0 | s3>0 | s4>0) -> (s1'=2);
		// ...
		
		processMachineStartX(actions,AE.start1.ordinal(),GSE.s1.ordinal(),GSE.s2.ordinal(),GSE.s3.ordinal(),GSE.s4.ordinal(),"s1","2");
		processMachineStartX(actions,AE.start2.ordinal(),GSE.s2.ordinal(),GSE.s1.ordinal(),GSE.s3.ordinal(),GSE.s4.ordinal(),"s2","2");
		processMachineStartX(actions,AE.start3.ordinal(),GSE.s3.ordinal(),GSE.s2.ordinal(),GSE.s1.ordinal(),GSE.s4.ordinal(),"s3","2");
		processMachineStartX(actions,AE.start4.ordinal(),GSE.s4.ordinal(),GSE.s2.ordinal(),GSE.s3.ordinal(),GSE.s1.ordinal(),"s4","2");
		
		//[dont_send_view_0_1] s1=2 & size_of_view1=0 -> (s1'=0);
		processDontSend(actions,AE.dont_send_view_0_1.ordinal(), GSE.s1.ordinal(),GSE.v1_1_a.ordinal(), GSE.v1_2_a.ordinal(), "s1","0");
		processDontSend(actions,AE.dont_send_view_0_2.ordinal(), GSE.s2.ordinal(),GSE.v2_1_a.ordinal(), GSE.v2_2_a.ordinal(), "s2","0");
		processDontSend(actions,AE.dont_send_view_0_3.ordinal(), GSE.s3.ordinal(),GSE.v3_1_a.ordinal(), GSE.v3_2_a.ordinal(), "s3","0");
		processDontSend(actions,AE.dont_send_view_0_4.ordinal(), GSE.s4.ordinal(),GSE.v4_1_a.ordinal(), GSE.v4_2_a.ordinal(), "s4","0");
		

		
		//[send_the_first_view_1] s1=2 & size_of_view1=1 -> (s1'=3) & (send1'=v1_1_a);
		processDecideSendView1(actions,AE.send_the_first_view_1.ordinal(), GSE.s1.ordinal(),GSE.v1_1_a.ordinal(), GSE.v1_2_a.ordinal(),"s1;send1", "3;" + returnStateValueById(GSE.v1_1_a.ordinal()) );
		processDecideSendView1(actions,AE.send_the_first_view_2.ordinal(), GSE.s2.ordinal(),GSE.v2_1_a.ordinal(), GSE.v2_2_a.ordinal(),"s2;send2", "3;" + returnStateValueById(GSE.v2_1_a.ordinal()) );
		processDecideSendView1(actions,AE.send_the_first_view_3.ordinal(), GSE.s3.ordinal(),GSE.v3_1_a.ordinal(), GSE.v3_2_a.ordinal(),"s3;send3", "3;" + returnStateValueById(GSE.v3_1_a.ordinal()) );
		processDecideSendView1(actions,AE.send_the_first_view_4.ordinal(), GSE.s4.ordinal(),GSE.v4_1_a.ordinal(), GSE.v4_2_a.ordinal(),"s4;send4", "3;" + returnStateValueById(GSE.v4_1_a.ordinal()) );
		
		
		//[send_1_or_2_view_1] s1=2 & size_of_view1=2 -> 0.5 : (s1'=3) & (send1'=v1_1_a) + 0.5 : (s1'=3) & (send1'=v1_2_a);
		processDecideSendView1OrView2(actions,AE.send_1_or_2_view_1.ordinal(), GSE.s1.ordinal(),GSE.v1_1_a.ordinal(), GSE.v1_2_a.ordinal(),"{\"transitions\":[{\"0.5\":\"s1=3;send1="+returnStateValueById(GSE.v1_1_a.ordinal())+"\"},{\"0.5\":\"s1=3;send1="+returnStateValueById(GSE.v1_2_a.ordinal())+"\"}]}" );
		processDecideSendView1OrView2(actions,AE.send_1_or_2_view_2.ordinal(), GSE.s2.ordinal(),GSE.v2_1_a.ordinal(), GSE.v2_2_a.ordinal(),"{\"transitions\":[{\"0.5\":\"s2=3;send2="+returnStateValueById(GSE.v2_1_a.ordinal())+"\"},{\"0.5\":\"s2=3;send2="+returnStateValueById(GSE.v2_2_a.ordinal())+"\"}]}" );
		processDecideSendView1OrView2(actions,AE.send_1_or_2_view_3.ordinal(), GSE.s3.ordinal(),GSE.v3_1_a.ordinal(), GSE.v3_2_a.ordinal(),"{\"transitions\":[{\"0.5\":\"s3=3;send3="+returnStateValueById(GSE.v3_1_a.ordinal())+"\"},{\"0.5\":\"s3=3;send3="+returnStateValueById(GSE.v3_2_a.ordinal())+"\"}]}" );
		processDecideSendView1OrView2(actions,AE.send_1_or_2_view_4.ordinal(), GSE.s4.ordinal(),GSE.v4_1_a.ordinal(), GSE.v4_2_a.ordinal(),"{\"transitions\":[{\"0.5\":\"s4=3;send4="+returnStateValueById(GSE.v4_1_a.ordinal())+"\"},{\"0.5\":\"s4=3;send4="+returnStateValueById(GSE.v4_2_a.ordinal())+"\"}]}" );
		
		//(Action[] act, int i, int s, int s2, int send, int id, int ii, String vn, String uv) {
		//[push1_2_0] s1=3 & send1=id2 & i1=0 -> (i1'=i1+1);
		//[push1_2_0] s2=0 -> (s2'=1) & (a2'=id1) & (h2'=0);
		processPushX_Y_0(actions,AE.push1_2_0.ordinal(),GSE.s1.ordinal(), GSE.s2.ordinal(), GSE.send1.ordinal(), 2, GSE.i1.ordinal(), "i1;s2;a2;h2", (returnIntValueById(GSE.i1.ordinal())+1) + ";1;1;0");
		
		//[push1_3_0] s1=3 & send1=id3 & i1=0 -> (i1'=i1+1);
		//[push1_3_0] s3=0 -> (s3'=1) & (a3'=id1) & (h3'=0);
		processPushX_Y_0(actions,AE.push1_3_0.ordinal(),GSE.s1.ordinal(), GSE.s3.ordinal(), GSE.send1.ordinal(), 3, GSE.i1.ordinal(), "i1;s3;a3;h3", (returnIntValueById(GSE.i1.ordinal())+1) + ";1;1;0");
		
		//[push1_4_0] s1=3 & send1=id4 & i1=0 -> (i1'=i1+1);
		//[push1_4_0] s4=0 -> (s4'=1) & (a4'=id1) & (h4'=0);
		processPushX_Y_0(actions,AE.push1_4_0.ordinal(),GSE.s1.ordinal(), GSE.s4.ordinal(), GSE.send1.ordinal(), 4, GSE.i1.ordinal(), "i1;s4;a4;h4", (returnIntValueById(GSE.i1.ordinal())+1) + ";1;1;0");
		

		processPushX_Y_0(actions,AE.push2_1_0.ordinal(),GSE.s2.ordinal(), GSE.s1.ordinal(), GSE.send2.ordinal(), 1, GSE.i2.ordinal(), "i2;s1;a1;h1", (returnIntValueById(GSE.i2.ordinal())+1) + ";1;2;0");
		processPushX_Y_0(actions,AE.push2_3_0.ordinal(),GSE.s2.ordinal(), GSE.s3.ordinal(), GSE.send2.ordinal(), 3, GSE.i2.ordinal(), "i2;s3;a3;h3", (returnIntValueById(GSE.i2.ordinal())+1) + ";1;2;0");
		processPushX_Y_0(actions,AE.push2_4_0.ordinal(),GSE.s2.ordinal(), GSE.s4.ordinal(), GSE.send2.ordinal(), 4, GSE.i2.ordinal(), "i2;s4;a4;h4", (returnIntValueById(GSE.i2.ordinal())+1) + ";1;2;0");
		
		processPushX_Y_0(actions,AE.push3_1_0.ordinal(),GSE.s3.ordinal(), GSE.s1.ordinal(), GSE.send3.ordinal(), 1, GSE.i3.ordinal(), "i3;s1;a1;h1", (returnIntValueById(GSE.i3.ordinal())+1) + ";1;3;0");
		processPushX_Y_0(actions,AE.push3_2_0.ordinal(),GSE.s3.ordinal(), GSE.s2.ordinal(), GSE.send3.ordinal(), 2, GSE.i3.ordinal(), "i3;s2;a2;h2", (returnIntValueById(GSE.i3.ordinal())+1) + ";1;3;0");
		processPushX_Y_0(actions,AE.push3_4_0.ordinal(),GSE.s3.ordinal(), GSE.s4.ordinal(), GSE.send3.ordinal(), 4, GSE.i3.ordinal(), "i3;s4;a4;h4", (returnIntValueById(GSE.i3.ordinal())+1) + ";1;3;0");
		
		processPushX_Y_0(actions,AE.push4_1_0.ordinal(),GSE.s4.ordinal(), GSE.s1.ordinal(), GSE.send4.ordinal(), 1, GSE.i4.ordinal(), "i4;s1;a1;h1", (returnIntValueById(GSE.i4.ordinal())+1) + ";1;4;0");
		processPushX_Y_0(actions,AE.push4_2_0.ordinal(),GSE.s4.ordinal(), GSE.s2.ordinal(), GSE.send4.ordinal(), 2, GSE.i4.ordinal(), "i4;s2;a2;h2", (returnIntValueById(GSE.i4.ordinal())+1) + ";1;4;0");
		processPushX_Y_0(actions,AE.push4_3_0.ordinal(),GSE.s4.ordinal(), GSE.s3.ordinal(), GSE.send4.ordinal(), 3, GSE.i4.ordinal(), "i4;s3;a3;h3", (returnIntValueById(GSE.i4.ordinal())+1) + ";1;4;0");
		
		
		//processPushX_Y_1(Action[] act, int i, int s,
		// int s2, int send, int id, int ii, int v1_1_h, String vn, String uv)
		//[push1_2_1] s1=3 & send1=id2 & i1=1 & v1_1_h<4 -> (s1'=0) & (i1'=0) & (send1'=0);
		//[push1_2_1] s2=0 -> (s2'=1) & (a2'=v1_1_a) & (h2'=v1_1_h);
		processPushX_Y_1(actions,AE.push1_2_1.ordinal(),GSE.s1.ordinal(), GSE.s2.ordinal(), GSE.send1.ordinal(), 2, GSE.i1.ordinal(),GSE.v1_1_h.ordinal(), "s1;i1;send1;s2;a2;h2", "0;0;0;1;"+(returnIntValueById(GSE.v1_1_a.ordinal())) +";" + (returnIntValueById(GSE.v1_1_h.ordinal())));
		processPushX_Y_1(actions,AE.push1_3_1.ordinal(),GSE.s1.ordinal(), GSE.s3.ordinal(), GSE.send1.ordinal(), 3, GSE.i1.ordinal(),GSE.v1_1_h.ordinal(), "s1;i1;send1;s3;a3;h3", "0;0;0;1;"+(returnIntValueById(GSE.v1_1_a.ordinal())) +";" + (returnIntValueById(GSE.v1_1_h.ordinal())));
		processPushX_Y_1(actions,AE.push1_4_1.ordinal(),GSE.s1.ordinal(), GSE.s4.ordinal(), GSE.send1.ordinal(), 4, GSE.i1.ordinal(),GSE.v1_1_h.ordinal(), "s1;i1;send1;s4;a4;h4", "0;0;0;1;"+(returnIntValueById(GSE.v1_1_a.ordinal())) +";" + (returnIntValueById(GSE.v1_1_h.ordinal())));
		

		processPushX_Y_1(actions,AE.push2_1_1.ordinal(),GSE.s2.ordinal(), GSE.s1.ordinal(), GSE.send2.ordinal(), 1, GSE.i2.ordinal(),GSE.v2_1_h.ordinal(), "s2;i2;send2;s1;a1;h1", "0;0;0;1;"+(returnIntValueById(GSE.v2_1_a.ordinal())) +";" + (returnIntValueById(GSE.v2_1_h.ordinal())));
		processPushX_Y_1(actions,AE.push2_3_1.ordinal(),GSE.s2.ordinal(), GSE.s3.ordinal(), GSE.send2.ordinal(), 3, GSE.i2.ordinal(),GSE.v2_1_h.ordinal(), "s2;i2;send2;s3;a3;h3", "0;0;0;1;"+(returnIntValueById(GSE.v2_1_a.ordinal())) +";" + (returnIntValueById(GSE.v2_1_h.ordinal())));
		processPushX_Y_1(actions,AE.push2_4_1.ordinal(),GSE.s2.ordinal(), GSE.s4.ordinal(), GSE.send2.ordinal(), 4, GSE.i2.ordinal(),GSE.v2_1_h.ordinal(), "s2;i2;send2;s4;a4;h4", "0;0;0;1;"+(returnIntValueById(GSE.v2_1_a.ordinal())) +";" + (returnIntValueById(GSE.v2_1_h.ordinal())));
		
		processPushX_Y_1(actions,AE.push3_1_1.ordinal(),GSE.s3.ordinal(), GSE.s1.ordinal(), GSE.send3.ordinal(), 1, GSE.i3.ordinal(),GSE.v3_1_h.ordinal(), "s3;i3;send3;s1;a1;h1", "0;0;0;1;"+(returnIntValueById(GSE.v3_1_a.ordinal())) +";" + (returnIntValueById(GSE.v3_1_h.ordinal())));
		processPushX_Y_1(actions,AE.push3_2_1.ordinal(),GSE.s3.ordinal(), GSE.s2.ordinal(), GSE.send3.ordinal(), 2, GSE.i3.ordinal(),GSE.v3_1_h.ordinal(), "s3;i3;send3;s2;a2;h2", "0;0;0;1;"+(returnIntValueById(GSE.v3_1_a.ordinal())) +";" + (returnIntValueById(GSE.v3_1_h.ordinal())));
		processPushX_Y_1(actions,AE.push3_4_1.ordinal(),GSE.s3.ordinal(), GSE.s4.ordinal(), GSE.send3.ordinal(), 4, GSE.i3.ordinal(),GSE.v3_1_h.ordinal(), "s3;i3;send3;s4;a4;h4", "0;0;0;1;"+(returnIntValueById(GSE.v3_1_a.ordinal())) +";" + (returnIntValueById(GSE.v3_1_h.ordinal())));
		
		processPushX_Y_1(actions,AE.push4_1_1.ordinal(),GSE.s4.ordinal(), GSE.s1.ordinal(), GSE.send4.ordinal(), 1, GSE.i4.ordinal(),GSE.v4_1_h.ordinal(), "s4;i4;send4;s1;a1;h1", "0;0;0;1;"+(returnIntValueById(GSE.v4_1_a.ordinal())) +";" + (returnIntValueById(GSE.v4_1_h.ordinal())));
		processPushX_Y_1(actions,AE.push4_2_1.ordinal(),GSE.s4.ordinal(), GSE.s2.ordinal(), GSE.send4.ordinal(), 2, GSE.i4.ordinal(),GSE.v4_1_h.ordinal(), "s4;i4;send4;s2;a2;h2", "0;0;0;1;"+(returnIntValueById(GSE.v4_1_a.ordinal())) +";" + (returnIntValueById(GSE.v4_1_h.ordinal())));
		processPushX_Y_1(actions,AE.push4_3_1.ordinal(),GSE.s4.ordinal(), GSE.s3.ordinal(), GSE.send4.ordinal(), 3, GSE.i4.ordinal(),GSE.v4_1_h.ordinal(), "s4;i4;send4;s3;a3;h3", "0;0;0;1;"+(returnIntValueById(GSE.v4_1_a.ordinal())) +";" + (returnIntValueById(GSE.v4_1_h.ordinal())));
		
		//[push1_2_end] s1=3 & send1=id2 & ((i1=1&v1_1_h=4) | (i1=2&v1_2_h=4)) -> (s1'=0) & (i1'=0) & (send1'=0); 
		//[push1_2_end] s2=0 -> (s2'=0) & (a2'=0) & (h2'=0);;
		processPushX_Y_end(actions,AE.push1_2_end.ordinal(),GSE.s1.ordinal(), GSE.s2.ordinal(), GSE.send1.ordinal(), 2, GSE.i1.ordinal(),GSE.v1_1_h.ordinal(),GSE.v1_2_h.ordinal(), "s1;i1;send1;s2;a2;h2", "0;0;0;0;0;0");
		processPushX_Y_end(actions,AE.push1_3_end.ordinal(),GSE.s1.ordinal(), GSE.s3.ordinal(), GSE.send1.ordinal(), 3, GSE.i1.ordinal(),GSE.v1_1_h.ordinal(),GSE.v1_2_h.ordinal(), "s1;i1;send1;s3;a3;h3", "0;0;0;0;0;0");
		processPushX_Y_end(actions,AE.push1_4_end.ordinal(),GSE.s1.ordinal(), GSE.s4.ordinal(), GSE.send1.ordinal(), 4, GSE.i1.ordinal(),GSE.v1_1_h.ordinal(),GSE.v1_2_h.ordinal(), "s1;i1;send1;s4;a4;h4", "0;0;0;0;0;0");
		
		processPushX_Y_end(actions,AE.push2_1_end.ordinal(),GSE.s2.ordinal(), GSE.s1.ordinal(), GSE.send2.ordinal(), 1, GSE.i2.ordinal(),GSE.v2_1_h.ordinal(),GSE.v2_2_h.ordinal(), "s2;i2;send2;s1;a1;h1", "0;0;0;0;0;0");
		processPushX_Y_end(actions,AE.push2_3_end.ordinal(),GSE.s2.ordinal(), GSE.s3.ordinal(), GSE.send2.ordinal(), 3, GSE.i2.ordinal(),GSE.v2_1_h.ordinal(),GSE.v2_2_h.ordinal(), "s2;i2;send2;s3;a3;h3", "0;0;0;0;0;0");
		processPushX_Y_end(actions,AE.push2_4_end.ordinal(),GSE.s2.ordinal(), GSE.s4.ordinal(), GSE.send2.ordinal(), 4, GSE.i2.ordinal(),GSE.v2_1_h.ordinal(),GSE.v2_2_h.ordinal(), "s2;i2;send2;s4;a4;h4", "0;0;0;0;0;0");
		
		processPushX_Y_end(actions,AE.push3_1_end.ordinal(),GSE.s3.ordinal(), GSE.s1.ordinal(), GSE.send3.ordinal(), 1, GSE.i3.ordinal(),GSE.v3_1_h.ordinal(),GSE.v3_2_h.ordinal(), "s3;i3;send1;s1;a1;h1", "0;0;0;0;0;0");
		processPushX_Y_end(actions,AE.push3_2_end.ordinal(),GSE.s3.ordinal(), GSE.s2.ordinal(), GSE.send3.ordinal(), 2, GSE.i3.ordinal(),GSE.v3_1_h.ordinal(),GSE.v3_2_h.ordinal(), "s3;i3;send1;s2;a2;h2", "0;0;0;0;0;0");
		processPushX_Y_end(actions,AE.push3_4_end.ordinal(),GSE.s3.ordinal(), GSE.s4.ordinal(), GSE.send3.ordinal(), 4, GSE.i3.ordinal(),GSE.v3_1_h.ordinal(),GSE.v3_2_h.ordinal(), "s3;i3;send1;s4;a4;h4", "0;0;0;0;0;0");
		
		processPushX_Y_end(actions,AE.push4_1_end.ordinal(),GSE.s4.ordinal(), GSE.s1.ordinal(), GSE.send4.ordinal(), 1, GSE.i4.ordinal(),GSE.v4_1_h.ordinal(),GSE.v4_2_h.ordinal(), "s4;i4;send1;s1;a1;h1", "0;0;0;0;0;0");
		processPushX_Y_end(actions,AE.push4_2_end.ordinal(),GSE.s4.ordinal(), GSE.s2.ordinal(), GSE.send4.ordinal(), 2, GSE.i4.ordinal(),GSE.v4_1_h.ordinal(),GSE.v4_2_h.ordinal(), "s4;i4;send1;s2;a2;h2", "0;0;0;0;0;0");
		processPushX_Y_end(actions,AE.push4_3_end.ordinal(),GSE.s4.ordinal(), GSE.s3.ordinal(), GSE.send4.ordinal(), 3, GSE.i4.ordinal(),GSE.v4_1_h.ordinal(),GSE.v4_2_h.ordinal(), "s4;i4;send1;s3;a3;h3", "0;0;0;0;0;0");
		
		// Received hop count is for this process so discard (will always be > 0)
		//[discard_received_hop_for_this_address1] s1=1 & a1=id1 -> (s1'=0) & (a1'=0) & (h1'=0);
		processDiscardHopForSelfAddress(actions,AE.discard_received_hop_for_this_address1.ordinal(),GSE.s1.ordinal(), GSE.a1.ordinal(), 1,"s1;a1;h1","0;0;0");
		processDiscardHopForSelfAddress(actions,AE.discard_received_hop_for_this_address2.ordinal(),GSE.s2.ordinal(), GSE.a2.ordinal(), 2,"s2;a2;h2","0;0;0");
		processDiscardHopForSelfAddress(actions,AE.discard_received_hop_for_this_address3.ordinal(),GSE.s3.ordinal(), GSE.a3.ordinal(), 3,"s3;a3;h3","0;0;0");
		processDiscardHopForSelfAddress(actions,AE.discard_received_hop_for_this_address4.ordinal(),GSE.s4.ordinal(), GSE.a4.ordinal(), 4,"s4;a4;h4","0;0;0");
		
		// Received hop count is for this process so discard (will always be > 0)
		//[received_hop_count_for_address_which_we_have_already_lower_hop_count_1] s1=1 & !(a1=id1) & already_have_lower1 -> (s1'=0) & (a1'=0) & (h1'=0);
		//private void processHopVSLowerHopCount(Action[] act, int i, int s, int a, int id, int h, int v1_1_a, int v1_1_h,int v1_2_a, int v1_2_h,String vn, String uv) {
		processHopVSLowerHopCount(actions,AE.received_hop_count_for_address_which_we_have_already_lower_hop_count_1.ordinal(),GSE.s1.ordinal(), GSE.a1.ordinal(), 1,GSE.h1.ordinal(),GSE.v1_1_a.ordinal(),GSE.v1_1_h.ordinal(),GSE.v1_2_a.ordinal(), GSE.v1_2_h.ordinal(),"s1;a1;h1","0;0;0");
		processHopVSLowerHopCount(actions,AE.received_hop_count_for_address_which_we_have_already_lower_hop_count_2.ordinal(),GSE.s2.ordinal(), GSE.a2.ordinal(), 2,GSE.h2.ordinal(),GSE.v2_1_a.ordinal(),GSE.v2_1_h.ordinal(),GSE.v2_2_a.ordinal(), GSE.v2_2_h.ordinal(),"s2;a2;h2","0;0;0");
		processHopVSLowerHopCount(actions,AE.received_hop_count_for_address_which_we_have_already_lower_hop_count_3.ordinal(),GSE.s3.ordinal(), GSE.a3.ordinal(), 3,GSE.h3.ordinal(),GSE.v3_1_a.ordinal(),GSE.v3_1_h.ordinal(),GSE.v3_2_a.ordinal(), GSE.v3_2_h.ordinal(),"s3;a3;h3","0;0;0");
		processHopVSLowerHopCount(actions,AE.received_hop_count_for_address_which_we_have_already_lower_hop_count_4.ordinal(),GSE.s4.ordinal(), GSE.a4.ordinal(), 4,GSE.h4.ordinal(),GSE.v4_1_a.ordinal(),GSE.v4_1_h.ordinal(),GSE.v4_2_a.ordinal(), GSE.v4_2_h.ordinal(),"s4;a4;h4","0;0;0");
		
		// Received hop count for address for which we already have a value but new one is lower
		// (need to store new pair in position "where_to_put1" and shift some pairs to the right)
		// (i.e.: pairs at position i for which i>=where_to_put1 and i<position of old value)
		// Old value stored in position 1
		//[received_hop_count_lower_than_v_1_1_h_store_the_newest_hop_1] s1=1 & !(a1=id1) & a1=v1_1_a & h1+1<v1_1_h -> (s1'=0) & (v1_1_h'=h1+1) & (a1'=0) & (h1'=0);
		processRefreshHopCount(actions,AE.received_hop_count_lower_than_v_1_1_h_store_the_newest_hop_1.ordinal(),GSE.s1.ordinal(), GSE.a1.ordinal(), 1,GSE.h1.ordinal(),GSE.v1_1_a.ordinal(),GSE.v1_1_h.ordinal(), "s1;a1;h1;v1_1_h","0;0;0;"+(returnIntValueById(GSE.h1.ordinal())+1));
		processRefreshHopCount(actions,AE.received_hop_count_lower_than_v_1_1_h_store_the_newest_hop_2.ordinal(),GSE.s2.ordinal(), GSE.a2.ordinal(), 2,GSE.h2.ordinal(),GSE.v2_1_a.ordinal(),GSE.v2_1_h.ordinal(), "s2;a2;h2;v2_1_h","0;0;0;"+(returnIntValueById(GSE.h2.ordinal())+1));
		processRefreshHopCount(actions,AE.received_hop_count_lower_than_v_1_1_h_store_the_newest_hop_3.ordinal(),GSE.s3.ordinal(), GSE.a3.ordinal(), 3,GSE.h3.ordinal(),GSE.v3_1_a.ordinal(),GSE.v3_1_h.ordinal(), "s3;a3;h3;v3_1_h","0;0;0;"+(returnIntValueById(GSE.h3.ordinal())+1));
		processRefreshHopCount(actions,AE.received_hop_count_lower_than_v_1_1_h_store_the_newest_hop_4.ordinal(),GSE.s4.ordinal(), GSE.a4.ordinal(), 4,GSE.h4.ordinal(),GSE.v4_1_a.ordinal(),GSE.v4_1_h.ordinal(), "s4;a4;h4;v4_1_h","0;0;0;"+(returnIntValueById(GSE.h4.ordinal())+1));
		
		//[old_value_with_new_hop_store_to_position_2_for_hop_less_than_v1_2h_1] s1=1 & !(a1=id1) & !(a1=v1_1_a) & a1=v1_2_a & h1+1<v1_2_h & where_to_put1=2 -> (s1'=0) & (v1_2_a'=a1) & (v1_2_h'=h1+1) & (a1'=0) & (h1'=0);
		processRefreshAddress2Position(actions,AE.old_value_with_new_hop_store_to_position_2_for_hop_less_than_v1_2h_1.ordinal(),GSE.s1.ordinal(), GSE.a1.ordinal(), 1,GSE.h1.ordinal(),GSE.v1_1_a.ordinal(),GSE.v1_1_h.ordinal(),GSE.v1_2_a.ordinal(), GSE.v1_2_h.ordinal(), "s1;a1;h1;v1_2_a;v1_2_h","0;0;0;"+(returnIntValueById(GSE.a1.ordinal()))+ ";" +(returnIntValueById(GSE.h1.ordinal())+1));
		processRefreshAddress2Position(actions,AE.old_value_with_new_hop_store_to_position_2_for_hop_less_than_v1_2h_2.ordinal(),GSE.s2.ordinal(), GSE.a2.ordinal(), 2,GSE.h2.ordinal(),GSE.v2_1_a.ordinal(),GSE.v2_1_h.ordinal(),GSE.v2_2_a.ordinal(), GSE.v2_2_h.ordinal(), "s2;a2;h2;v2_2_a;v2_2_h","0;0;0;"+(returnIntValueById(GSE.a2.ordinal()))+ ";" +(returnIntValueById(GSE.h2.ordinal())+1));
		processRefreshAddress2Position(actions,AE.old_value_with_new_hop_store_to_position_2_for_hop_less_than_v1_2h_3.ordinal(),GSE.s3.ordinal(), GSE.a3.ordinal(), 3,GSE.h3.ordinal(),GSE.v3_1_a.ordinal(),GSE.v3_1_h.ordinal(),GSE.v3_2_a.ordinal(), GSE.v3_2_h.ordinal(), "s3;a3;h3;v3_2_a;v3_2_h","0;0;0;"+(returnIntValueById(GSE.a3.ordinal()))+ ";" +(returnIntValueById(GSE.h3.ordinal())+1));
		processRefreshAddress2Position(actions,AE.old_value_with_new_hop_store_to_position_2_for_hop_less_than_v1_2h_4.ordinal(),GSE.s4.ordinal(), GSE.a4.ordinal(), 4,GSE.h4.ordinal(),GSE.v4_1_a.ordinal(),GSE.v4_1_h.ordinal(),GSE.v4_2_a.ordinal(), GSE.v4_2_h.ordinal(), "s4;a4;h4;v4_2_a;v4_2_h","0;0;0;"+(returnIntValueById(GSE.a4.ordinal()))+ ";" +(returnIntValueById(GSE.h4.ordinal())+1));
		
		//	[old_value_with_new_hop_store_to_position_1_for_hop_less_than_v1_1h_1] s1=1 & !(a1=id1) & !(a1=v1_1_a) & a1=v1_2_a & h1+1<v1_2_h & where_to_put1=1 ->                                                                                                               (s1'=0) & (v1_1_a'=a1) & (v1_1_h'=h1+1) & (v1_2_a'=v1_1_a) & (v1_2_h'=v1_1_h) & (a1'=0) & (h1'=0);
		processRefreshAddress1Position(actions,AE.old_value_with_new_hop_store_to_position_1_for_hop_less_than_v1_1h_1.ordinal(),GSE.s1.ordinal(), GSE.a1.ordinal(), 1,GSE.h1.ordinal(),GSE.v1_1_a.ordinal(),GSE.v1_1_h.ordinal(),GSE.v1_2_a.ordinal(), GSE.v1_2_h.ordinal(), "s1;v1_1_a;v1_1_h;v1_2_a;v1_2_h;a1;h1","0;"+(returnIntValueById(GSE.a1.ordinal()))+ ";" +(returnIntValueById(GSE.h1.ordinal())+1)+ ";"+(returnIntValueById(GSE.v1_1_a.ordinal()))+";"+(returnIntValueById(GSE.v1_1_h.ordinal())) + ";0;0");
		processRefreshAddress1Position(actions,AE.old_value_with_new_hop_store_to_position_1_for_hop_less_than_v1_1h_2.ordinal(),GSE.s2.ordinal(), GSE.a2.ordinal(), 2,GSE.h2.ordinal(),GSE.v2_1_a.ordinal(),GSE.v2_1_h.ordinal(),GSE.v2_2_a.ordinal(), GSE.v2_2_h.ordinal(), "s2;v2_1_a;v2_1_h;v2_2_a;v2_2_h;a2;h2","0;"+(returnIntValueById(GSE.a2.ordinal()))+ ";" +(returnIntValueById(GSE.h2.ordinal())+1)+ ";"+(returnIntValueById(GSE.v2_1_a.ordinal()))+";"+(returnIntValueById(GSE.v2_1_h.ordinal())) + ";0;0");
		processRefreshAddress1Position(actions,AE.old_value_with_new_hop_store_to_position_1_for_hop_less_than_v1_1h_3.ordinal(),GSE.s3.ordinal(), GSE.a3.ordinal(), 3,GSE.h3.ordinal(),GSE.v3_1_a.ordinal(),GSE.v3_1_h.ordinal(),GSE.v3_2_a.ordinal(), GSE.v3_2_h.ordinal(), "s3;v3_1_a;v3_1_h;v3_2_a;v3_2_h;a3;h3","0;"+(returnIntValueById(GSE.a3.ordinal()))+ ";" +(returnIntValueById(GSE.h3.ordinal())+1)+ ";"+(returnIntValueById(GSE.v3_1_a.ordinal()))+";"+(returnIntValueById(GSE.v3_1_h.ordinal())) + ";0;0");
		processRefreshAddress1Position(actions,AE.old_value_with_new_hop_store_to_position_1_for_hop_less_than_v1_1h_4.ordinal(),GSE.s4.ordinal(), GSE.a4.ordinal(), 4,GSE.h4.ordinal(),GSE.v4_1_a.ordinal(),GSE.v4_1_h.ordinal(),GSE.v4_2_a.ordinal(), GSE.v4_2_h.ordinal(), "s4;v4_1_a;v4_1_h;v4_2_a;v4_2_h;a4;h4","0;"+(returnIntValueById(GSE.a4.ordinal()))+ ";" +(returnIntValueById(GSE.h4.ordinal())+1)+ ";"+(returnIntValueById(GSE.v4_1_a.ordinal()))+";"+(returnIntValueById(GSE.v4_1_h.ordinal())) + ";0;0");
		
		// Received hop count for address for which we have no existing value
		// (need to shift existing pair one to the right)
		//[discard_new_address_hop_too_big_1] s1=1 & !(a1=id1 | already_have_addr1) & where_to_put1=3 -> (s1'=0) & (a1'=0) & (h1'=0);
		processDiscardNewAddressHopTooBig(actions,AE.discard_new_address_hop_too_big_1.ordinal(),GSE.s1.ordinal(), GSE.a1.ordinal(), 1,GSE.h1.ordinal(),GSE.v1_1_a.ordinal(),GSE.v1_1_h.ordinal(),GSE.v1_2_a.ordinal(), GSE.v1_2_h.ordinal(), "s1;a1;h1","0;0;0");
		processDiscardNewAddressHopTooBig(actions,AE.discard_new_address_hop_too_big_2.ordinal(),GSE.s2.ordinal(), GSE.a2.ordinal(), 2,GSE.h2.ordinal(),GSE.v2_1_a.ordinal(),GSE.v2_1_h.ordinal(),GSE.v2_2_a.ordinal(), GSE.v2_2_h.ordinal(), "s2;a2;h2","0;0;0");
		processDiscardNewAddressHopTooBig(actions,AE.discard_new_address_hop_too_big_3.ordinal(),GSE.s3.ordinal(), GSE.a3.ordinal(), 3,GSE.h3.ordinal(),GSE.v3_1_a.ordinal(),GSE.v3_1_h.ordinal(),GSE.v3_2_a.ordinal(), GSE.v3_2_h.ordinal(), "s3;a3;h3","0;0;0");
		processDiscardNewAddressHopTooBig(actions,AE.discard_new_address_hop_too_big_4.ordinal(),GSE.s4.ordinal(), GSE.a4.ordinal(), 4,GSE.h4.ordinal(),GSE.v4_1_a.ordinal(),GSE.v4_1_h.ordinal(),GSE.v4_2_a.ordinal(), GSE.v4_2_h.ordinal(), "s4;a4;h4","0;0;0");
		
		
		//[new_entry_store_to_2_position_1] s1=1 & !(a1=id1 | already_have_addr1) & where_to_put1=2 -> (s1'=0) & (v1_2_a'=a1) & (v1_2_h'=h1+1) & (a1'=0) & (h1'=0);
		processAddEntryToSecondPosition(actions,AE.new_entry_store_to_2_position_1.ordinal(),GSE.s1.ordinal(), GSE.a1.ordinal(), 1,GSE.h1.ordinal(),GSE.v1_1_a.ordinal(),GSE.v1_1_h.ordinal(),GSE.v1_2_a.ordinal(), GSE.v1_2_h.ordinal(), "s1;a1;h1;v1_2_a;v1_2_h;","0;0;0;"+(returnIntValueById(GSE.a1.ordinal()))+ ";" +(returnIntValueById(GSE.h1.ordinal())+1));
		processAddEntryToSecondPosition(actions,AE.new_entry_store_to_2_position_2.ordinal(),GSE.s2.ordinal(), GSE.a2.ordinal(), 2,GSE.h2.ordinal(),GSE.v2_1_a.ordinal(),GSE.v2_1_h.ordinal(),GSE.v2_2_a.ordinal(), GSE.v2_2_h.ordinal(), "s2;a2;h2;v2_2_a;v2_2_h;","0;0;0;"+(returnIntValueById(GSE.a2.ordinal()))+ ";" +(returnIntValueById(GSE.h2.ordinal())+1));
		processAddEntryToSecondPosition(actions,AE.new_entry_store_to_2_position_3.ordinal(),GSE.s3.ordinal(), GSE.a3.ordinal(), 3,GSE.h3.ordinal(),GSE.v3_1_a.ordinal(),GSE.v3_1_h.ordinal(),GSE.v3_2_a.ordinal(), GSE.v3_2_h.ordinal(), "s3;a3;h3;v3_2_a;v3_2_h;","0;0;0;"+(returnIntValueById(GSE.a3.ordinal()))+ ";" +(returnIntValueById(GSE.h3.ordinal())+1));
		processAddEntryToSecondPosition(actions,AE.new_entry_store_to_2_position_4.ordinal(),GSE.s4.ordinal(), GSE.a4.ordinal(), 4,GSE.h4.ordinal(),GSE.v4_1_a.ordinal(),GSE.v4_1_h.ordinal(),GSE.v4_2_a.ordinal(), GSE.v4_2_h.ordinal(), "s4;a4;h4;v4_2_a;v4_2_h;","0;0;0;"+(returnIntValueById(GSE.a4.ordinal()))+ ";" +(returnIntValueById(GSE.h4.ordinal())+1));
		
		//[new_entry_store_to_1_position_1] s1=1 & !(a1=id1 | already_have_addr1) & where_to_put1=1 -> (s1'=0) & (v1_1_a'=a1) & (v1_1_h'=h1+1) & (v1_2_a'=v1_1_a) & (v1_2_h'=v1_1_h) & (a1'=0) & (h1'=0);
		processAddEntryToFirstPosition(actions,AE.new_entry_store_to_1_position_1.ordinal(),GSE.s1.ordinal(), GSE.a1.ordinal(), 1,GSE.h1.ordinal(),GSE.v1_1_a.ordinal(),GSE.v1_1_h.ordinal(),GSE.v1_2_a.ordinal(), GSE.v1_2_h.ordinal(), "s1;a1;h1;v1_2_a;v1_2_h;v1_1_a;v1_1_h","0;0;0;"+(returnIntValueById(GSE.v1_1_a.ordinal()))+";"+(returnIntValueById(GSE.v1_1_h.ordinal()))+";"+(returnIntValueById(GSE.a1.ordinal()))+ ";" +(returnIntValueById(GSE.h1.ordinal())+1));
		processAddEntryToFirstPosition(actions,AE.new_entry_store_to_1_position_2.ordinal(),GSE.s2.ordinal(), GSE.a2.ordinal(), 2,GSE.h2.ordinal(),GSE.v2_1_a.ordinal(),GSE.v2_1_h.ordinal(),GSE.v2_2_a.ordinal(), GSE.v2_2_h.ordinal(), "s2;a2;h2;v2_2_a;v2_2_h;v2_1_a;v2_1_h","0;0;0;"+(returnIntValueById(GSE.v2_1_a.ordinal()))+";"+(returnIntValueById(GSE.v2_1_h.ordinal()))+";"+(returnIntValueById(GSE.a2.ordinal()))+ ";" +(returnIntValueById(GSE.h2.ordinal())+1));
		processAddEntryToFirstPosition(actions,AE.new_entry_store_to_1_position_3.ordinal(),GSE.s3.ordinal(), GSE.a3.ordinal(), 3,GSE.h3.ordinal(),GSE.v3_1_a.ordinal(),GSE.v3_1_h.ordinal(),GSE.v3_2_a.ordinal(), GSE.v3_2_h.ordinal(), "s3;a3;h3;v3_2_a;v3_2_h;v3_1_a;v3_1_h","0;0;0;"+(returnIntValueById(GSE.v3_1_a.ordinal()))+";"+(returnIntValueById(GSE.v3_1_h.ordinal()))+";"+(returnIntValueById(GSE.a3.ordinal()))+ ";" +(returnIntValueById(GSE.h3.ordinal())+1));
		processAddEntryToFirstPosition(actions,AE.new_entry_store_to_1_position_4.ordinal(),GSE.s4.ordinal(), GSE.a4.ordinal(), 4,GSE.h4.ordinal(),GSE.v4_1_a.ordinal(),GSE.v4_1_h.ordinal(),GSE.v4_2_a.ordinal(), GSE.v4_2_h.ordinal(), "s4;a4;h4;v4_2_a;v4_2_h;v4_1_a;v4_1_h","0;0;0;"+(returnIntValueById(GSE.v4_1_a.ordinal()))+";"+(returnIntValueById(GSE.v4_1_h.ordinal()))+";"+(returnIntValueById(GSE.a4.ordinal()))+ ";" +(returnIntValueById(GSE.h4.ordinal())+1));
		
		
		//filtered actions
		List<Action> f_actions = new ArrayList<Action>();
		for(int i = 0; i < AE.values().length; i++) {
			if(actions[i].isEnable()) {
				f_actions.add(actions[i]);
			}
		}
		
		
		return f_actions.toArray(new Action[f_actions.size()]);
	}

	// Methods for ModelInfo interface

	// The model is a Markov decision process (MDP)

	@Override
	public ModelType getModelType()
	{
		return ModelType.MDP;
	}


	@Override
	public List<String> getVarNames()
	{
		List<String> names = new ArrayList<String>();
		
		for(int i = 0; i < variables.length; i++) {
			if(variables[i] != null) {
				names.add(variables[i].getVarName());
			}
			
		}
		
		return names;
	}

	@Override
	public List<Type> getVarTypes()
	{
		List<Type> types = new ArrayList<Type>();
		
		for(int i = 0; i < variables.length; i++) {
			if(variables[i].getVarType() == "int") {
				types.add(TypeInt.getInstance());
			}else if(variables[i].getVarType() == "bool") {
				types.add(TypeBool.getInstance());
			}
			
		}
		
		return types;
	}

	@Override
	public DeclarationType getVarDeclarationType(int i) throws PrismException
	{
		Variable p = variables[i];
		
		if(p.getVarType() == "int") {
			return new DeclarationInt(Expression.Int(p.getLowerBound()), Expression.Int(p.getUpperBound())); 
		}else if(p.getVarType() == "bool") {
			return super.getVarDeclarationType(i);
		}else {
			return super.getVarDeclarationType(i);
		}
	}
	
	
	@Override
	public List<String> getLabelNames()
	{
		return Arrays.asList("done");
	}
	
	// Methods for ModelGenerator interface (rather than superclass ModelInfo)

	@Override
	public State getInitialState() throws PrismException
	{
		State s = new State(variables.length);
		for(GSE x : GSE.values()) {
			s.setValue(x.ordinal(), variables[x.ordinal()].getInitialValue());
		}

		return s;
	}

	@Override
	public void exploreState(State exploreState) throws PrismException
	{
		// Store the state (for reference, and because will clone/copy it later)
		this.exploreState = exploreState;
		
		if(this.debug == true) {
			System.out.println("exploreState: " + exploreState);
		}


		this.av_actions = calculateTransitions();

	}

	@Override
	public int getNumChoices() throws PrismException
	{
		if(this.debug == true) {
			System.out.println("getNumChoices: " + av_actions.length);
		}
		return av_actions.length;
	}

	@Override
	public int getNumTransitions(int i) throws PrismException
	{
		String json_update = av_actions[i].getJsonUpdate();
		if(json_update == "") {
			
			return 1;
		}else {
			JSONObject obj = new JSONObject(json_update);
			JSONArray arr = obj.getJSONArray("transitions");
			return arr.length();
		}
		
		
	}

	@Override
	public Object getTransitionAction(int i, int offset) throws PrismException
	{
		// Action labels are the same in every state
		if(this.debug == true) {
			System.out.println("Action i: " + i + " offset: " + offset + " action name: " + av_actions[i].getActionName());
		}
		return av_actions[i].getActionName();
	}

	@Override
	public Double getTransitionProbability(int i, int offset) throws PrismException
	{

		String json_update = av_actions[i].getJsonUpdate();
		if(json_update == "") {
			return 1.0;
		}else {
			JSONObject obj = new JSONObject(json_update);
			JSONArray arr = obj.getJSONArray("transitions");
			
							
			JSONObject jsonObject = arr.getJSONObject(offset);
		    Iterator<String> keys = jsonObject.keys();
		    if (keys.hasNext()) {
		        String key = keys.next();
		        String value = jsonObject.get(key).toString();
		        
		        return Double.parseDouble(key);
		    }else {
		    	if(this.debug == true) {
		    		System.out.println("Error getTransitionProbability: " + json_update);
		    	}
		    	return 0.0;
		    }
		}
	}
	

	@Override
	public State computeTransitionTarget(int i, int offset) throws PrismException
	{
		State target = new State(exploreState);
		if(this.debug == true) {
			System.out.println("computeTransitionTarget: " + exploreState + " i: " + i + " offset:" +offset);
		}
		// If we have already failed, there is just one transition (a self-loop)
		
		//json-like update with probability update
		String json_update = av_actions[i].getJsonUpdate();
		if(json_update == "") {
			String[] var_names = av_actions[i].getUpdateVarName().split(";");
			String[] update_var_names_values = av_actions[i].getUpdateValue().split(";");
			for(int j = 0; j < var_names.length; j++) {
				if(this.debug == true) {
					System.out.println("Enum value: " + var_names[j]);
				}
				this.variables[GSE.valueOf(var_names[j]).ordinal()].
					setCurrentValue(Integer.parseInt(update_var_names_values[j]));
				
				target.setValue(GSE.valueOf(var_names[j]).ordinal(), Integer.parseInt(update_var_names_values[j]));
				
				
			}
		}else {
			JSONObject obj = new JSONObject(json_update);
			JSONArray arr = obj.getJSONArray("transitions");
			
							
			JSONObject jsonObject = arr.getJSONObject(offset);
		    Iterator<String> keys = jsonObject.keys();
		    if (keys.hasNext()) {
		        String key = keys.next();
		        String value = jsonObject.get(key).toString();
		        
		        String[] update_var_value_pairs = value.split(";");
		        for(int k = 0; k < update_var_value_pairs.length; k++) {
		        	String[] var_name_and_value = update_var_value_pairs[k].split("=");
		        	
		        	this.variables[GSE.valueOf(var_name_and_value[0]).ordinal()].
					setCurrentValue(Integer.parseInt(var_name_and_value[1]));
				
		        	target.setValue(GSE.valueOf(var_name_and_value[0]).ordinal(), Integer.parseInt(var_name_and_value[1]));
				
		        }
		    }
			
		}
		
		

		return target;
	}
	
	private int getMaxHopCount() {
		int max = returnIntValueById(GSE.v1_1_h.ordinal());
		
		if(returnIntValueById(GSE.v1_2_h.ordinal()) > max) {
			max = returnIntValueById(GSE.v1_2_h.ordinal());
		}
		
		if(returnIntValueById(GSE.v2_1_h.ordinal()) > max) {
			max = returnIntValueById(GSE.v2_1_h.ordinal());
		}
		
		if(returnIntValueById(GSE.v2_2_h.ordinal()) > max) {
			max = returnIntValueById(GSE.v2_2_h.ordinal());
		}
		
		if(returnIntValueById(GSE.v3_1_h.ordinal()) > max) {
			max = returnIntValueById(GSE.v3_1_h.ordinal());
		}
		
		if(returnIntValueById(GSE.v3_2_h.ordinal()) > max) {
			max = returnIntValueById(GSE.v3_2_h.ordinal());
		}
		
		return max;
	}

	private int getPathLengthX( int vx_1_a, int vx_2_a, int vy_1_a, int vy_2_a,
								int vz_1_a, int vz_2_a, int vw_1_a, int vw_2_a,
								int target_address, int inter2, int inter3) {
		int result = -1;
		//formula path_len12 = n12?1:n13&n32|n14&n42?2:n13&n34&n42|n14&n43&n32?3:4;
		
		//n12 formula n12 = v1_1_a=2|v1_2_a=2;
		if(returnIntValueById(vx_1_a)==target_address || returnIntValueById(vx_2_a) == target_address) {
			result = 1;
		}else {
			//n13&n32
			//formula n13 = v1_1_a=3|v1_2_a=3;
			//formula n32 = v3_1_a=2|v3_2_a=2;
			if( ((returnIntValueById(vx_1_a)==inter2 || returnIntValueById(vx_2_a) == inter2) && (returnIntValueById(vz_1_a)==target_address || returnIntValueById(vz_2_a) == target_address) )
					||
				//|n14&n42
				//formula n14 = v1_1_a=4|v1_2_a=4;
				//formula n42 = v4_1_a=2|v4_2_a=2;
				((returnIntValueById(vx_1_a)==inter3 || returnIntValueById(vx_2_a) == inter3) && (returnIntValueById(vw_1_a)==target_address || returnIntValueById(vw_2_a) == target_address) )
				) {
				result = 2;
				
			}else {
				//n13&n34&n42
				//formula n13 = v1_1_a=3|v1_2_a=3;
				//formula n34 = v3_1_a=4|v3_2_a=4;
				//formula n42 = v4_1_a=2|v4_2_a=2;
				if( ((returnIntValueById(vx_1_a)==inter2 || returnIntValueById(vx_2_a) == inter2) && (returnIntValueById(vz_1_a)==inter3 || returnIntValueById(vz_2_a) == inter3) && (returnIntValueById(vw_1_a)==target_address || returnIntValueById(vw_2_a) == target_address) )
						||
					//|n14&n43&n32
					//formula n14 = v1_1_a=4|v1_2_a=4;
					//formula n43 = v4_1_a=3|v4_2_a=3;
					//formula n32 = v3_1_a=2|v3_2_a=2;
					((returnIntValueById(vx_1_a)==inter3 || returnIntValueById(vx_2_a) == inter3) && (returnIntValueById(vw_1_a)==inter2 || returnIntValueById(vw_2_a) == inter2) && (returnIntValueById(vz_1_a)==target_address || returnIntValueById(vz_2_a) == target_address))
					) {
					result = 3;
					
				}else {
					result = 4;
				}
			}
		}
		
		
		
		return result;
	}
	
	//formula max_path_len=max(max_path_len1,max_path_len2,max_path_len3,max_path_len4);
	private int getMaxPathLength() {
		//formula path_len12 = n12?1:n13&n32|n14&n42?2:n13&n34&n42|n14&n43&n32?3:4;
		int path_len12 = getPathLengthX(GSE.v1_1_a.ordinal(),GSE.v1_2_a.ordinal(), GSE.v2_1_a.ordinal(), GSE.v2_2_a.ordinal(),
										GSE.v3_1_a.ordinal(), GSE.v3_2_a.ordinal(), GSE.v4_1_a.ordinal(), GSE.v4_2_a.ordinal(),
										2,3,4);
		//formula path_len13 = n13?1:n12&n23|n14&n43?2:n12&n24&n43|n14&n42&n23?3:4; (pos) 3->2
		int path_len13 = getPathLengthX(GSE.v1_1_a.ordinal(),GSE.v1_2_a.ordinal(), GSE.v3_1_a.ordinal(), GSE.v3_2_a.ordinal(),
										GSE.v2_1_a.ordinal(), GSE.v2_2_a.ordinal(), GSE.v4_1_a.ordinal(), GSE.v4_2_a.ordinal(),
										3,2,4);
		//formula path_len14 = n14?1:n12&n24|n13&n34?2:n12&n23&n34|n13&n32&n24?3:4; (pos) 3->2, 4->3
		int path_len14 = getPathLengthX(GSE.v1_1_a.ordinal(),GSE.v1_2_a.ordinal(), GSE.v4_1_a.ordinal(), GSE.v4_2_a.ordinal(),
										GSE.v2_1_a.ordinal(), GSE.v2_2_a.ordinal(), GSE.v3_1_a.ordinal(), GSE.v3_2_a.ordinal(),
										4,2,3);
		int max_path_len1 = Math.max(path_len14, Math.max(path_len12, path_len13));
		
		
		//formula path_len21 = n21?1:n23&n31|n24&n41?2:n23&n34&n41|n24&n43&n31?3:4; 1->2, 
		int path_len21 = getPathLengthX(GSE.v2_1_a.ordinal(),GSE.v2_2_a.ordinal(), GSE.v1_1_a.ordinal(), GSE.v1_2_a.ordinal(),
				GSE.v3_1_a.ordinal(), GSE.v3_2_a.ordinal(), GSE.v4_1_a.ordinal(), GSE.v4_2_a.ordinal(),
				1,3,4);
		
		//formula path_len23 = n23?1:n21&n13|n24&n43?2:n21&n14&n43|n24&n41&n13?3:4; 1->2; 3->1
		int path_len23 = getPathLengthX(GSE.v2_1_a.ordinal(),GSE.v2_2_a.ordinal(), GSE.v3_1_a.ordinal(), GSE.v3_2_a.ordinal(),
							GSE.v1_1_a.ordinal(), GSE.v1_2_a.ordinal(), GSE.v4_1_a.ordinal(), GSE.v4_2_a.ordinal(),
							3,1,4);
		//formula path_len12 = n12?1:n13&n32|n14&n42?2:n13&n34&n42|n14&n43&n32?3:4;
		//formula path_len24 = n24?1:n21&n14|n23&n34?2:n21&n13&n34|n23&n31&n14?3:4; 1->2, 3->1; 4->3
		int path_len24 = getPathLengthX(GSE.v2_1_a.ordinal(),GSE.v2_2_a.ordinal(), GSE.v4_1_a.ordinal(), GSE.v4_2_a.ordinal(),
							GSE.v1_1_a.ordinal(), GSE.v1_2_a.ordinal(), GSE.v3_1_a.ordinal(), GSE.v3_2_a.ordinal(),
							4,1,3);
		int max_path_len2 = Math.max(path_len21, Math.max(path_len23, path_len24));
		
		
		
		//formula path_len31 = n31?1:n32&n21|n34&n41?2:n32&n24&n41|n34&n42&n21?3:4; 1->3, 3->2, 
		int path_len31 = getPathLengthX(GSE.v3_1_a.ordinal(),GSE.v3_2_a.ordinal(), GSE.v1_1_a.ordinal(), GSE.v1_2_a.ordinal(),
				GSE.v2_1_a.ordinal(), GSE.v2_2_a.ordinal(), GSE.v4_1_a.ordinal(), GSE.v4_2_a.ordinal(),
				1,2,4);
		
		
		//formula path_len32 = n32?1:n31&n12|n34&n42?2:n31&n14&n42|n34&n41&n12?3:4; 1->3, 3->1
		int path_len32 = getPathLengthX(GSE.v3_1_a.ordinal(),GSE.v3_2_a.ordinal(), GSE.v2_1_a.ordinal(), GSE.v2_2_a.ordinal(),
							GSE.v1_1_a.ordinal(), GSE.v1_2_a.ordinal(), GSE.v4_1_a.ordinal(), GSE.v4_2_a.ordinal(),
							2,1,4);
		
		
		//formula path_len34 = n34?1:n32&n24|n31&n14?2:n32&n21&n14|n31&n12&n24?3:4; 1->3, 3->2, 4->1
		int path_len34 = getPathLengthX(GSE.v3_1_a.ordinal(),GSE.v3_2_a.ordinal(), GSE.v4_1_a.ordinal(), GSE.v4_2_a.ordinal(),
							GSE.v2_1_a.ordinal(), GSE.v2_2_a.ordinal(), GSE.v1_1_a.ordinal(), GSE.v1_2_a.ordinal(),
							4,2,1);
		int max_path_len3 = Math.max(path_len31, Math.max(path_len32, path_len34));
		
		
		//formula path_len41 = n41?1:n42&n21|n43&n31?2:n42&n23&n31|n43&n32&n21?3:4; 1->4, 3->2, 4->3
		int path_len41 = getPathLengthX(GSE.v4_1_a.ordinal(),GSE.v4_2_a.ordinal(), GSE.v1_1_a.ordinal(), GSE.v1_2_a.ordinal(),
				GSE.v2_1_a.ordinal(), GSE.v2_2_a.ordinal(), GSE.v3_1_a.ordinal(), GSE.v3_2_a.ordinal(),
				1,2,3);
		

		//formula path_len42 = n42?1:n43&n32|n41&n12?2:n43&n31&n12|n41&n13&n32?3:4; 1->4, 4->1
		int path_len42 = getPathLengthX(GSE.v4_1_a.ordinal(),GSE.v4_2_a.ordinal(), GSE.v2_1_a.ordinal(), GSE.v2_2_a.ordinal(),
							GSE.v3_1_a.ordinal(), GSE.v3_2_a.ordinal(), GSE.v1_1_a.ordinal(), GSE.v1_2_a.ordinal(),
							2,3,1);
		//formula path_len12 = n12?1:n13&n32|n14&n42?2:n13&n34&n42|n14&n43&n32?3:4;
		//formula path_len43 = n43?1:n42&n23|n41&n13?2:n42&n21&n13|n41&n12&n23?3:4;
		int path_len43 = getPathLengthX(GSE.v4_1_a.ordinal(),GSE.v4_2_a.ordinal(), GSE.v3_1_a.ordinal(), GSE.v3_2_a.ordinal(),
							GSE.v2_1_a.ordinal(), GSE.v2_2_a.ordinal(), GSE.v1_1_a.ordinal(), GSE.v1_2_a.ordinal(),
							3,2,1);
		int max_path_len4 = Math.max(path_len41, Math.max(path_len42, path_len43));
		
		int max_path_len = Math.max(Math.max(max_path_len1, max_path_len2), Math.max(max_path_len3, max_path_len4));
		
		return max_path_len;
		
	}
	
	// label "done" = max_path_len<4;
	@Override
	public boolean isLabelTrue(int i) throws PrismException
	{
		switch (i) {
		case 0:
			if(getMaxPathLength() < 4) {
				return true;
			}else {
				return false;
			}
			
		
		
		default:
			throw new PrismException("Label number \"" + i + "\" not defined");
		}
	}


	@Override
	public List<String> getRewardStructNames()
	{
		return Arrays.asList("max_path_len","max_path_len_sq","rounds");
	}
	
	
	@Override
	public Double getStateReward(int r, State state) throws PrismException
	{
		/*
		 * // maximum path length
			rewards "max_path_len"
				true : max_path_len;
			endrewards
			// maximum path length squared (for computing variance)
			rewards "max_path_len_sq"
				true : max_path_len*max_path_len;
			endrewards

		 * 
		 */
		switch(r) {
			case 0:
				return (double)getMaxPathLength();

			case 1:
				double s = (double)getMaxPathLength();
				return s*s;

			default:
				return 0.0;
		}
		
	}
	
	@Override
	public Double getStateActionReward(int r, State state, Object action) throws PrismException
	{
		// rounds
		/*
		 * rewards "rounds"
			[start1] b1+b2+b3+b4=3 : 1;
			[start2] b1+b2+b3+b4=3 : 1;
			[start3] b1+b2+b3+b4=3 : 1;
			[start4] b1+b2+b3+b4=3 : 1;
		endrewards
		
		*/
		if(r == 2) {
			if(action == "start1" || action == "start2" || action == "start3" || action == "start4") {
				if( (returnIntValueById(GSE.b1.ordinal()) + returnIntValueById(GSE.b2.ordinal()) + returnIntValueById(GSE.b3.ordinal()) +
						returnIntValueById(GSE.b4.ordinal())) == 3) {
					return 1.0;
				}
			}
		}

		
		return 0.0;
	}
}

/*
// vars for scheduler, when set 1 to bi-th, it means schedule the i-th node
private int b1;
private int b2;
private int b3;
private int b4;

// each state of node
private int s1;
private int s2;
private int s3;
private int s4;

// initial view of node 1 (can see 2 one hop away)
private int v1_1_a = 2;
private int v1_2_a = 0;
private int v1_1_h = 1;
private int v1_2_h = 4;

// initial view of node 2 (empty)
private int v2_1_a = 0;
private int v2_2_a = 0;
private int v2_1_h = 4;
private int v2_2_h = 4;

// initial view of node 3 (can see 2 one hop away)
private int v3_1_a = 2;
private int v3_2_a = 0;
private int v3_1_h = 1;
private int v3_2_h = 4;

// initial view of node 4 (can see 2 one hop away)
private int v4_1_a = 2;
private int v4_2_a = 0;
private int v4_1_h = 1;
private int v4_2_h = 4;

// variables to store the information received before it is merged with the view

// hold temporary address for each node
private int a1; 
private int a2;
private int a3;
private int a4;

// hold temporary the hop used for each node
private int h1;
private int h2;
private int h3;
private int h4;

// next push when 1
private int i1;
private int i2;
private int i3;
private int i4;

*/