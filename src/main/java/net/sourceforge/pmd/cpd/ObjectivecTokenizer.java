package net.sourceforge.pmd.cpd;

import java.io.IOException;
import java.io.StringReader;

import net.sourceforge.pmd.cpd.SourceCode;
import net.sourceforge.pmd.cpd.TokenEntry;
import net.sourceforge.pmd.cpd.Tokenizer;
import net.sourceforge.pmd.cpd.Tokens;

import com.deadmeta4.cpd.generated.ObjCParser;
import com.deadmeta4.cpd.generated.Token;
import com.deadmeta4.cpd.generated.TokenMgrError;

public class ObjectivecTokenizer implements Tokenizer{
	
	public ObjectivecTokenizer(){
		super();
	}

	@Override
	public void tokenize( SourceCode sourceCode, Tokens tokenEntries ) throws IOException {
		
		StringBuffer buffer = sourceCode.getCodeBuffer();
		
		try {
			
			ObjCParser parser = new ObjCParser( new StringReader( buffer.toString() ) );

		    Token currentToken = parser.getNextToken();
		    while( currentToken.image.length() > 0 ){
		    	tokenEntries.add( new TokenEntry( currentToken.image, sourceCode.getFileName(), currentToken.beginLine ) );
		    	currentToken = (Token) parser.getNextToken();
		    }
		    
		    tokenEntries.add( TokenEntry.getEOF() );
		    System.out.println( "CPD Processing: " + sourceCode.getFileName() );
		    
		}catch( TokenMgrError err) {
			
			tokenEntries.add( TokenEntry.getEOF() );
			System.out.println( "CPD Error - Skipping " + sourceCode.getFileName() + " due to parse errors: " + err.getLocalizedMessage() );
		    err.printStackTrace();

		}
	}

}
