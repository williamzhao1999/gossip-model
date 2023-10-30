package model;

import java.io.File;
import java.io.FileNotFoundException;

import prism.Prism;
import prism.PrismDevNullLog;
import prism.PrismException;
import prism.PrismLog;

public class Gossip {
	
	public static void main(String[] args)
	{
		new Gossip().run();
	}
	
	public void run()
	{
		try {
			// Create a log for PRISM output (hidden or stdout)
			PrismLog mainLog = new PrismDevNullLog();
			//PrismLog mainLog = new PrismFileLog("stdout");
			
			// Initialise PRISM engine 
			Prism prism = new Prism(mainLog);
			prism.initialise(); 
			prism.setEngine(Prism.HYBRID);


			// Create a model generator to specify the model that PRISM should build
			GossipModel modelGen = new GossipModel(4);
			

			// Load the model generator into PRISM,
			// export the model to a dot file (which triggers its construction)
			prism.loadModelGenerator(modelGen);
			prism.buildModel();
			prism.exportTransToFile(true, Prism.EXPORT_DOT_STATES, new File("mdp.dot"));
			


			String[] props = new String[] {
					"R{\"max_path_len_sq\"}min=?[I=100]",
					"R{\"max_path_len_sq\"}max=?[I=100]",
					"Pmin=? [ F<=100 \"done\" ]",
					"Pmax=? [ F<=100 \"done\" ]",
					"R{\"rounds\"}min=? [ F \"done\" ]",
					"R{\"rounds\"}max=? [ F \"done\" ]"
			};
			for (String prop : props) {
				System.out.println(prop + ":");
				System.out.println(prism.modelCheck(prop).getResult());
			}
			
			// Now check the first property again,
			// but this time export the optimal strategy ("adversary") too
			prism.setExportAdv(Prism.EXPORT_ADV_MDP);
			prism.setExportAdvFilename("adv.tra");


			//System.out.println(prism.modelCheck("Pmax=?[F \"target\"]").getResult());
			// Also export the MDP states and labels
			prism.exportStatesToFile(Prism.EXPORT_PLAIN, new File("adv.sta"));
			prism.exportLabelsToFile(null, Prism.EXPORT_PLAIN, new File("adv.lab"));
			
			//prism.getBuiltModelExplicit().exportToPrismLanguage("model.prism");
			

			// Then load it back in, re-verify the property (as a sanity check)
			// and export the induced model as a dot file
			// (really, the induced model is a DTMC, but we keep it as an MDP
			// so that we can see the action labels taken in the optimal strategy)
			//prism.setEngine(Prism.HYBRID);
			//prism.loadModelFromExplicitFiles(new File("adv.sta"), new File("adv.tra"), new File("adv.lab"), null, ModelType.MDP);
			
			//System.out.println(prism.modelCheck("Pmax=?[F \"target\"]").getResult());
			//prism.exportTransToFile(true, Prism.EXPORT_DOT_STATES, new File("adv.dot"));
			//System.out.println("Exporting Model:");
			

			// Close down PRISM
			prism.closeDown();

		} catch (FileNotFoundException e) {
			System.out.println("Error: " + e.getMessage());
			System.exit(1);
		} catch (PrismException e) {
			System.out.println("Error: " + e.getMessage());
			System.exit(1);
		}
	}
	

}


