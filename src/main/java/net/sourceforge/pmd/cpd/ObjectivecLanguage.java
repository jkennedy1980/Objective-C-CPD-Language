package net.sourceforge.pmd.cpd;

import net.sourceforge.pmd.cpd.AbstractLanguage;
import net.sourceforge.pmd.cpd.Language;

public class ObjectivecLanguage extends AbstractLanguage implements Language{

	public ObjectivecLanguage(){
		super( new ObjectivecTokenizer(), ".h", ".m" );
	}

}
